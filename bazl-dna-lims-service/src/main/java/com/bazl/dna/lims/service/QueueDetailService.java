package com.bazl.dna.lims.service;

import com.bazl.dna.lims.model.po.QueueDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Sun on 2019/4/9.
 */
@Repository
public interface QueueDetailService {

    int deleteByPrimaryKey(String id);

    int insert(QueueDetail record);

    QueueDetail selectByPrimaryKey(String id);

    List<QueueDetail> selectAll();

    int updateByPrimaryKey(QueueDetail record);
}
