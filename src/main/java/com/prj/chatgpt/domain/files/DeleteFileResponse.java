package com.prj.chatgpt.domain.files;

import lombok.Data;

import java.io.Serializable;

/**
 * @description Delete file response
 */
@Data
public class DeleteFileResponse implements Serializable {
    /** File ID */
    private String id;
    /** Object: file */
    private String object;
    /** If deleted: true */
    private boolean deleted;
}
