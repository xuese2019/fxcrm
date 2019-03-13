package com.yunang.fangda.business.login.controller;

import com.yunang.fangda.business.account.model.AccountModel;
import com.yunang.fangda.business.account.service.AccountService;
import com.yunang.fangda.sys.shiro.JWTUtils;
import com.yunang.fangda.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

/**
 * @author LD
 */
@Api(value = "登陆接口", description = "登陆接口")
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "获取token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseResult<String> login(@RequestBody AccountModel model) throws Exception {
        String md5Password = DigestUtils.md5DigestAsHex(model.getPassword().getBytes(StandardCharsets.UTF_8));
        ResponseResult<AccountModel> responseResult = accountService.findByAccount(model.getAccount());
        if (responseResult.isSuccess()) {
            if (responseResult.getData().getPassword().equals(md5Password)) {
                String token = JWTUtils.creaToken(responseResult.getData().getAccount(), responseResult.getData().getUuid(), "token");
                return new ResponseResult<>(true, "成功", token);
            }
        }
        return new ResponseResult<>(false, "账号或密码错误");
    }

}
