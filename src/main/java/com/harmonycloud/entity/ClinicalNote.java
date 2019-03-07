package com.harmonycloud.entity;

import java.util.Date;


public class ClinicalNote {
    private Integer clinicalNoteId;
    private Integer encounterId;
    private String noteContent;
    private String createBy;
    private Date createDate;
    private Integer patientId;
    private String recordType;

    public ClinicalNote() {
    }

    public ClinicalNote(Integer encounterId, String noteContent, String createBy, Date createDate, Integer patientId, String recordType) {
        this.encounterId = encounterId;
        this.noteContent = noteContent;
        this.createBy = createBy;
        this.createDate = createDate;
        this.patientId = patientId;
        this.recordType = recordType;
    }

    public Integer getClinicalNoteId() {
        return clinicalNoteId;
    }

    public void setClinicalNoteId(Integer clinicalNoteId) {
        this.clinicalNoteId = clinicalNoteId;
    }

    public Integer getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Integer encounterId) {
        this.encounterId = encounterId;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
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

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }
}
