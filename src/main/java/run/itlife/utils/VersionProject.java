package run.itlife.utils;

import java.io.IOException;
import java.util.Properties;

public class VersionProject {
    private String majorVersion;
    private String minorVersion;
    private String microVersion;
    private String stageVersion;

    public VersionProject getVersionProject() throws IOException {
        Properties props = new Properties();
        props.load(VersionProject.class.getResourceAsStream("/version.properties"));
        this.setMajorVersion(props.getProperty("majorVersion"));
        this.setMinorVersion(props.getProperty("minorVersion"));
        this.setMicroVersion(props.getProperty("microVersion"));
        this.setStageVersion(props.getProperty("stageVersion"));
        return this;
    }

    public String getMajorVersion() {
        return majorVersion;
    }

    public void setMajorVersion(String majorVersion) {
        this.majorVersion = majorVersion;
    }

    public String getMinorVersion() {
        return minorVersion;
    }

    public void setMinorVersion(String minorVersion) {
        this.minorVersion = minorVersion;
    }

    public String getMicroVersion() {
        return microVersion;
    }

    public void setMicroVersion(String microVersion) {
        this.microVersion = microVersion;
    }

    public String getStageVersion() {
        return stageVersion;
    }

    public void setStageVersion(String stageVersion) {
        this.stageVersion = stageVersion;
    }
}
