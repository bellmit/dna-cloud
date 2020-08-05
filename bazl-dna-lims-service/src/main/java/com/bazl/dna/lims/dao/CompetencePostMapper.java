package com.bazl.dna.lims.dao;

import com.bazl.dna.lims.model.po.CompetencePost;
import java.util.List;

public interface CompetencePostMapper {
    int insert(CompetencePost record);

    List<CompetencePost> selectAll();
}