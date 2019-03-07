package com.harmonycloud.controller;

import com.harmonycloud.result.Result;
import com.harmonycloud.service.BffService;
import com.harmonycloud.vo.NoteDiagnosisVo;
import com.harmonycloud.vo.NoteDiagnosis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "ClinicNote", httpMethod = "POST")
    @ApiImplicitParam(name = "noteDiagnosis", value = "noteDiagnosis", paramType = "body", dataType = "NoteDiagnosis")
    public Result saveNoteDiagnosis(@RequestBody NoteDiagnosis noteDiagnosis) {
        return bffService.save(noteDiagnosis);
    }

    @PostMapping("/update")
    @ApiOperation(value = "ClinicNote", httpMethod = "POST")
    @ApiImplicitParam(name = "noteDiagnosisVo", value = "noteDiagnosisVo", paramType = "body", dataType = "NoteDiagnosisVo")
    public Result updateNoteDiagnosis(@RequestBody NoteDiagnosisVo noteDiagnosis1Vo) {
        return bffService.update(noteDiagnosis1Vo);
    }
}
