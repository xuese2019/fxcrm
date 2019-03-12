package com.fxcrm.utils.jackjsonutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author: LD
 * @date:
 * @description:
 */
public class JackJsonUtils<T> {

    private ObjectMapper objectMapper = new ObjectMapper();

    public String beanToJackJsonString(T t) {
        try {
            return objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
