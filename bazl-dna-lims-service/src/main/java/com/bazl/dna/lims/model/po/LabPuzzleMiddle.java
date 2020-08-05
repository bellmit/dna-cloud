package com.bazl.dna.lims.model.po;

import java.io.Serializable;

public class LabPuzzleMiddle implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String taskId;

    private String pcrId;

    private String syId;

    public String getSyId() {
        return syId;
    }

    public void setSyId(String syId) {
        this.syId = syId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public String getPcrId() {
        return pcrId;
    }

    public void setPcrId(String pcrId) {
        this.pcrId = pcrId == null ? null : pcrId.trim();
    }
}