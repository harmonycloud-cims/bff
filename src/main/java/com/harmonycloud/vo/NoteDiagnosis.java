package com.harmonycloud.vo;

import com.harmonycloud.entity.AttendingDiagnosis;
import com.harmonycloud.entity.ChronicDiagnosis;
import com.harmonycloud.entity.ClinicalNote;

import java.util.List;

public class NoteDiagnosis {
    private ClinicalNote clinicalNote;
    private List<AttendingDiagnosis> attendingDiagnosisList;
    private List<ChronicDiagnosis> chronicDiagnosisList;

    public NoteDiagnosis() {
    }

    public NoteDiagnosis(ClinicalNote clinicalNote, List<AttendingDiagnosis> attendingDiagnosisList, List<ChronicDiagnosis> chronicDiagnosisList) {
        this.clinicalNote = clinicalNote;
        this.attendingDiagnosisList = attendingDiagnosisList;
        this.chronicDiagnosisList = chronicDiagnosisList;
    }


    public ClinicalNote getClinicalNote() {
        return clinicalNote;
    }

    public void setClinicalNote(ClinicalNote clinicalNote) {
        this.clinicalNote = clinicalNote;
    }

    public List<AttendingDiagnosis> getAttendingDiagnosisList() {
        return attendingDiagnosisList;
    }

    public void setAttendingDiagnosisList(List<AttendingDiagnosis> attendingDiagnosisList) {
        this.attendingDiagnosisList = attendingDiagnosisList;
    }

    public List<ChronicDiagnosis> getChronicDiagnosisList() {
        return chronicDiagnosisList;
    }

    public void setChronicDiagnosisList(List<ChronicDiagnosis> chronicDiagnosisList) {
        this.chronicDiagnosisList = chronicDiagnosisList;
    }
}
