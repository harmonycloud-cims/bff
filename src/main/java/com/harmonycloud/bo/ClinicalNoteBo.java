package com.harmonycloud.bo;

import com.harmonycloud.entity.ClinicalNote;

public class ClinicalNoteBo {
    private ClinicalNote newCalinicalNote;
    private ClinicalNote oldCalinicalNote;

    public ClinicalNoteBo() {
    }

    public ClinicalNoteBo(ClinicalNote newCalinicalNote, ClinicalNote oldCalinicalNote) {
        this.newCalinicalNote = newCalinicalNote;
        this.oldCalinicalNote = oldCalinicalNote;
    }

    public ClinicalNote getNewCalinicalNote() {
        return newCalinicalNote;
    }

    public void setNewCalinicalNote(ClinicalNote newCalinicalNote) {
        this.newCalinicalNote = newCalinicalNote;
    }

    public ClinicalNote getOldCalinicalNote() {
        return oldCalinicalNote;
    }

    public void setOldCalinicalNote(ClinicalNote oldCalinicalNote) {
        this.oldCalinicalNote = oldCalinicalNote;
    }
}
