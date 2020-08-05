package com.bazl.dna.mix.model.po;

import java.io.Serializable;

/**
 * DICT_COUNT
 * @author 
 */
public class DictCount implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

    private String dictTypeCode;

    private String dictCountNumber;

    private String years;

    private String months;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDictTypeCode() {
        return dictTypeCode;
    }

    public void setDictTypeCode(String dictTypeCode) {
        this.dictTypeCode = dictTypeCode == null ? null : dictTypeCode.trim();
    }

    public String getDictCountNumber() {
        return dictCountNumber;
    }

    public void setDictCountNumber(String dictCountNumber) {
        this.dictCountNumber = dictCountNumber == null ? null : dictCountNumber.trim();
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years == null ? null : years.trim();
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months == null ? null : months.trim();
    }
}