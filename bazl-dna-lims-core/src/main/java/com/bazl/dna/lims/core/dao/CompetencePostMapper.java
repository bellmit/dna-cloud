package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.CompetencePost;
import java.util.List;

public interface CompetencePostMapper {
    int insert(CompetencePost record);

    List<CompetencePost> selectAll();
}