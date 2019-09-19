package org.chen.groovy

public class ProjectVersion {
    private int major;
    private int minor;

    public ProjectVersion(int major, int minor) {
        this.major = major;
        this.minor = minor;
    }

    public int getMajor() {
        major
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        minor
    }

    public void setMinor(int minor) {
        this.minor = minor
    }
}

