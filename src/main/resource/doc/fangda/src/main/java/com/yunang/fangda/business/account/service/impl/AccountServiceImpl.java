package com.yunang.fangda.business.account.service.impl;

import com.yunang.fangda.business.account.jpa.AccountJpa;
import com.yunang.fangda.business.account.model.AccountModel;
import com.yunang.fangda.business.account.service.AccountService;
import com.yunang.fangda.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ld
 * @name
 * @table
 * @remarks
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountJpa jpa;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseResult<AccountModel> save(AccountModel model) {
        if (model == null)
            return new ResponseResult<>(false, "数据为空", null);
        List<AccountModel> list = jpa.findByAccount(model.getAccount());
        if (list.size() > 0)
            return new ResponseResult<>(false, "该账户已存在", null);
        else {
            model.setIsLogin(1);
            model.setSystemTimes(new Timestamp(System.currentTimeMillis()));
            jpa.save(model);
            return new ResponseResult<>(true, "成功", null);
        }
    }

    @Transactional
    @Override
    public ResponseResult<AccountModel> delete(String uuid) {
        if (uuid == null || uuid.isEmpty())
            return new ResponseResult<>(false, "删除条件不能为空", null);
        jpa.deleteById(uuid);
        return new ResponseResult<>(true, "成功", null);
    }

    @Transactional
    @Override
    public ResponseResult<AccountModel> update(AccountModel model) {
        if (model != null && model.getUuid() != null && !model.getUuid().isEmpty()) {
            AccountModel one = jpa.getOne(model.getUuid());
            if (model.getPassword() != null && !model.getPassword().isEmpty())
                one.setPassword(model.getPassword());
            if (model.getIsLogin() != null && model.getIsLogin() > 0)
                one.setIsLogin(model.getIsLogin());
            return new ResponseResult<>(true, "成功", null);
        } else
            return new ResponseResult<>(false, "主键不能为空", null);
    }

    @Override
    public ResponseResult<AccountModel> findById(String uuid) {
        AccountModel one = jpa.getOne(uuid);
        return new ResponseResult<>(true, "成功", one);
    }

    @Override
    public ResponseResult<AccountModel> findByAccount(String account) {
        List<AccountModel> list = jpa.findByAccount(account);
        if (list.size() > 0)
            return new ResponseResult<>(true, "成功", list.get(0));
        else
            return new ResponseResult<>(false, "记录未找到", null);
    }

    @Override
    public ResponseResult<Page<AccountModel>> findAll(int pageNow, int pageSize, AccountModel model) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "systemTimes"));
        orders.add(new Sort.Order(Sort.Direction.ASC, "account"));
        Specification<AccountModel> spec = queryTj(model);
        PageRequest pageRequest = PageRequest.of(pageNow, pageSize, Sort.by(orders));
//        Slice<AccountModel> page = jpa.findAll(spec, pageRequest);
        Page<AccountModel> page = jpa.findAll(spec, pageRequest);
        if (!page.getContent().isEmpty())
            return new ResponseResult<>(true, "成功", page);
        else
            return new ResponseResult<>(false, "未查询到数据", null);
    }

    //    查询条件
    private Specification<AccountModel> queryTj(AccountModel model) {
        return new Specification<AccountModel>() {//查询条件构造
            @Override
            public Predicate toPredicate(Root<AccountModel> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (model != null) {
                    if (model.getAccount() != null && !model.getAccount().isEmpty()) {
                        Predicate p1 = cb.like(root.get("account").as(String.class), "%" + model.getAccount() + "%");
                        predicates.add(cb.and(p1));
                    }
                }
//                if (model.getDrnyStar() != null && !model.getDrnyStar().trim().equals("")) {
////                    时间   小于等于 导入日期 大于等于 导入日期
//                    try {
//                        Predicate p1 = cb.greaterThanOrEqualTo(root.get("impDate").as(Long.class),
//                                sdfmat.parse(model.getDrnyStar()).getTime());
//                        predicates.add(cb.and(p1));
//                    } catch (Exception e) {
//                        return null;
//                    }
//                }
//                if (model.getDrnyEnd() != null && !model.getDrnyEnd().trim().equals("")) {
////                    时间  小于等于 导入日期
//                    try {
//                        Predicate p1 = cb.lessThanOrEqualTo(root.get("impDate").as(Long.class),
//                                sdfmat.parse(model.getDrnyEnd()).getTime());
//                        predicates.add(cb.and(p1));
//                    } catch (Exception e) {
//                        return null;
//                    }
//                }

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

    @Override
    public ResponseResult<List<AccountModel>> findByAccountLike(String account) {
        List<AccountModel> list = jpa.findByAccountLike("%" + account + "%");
        if (list != null && list.size() > 0)
            return new ResponseResult<>(true, "成功", list);
        else
            return new ResponseResult<>(false, "未查询到数据", null);
    }
}
