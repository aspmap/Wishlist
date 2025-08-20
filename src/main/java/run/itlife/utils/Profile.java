package run.itlife.utils;

import java.io.File;

public class Profile {
    public static void recursiveFilesDelete(File file) {
        if (!file.exists())
            return;

        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                recursiveFilesDelete(f);
            }
        }
        file.delete();
    }
}
