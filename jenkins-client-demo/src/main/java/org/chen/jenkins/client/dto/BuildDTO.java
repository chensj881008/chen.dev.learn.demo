package org.chen.jenkins.client.dto;

import lombok.Data;

/**
 * @author chensj
 * @date 2019-09-19 16:51
 */
@Data
public class BuildDTO {

    private String jobName;
    private Boolean isRunning;
    private String currentBuildNumber;
    private String status;
    private String buildResult;
    private String buildName;
    private String buildLog;
}
