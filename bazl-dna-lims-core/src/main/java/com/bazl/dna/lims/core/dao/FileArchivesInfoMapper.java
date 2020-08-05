package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.FileArchivesInfo;

import java.util.List;

public interface FileArchivesInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(FileArchivesInfo record);

    FileArchivesInfo selectByPrimaryKey(String id);

    List<FileArchivesInfo> selectAll();

    int updateByPrimaryKey(FileArchivesInfo record);

    FileArchivesInfo queryFileLetter(String caseId);

    FileArchivesInfo queryEntrustBook(String caseId);
}