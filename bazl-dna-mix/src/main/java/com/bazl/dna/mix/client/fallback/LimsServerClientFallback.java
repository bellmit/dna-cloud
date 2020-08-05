package com.bazl.dna.mix.client.fallback;


import com.bazl.dna.common.ResponseData;
import com.bazl.dna.mix.client.LimsServerClient;
import com.bazl.dna.mix.constants.ErrorCodes;
import com.bazl.dna.mix.constants.ErrorInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
public class LimsServerClientFallback implements LimsServerClient {
    @Override
    public ResponseData getCaseInfoByCaseId(@RequestParam("caseId") String caseId) {
        return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
    }

    @Override
    public ResponseData getSampelInfoByGeneId(@RequestBody List<String> geneIdList) {
        return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
    }
}
