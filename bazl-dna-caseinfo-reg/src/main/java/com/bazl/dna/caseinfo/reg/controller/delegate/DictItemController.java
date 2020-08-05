package com.bazl.dna.caseinfo.reg.controller.delegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bazl.dna.lims.service.DictItemService;

/**
 * Created by Administrator on 2018/12/25.
 */
@Controller
@RequestMapping("/DictItemController")
public class DictItemController {
    @Autowired
    private DictItemService dictItemService;
}