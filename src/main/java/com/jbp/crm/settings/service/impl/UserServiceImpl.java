package com.jbp.crm.settings.service.impl;

import com.jbp.crm.exception.LoginException;
import com.jbp.crm.exception.UserException;
import com.jbp.crm.settings.dao.UserDao;
import com.jbp.crm.settings.domain.User;
import com.jbp.crm.settings.service.UserService;
import com.jbp.crm.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SQueen
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws UserException {
        Map<String, String> map = new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);
        User user = userDao.login(map);
        if (user == null) {
            throw new LoginException("用户名密码错误");
        }
        //账号密码正确,继续验证其他信息
        String expireTime = user.getExpireTime();
        String sysTime = DateTimeUtil.getSysTime();
        //账号失效时间
        if (expireTime.compareTo(sysTime) < 0) {
            throw new LoginException("账号已失效");
        }
        //锁定状态
        String lockState = user.getLockState();
        if ("0".equals(lockState)) {
            throw new LoginException("账号已锁定");
        }
        //ip地址
        String allowIps = user.getAllowIps();
        if (!allowIps.contains(ip)) {
            throw new LoginException("ip地址受限");
        }
        return user;
    }
}
