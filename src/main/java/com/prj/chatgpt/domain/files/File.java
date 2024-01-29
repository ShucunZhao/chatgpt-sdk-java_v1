package com.prj.chatgpt.domain.files;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class File implements Serializable {
    /** ID */
    private String id;
    /** Object */
    private String object;
    /** Bytes */
    private long bytes;
    /** Created time */
    private long created_at;
    /** File name */
    private String filename;
    /** Type(Purpose): search */
    private String purpose;
    /** Status */
    private String status;
    @JsonProperty("status_details")
    private String statusDetails;
}
