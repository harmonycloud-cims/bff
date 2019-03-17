package com.harmonycloud.bo;

import com.harmonycloud.entity.Prescription;
import com.harmonycloud.entity.PrescriptionDrug;

import java.util.List;

public class PrescriptionDrugBo {
    private Prescription oldPrescription;
    private List<PrescriptionDrug> oldPrescriptionDrugList;
    private List<PrescriptionDrug> newPrescriptionDrugList;

    public PrescriptionDrugBo() {
    }

    public PrescriptionDrugBo(Prescription oldPrescription, List<PrescriptionDrug> oldPrescriptionDrugList, List<PrescriptionDrug> newPrescriptionDrugList) {
        this.oldPrescription = oldPrescription;
        this.oldPrescriptionDrugList = oldPrescriptionDrugList;
        this.newPrescriptionDrugList = newPrescriptionDrugList;
    }

    public Prescription getOldPrescription() {
        return oldPrescription;
    }

    public void setOldPrescription(Prescription oldPrescription) {
        this.oldPrescription = oldPrescription;
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
