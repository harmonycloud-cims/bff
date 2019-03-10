package com.harmonycloud.service;


import com.github.jedis.lock.JedisLock;
import com.harmonycloud.bo.AttendingDiagnosisBo;
import com.harmonycloud.bo.ChronicDiagnosisBo;
import com.harmonycloud.bo.ClinicalNoteBo;
import com.harmonycloud.bo.UserPrincipal;
import com.harmonycloud.config.BffConfigurationProperties;
import com.harmonycloud.dto.ResponseDto;
import com.harmonycloud.entity.AttendingDiagnosis;
import com.harmonycloud.entity.ChronicDiagnosis;
import com.harmonycloud.entity.ClinicalNote;
import com.harmonycloud.result.Result;

import com.harmonycloud.vo.NoteDiagnosisVo;
import com.harmonycloud.vo.NoteDiagnosis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${redis.url}")
    private String REDIS_URL;
    @Value("${redis.port}")
    private int REDIS_PORT;

    public Result save(NoteDiagnosis noteDiagnosis) {
        ResponseDto clinicalNoteResponse = null;
        ResponseDto attendingResponse = null;
        ResponseDto chronicResponse = null;
        ClinicalNote clinicalNote = noteDiagnosis.getClinicalNote();

        List<AttendingDiagnosis> attendingDiagnosisList = noteDiagnosis.getAttendingDiagnosisList();
        List<ChronicDiagnosis> chronicDiagnosisList = noteDiagnosis.getChronicDiagnosisList();

        Jedis jedis = new Jedis(REDIS_URL, REDIS_PORT);
        JedisLock lock = new JedisLock(jedis, "test", 10000, 20000);
        try {
            if (jedis.exists("test")) {
                throw new InterruptedException("save error");
            } else {
                lock.acquire();
                UserPrincipal userDetails = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
                String token = userDetails.getToken();
                Date date = new Date();

                clinicalNote.setCreateBy(userDetails.getUsername());
                clinicalNote.setCreateDate(date);
                clinicalNoteResponse = syncService.save(config.getSaveClinicalNoteUri(), token, clinicalNote);
                attendingResponse = syncService.save(config.getSaveAttendingDiagnosisUri(), token, attendingDiagnosisList);
                chronicResponse = syncService.save(config.getSaveChronicDiagnosisUri(), token, chronicDiagnosisList);
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


    public Result update(NoteDiagnosisVo noteDiagnosisVo) {

        ResponseDto clinicalNoteResponse = null;
        ResponseDto attendingResponse = null;
        ResponseDto chronicResponse = null;
        ClinicalNote oldClinicalNote = noteDiagnosisVo.getOldClinicalNote();
        ClinicalNote newClinicalNote = noteDiagnosisVo.getNewClinicalNote();
        List<AttendingDiagnosis> newAttendingDiagnosisList = noteDiagnosisVo.getNewAttendingDiagnosisList();
        List<AttendingDiagnosis> oldAttendingDiagnosisList = noteDiagnosisVo.getOldAttendingDiagnosisList();
        List<ChronicDiagnosis> newChronicDiagnosisList = noteDiagnosisVo.getNewChronicDiagnosisList();
        List<ChronicDiagnosis> oldChronicDiagnosisList = noteDiagnosisVo.getOldChronicDiagnosisList();

        Jedis jedis = new Jedis(REDIS_URL,REDIS_PORT);
        JedisLock lock = new JedisLock(jedis, "test", 10000, 20000);
        try {
            if (jedis.exists("test")) {
                throw new InterruptedException("update error");
            } else {
                lock.acquire();
                UserPrincipal userDetails = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
                String token = userDetails.getToken();
                Date date = new Date();

                newClinicalNote.setCreateBy(userDetails.getUsername());
                newClinicalNote.setCreateDate(date);

                ClinicalNoteBo clinicalNoteBo = new ClinicalNoteBo(newClinicalNote, oldClinicalNote);
                clinicalNoteResponse = syncService.save(config.getUpdateClinicalNoteUri(), token, clinicalNoteBo);

                AttendingDiagnosisBo attendingDiagnosisBo = new AttendingDiagnosisBo(oldAttendingDiagnosisList, newAttendingDiagnosisList);
                attendingResponse = syncService.save(config.getUpdateAttendingDiagnosisUri(), token, attendingDiagnosisBo);

                ChronicDiagnosisBo chronicDiagnosisBo = new ChronicDiagnosisBo(oldChronicDiagnosisList, newChronicDiagnosisList);
                chronicResponse = syncService.save(config.getUpdateChronicDiagnosisUri(), token, chronicDiagnosisBo);
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
}
