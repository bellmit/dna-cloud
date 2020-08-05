package com.bazl.dna.database.nation.converter.dao;

import com.bazl.dna.database.nation.converter.model.po.Consignment;
import com.bazl.dna.database.nation.converter.model.vo.ConsignmentVo;

import java.util.List;

public interface ConsignmentMapper {
    int deleteByPrimaryKey(String id);

    int insert(Consignment record);

    Consignment selectByPrimaryKey(String id);

    Consignment selectByPrimaryKeyEventId(String eventId);

    List<Consignment> listSelectListData(ConsignmentVo query);

    List<Consignment>  ListSelectByPrimaryKeyEventId(Consignment eventId);

    List<Consignment> selectAll();

    int updateByPrimaryKey(Consignment record);
}