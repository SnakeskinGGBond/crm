package com.jbp.crm.web.listener;

import com.jbp.crm.settings.domain.DicValue;
import com.jbp.crm.settings.domain.User;
import com.jbp.crm.settings.service.DicService;
import com.jbp.crm.settings.service.UserService;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class SysInitListener implements ServletContextListener {
    @Autowired
    UserService userService;
    /**
     * 该方法是用来监听全局作用域变量(上下文作用域变量)对象的方法,当服务器启动,上下文作用域对象被创建
     * 对象创建完毕后,执行该方法
     *
     * @param servletContextEvent 该参数能取得监听的对象,监听的事什么对象(上下文域对象),就可以通过该参数取得什么对象
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("SysInitListener初始化方法执行");
        ServletContext application = servletContextEvent.getServletContext();
        List<User> users = userService.getUserList();
        /*
        取数据字典
            需要7个List
            可以打包成为一个Map
            业务层:
                map.put("application",dvList1);
                map.put("clueState",dvList2);
                ...
         */
        //Map<String, List<DicValue>> map = ds.getAll();

        //将map解析为上下文域对象中保存的键值对
        /*Set<String> set = map.keySet();
        for (String key : set) {
            application.setAttribute(key,map.get(key));
        }*/

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
