package com.jbp.crm.handler;

import com.jbp.crm.exception.ActivityAddException;
import com.jbp.crm.exception.ActivityDeleteException;
import com.jbp.crm.exception.LoginException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SQueen
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LoginException.class)
    @ResponseBody
    public Map<String,Object> loginException(Exception e){
       Map<String,Object> map = new HashMap<>();
       map.put("msg",e.getMessage());
       map.put("success",false);
       return map;
    }

    @ExceptionHandler(ActivityAddException.class)
    @ResponseBody
    public Map<String,Object> activityAddException(Exception e){
        Map<String,Object> map = new HashMap<>();
        map.put("msg",e.getMessage());
        map.put("success",false);
        return map;
    }

    @ExceptionHandler(ActivityDeleteException.class)
    @ResponseBody
    public Map<String,Object> activityDeleteException(Exception e){
        Map<String,Object> map = new HashMap<>();
        map.put("msg",e.getMessage());
        map.put("success",false);
        return map;
    }
}
