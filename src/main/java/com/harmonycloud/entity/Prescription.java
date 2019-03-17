package com.harmonycloud.entity;

import java.util.Date;

public class Prescription {

    private Integer prescriptionId;
    private Integer patientId;
    private Integer encounterId;
    private String createBy;
    private Date createDate;
    private Integer clinicId;
    private String clinicName;


    public Prescription() {
    }

    public Prescription(Integer patientId, Integer encounterId, String createBy, Date createDate, Integer clinicId, String clinicName) {
        this.patientId = patientId;
        this.encounterId = encounterId;
        this.createBy = createBy;
        this.createDate = createDate;
        this.clinicId = clinicId;
        this.clinicName = clinicName;
    }

    public Integer getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Integer prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Integer encounterId) {
        this.encounterId = encounterId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }
}
