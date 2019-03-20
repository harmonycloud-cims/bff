package com.harmonycloud.service;


import com.github.jedis.lock.JedisLock;
import com.harmonycloud.bo.*;
import com.harmonycloud.config.BffConfigurationProperties;
import com.harmonycloud.dto.*;
import com.harmonycloud.entity.*;
import com.harmonycloud.enums.ErrorMsgEnum;
import com.harmonycloud.exception.BffException;
import com.harmonycloud.exception.BffException;
import com.harmonycloud.result.CimsResponseWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

@Service
public class BffService {

    @Autowired
    private SyncService syncService;

    @Autowired
    private BffConfigurationProperties config;

    @Value("${cims.redis.url}")
    private String SPRING_REDIS_URL;
    @Value("${cims.redis.port}")
    private int SPRING_REDIS_PORT;

    /**
     * save clinical note and diagnosis
     *
     * @param noteDiagnosisBo model
     * @param forwardHeaders  headers
     * @return
     * @throws Exception
     */
    public CimsResponseWrapper<String> saveNoteDiagnosis(NoteDiagnosisBo noteDiagnosisBo, HttpHeaders forwardHeaders) throws Exception {
        ResponseDto responseDto = null;
        ClinicalNote clinicalNote = noteDiagnosisBo.getClinicalNote();
        List<AttendingDiagnosis> attendingDiagnosisList = noteDiagnosisBo.getAttendingDiagnosisList();
        List<ChronicDiagnosis> chronicDiagnosisList = noteDiagnosisBo.getChronicDiagnosisList();
        UserPrincipal userDetails = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String token = userDetails.getToken();

        String encounterId = clinicalNote.getEncounterId().toString();

        Jedis jedis = new Jedis(SPRING_REDIS_URL, SPRING_REDIS_PORT);
        JedisLock lock = new JedisLock(jedis, encounterId, 1000, 10000);

        //lock function
        if (jedis.exists(encounterId)) {
            throw new BffException(ErrorMsgEnum.LOCKD.getMessage());
        } else {
            lock.acquire();
            try {
                //save clinical note
                responseDto = syncService.save(config.getSaveClinicalNoteUri(), token, clinicalNote, forwardHeaders);
                if (!responseDto.isSuccess()) {
                    throw new BffException(ErrorMsgEnum.CLINICAL_SAVE_ERROR.getMessage());
                }
                //save attending diagnosis
                responseDto = syncService.save(config.getSaveAttendingDiagnosisUri(), token, attendingDiagnosisList, forwardHeaders);
                if (!responseDto.isSuccess()) {
                    throw new BffException(ErrorMsgEnum.DIAGNOSIS_SAVE_ERROR.getMessage());
                }
                //save chronic diagnosis
                responseDto = syncService.save(config.getSaveChronicDiagnosisUri(), token, chronicDiagnosisList, forwardHeaders);
                if (!responseDto.isSuccess()) {
                    throw new BffException(ErrorMsgEnum.DIAGNOSIS_SAVE_ERROR.getMessage());
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
                throw new IllegalStateException("URI excepstion.");
            } finally {
                lock.release();
            }
        }


        return new CimsResponseWrapper<String>(true, null, "Save success");
    }

    /**
     * update clinical note and diagnosis
     *
     * @param noteDiagnosisDto model
     * @param forwardHeaders   headers
     * @return
     * @throws Exception
     */
    public CimsResponseWrapper<String> updateNoteDiagnosis(NoteDiagnosisDto noteDiagnosisDto, HttpHeaders forwardHeaders) throws Exception {
        ResponseDto responseDto = null;
        ClinicalNote oldClinicalNote = noteDiagnosisDto.getOldClinicalNote();
        ClinicalNote newClinicalNote = noteDiagnosisDto.getNewClinicalNote();
        List<AttendingDiagnosis> newAttendingDiagnosisList = noteDiagnosisDto.getNewAttendingDiagnosisList();
        List<AttendingDiagnosis> oldAttendingDiagnosisList = noteDiagnosisDto.getOldAttendingDiagnosisList();
        List<ChronicDiagnosis> newChronicDiagnosisList = noteDiagnosisDto.getNewChronicDiagnosisList();
        List<ChronicDiagnosis> oldChronicDiagnosisList = noteDiagnosisDto.getOldChronicDiagnosisList();

        UserPrincipal userDetails = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String token = userDetails.getToken();

        String encounterId = oldClinicalNote.getEncounterId().toString();
        Jedis jedis = new Jedis(SPRING_REDIS_URL, SPRING_REDIS_PORT);
        JedisLock lock = new JedisLock(jedis, encounterId, 10000, 20000);

        if (jedis.exists(encounterId)) {
            throw new BffException(ErrorMsgEnum.LOCKD.getMessage());
        } else {
            lock.acquire();
            try {
                newClinicalNote.setCreateBy(userDetails.getUsername());
                newClinicalNote.setCreateDate(new Date());

                //update clinical note
                ClinicalNoteBo clinicalNoteBo = new ClinicalNoteBo(newClinicalNote, oldClinicalNote);
                responseDto = syncService.save(config.getUpdateClinicalNoteUri(), token, clinicalNoteBo, forwardHeaders);
                if (!responseDto.isSuccess()) {
                    throw new BffException(ErrorMsgEnum.CLINICAL_UPDATE_ERROR.getMessage());
                }

                //update attending diagnosis
                AttendingDiagnosisBo attendingDiagnosisBo = new AttendingDiagnosisBo(newAttendingDiagnosisList, oldAttendingDiagnosisList);
                responseDto = syncService.save(config.getUpdateAttendingDiagnosisUri(), token, attendingDiagnosisBo, forwardHeaders);
                if (!responseDto.isSuccess()) {
                    throw new BffException(ErrorMsgEnum.DIAGNOSIS_UPDATE_ERROR.getMessage());
                }

                //update chronic diagnosis
                ChronicDiagnosisBo chronicDiagnosisBo = new ChronicDiagnosisBo(newChronicDiagnosisList, oldChronicDiagnosisList);
                responseDto = syncService.save(config.getUpdateChronicDiagnosisUri(), token, chronicDiagnosisBo, forwardHeaders);
                if (!responseDto.isSuccess()) {
                    throw new BffException(ErrorMsgEnum.DIAGNOSIS_UPDATE_ERROR.getMessage());
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
                throw new IllegalStateException("URI excepstion.");
            } finally {
                lock.release();
            }
        }


        return new CimsResponseWrapper<String>(true, null, "Update success");
    }


    /**
     * save/update clinical note 、diagnosis and prescription
     *
     * @param noteDiagnosisDrugDto model
     * @param forwardHeaders       headers
     * @return
     * @throws Exception
     */
    public CimsResponseWrapper<String> nextPatient(NoteDiagnosisDrugDto noteDiagnosisDrugDto, HttpHeaders forwardHeaders) throws Exception {
        ClinicalNote oldClinicalNote = noteDiagnosisDrugDto.getOldClinicalNote();
        ClinicalNote newClinicalNote = noteDiagnosisDrugDto.getNewClinicalNote();
        ClinicalNoteBo clinicalNoteBo = new ClinicalNoteBo(newClinicalNote, oldClinicalNote);

        List<AttendingDiagnosis> newAttendingDiagnosisList = noteDiagnosisDrugDto.getNewAttendingDiagnosisList();
        AttendingDiagnosisBo attendingDiagnosisBo = new AttendingDiagnosisBo(newAttendingDiagnosisList, noteDiagnosisDrugDto.getOldAttendingDiagnosisList());
        List<ChronicDiagnosis> newChronicDiagnosisList = noteDiagnosisDrugDto.getNewChronicDiagnosisList();
        ChronicDiagnosisBo chronicDiagnosisBo = new ChronicDiagnosisBo(newChronicDiagnosisList, noteDiagnosisDrugDto.getOldChronicDiagnosisList());

        Prescription prescription = noteDiagnosisDrugDto.getOldPrescription();
        List<PrescriptionDrug> oldPrescriptionDrugList = noteDiagnosisDrugDto.getOldPrescriptionDrugList();
        List<PrescriptionDrug> newPrescriptionDrugList = noteDiagnosisDrugDto.getNewPrescriptionDrugList();
        PrescriptionBo prescriptionBo = new PrescriptionBo(prescription, newPrescriptionDrugList);
        PrescriptionDrugBo prescriptionDrugBo = new PrescriptionDrugBo(prescription, oldPrescriptionDrugList, newPrescriptionDrugList);

        UserPrincipal userDetails = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String token = userDetails.getToken();

        String encounterId = newClinicalNote.getEncounterId().toString();

        Jedis jedis = new Jedis(SPRING_REDIS_URL, SPRING_REDIS_PORT);

        JedisLock lock = new JedisLock(jedis, encounterId, 1000, 10000);


        if (jedis.exists(encounterId)) {
            throw new BffException(ErrorMsgEnum.LOCKD.getMessage());
        } else {
            lock.acquire();
            try {
                if (oldClinicalNote == null) {
                    if (prescription.getPrescriptionId() == null) {
                        //save note and diagnosis
                        if (!syncService.save(config.getSaveClinicalNoteUri(), token, newClinicalNote, forwardHeaders).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.CLINICAL_SAVE_ERROR.getMessage());
                        }
                        if (!syncService.save(config.getSaveAttendingDiagnosisUri(), token, newAttendingDiagnosisList, forwardHeaders).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.DIAGNOSIS_SAVE_ERROR.getMessage());
                        }
                        if (!syncService.save(config.getSaveChronicDiagnosisUri(), token, newChronicDiagnosisList, forwardHeaders).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.DIAGNOSIS_SAVE_ERROR.getMessage());
                        }
                        //save prescirption
                        if (!syncService.save(config.getSavePrescriptionUri(), token, prescriptionBo, forwardHeaders).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.ORDER_SAVE_ERROR.getMessage());
                        }
                    } else {
                        //save note and diagnosis
                        if (!syncService.save(config.getSaveClinicalNoteUri(), token, newClinicalNote, forwardHeaders).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.CLINICAL_SAVE_ERROR.getMessage());
                        }
                        if (!syncService.save(config.getSaveAttendingDiagnosisUri(), token, newAttendingDiagnosisList, forwardHeaders).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.DIAGNOSIS_SAVE_ERROR.getMessage());
                        }
                        if (!syncService.save(config.getSaveChronicDiagnosisUri(), token, newChronicDiagnosisList, forwardHeaders).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.DIAGNOSIS_SAVE_ERROR.getMessage());
                        }
                        //update prescirption
                        if (!syncService.save(config.getUpdatePrescriptionUri(), token, prescriptionDrugBo, forwardHeaders).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.ORDER_UPDATE_ERROR.getMessage());
                        }
                    }
                } else {
                    if (prescription.getPrescriptionId() == null) {

                        //update note and diagnosis
                        if (!syncService.save(config.getUpdateClinicalNoteUri(), token, clinicalNoteBo, forwardHeaders).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.CLINICAL_UPDATE_ERROR.getMessage());
                        }
                        if (!syncService.save(config.getUpdateAttendingDiagnosisUri(), token, attendingDiagnosisBo, forwardHeaders).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.DIAGNOSIS_UPDATE_ERROR.getMessage());
                        }
                        if (!syncService.save(config.getUpdateChronicDiagnosisUri(), token, chronicDiagnosisBo, forwardHeaders).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.DIAGNOSIS_UPDATE_ERROR.getMessage());
                        }
                        //save prescirption
                        if (!syncService.save(config.getSavePrescriptionUri(), token, prescriptionBo, forwardHeaders).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.ORDER_SAVE_ERROR.getMessage());
                        }
                    } else {

                        //update note and diagnosis
                        if (!syncService.save(config.getUpdateClinicalNoteUri(), token, clinicalNoteBo, forwardHeaders).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.CLINICAL_UPDATE_ERROR.getMessage());
                        }

                        if (!syncService.save(config.getUpdateAttendingDiagnosisUri(), token, attendingDiagnosisBo, forwardHeaders).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.DIAGNOSIS_UPDATE_ERROR.getMessage());
                        }

                        if (!syncService.save(config.getUpdateChronicDiagnosisUri(), token, chronicDiagnosisBo, forwardHeaders).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.DIAGNOSIS_UPDATE_ERROR.getMessage());
                        }
                        //update prescirption
                        if (!syncService.save(config.getUpdatePrescriptionUri(), token, prescriptionDrugBo, forwardHeaders).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.ORDER_UPDATE_ERROR.getMessage());
                        }
                    }
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
                throw new IllegalStateException("URI excepstion.");
            } finally {
                lock.release();
            }
        }
        return new CimsResponseWrapper<>(true, null, "Success");
    }
}
