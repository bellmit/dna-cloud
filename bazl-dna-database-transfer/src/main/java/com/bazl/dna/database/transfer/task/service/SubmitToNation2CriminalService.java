package com.bazl.dna.database.transfer.task.service;


import com.bazl.dna.database.transfer.model.bo.SubmitNationDataModel;

import java.util.Map;

/**
 * Created by Administrator on 2019/6/10.
 */
public interface SubmitToNation2CriminalService {

    Map<String, String> submitToNation(SubmitNationDataModel var1) throws Exception;

}
