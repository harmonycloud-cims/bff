package com.harmonycloud.service;


import com.github.jedis.lock.JedisLock;
import com.harmonycloud.bo.*;
import com.harmonycloud.config.BffConfigurationProperties;
import com.harmonycloud.dto.*;
import com.harmonycloud.entity.*;
import com.harmonycloud.enums.ErrorMsgEnum;
import com.harmonycloud.exception.BffException;
import com.harmonycloud.result.CimsResponseWrapper;

import com.harmonycloud.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

@Service
public class BffService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SyncService syncService;

    @Autowired
    private BffConfigurationProperties config;
    @Autowired
    private HttpServletRequest request;

    @Value("${cims.redis.url}")
    private String SPRING_REDIS_URL;
    @Value("${cims.redis.port}")
    private int SPRING_REDIS_PORT;

    /**
     * save clinical note and diagnosis
     *
     * @param dto model
     * @return
     * @throws Exception
     */
    public void saveNoteDiagnosis(NoteDiagnosisUpdateDto dto) throws Exception {
        String msg = LogUtil.getRequest(request) + ", information='";

        CimsResponseWrapper responseDto = null;
        ClinicalNote clinicalNote = dto.getClinicalNote();
        List<AttendingDiagnosis> attendingDiagnosisList = dto.getAttendingDiagnosisList();
        List<ChronicDiagnosis> chronicDiagnosisList = dto.getChronicDiagnosisList();
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
            logger.info(msg + "save note and diagnosis locked '");
            try {
                //save clinical note
                responseDto = syncService.save(config.getSaveClinicalNoteUri(), token, clinicalNote);
                if (!responseDto.isSuccess()) {
                    throw new BffException(ErrorMsgEnum.CLINICAL_SAVE_ERROR.getMessage());
                }
                logger.info(msg + "Save clinical note success '");

                //save attending diagnosis
                responseDto = syncService.save(config.getSaveAttendingDiagnosisUri(), token, attendingDiagnosisList);
                if (!responseDto.isSuccess()) {
                    throw new BffException(ErrorMsgEnum.DIAGNOSIS_SAVE_ERROR.getMessage());
                }
                logger.info(msg + "Save attending diagnosis success '");

                //save chronic diagnosis
                responseDto = syncService.save(config.getSaveChronicDiagnosisUri(), token, chronicDiagnosisList);
                if (!responseDto.isSuccess()) {
                    throw new BffException(ErrorMsgEnum.DIAGNOSIS_SAVE_ERROR.getMessage());
                }
                logger.info(msg + "Save chronic diagnosis success '");

            } catch (URISyntaxException e) {
                e.printStackTrace();
                throw new IllegalStateException("URI excepstion.");
            } finally {
                lock.release();
                logger.info(msg + "save note and diagnosis lock release '");

            }
        }

    }

    /**
     * update clinical note and diagnosis
     *
     * @param noteDiagnosisDto model
     * @return
     * @throws Exception
     */
    public void updateNoteDiagnosis(NoteDiagnosisDto noteDiagnosisDto) throws Exception {
        String msg = LogUtil.getRequest(request) + ", information='";

        CimsResponseWrapper responseDto = null;
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
            logger.info(msg + "Update note and diagnosis locked '");

            try {
                newClinicalNote.setCreateBy(userDetails.getUsername());
                newClinicalNote.setCreateDate(new Date());

                //update clinical note
                ClinicalNoteBo clinicalNoteBo = new ClinicalNoteBo(newClinicalNote, oldClinicalNote);
                responseDto = syncService.save(config.getUpdateClinicalNoteUri(), token, clinicalNoteBo);
                if (!responseDto.isSuccess()) {
                    throw new BffException(ErrorMsgEnum.CLINICAL_UPDATE_ERROR.getMessage());
                }
                logger.info(msg + "Update clinical note success '");


                //update attending diagnosis
                AttendingDiagnosisBo attendingDiagnosisBo = new AttendingDiagnosisBo(newAttendingDiagnosisList, oldAttendingDiagnosisList);
                responseDto = syncService.save(config.getUpdateAttendingDiagnosisUri(), token, attendingDiagnosisBo);
                if (!responseDto.isSuccess()) {
                    throw new BffException(ErrorMsgEnum.DIAGNOSIS_UPDATE_ERROR.getMessage());
                }
                logger.info(msg + "Update attending diagnosis success '");

                //update chronic diagnosis
                ChronicDiagnosisBo chronicDiagnosisBo = new ChronicDiagnosisBo(newChronicDiagnosisList, oldChronicDiagnosisList);
                responseDto = syncService.save(config.getUpdateChronicDiagnosisUri(), token, chronicDiagnosisBo);
                if (!responseDto.isSuccess()) {
                    throw new BffException(ErrorMsgEnum.DIAGNOSIS_UPDATE_ERROR.getMessage());
                }
                logger.info(msg + "Update chronic diagnosis success '");

            } catch (URISyntaxException e) {
                e.printStackTrace();
                throw new IllegalStateException("URI excepstion.");
            } finally {
                lock.release();
                logger.info(msg + "save note and diagnosis lock release '");

            }
        }

    }


    /**
     * save/update clinical note 、diagnosis and prescription
     *
     * @param noteDiagnosisDrugDto model
     * @return
     * @throws Exception
     */
    public void nextPatient(NoteDiagnosisDrugDto noteDiagnosisDrugDto) throws Exception {
        String msg = LogUtil.getRequest(request) + ", information='";

        ClinicalNote oldClinicalNote = noteDiagnosisDrugDto.getOldClinicalNote();
        ClinicalNote newClinicalNote = noteDiagnosisDrugDto.getNewClinicalNote();
        ClinicalNoteBo clinicalNoteBo = new ClinicalNoteBo(newClinicalNote, oldClinicalNote);

        List<AttendingDiagnosis> newAttendingDiagnosisList = noteDiagnosisDrugDto.getNewAttendingDiagnosisList();
        AttendingDiagnosisBo attendingDiagnosisBo = new AttendingDiagnosisBo(newAttendingDiagnosisList, noteDiagnosisDrugDto.getOldAttendingDiagnosisList());
        List<ChronicDiagnosis> newChronicDiagnosisList = noteDiagnosisDrugDto.getNewChronicDiagnosisList();
        ChronicDiagnosisBo chronicDiagnosisBo = new ChronicDiagnosisBo(newChronicDiagnosisList, noteDiagnosisDrugDto.getOldChronicDiagnosisList());

        Prescription prescription = noteDiagnosisDrugDto.getPrescription();
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
            logger.info(msg + "Save note 、 diagnosis and prescription locked '");

            try {
                if (oldClinicalNote == null) {
                    if (prescription.getPrescriptionId() == null) {
                        //save note and diagnosis
                        if (!syncService.save(config.getSaveClinicalNoteUri(), token, newClinicalNote).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.CLINICAL_SAVE_ERROR.getMessage());
                        }
                        if (!syncService.save(config.getSaveAttendingDiagnosisUri(), token, newAttendingDiagnosisList).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.DIAGNOSIS_SAVE_ERROR.getMessage());
                        }
                        if (!syncService.save(config.getSaveChronicDiagnosisUri(), token, newChronicDiagnosisList).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.DIAGNOSIS_SAVE_ERROR.getMessage());
                        }
                        //save prescirption
                        if (!syncService.save(config.getSavePrescriptionUri(), token, prescriptionBo).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.ORDER_SAVE_ERROR.getMessage());
                        }
                    } else {
                        //save note and diagnosis
                        if (!syncService.save(config.getSaveClinicalNoteUri(), token, newClinicalNote).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.CLINICAL_SAVE_ERROR.getMessage());
                        }
                        if (!syncService.save(config.getSaveAttendingDiagnosisUri(), token, newAttendingDiagnosisList).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.DIAGNOSIS_SAVE_ERROR.getMessage());
                        }
                        if (!syncService.save(config.getSaveChronicDiagnosisUri(), token, newChronicDiagnosisList).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.DIAGNOSIS_SAVE_ERROR.getMessage());
                        }
                        //update prescirption
                        if (!syncService.save(config.getUpdatePrescriptionUri(), token, prescriptionDrugBo).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.ORDER_UPDATE_ERROR.getMessage());
                        }
                    }
                } else {
                    if (prescription.getPrescriptionId() == null) {

                        //update note and diagnosis
                        if (!syncService.save(config.getUpdateClinicalNoteUri(), token, clinicalNoteBo).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.CLINICAL_UPDATE_ERROR.getMessage());
                        }
                        if (!syncService.save(config.getUpdateAttendingDiagnosisUri(), token, attendingDiagnosisBo).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.DIAGNOSIS_UPDATE_ERROR.getMessage());
                        }
                        if (!syncService.save(config.getUpdateChronicDiagnosisUri(), token, chronicDiagnosisBo).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.DIAGNOSIS_UPDATE_ERROR.getMessage());
                        }
                        //save prescirption
                        if (!syncService.save(config.getSavePrescriptionUri(), token, prescriptionBo).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.ORDER_SAVE_ERROR.getMessage());
                        }
                    } else {

                        //update note and diagnosis
                        if (!syncService.save(config.getUpdateClinicalNoteUri(), token, clinicalNoteBo).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.CLINICAL_UPDATE_ERROR.getMessage());
                        }

                        if (!syncService.save(config.getUpdateAttendingDiagnosisUri(), token, attendingDiagnosisBo).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.DIAGNOSIS_UPDATE_ERROR.getMessage());
                        }

                        if (!syncService.save(config.getUpdateChronicDiagnosisUri(), token, chronicDiagnosisBo).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.DIAGNOSIS_UPDATE_ERROR.getMessage());
                        }
                        //update prescirption
                        if (!syncService.save(config.getUpdatePrescriptionUri(), token, prescriptionDrugBo).isSuccess()) {
                            throw new BffException(ErrorMsgEnum.ORDER_UPDATE_ERROR.getMessage());
                        }
                    }
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
                throw new IllegalStateException("URI excepstion.");
            } finally {
                lock.release();
                logger.info(msg + "Save note 、 diagnosis and prescription lock release '");

            }
        }
    }
}
