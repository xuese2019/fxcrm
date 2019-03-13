package com.yunang.fangda.business.account.service;

import com.yunang.fangda.business.account.model.AccountModel;
import com.yunang.fangda.utils.ResponseResult;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author ld
 * @name
 * @table
 * @remarks
 */
public interface AccountService {

    ResponseResult<AccountModel> save(AccountModel model);

    ResponseResult<AccountModel> delete(String uuid);

    ResponseResult<AccountModel> update(AccountModel model);

    ResponseResult<AccountModel> findById(String uuid);

    ResponseResult<AccountModel> findByAccount(String account);

    ResponseResult<Page<AccountModel>> findAll(int pageNow, int pageSize, AccountModel model);

    ResponseResult<List<AccountModel>> findByAccountLike(String account);


}
