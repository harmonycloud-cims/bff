package com.harmonycloud.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;
import java.net.URISyntaxException;

@ConfigurationProperties(prefix = "service")
public class BffConfigurationProperties {
    private String saveClinicalNote;
    private String updateClinicalNote;

    private String saveAttendingDiagnosis;
    private String updateAttendingDiagnosis;

    private String saveChronicDiagnosis;
    private String updateChronicDiagnosis;

    public String getSaveClinicalNote() {
        return saveClinicalNote;
    }

    public void setSaveClinicalNote(String saveClinicalNote) {
        this.saveClinicalNote = saveClinicalNote;
    }

    public String getUpdateClinicalNote() {
        return updateClinicalNote;
    }

    public void setUpdateClinicalNote(String updateClinicalNote) {
        this.updateClinicalNote = updateClinicalNote;
    }

    public String getSaveAttendingDiagnosis() {
        return saveAttendingDiagnosis;
    }

    public void setSaveAttendingDiagnosis(String saveAttendingDiagnosis) {
        this.saveAttendingDiagnosis = saveAttendingDiagnosis;
    }

    public String getUpdateAttendingDiagnosis() {
        return updateAttendingDiagnosis;
    }

    public void setUpdateAttendingDiagnosis(String updateAttendingDiagnosis) {
        this.updateAttendingDiagnosis = updateAttendingDiagnosis;
    }

    public String getSaveChronicDiagnosis() {
        return saveChronicDiagnosis;
    }

    public void setSaveChronicDiagnosis(String saveChronicDiagnosis) {
        this.saveChronicDiagnosis = saveChronicDiagnosis;
    }

    public String getUpdateChronicDiagnosis() {
        return updateChronicDiagnosis;
    }

    public void setUpdateChronicDiagnosis(String updateChronicDiagnosis) {
        this.updateChronicDiagnosis = updateChronicDiagnosis;
    }

    public URI getUpdateClinicalNoteUri() {
        try {
            return new URI(updateClinicalNote);
        } catch (URISyntaxException e) {
            return null;
        }
    }
    public URI getSaveClinicalNoteUri() {
        try {
            return new URI(saveClinicalNote);
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public URI getSaveAttendingDiagnosisUri() {
        try {
            return new URI(saveAttendingDiagnosis);
        } catch (URISyntaxException e) {
            return null;
        }
    }
    public URI getUpdateAttendingDiagnosisUri() {
        try {
            return new URI(updateAttendingDiagnosis);
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public URI getSaveChronicDiagnosisUri() {
        try {
            return new URI(saveChronicDiagnosis);
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public URI getUpdateChronicDiagnosisUri() {
        try {
            return new URI(updateChronicDiagnosis);
        } catch (URISyntaxException e) {
            return null;
        }
    }
}
