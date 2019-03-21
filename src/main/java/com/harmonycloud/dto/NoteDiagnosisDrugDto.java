package com.harmonycloud.dto;

import com.harmonycloud.entity.*;

import java.util.List;

public class NoteDiagnosisDrugDto {

    private ClinicalNote newClinicalNote;

    private ClinicalNote oldClinicalNote;

    private List<AttendingDiagnosis> oldAttendingDiagnosisList;

    private List<ChronicDiagnosis> oldChronicDiagnosisList;

    private List<AttendingDiagnosis> newAttendingDiagnosisList;

    private List<ChronicDiagnosis> newChronicDiagnosisList;

    private Prescription prescription;

    private List<PrescriptionDrug> oldPrescriptionDrugList;

    private List<PrescriptionDrug> newPrescriptionDrugList;

    public NoteDiagnosisDrugDto() {
    }

    public NoteDiagnosisDrugDto(ClinicalNote newClinicalNote, ClinicalNote oldClinicalNote, List<AttendingDiagnosis> oldAttendingDiagnosisList, List<ChronicDiagnosis> oldChronicDiagnosisList,
                                List<AttendingDiagnosis> newAttendingDiagnosisList, List<ChronicDiagnosis> newChronicDiagnosisList, Prescription prescription, List<PrescriptionDrug> oldPrescriptionDrugList,
                                List<PrescriptionDrug> newPrescriptionDrugList) {
        this.newClinicalNote = newClinicalNote;
        this.oldClinicalNote = oldClinicalNote;
        this.oldAttendingDiagnosisList = oldAttendingDiagnosisList;
        this.oldChronicDiagnosisList = oldChronicDiagnosisList;
        this.newAttendingDiagnosisList = newAttendingDiagnosisList;
        this.newChronicDiagnosisList = newChronicDiagnosisList;
        this.prescription = prescription;
        this.oldPrescriptionDrugList = oldPrescriptionDrugList;
        this.newPrescriptionDrugList = newPrescriptionDrugList;
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

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public List<PrescriptionDrug> getOldPrescriptionDrugList() {
        return oldPrescriptionDrugList;
    }

    public void setOldPrescriptionDrugList(List<PrescriptionDrug> oldPrescriptionDrugList) {
        this.oldPrescriptionDrugList = oldPrescriptionDrugList;
    }

    public List<PrescriptionDrug> getNewPrescriptionDrugList() {
        return newPrescriptionDrugList;
    }

    public void setNewPrescriptionDrugList(List<PrescriptionDrug> newPrescriptionDrugList) {
        this.newPrescriptionDrugList = newPrescriptionDrugList;
    }
}
