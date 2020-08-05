package com.bazl.dna.database.transfer.mapper;

import com.bazl.dna.database.transfer.model.po.TransferPersonQueue;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 * Created by lizhihua on 2019-05-08.
 */
@Repository
public interface TransferPersonQueueMapper {

    List<TransferPersonQueue> select(TransferPersonQueue transferPersonQueue);

    int updateStatus(TransferPersonQueue queueSample);

}
