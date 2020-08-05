package com.bazl.dna.database.core.helper.codis;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.utils.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@RestController
@RequestMapping("/codis")
public class CodisController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CodisController.class); 
    /**
     * codis导入
     */
    @RequestMapping(value = "/uploadCodis", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResponseData uploadCodis(HttpServletRequest request, HttpServletResponse response, @RequestParam("codisFile") List<MultipartFile> codisFiles, String reagentName, Integer types) throws Exception {
        if (StringUtils.isNotBlank(reagentName)) {
            try {
                reagentName = URLDecoder.decode(reagentName, "UTF-8");
            } catch (UnsupportedEncodingException e) {
            	LOGGER.error("error:", e);
            }
        }
        List<CodisQcModel> codisContentModelList = null;
        for (MultipartFile codisFile:codisFiles) {
            try {//解析
                codisContentModelList = CodisFileParser.getDataCodisFile(codisFile.getInputStream(), codisFile.getOriginalFilename(), reagentName);
                /*for (CodisQcModel codisContentModel:codisContentModelList) {
                    codisContentModel.setGeneImagePath(String.valueOf(0));
                }*/
            } catch (Exception ex) {
                return new ResponseData(ex.getMessage());
            }
            if (ListUtils.isNullOrEmptyList(codisContentModelList)) {
                return new ResponseData(0, "CODIS文件中未检索到数据!");
            }
        }
        return new ResponseData(codisContentModelList);
    }

}
