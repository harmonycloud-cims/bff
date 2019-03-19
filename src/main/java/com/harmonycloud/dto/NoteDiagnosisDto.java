package com.harmonycloud.dto;

import com.harmonycloud.entity.AttendingDiagnosis;
import com.harmonycloud.entity.ChronicDiagnosis;
import com.harmonycloud.entity.ClinicalNote;

import java.util.List;

public class NoteDiagnosisDto {
    private ClinicalNote newClinicalNote;
    private ClinicalNote oldClinicalNote;
    private List<AttendingDiagnosis> oldAttendingDiagnosisList;
    private List<ChronicDiagnosis> oldChronicDiagnosisList;
    private List<AttendingDiagnosis> newAttendingDiagnosisList;
    private List<ChronicDiagnosis> newChronicDiagnosisList;

    public NoteDiagnosisDto() {
    }

    public NoteDiagnosisDto(ClinicalNote newClinicalNote, ClinicalNote oldClinicalNote, List<AttendingDiagnosis> oldAttendingDiagnosisList,
                            List<ChronicDiagnosis> oldChronicDiagnosisList, List<AttendingDiagnosis> newAttendingDiagnosisList,
                            List<ChronicDiagnosis> newChronicDiagnosisList) {
        this.newClinicalNote = newClinicalNote;
        this.oldClinicalNote = oldClinicalNote;
        this.oldAttendingDiagnosisList = oldAttendingDiagnosisList;
        this.oldChronicDiagnosisList = oldChronicDiagnosisList;
        this.newAttendingDiagnosisList = newAttendingDiagnosisList;
        this.newChronicDiagnosisList = newChronicDiagnosisList;
    }

    public ClinicalNote getNewClinicalNote() {
        return newClinicalNote;
    }

    public void setNewClinicalNote(ClinicalNote newClinicalNote) {
        this.newClinicalNote = newClinicalNote;
    }

    public ClinicalNote getOldClinicalNote() {
        return oldClinicalNote;
    }

    public void setOldClinicalNote(ClinicalNote oldClinicalNote) {
        this.oldClinicalNote = oldClinicalNote;
    }

    public List<AttendingDiagnosis> getOldAttendingDiagnosisList() {
        return oldAttendingDiagnosisList;
    }

    public void setOldAttendingDiagnosisList(List<AttendingDiagnosis> oldAttendingDiagnosisList) {
        this.oldAttendingDiagnosisList = oldAttendingDiagnosisList;
    }

    public List<ChronicDiagnosis> getOldChronicDiagnosisList() {
        return oldChronicDiagnosisList;
    }

    public void setOldChronicDiagnosisList(List<ChronicDiagnosis> oldChronicDiagnosisList) {
        this.oldChronicDiagnosisList = oldChronicDiagnosisList;
    }

    public List<AttendingDiagnosis> getNewAttendingDiagnosisList() {
        return newAttendingDiagnosisList;
    }

    public void setNewAttendingDiagnosisList(List<AttendingDiagnosis> newAttendingDiagnosisList) {
        this.newAttendingDiagnosisList = newAttendingDiagnosisList;
    }

    public List<ChronicDiagnosis> getNewChronicDiagnosisList() {
        return newChronicDiagnosisList;
    }

    public void setNewChronicDiagnosisList(List<ChronicDiagnosis> newChronicDiagnosisList) {
        this.newChronicDiagnosisList = newChronicDiagnosisList;
    }
}
