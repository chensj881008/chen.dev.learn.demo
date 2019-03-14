package com.winning.devops.springboot.websocket.web;

import com.winning.devops.springboot.websocket.model.Greeting;
import com.winning.devops.springboot.websocket.model.HelloMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

/**
 * @author chensj
 * @title
 * @project spring-boot-websocket
 * @package com.winning.devops.springboot.websocket.web
 * @date: 2019-01-30 14:47
 */
@Controller
public class GreetingController {

    private static final Logger LOG = LoggerFactory.getLogger(GreetingController.class);
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws InterruptedException {
        LOG.info(message.toString());
        Thread.sleep(1000);
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

}
