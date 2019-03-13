package com.yunang.fangda.sys.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

@Slf4j
public class StatelessRealm extends AuthorizingRealm {

//    @Resource
//    private AccountService accountService;
//    @Resource
//    private QxfyService qxfyService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof StatelessToken;
    }

    /**
     * @param arg0 当前登陆的实体
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        try {
//            AccountModel token = (AccountModel) arg0.getPrimaryPrincipal();
//            AccountModel model = new AccountModel();
//            model.setUuid(token.getAccount());
//            ResponseResult<List<AccountModel>> result = accountService.findByAccount(model);
//            if (result.isSuccess()) {
//                info.addRole("user");
//                ResponseResult<List<QxglModel>> result1 = qxfyService.findByAccId(token.getUuid());
//                if (result1.isSuccess()) {
//                    result1.getData().forEach(k -> {
//                        if (k.getIco().equals("1")) {
//                            info.addStringPermission(k.getQxbs());
//                        }
//                    });
//                }
//            } else {
//                AdminModel model1 = new AdminModel();
//                model1.setAccount(token.getAccount());
//                ResponseResult<AccountModel> result1 = accountService.getAdminByAccount(model1);
//                if (result1.isSuccess()) {
//                    info.addRole("admin");
//                }
//            }
//            return info;
//        } catch (Exception e) {
//            return info;
//        }
        return info;
    }

    /**
     * 登陆验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
//        boolean pc = PC2Utils.pc();
//        if (!pc) {
//            throw new UnknownAccountException("程序未激活");
//        }
//        String token = (String) authcToken.getPrincipal();
//        if (token == null)
//            throw new UnknownAccountException("令牌丢失!");
//        Map<String, Claim> map = JWTUtils.verifToken(token);
//        if (map == null)
//            throw new UnknownAccountException("令牌过期，请从新登陆!");
//        String iss = map.get("iss").asString();
//        if (!iss.equals("ldtoken"))
//            throw new UnknownAccountException("令牌签名不正确!");
//        Date nowDate = new Date();
//        Date iat = map.get("iat").asDate();
//        if (nowDate.before(iat))
//            throw new UnknownAccountException("令牌过期，请从新登陆!");
//        Date exp = map.get("exp").asDate();
//        if (exp.before(nowDate))
//            throw new UnknownAccountException("令牌过期，请从新登陆!");
//
//        String account = map.get("sub").asString();
//        try {
//            AccountModel model = new AccountModel();
//            model.setAccount(account);
//            ResponseResult<List<AccountModel>> result = accountService.findByAccount(model);
//            if (result.isSuccess()) {
//                AccountModel model1 = result.getData().get(0);
//                if (model1.getYgzt() != 1) {
//                    throw new UnknownAccountException("当前员工不是在职状态!");
//                }
//                return new SimpleAuthenticationInfo(model1, token, getName());
//            } else {
//                AdminModel model1 = new AdminModel();
//                model1.setAccount(account);
//                ResponseResult<AccountModel> result1 = accountService.getAdminByAccount(model1);
//                if (result1.isSuccess())
//                    return new SimpleAuthenticationInfo(result1.getData(), token, getName());
//            }
//            throw new UnknownAccountException("当前用户已不存在!");
//        } catch (Exception e) {
//            throw new UnknownAccountException("服务器错误!");
//        }
//        return new SimpleAuthenticationInfo(, "");
        return null;
    }

}
