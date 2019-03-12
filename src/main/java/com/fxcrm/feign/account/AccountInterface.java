package com.fxcrm.feign.account;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fxcrm.utils.ResponseResult;
import feign.FeignException;
import feign.Headers;
import feign.RequestLine;

/**
 * @author: LD
 * @date:
 * @description:
 */
public interface AccountInterface {

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @RequestLine("POST /login/login")
    ResponseResult<Object> login(JSONPObject json) throws Exception;

//    @RequestLine("POST /api/commodity/commodity")
//    @Body("json={json}")
//    ResponseResult<String> add(@Param("json") String json);
//
//    @RequestLine("GET /api/commodity/del/{uuid}?token={token}")
//    ResponseResult<String> del(@Param("uuid") String uuid, @Param("token") String token);
//
//    @RequestLine("POST /api/commodity/update")
//    @Body("json={json}")
//    ResponseResult<String> update(@Param("json") String json);
//
//    @RequestLine("GET /api/commodity/commodity/page/{pageNow}/{pageSize}?token={token}")
//    ResponseResult<String> page(@Param("pageNow") int pageNow, @Param("pageSize") int pageSize, @Param("token") String token);
//
//    @RequestLine("GET /api/category/findAll")
//    ResponseResult<String> spfl();
}
