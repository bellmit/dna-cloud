package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.AmPersonalCertificate;
import java.util.List;

public interface AmPersonalCertificateMapper {
    int insert(AmPersonalCertificate record);

    List<AmPersonalCertificate> selectAll();
}