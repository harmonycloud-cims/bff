package com.harmonycloud.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;


@ApiModel
@Entity
@Table(name = "clinical_note")
public class ClinicalNote {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ApiModelProperty(name = "报告id", example = "1")
    private Integer clinicalNoteId;
    @ApiModelProperty(name = "会诊记录id", example = "1")
    @Column(name = "encounter_id")
    private Integer encounterId;
    @ApiModelProperty(name = "报告id", example = "1")
    @Column(name = "note_content")
    private String noteContent;
    @ApiModelProperty(name = "报告id", example = "1")
    @Column(name = "create_by")
    private String createBy;
    @ApiModelProperty(name = "报告id", example = "1")
    @Column(name = "create_date")
    private String createDate;
    @ApiModelProperty(name = "报告id", example = "1")
    @Column(name = "patient_id")
    private Integer patientId;

    public ClinicalNote() {
    }

    public ClinicalNote(Integer encounterId, String noteContent, String createBy, String createDate) {
        this.encounterId = encounterId;
        this.noteContent = noteContent;
        this.createBy = createBy;
        this.createDate = createDate;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }
}
