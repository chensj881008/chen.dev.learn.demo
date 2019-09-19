package org.chen.jenkins.client.service;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.*;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.chen.jenkins.client.dto.BuildDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author chensj
 * @date 2019-09-18 20:20
 */
@Service
public class JenkinsService {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(JenkinsService.class);

    private final static String JENKINS_URL = "http://172.17.1.242:8080";
    private final static String JENKINS_USERNAME = "admin";
    private final static String JENKINS_PASSWORD = "admin";

    private synchronized JenkinsServer getServerInstance() throws URISyntaxException {
        return new JenkinsServer(new URI(JENKINS_URL), JENKINS_USERNAME, JENKINS_PASSWORD);
    }

    public Map<String, Job> getJobs() throws URISyntaxException, IOException, InterruptedException {
        return getServerInstance().getJobs();
    }

    public void createJob() throws URISyntaxException, IOException {
        getServerInstance().createJob(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                "<?xml version='1.1' encoding='UTF-8'?>\n<maven2-moduleset plugin=\"maven-plugin@3.4\"> " +
                        "</maven2-moduleset>");
    }

    public void deleteJob() throws URISyntaxException, IOException {
        getServerInstance().deleteJob(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }

    public String getDetailList(String jobName) throws URISyntaxException, IOException, InterruptedException {
        Map<String, Job> jobs = getServerInstance().getJobs();
        Job job = jobs.get(jobName);
        JobWithDetails details = job.details();
        logger.info("JobWithDetails: [{}]", ReflectionToStringBuilder.toString(details));
        logger.info("builds first element: [{}]", ReflectionToStringBuilder.toString(details.getBuilds().get(0)));
        logger.info("build first build: [{}]", ReflectionToStringBuilder.toString(details.getFirstBuild()));
        logger.info("build last  build: [{}]", ReflectionToStringBuilder.toString(details.getLastBuild()));
        logger.info(ReflectionToStringBuilder.toString(details, ToStringStyle.MULTI_LINE_STYLE));
        return ReflectionToStringBuilder.toString(details, ToStringStyle.MULTI_LINE_STYLE);
    }

    public QueueReference build(String jobName) throws URISyntaxException, IOException, InterruptedException {
        Map<String, Job> jobs = getServerInstance().getJobs();
        Job job = jobs.get(jobName);
        return job.build();
    }

    public String buildItemInfo(String jobName, int buildNum) throws URISyntaxException, IOException,
            InterruptedException {
        Map<String, Job> jobs = getServerInstance().getJobs();
        Job job = jobs.get(jobName);
        Build buildByNumber = job.details().getBuildByNumber(buildNum);
        return ReflectionToStringBuilder.toString(buildByNumber, ToStringStyle.MULTI_LINE_STYLE);
    }

    public BuildDTO checkIsBuild(String jobName) throws URISyntaxException, IOException {
        Map<String, Job> jobs = getServerInstance().getJobs();
        Job job = jobs.get(jobName);
        JobWithDetails details = job.details();
        Build lastBuild = details.getLastBuild();
        Build lastCompletedBuild = details.getLastCompletedBuild();
        BuildWithDetails details1 = lastBuild.details();
        BuildDTO dto = new BuildDTO();
        dto.setJobName(jobName);
        dto.setIsRunning(details1.isBuilding());
        dto.setCurrentBuildNumber(lastBuild.getNumber() + "");
        dto.setBuildResult(details1.getResult() == null ? "构建中" : details1.getResult().toString());
        dto.setBuildName(details1.getDisplayName());
        if (lastBuild.getNumber() == lastCompletedBuild.getNumber()) {
            dto.setBuildLog(details1.getConsoleOutputText());
            dto.setStatus("构建完成");
            String format = String.format("%s 构建完成, 最后一次构建是[%s], \n当前状态[%s], 构建结果[%s], \n构建日志:\n[%s]", jobName,
                    lastBuild.getNumber(),
                    details1.isBuilding(), details1.getResult(), details1.getConsoleOutputText());
            logger.info(format);
            return dto;
        } else {
            dto.setBuildLog(details1.getConsoleOutputText());
            dto.setStatus("构建中...");
            String format = String.format("%s 构建中..., 最后一次构建是[%s], \n当前状态[%s], 构建结果[%s], \n构建日志:\n[%s]", jobName,
                    lastBuild.getNumber(),
                    details1.isBuilding(), details1.getResult(), details1.getConsoleOutputText());
            logger.info(format);
            return dto;
        }

    }
}
