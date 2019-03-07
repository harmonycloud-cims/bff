package com.harmonycloud.entity;

public class AttendingDiagnosis {
    private Integer attendingDiagnosisId;
    private Integer diagnosisId;
    private Integer patientId;
    private Integer encounterId;


    public AttendingDiagnosis() {
    }

    public AttendingDiagnosis(Integer patientId, Integer diagnosisId, Integer encounterId) {
        this.patientId=patientId;
        this.diagnosisId = diagnosisId;
        this.encounterId = encounterId;
    }

    public Integer getId() {
        return attendingDiagnosisId;
    }

    public void setId(Integer attendingDiagnosisId) {
        this.attendingDiagnosisId = attendingDiagnosisId;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(Integer diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public Integer getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Integer encounterId) {
        this.encounterId = encounterId;
    }


}
