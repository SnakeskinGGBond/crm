package com.jbp.setting.test;

import com.jbp.crm.utils.DateTimeUtil;
import com.jbp.crm.utils.MD5Util;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyApp {
    @Test
    public void shiXiaoTest(){
        //验证失效时间
        String expireTime = "2019-10-10 10:10:10";
        String currTime = DateTimeUtil.getSysTime();
        int i = expireTime.compareTo(currTime);
    }
    @Test
    public void lockTest(){
        String lockState = "0";
        if ("0".equals(lockState)){
            System.out.println("账号已锁定!");
        }
    }
    @Test
    public void ipTest(){
        //浏览器ip
        String ip = "192.168.1.1";
        //允许访问的ip地址
        String allowIps = "192.168.1.1,192.168.1.2";
        if(allowIps.contains(ip)){
            System.out.println("有效的ip地址");
        } else {
            System.out.println("ip地址受限");
        }
    }
    @Test
    public void MdTest(){
        String pwd = "123";
        pwd = MD5Util.getMD5(pwd);
        System.out.println(pwd);
    }

}
