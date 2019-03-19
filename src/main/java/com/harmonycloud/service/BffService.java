package com.harmonycloud.service;


import com.github.jedis.lock.JedisLock;
import com.harmonycloud.bo.*;
import com.harmonycloud.config.BffConfigurationProperties;
import com.harmonycloud.dto.*;
import com.harmonycloud.entity.*;
import com.harmonycloud.result.CimsResponseWrapper;
import com.harmonycloud.result.Result;

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

    public Result saveNoteDiagnosis(NoteDiagnosisBo noteDiagnosisBo, HttpHeaders forwardHeaders) throws Exception {

        ClinicalNote clinicalNote = noteDiagnosisBo.getClinicalNote();

        List<AttendingDiagnosis> attendingDiagnosisList = noteDiagnosisBo.getAttendingDiagnosisList();
        List<ChronicDiagnosis> chronicDiagnosisList = noteDiagnosisBo.getChronicDiagnosisList();
        String encounterId = clinicalNote.getEncounterId().toString();

        Jedis jedis = new Jedis(SPRING_REDIS_URL, SPRING_REDIS_PORT);
        JedisLock lock = new JedisLock(jedis, encounterId, 10000, 20000);
        try {
            if (jedis.exists(encounterId)) {
                throw new InterruptedException("locked");
            } else {
                lock.acquire();
                UserPrincipal userDetails = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
                String token = userDetails.getToken();


                syncService.save(config.getSaveClinicalNoteUri(), token, clinicalNote, forwardHeaders);
                syncService.save(config.getSaveAttendingDiagnosisUri(), token, attendingDiagnosisList, forwardHeaders);
                syncService.save(config.getSaveChronicDiagnosisUri(), token, chronicDiagnosisList, forwardHeaders);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new IllegalStateException("The clinical note has been updated by another user");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new IllegalStateException("URI excepstion.");
        } finally {
            lock.release();
        }


        return Result.buildSuccess("save success");
    }


    public Result updateNoteDiagnosis(NoteDiagnosisDto noteDiagnosisDto, HttpHeaders forwardHeaders) throws Exception {

        ClinicalNote oldClinicalNote = noteDiagnosisDto.getOldClinicalNote();
        ClinicalNote newClinicalNote = noteDiagnosisDto.getNewClinicalNote();
        List<AttendingDiagnosis> newAttendingDiagnosisList = noteDiagnosisDto.getNewAttendingDiagnosisList();
        List<AttendingDiagnosis> oldAttendingDiagnosisList = noteDiagnosisDto.getOldAttendingDiagnosisList();
        List<ChronicDiagnosis> newChronicDiagnosisList = noteDiagnosisDto.getNewChronicDiagnosisList();
        List<ChronicDiagnosis> oldChronicDiagnosisList = noteDiagnosisDto.getOldChronicDiagnosisList();

        String encounterId = oldClinicalNote.getEncounterId().toString();
        Jedis jedis = new Jedis(SPRING_REDIS_URL, SPRING_REDIS_PORT);
        JedisLock lock = new JedisLock(jedis, encounterId, 10000, 20000);
        try {
            if (jedis.exists(encounterId)) {
                throw new InterruptedException("locked");
            } else {
                lock.acquire();
                UserPrincipal userDetails = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
                String token = userDetails.getToken();
                Date date = new Date();

                newClinicalNote.setCreateBy(userDetails.getUsername());
                newClinicalNote.setCreateDate(date);

                ClinicalNoteBo clinicalNoteBo = new ClinicalNoteBo(newClinicalNote, oldClinicalNote);
                syncService.save(config.getUpdateClinicalNoteUri(), token, clinicalNoteBo, forwardHeaders);

                AttendingDiagnosisBo attendingDiagnosisBo = new AttendingDiagnosisBo(newAttendingDiagnosisList, oldAttendingDiagnosisList);
                syncService.save(config.getUpdateAttendingDiagnosisUri(), token, attendingDiagnosisBo, forwardHeaders);

                ChronicDiagnosisBo chronicDiagnosisBo = new ChronicDiagnosisBo(newChronicDiagnosisList, oldChronicDiagnosisList);
                syncService.save(config.getUpdateChronicDiagnosisUri(), token, chronicDiagnosisBo, forwardHeaders);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new IllegalStateException("The clinical note has been updated by another user");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new IllegalStateException("URI excepstion.");
        } finally {
            lock.release();
        }
        return Result.buildSuccess("update success");

    }


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

        String encounterId = newClinicalNote.getEncounterId().toString();

        Jedis jedis = new Jedis(SPRING_REDIS_URL, SPRING_REDIS_PORT);
        JedisLock lock = new JedisLock(jedis, encounterId, 10000, 20000);
        try {
            if (jedis.exists(encounterId)) {
                throw new InterruptedException("locked");
            } else {
                lock.acquire();
                UserPrincipal userDetails = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
                String token = userDetails.getToken();


                if (oldClinicalNote == null) {
                    if (prescription.getCreateBy() == null) {
                        //save note and diagnosis
                        syncService.save(config.getSaveClinicalNoteUri(), token, newClinicalNote, forwardHeaders);
                        syncService.save(config.getSaveAttendingDiagnosisUri(), token, newAttendingDiagnosisList, forwardHeaders);
                        syncService.save(config.getSaveChronicDiagnosisUri(), token, newChronicDiagnosisList, forwardHeaders);
                        //save prescirption
                        syncService.save(config.getSavePrescriptionUri(), token, prescriptionBo, forwardHeaders);
                    } else {
                        //save note and diagnosis
                        syncService.save(config.getSaveClinicalNoteUri(), token, newClinicalNote, forwardHeaders);
                        syncService.save(config.getSaveAttendingDiagnosisUri(), token, newAttendingDiagnosisList, forwardHeaders);
                        syncService.save(config.getSaveChronicDiagnosisUri(), token, newChronicDiagnosisList, forwardHeaders);
                        //update prescirption
                        syncService.save(config.getUpdatePrescriptionUri(), token, prescriptionDrugBo, forwardHeaders);
                    }
                } else {
                    if (prescription.getCreateBy() == null) {
                        //update note and diagnosis
                        syncService.save(config.getUpdateClinicalNoteUri(), token, clinicalNoteBo, forwardHeaders);
                        syncService.save(config.getUpdateAttendingDiagnosisUri(), token, attendingDiagnosisBo, forwardHeaders);
                        syncService.save(config.getUpdateChronicDiagnosisUri(), token, chronicDiagnosisBo, forwardHeaders);
                        //save prescirption
                        syncService.save(config.getSavePrescriptionUri(), token, prescriptionBo, forwardHeaders);
                    } else {
                        //update note and diagnosis
                        syncService.save(config.getUpdateClinicalNoteUri(), token, clinicalNoteBo, forwardHeaders);
                        syncService.save(config.getUpdateAttendingDiagnosisUri(), token, attendingDiagnosisBo, forwardHeaders);
                        syncService.save(config.getUpdateChronicDiagnosisUri(), token, chronicDiagnosisBo, forwardHeaders);
                        //update prescirption
                        syncService.save(config.getUpdatePrescriptionUri(), token, prescriptionDrugBo, forwardHeaders);

                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new IllegalStateException("The clinical note has been updated by another user");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new IllegalStateException("URI excepstion.");
        } finally {
            lock.release();
        }
        return new CimsResponseWrapper<>(true, null, "Success");
    }


}
