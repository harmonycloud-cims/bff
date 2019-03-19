package com.harmonycloud.controller;

import com.harmonycloud.dto.NoteDiagnosisDrugDto;
import com.harmonycloud.enums.ErrorMsgEnum;
import com.harmonycloud.exception.BffException;
import com.harmonycloud.result.CimsResponseWrapper;
import com.harmonycloud.service.BffService;
import com.harmonycloud.dto.NoteDiagnosisDto;
import com.harmonycloud.bo.NoteDiagnosisBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.servicecomb.saga.omega.context.annotations.SagaStart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api
public class BffController {

    @Autowired
    private BffService bffService;

    /**
     * save clinical note and diagnosis
     *
     * @param noteDiagnosisBo model
     * @param forwardHeaders  headers
     * @return
     * @throws Exception
     */
    @PostMapping("/insert")
    @ApiOperation(value = "save clinical note and diagnosis", httpMethod = "POST")
    @ApiImplicitParam(name = "noteDiagnosisBo", value = "noteDiagnosisBo", paramType = "body", dataType = "NoteDiagnosisBo")
    @SagaStart
    public CimsResponseWrapper<String> saveNoteDiagnosis(@RequestBody NoteDiagnosisBo noteDiagnosisBo, @RequestHeader HttpHeaders forwardHeaders) throws Exception {
        if (noteDiagnosisBo == null || noteDiagnosisBo.getClinicalNote().getEncounterId() <= 0 || noteDiagnosisBo.getClinicalNote().getPatientId() <= 0) {
            throw new BffException(ErrorMsgEnum.PARAMETER_ERROR.getMessage());
        }
        return bffService.saveNoteDiagnosis(noteDiagnosisBo, forwardHeaders);
    }

    /**
     * update clinical note and diagnosis
     *
     * @param noteDiagnosisDto model
     * @param forwardHeaders   headers
     * @return
     * @throws Exception
     */
    @PostMapping("/update")
    @ApiOperation(value = "update clinical note and diagnosis", httpMethod = "POST")
    @ApiImplicitParam(name = "noteDiagnosisDto", value = "noteDiagnosisDto", paramType = "body", dataType = "NoteDiagnosisDto")
    @SagaStart
    public CimsResponseWrapper<String> updateNoteDiagnosis(@RequestBody NoteDiagnosisDto noteDiagnosisDto, @RequestHeader HttpHeaders forwardHeaders) throws Exception {
        if (noteDiagnosisDto == null || noteDiagnosisDto.getNewClinicalNote().getEncounterId() <= 0 || noteDiagnosisDto.getNewClinicalNote().getEncounterId() <= 0
                || noteDiagnosisDto.getOldClinicalNote().getEncounterId() <= 0 || noteDiagnosisDto.getOldClinicalNote().getPatientId() <= 0) {
            throw new BffException(ErrorMsgEnum.PARAMETER_ERROR.getMessage());
        }
        return bffService.updateNoteDiagnosis(noteDiagnosisDto, forwardHeaders);
    }


    /**
     * save/update clinical note 、diagnosis and prescription
     *
     * @param noteDiagnosisDrugDto model
     * @param forwardHeaders       headers
     * @return
     * @throws Exception
     */
    @PostMapping("/nextPatient")
    @ApiOperation(value = "save clinical note、diagnosis and prescription", httpMethod = "POST")
    @ApiImplicitParam(name = "noteDiagnosisDrugDto", value = "noteDiagnosisDrugDto", paramType = "body", dataType = "NoteDiagnosisDrugDto")
    @SagaStart
    public CimsResponseWrapper<String> nextPatient(@RequestBody NoteDiagnosisDrugDto noteDiagnosisDrugDto, @RequestHeader HttpHeaders forwardHeaders) throws Exception {
        if (noteDiagnosisDrugDto == null || noteDiagnosisDrugDto.getNewClinicalNote().getEncounterId() <= 0
                || noteDiagnosisDrugDto.getNewClinicalNote().getPatientId() <= 0 || noteDiagnosisDrugDto.getOldPrescription().getEncounterId() <= 0
                || noteDiagnosisDrugDto.getOldPrescription().getPatientId() <= 0 || noteDiagnosisDrugDto.getOldPrescription().getClinicId() <= 0) {
            throw new BffException(ErrorMsgEnum.PARAMETER_ERROR.getMessage());
        }
        return bffService.nextPatient(noteDiagnosisDrugDto, forwardHeaders);
    }


}
