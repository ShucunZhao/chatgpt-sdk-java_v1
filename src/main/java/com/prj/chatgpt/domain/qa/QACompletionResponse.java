package com.prj.chatgpt.domain.qa;

import com.prj.chatgpt.domain.other.Usage;
import lombok.Data;

import java.io.Serializable;

@Data
public class QACompletionResponse implements Serializable {
    /** ID */
    private String id;
    /** Objects */
    private String object;
    /** Models */
    private String model;
    /** QA dialog */
    private QAChoice[] choices;
    /** Created */
    private long created;
    /** Usage */
    private Usage usage;
}
