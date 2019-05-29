package com.xzh.nettyspring;

import com.xzh.nettyspring.netty.WSServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @Authur: joshuasiu
 * @Date: 2019-05-29 10:15
 * @Description:
 */
@Component
@Slf4j
public class NettyApp implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            try {
                WSServer.getInstance().start();
            } catch (Exception e) {
                log.error("netty WSServer 启动异常", e);
            }

        }

    }
}
