package run.itlife.enums;

public enum FileTypes {

    IMAGE_PNG ("image/png"),
    VIDEO_MP4 ("video/mp4"),
    VIDEO_QT ("video/quicktime");

    private String type;

    FileTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
