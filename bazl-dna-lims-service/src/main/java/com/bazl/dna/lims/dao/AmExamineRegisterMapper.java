package com.bazl.dna.lims.dao;

import com.bazl.dna.lims.model.po.AmExamineRegister;
import java.util.List;

public interface AmExamineRegisterMapper {
    int insert(AmExamineRegister record);

    List<AmExamineRegister> selectAll();
}