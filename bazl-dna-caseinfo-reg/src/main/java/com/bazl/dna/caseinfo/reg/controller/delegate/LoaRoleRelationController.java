package com.bazl.dna.caseinfo.reg.controller.delegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bazl.dna.lims.service.LoaRoleRelationService;

/**
 * Created by Administrator on 2018/12/21.
 */
@Controller
@RequestMapping("/LoaRoleRelation")
public class LoaRoleRelationController {
    @Autowired
    private LoaRoleRelationService loaRoleRelationService;

}
