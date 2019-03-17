package com.harmonycloud.entity;


public class PrescriptionDrug {

    private Integer prescriptionDrugId;
    private Integer drugId;
    private String reginmenLine;
    private Integer prescriptionId;

    public PrescriptionDrug() {
    }

    public PrescriptionDrug(Integer drugId, String reginmenLine, Integer prescriptionId) {
        this.drugId = drugId;
        this.reginmenLine = reginmenLine;
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

    public String getReginmenLine() {
        return reginmenLine;
    }

    public void setReginmenLine(String reginmenLine) {
        this.reginmenLine = reginmenLine;
    }

    public Integer getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Integer prescriptionId) {
        this.prescriptionId = prescriptionId;
    }
}
