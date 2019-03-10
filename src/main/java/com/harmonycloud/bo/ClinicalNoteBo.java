package com.harmonycloud.bo;

import com.harmonycloud.entity.ClinicalNote;

public class ClinicalNoteBo {
    private ClinicalNote newClinicalNote;
    private ClinicalNote oldClinicalNote;

    public ClinicalNoteBo() {
    }

    public ClinicalNoteBo(ClinicalNote newClinicalNote, ClinicalNote oldClinicalNote) {
        this.newClinicalNote = newClinicalNote;
        this.oldClinicalNote = oldClinicalNote;
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
}
