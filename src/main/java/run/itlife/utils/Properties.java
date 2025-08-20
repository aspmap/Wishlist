package run.itlife.utils;

public class Properties {

    public interface Files {
        int MAX_UPLOAD_FILE_SIZE_IN_MB = 20 * 1024 * 1024; // 20 МБ
        int MAX_UPLOAD_MUSIC_FILE_SIZE_IN_MB = 100 * 1024 * 1024; // 100 МБ
        int MAX_UPLOAD_VIDEO_FILE_SIZE_IN_MB = 100 * 1024 * 1024; // 100 МБ
        int IMAGE_WIDTH = 500;
        int IMAGE_HEIGHT = 500;
        int WIDTH_QR_CODE = 400;
        int HEIGHT_QR_CODE = 400;
    }

    public interface Paths {
        String PATH_VIDEO_USERS = "/resources/video/users/";
        String PATH_IMAGE_USERS = "/resources/img/users/";
        String PATH_MUSIC_USERS = "/resources/music/users/";
        String PATH_FILES = "/resources/users_archive/users/";
        String PATH_COMMON_FILES = "/resources/common/utils/";
        String SEPARATOR = "/";
        String COMMA = ",";
        String POINT = ".";
        String UNDERSCORE = "_";
    }

    public interface ErrorMessages {
        String ERROR = "Error: ";
        String WARNING = "Warning: ";
        String INFO = "Info: ";
        String DEBUG = "Debug: ";
        String NOT_PUBLISH_POST = "Publishing post error. The file does not match the format, or the file name is too long, or the file is not attached";
        String NOT_PUBLISH_BUG = "Publishing bug error";
        String NOT_PUBLISH_COMMENT = "Publishing comment error";
        String NOT_PUBLISH_MESSAGE = "Publishing message error";
        String NOT_PUBLISH_SONG = "Publishing song error. The file does not match the format, or the file name is too long, or the file is not attached";
        String NOT_PUBLISH_PLAYLIST = "Publishing playlist error";
        String NOT_ADD_SONG_TO_PLAYLIST = "Add song to playlist error";
        String NOT_PUBLISH_TODO_TASK = "Publishing TODO-task error";
        String NOT_PUBLISH_WISHLIST = "Publishing wishlist error. The file does not match the format, or the file name is too long, or the file is not attached";
        String HANDSHAKE_SEARCH_ERROR = "Handshake search error";
    }
}