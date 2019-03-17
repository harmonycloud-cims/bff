package com.harmonycloud.controller;

import com.harmonycloud.dto.NoteDiagnosisDrugDto;
import com.harmonycloud.result.CimsResponseWrapper;
import com.harmonycloud.result.Result;
import com.harmonycloud.service.BffService;
import com.harmonycloud.dto.NoteDiagnosisDto;
import com.harmonycloud.bo.NoteDiagnosisBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.servicecomb.saga.omega.context.annotations.SagaStart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api
public class BffController {

    @Autowired
    private BffService bffService;


    @PostMapping("/insert")
    @ApiOperation(value = "save clinical note and diagnosis", httpMethod = "POST")
    @ApiImplicitParam(name = "noteDiagnosisBo", value = "noteDiagnosisBo", paramType = "body", dataType = "NoteDiagnosisBo")
    @SagaStart
    public Result saveNoteDiagnosis(@RequestBody NoteDiagnosisBo noteDiagnosisBo) {
        return bffService.saveNoteDiagnosis(noteDiagnosisBo);
    }

    @PostMapping("/update")
    @ApiOperation(value = "update clinical note and diagnosis", httpMethod = "POST")
    @ApiImplicitParam(name = "noteDiagnosisDto", value = "noteDiagnosisDto", paramType = "body", dataType = "NoteDiagnosisDto")
    @SagaStart
    public Result updateNoteDiagnosis(@RequestBody NoteDiagnosisDto noteDiagnosisDto) {
        return bffService.updateNoteDiagnosis(noteDiagnosisDto);
    }




    @PostMapping("/nextPatient")
    @ApiOperation(value = "save clinical note、diagnosis and prescription", httpMethod = "POST")
    @ApiImplicitParam(name = "noteDiagnosisDrugDto", value = "noteDiagnosisDrugDto", paramType = "body", dataType = "NoteDiagnosisDrugDto")
    @SagaStart
    public CimsResponseWrapper<String> nextPatient(@RequestBody NoteDiagnosisDrugDto noteDiagnosisDrugDto) throws  Exception{
        return bffService.nextPatient(noteDiagnosisDrugDto);
    }


}
