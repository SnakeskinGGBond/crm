package com.jbp.crm.settings.service;

import com.jbp.crm.exception.UserException;
import com.jbp.crm.settings.domain.User;

public interface UserService {
    User login(String loginAct,String loginPwd,String ip) throws UserException;
}
