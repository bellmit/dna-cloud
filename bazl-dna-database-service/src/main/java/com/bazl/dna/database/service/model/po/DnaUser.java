package com.bazl.dna.database.service.model.po;


import lombok.Data;

import java.io.Serializable;

/**
 * @author lizhihua
 */
@Data
public class DnaUser implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userId;

    private String userName;

}