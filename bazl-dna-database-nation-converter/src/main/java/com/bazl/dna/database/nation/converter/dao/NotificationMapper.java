package com.bazl.dna.database.nation.converter.dao;

import com.bazl.dna.database.nation.converter.model.po.Notification;

import java.util.List;

public interface NotificationMapper {
    int deleteByPrimaryKey(String id);

    int insert(Notification record);

    Notification selectByPrimaryKey(String id);

    List<Notification> selectAll();

    int updateByPrimaryKey(Notification record);
}