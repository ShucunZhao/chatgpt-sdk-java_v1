package com.prj.chatgpt.domain.edits;

import com.prj.chatgpt.domain.other.Choice;
import com.prj.chatgpt.domain.other.Usage;
import lombok.Data;

import java.io.Serializable;

@Data
public class EditResponse implements Serializable {
    /** ID */
    private String id;
    /** Object */
    private String object;
    /** Model */
    private String model;
    /** Dialog */
    private Choice[] choices;
    /** Created */
    private long created;
    /** Usage */
    private Usage usage;

}
