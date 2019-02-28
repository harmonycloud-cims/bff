package com.harmonycloud.entity;


import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "clinical_note_template")
public class ClinicalNoteTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ApiModelProperty(name = "诊断报告模板id", example = "1")
    private Integer clinicalNoteTemplateId;
    @ApiModelProperty(name = "诊断报告模板名称", example = "fever")
    @Column(name = "template_name")
    private String templateName;
    @ApiModelProperty(name = "诊断报告模板内容", example = "test")
    @Column(name = "template_content")
    private String templateContent;
    @ApiModelProperty(name = "诊所id", example = "1")
    @Column(name = "clinic_id")
    private Integer clinicId;


    public ClinicalNoteTemplate() {
    }

    public ClinicalNoteTemplate(String templateName, String templateContent, Integer clinicId) {
        this.clinicalNoteTemplateId = clinicalNoteTemplateId;
        this.templateName = templateName;
        this.templateContent = templateContent;
        this.clinicId = clinicId;

    }

    public Integer getClinicalNoteTemplateId() {
        return clinicalNoteTemplateId;
    }

    public void setClinicalNoteTemplateId(Integer clinicalNoteTemplateId) {
        this.clinicalNoteTemplateId = clinicalNoteTemplateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }
}
