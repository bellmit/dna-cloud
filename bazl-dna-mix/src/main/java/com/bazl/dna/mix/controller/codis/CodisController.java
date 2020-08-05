package com.bazl.dna.mix.controller.codis;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bazl.dna.mix.controller.base.ResultBean;
import com.bazl.dna.mix.controller.base.error.ErrorCode;
import com.bazl.dna.mix.utils.ListUtils;

@RestController
@RequestMapping("/codis")
public class CodisController {

    /**
     * codis导入 types传1表示是单一样本基因获取 0表示获取混合样本基因信息
     */
    @RequestMapping(value = "/uploadCodis", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResultBean uploadCodis(HttpServletRequest request, HttpServletResponse response, @RequestParam("codisFile") List<MultipartFile> codisFiles, String reagentName, Integer types) throws Exception {
        //------注!!!-------------------此版本只限混合库适用,已修改原版数据装订,未修改文件解析----------------------------------------------
        if (StringUtils.isNotBlank(reagentName)) {
            try {
                reagentName = URLDecoder.decode(reagentName, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        List<CodisContentModel> codisContentModelList = new ArrayList<>();
        for (MultipartFile codisFile:codisFiles) {
            try {//解析
                List<CodisContentModel> codisContentModels = CodisFileParser.getDataCodisFile(codisFile.getInputStream(), codisFile.getOriginalFilename(), reagentName,types);
                for (CodisContentModel codisContentModel:codisContentModels) {
                    codisContentModel.setGeneImagePath(String.valueOf(0));
                }
                codisContentModelList.addAll(codisContentModels);
            } catch (Exception ex) {
                return  new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, ex.getMessage());
            }
        }
        if (ListUtils.isNullOrEmptyList(codisContentModelList)) {
            return  new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "CODIS文件中未检索到数据！");
        }
        return new ResultBean(ResultBean.CODE_SUCCESS,0,codisContentModelList, "");
    }







}
