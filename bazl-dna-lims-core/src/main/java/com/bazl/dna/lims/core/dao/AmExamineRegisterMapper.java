package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.AmExamineRegister;
import java.util.List;

public interface AmExamineRegisterMapper {
    int insert(AmExamineRegister record);

    List<AmExamineRegister> selectAll();
}