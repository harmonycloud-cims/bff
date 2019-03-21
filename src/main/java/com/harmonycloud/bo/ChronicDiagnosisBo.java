package com.harmonycloud.bo;

import com.harmonycloud.entity.ChronicDiagnosis;

import java.util.List;

public class ChronicDiagnosisBo {

    List<ChronicDiagnosis> newChronicDiagnosisList;

    List<ChronicDiagnosis> oldChronicDiagnosisList;

    public ChronicDiagnosisBo() {
    }

    public ChronicDiagnosisBo(List<ChronicDiagnosis> newChronicDiagnosisList, List<ChronicDiagnosis> oldChronicDiagnosisList) {
        this.newChronicDiagnosisList = newChronicDiagnosisList;
        this.oldChronicDiagnosisList = oldChronicDiagnosisList;
    }

    public List<ChronicDiagnosis> getNewChronicDiagnosisList() {
        return newChronicDiagnosisList;
    }

    public void setNewChronicDiagnosisList(List<ChronicDiagnosis> newChronicDiagnosisList) {
        this.newChronicDiagnosisList = newChronicDiagnosisList;
    }

    public List<ChronicDiagnosis> getOldChronicDiagnosisList() {
        return oldChronicDiagnosisList;
    }

    public void setOldChronicDiagnosisList(List<ChronicDiagnosis> oldChronicDiagnosisList) {
        this.oldChronicDiagnosisList = oldChronicDiagnosisList;
    }
}
