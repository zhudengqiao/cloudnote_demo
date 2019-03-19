package com.jlu.cloudnote.startup;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartupService {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring/application-context.xml"});
        context.start();
        System.out.println("★★★★★★dubbo-provider服务启动成功★★★★★★");
        synchronized (StartupService.class)
        {
            while (true)
                try
                {
                    StartupService.class.wait();
                }
                catch (Throwable e) {
                    e.printStackTrace();
                }
        }

    }

}
