package com.yunang.fangda.business.account.jpa;

import com.yunang.fangda.business.account.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author ld
 * @name 账户
 * @table
 * @remarks
 */
public interface AccountJpa extends JpaSpecificationExecutor<AccountModel>,
        JpaRepository<AccountModel, String> {

    /**
     * @param account
     * @return
     */
    List<AccountModel> findByAccount(String account);

    List<AccountModel> findByAccountLike(String account);
}
