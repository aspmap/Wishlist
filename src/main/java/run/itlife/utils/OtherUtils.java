package run.itlife.utils;

import java.util.UUID;

public class OtherUtils {
    public static String generateFileName() {
        String fileName;
        UUID uuid = UUID.randomUUID();
        fileName = uuid.toString();
        return fileName;
    }

}
