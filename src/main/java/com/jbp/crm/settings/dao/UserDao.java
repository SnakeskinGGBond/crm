package com.jbp.crm.settings.dao;

import com.jbp.crm.settings.domain.User;

import java.util.Map;

/**
 * @author SQueen
 */
public interface UserDao {
    User login(Map<String, String> map);
    /*
    关于登录
        验证账号和密码
        User user = select * from tbl_user where loginAct=? and loginPwd=?
        user对象为null :说明账号密码错误
        user对象不为null:账号密码正确,继续向下验证
        从user中get到:
            expireTime:失效时间
            lockStatue:锁定状态
            allowIps:允许的ip地址
     */
}
