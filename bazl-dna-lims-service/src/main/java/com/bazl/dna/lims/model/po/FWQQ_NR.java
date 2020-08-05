package com.bazl.dna.lims.model.po;

import java.io.Serializable;

public class FWQQ_NR implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String method;//方法

    private Params params;//请求参数

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "FWQQ_NR{" +
                "method='" + method + '\'' +
                ", params=" + params +
                '}';
    }
}
