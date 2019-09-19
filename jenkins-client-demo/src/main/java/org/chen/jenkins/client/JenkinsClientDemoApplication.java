package org.chen.jenkins.client;

import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.QueueReference;
import org.chen.jenkins.client.service.JenkinsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author chensj
 */
@SpringBootApplication
public class JenkinsClientDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JenkinsClientDemoApplication.class, args);
    }
}
