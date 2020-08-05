package com.bazl.dna.lims.dao;

import com.bazl.dna.lims.model.po.QueueDetail;
import java.util.List;

public interface QueueDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(QueueDetail record);

    QueueDetail selectByPrimaryKey(String id);

    List<QueueDetail> selectAll();

    int updateByPrimaryKey(QueueDetail record);
}