package org.chen.jenkins.client.api;

import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.QueueReference;
import org.chen.jenkins.client.dto.BuildDTO;
import org.chen.jenkins.client.service.JenkinsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author chensj
 * @date 2019-09-19 16:23
 */
@RestController
public class JenkinsController {
    @Autowired
    private JenkinsService jenkinsService;

    @GetMapping(value = "/jenkins")
    public Map<String, Job> getJenkins() throws IOException, URISyntaxException, InterruptedException {
        return jenkinsService.getJobs();
    }

    @GetMapping(value = "/jenkins/create")
    public String create() throws IOException, URISyntaxException {
        jenkinsService.createJob();
        return "Jenkins Job 创建成功";
    }

    @GetMapping(value = "/jenkins/delete")
    public String delete() throws IOException, URISyntaxException {
        jenkinsService.deleteJob();
        return "Jenkins Job 删除成功";
    }

    @GetMapping(value = "/jenkins/list")
    public String getJenkinsList(@RequestParam String jobName) throws IOException, URISyntaxException,
            InterruptedException {
        return jenkinsService.getDetailList(jobName);
    }

    @GetMapping(value = "/jenkins/xml")
    public String getJenkinsXml(@RequestParam String jobName) throws IOException, URISyntaxException,
            InterruptedException {
        return jenkinsService.getJobXml(jobName);
    }

    @GetMapping(value = "/jenkins/build")
    public QueueReference build(@RequestParam String jobName) throws IOException, URISyntaxException,
            InterruptedException {
        return jenkinsService.build(jobName);
    }

    @GetMapping(value = "/jenkins/build/item")
    public String build(@RequestParam String jobName, @RequestParam Integer buildNum) throws IOException,
            URISyntaxException, InterruptedException {
        return jenkinsService.buildItemInfo(jobName, buildNum);
    }

    @GetMapping(value = "/jenkins/check")
    public BuildDTO check(@RequestParam String jobName) throws IOException,
            URISyntaxException, InterruptedException {
        return jenkinsService.checkIsBuild(jobName);
    }

}
