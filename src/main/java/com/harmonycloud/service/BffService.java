package com.harmonycloud.service;

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
import org.apache.servicecomb.saga.omega.context.annotations.SagaStart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

@Service
public class BffService {

    @Autowired
    private SyncService syncService;

    @Autowired
    private BffConfigurationProperties config;

    @SagaStart
    public Result save(NoteDiagnosis noteDiagnosis) {
        ResponseDto clinicalNoteResponse = null;
        ResponseDto attendingResponse = null;
        ResponseDto chronicResponse = null;
        ClinicalNote clinicalNote = noteDiagnosis.getClinicalNote();

        List<AttendingDiagnosis> attendingDiagnosisList = noteDiagnosis.getAttendingDiagnosisList();
        List<ChronicDiagnosis> chronicDiagnosisList = noteDiagnosis.getChronicDiagnosisList();
        try {
            UserPrincipal userDetails = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String token = userDetails.getToken();
            Date date = new Date();

            clinicalNote.setCreateBy(userDetails.getUsername());
            clinicalNote.setCreateDate(date);

            clinicalNoteResponse = syncService.save(config.getSaveClinicalNoteUri(), token, clinicalNote);

            attendingResponse = syncService.save(config.getSaveAttendingDiagnosisUri(), token, attendingDiagnosisList);

            chronicResponse = syncService.save(config.getSaveChronicDiagnosisUri(), token, chronicDiagnosisList);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new IllegalStateException("URI excepstion.");
        }
        return Result.buildSuccess(null);
    }


    @SagaStart
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
        try {
            UserPrincipal userDetails = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String token = userDetails.getToken();
            Date date = new Date();

            ClinicalNoteBo clinicalNoteBo = new ClinicalNoteBo(oldClinicalNote, newClinicalNote);
            clinicalNoteResponse = syncService.save(config.getSaveClinicalNoteUri(), token, clinicalNoteBo);

            AttendingDiagnosisBo attendingDiagnosisBo = new AttendingDiagnosisBo(oldAttendingDiagnosisList, newAttendingDiagnosisList);
            attendingResponse = syncService.save(config.getSaveAttendingDiagnosisUri(), token, attendingDiagnosisBo);

            ChronicDiagnosisBo chronicDiagnosisBo = new ChronicDiagnosisBo(oldChronicDiagnosisList, newChronicDiagnosisList);
            chronicResponse = syncService.save(config.getSaveChronicDiagnosisUri(), token, chronicDiagnosisBo);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new IllegalStateException("URI excepstion.");
        }
        return Result.buildSuccess(null);

    }
}
