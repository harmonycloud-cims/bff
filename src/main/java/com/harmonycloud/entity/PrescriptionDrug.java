package com.harmonycloud.entity;


public class PrescriptionDrug {

    private Integer prescriptionDrugId;
    private Integer drugId;
    private String regimenLine;
    private Integer prescriptionId;

    public PrescriptionDrug() {
    }

    public PrescriptionDrug(Integer prescriptionDrugId, Integer drugId, String regimenLine, Integer prescriptionId) {
        this.prescriptionDrugId = prescriptionDrugId;
        this.drugId = drugId;
        this.regimenLine = regimenLine;
        this.prescriptionId = prescriptionId;
    }

    public Integer getPrescriptionDrugId() {
        return prescriptionDrugId;
    }

    public void setPrescriptionDrugId(Integer prescriptionDrugId) {
        this.prescriptionDrugId = prescriptionDrugId;
    }

    public Integer getDrugId() {
        return drugId;
    }

    public void setDrugId(Integer drugId) {
        this.drugId = drugId;
    }

    public String getRegimenLine() {
        return regimenLine;
    }

    public void setRegimenLine(String regimenLine) {
        this.regimenLine = regimenLine;
    }

    public Integer getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Integer prescriptionId) {
        this.prescriptionId = prescriptionId;
    }
}
