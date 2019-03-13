package com.yunang.fangda.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LD
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseResult<T> {
    private boolean success;
    private String message;
    private T data;

    public ResponseResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
