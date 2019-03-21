package com.harmonycloud.bo;

import com.harmonycloud.entity.AttendingDiagnosis;

import java.util.List;

public class AttendingDiagnosisBo {

    List<AttendingDiagnosis> newAttendingDiagnosisList;

    List<AttendingDiagnosis> oldAttendingDiagnosisList;

    public AttendingDiagnosisBo() {
    }

    public AttendingDiagnosisBo(List<AttendingDiagnosis> newAttendingDiagnosisList, List<AttendingDiagnosis> oldAttendingDiagnosisList) {
        this.newAttendingDiagnosisList = newAttendingDiagnosisList;
        this.oldAttendingDiagnosisList = oldAttendingDiagnosisList;
    }

    public List<AttendingDiagnosis> getNewAttendingDiagnosisList() {
        return newAttendingDiagnosisList;
    }

    public void setNewAttendingDiagnosisList(List<AttendingDiagnosis> newAttendingDiagnosisList) {
        this.newAttendingDiagnosisList = newAttendingDiagnosisList;
    }

    public List<AttendingDiagnosis> getOldAttendingDiagnosisList() {
        return oldAttendingDiagnosisList;
    }

    public void setOldAttendingDiagnosisList(List<AttendingDiagnosis> oldAttendingDiagnosisList) {
        this.oldAttendingDiagnosisList = oldAttendingDiagnosisList;
    }
}
