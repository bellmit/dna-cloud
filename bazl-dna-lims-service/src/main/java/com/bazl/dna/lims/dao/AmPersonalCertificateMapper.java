package com.bazl.dna.lims.dao;

import com.bazl.dna.lims.model.po.AmPersonalCertificate;
import java.util.List;

public interface AmPersonalCertificateMapper {
    int insert(AmPersonalCertificate record);

    List<AmPersonalCertificate> selectAll();
}