package run.itlife.enums;

public enum FileExtensions {

    MP4 ("mp4"),
    MP3 ("mp3"),
    MOV ("mov"),
    JPG ("jpg"),
    PNG ("png"),
    TXT ("txt"),
    ZIP ("zip");

    private String extension;

    FileExtensions(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}
