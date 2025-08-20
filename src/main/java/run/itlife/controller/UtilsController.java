package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import run.itlife.utils.CommonsParams;

import javax.servlet.ServletContext;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static run.itlife.utils.OtherUtils.generateFileName;
import static run.itlife.utils.Properties.Paths.*;

@Controller
@RequestMapping("/utils")
public class UtilsController {
    @Autowired
    CommonsParams commonsParams;
    private final ServletContext context;

    public UtilsController(ServletContext context) {
        this.context = context;
    }

    @GetMapping("/change_date")
    @PreAuthorize("hasRole('ADMIN')")
    public String changeDate(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        return "admin/utils/change-date";
    }

    @PostMapping("/change_date")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveDate(ModelMap modelMap, @RequestParam("file") MultipartFile file, @RequestParam(name = "newDate") String newDate) throws IOException {
        commonsParams.setCommonParams(modelMap);
        BufferedOutputStream stream = null;
        if (file.getContentType() != null) {
            try {
                String filename = generateFileName() + POINT + "jpg";
                File dir = new File(context.getRealPath(PATH_COMMON_FILES));
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                byte[] bytes = file.getBytes();
                stream = new BufferedOutputStream(new FileOutputStream(new File(dir + SEPARATOR + filename)));
                stream.write(bytes);
                stream.close();

                String path = dir + SEPARATOR + filename;
                Path filePath = Paths.get(path);
                BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);

                // Сохранение даты в метаданные файла (пример) (Не все файловые системы поддерживают сохранение в метаданные)
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                Date formattedDate = dateFormat.parse(newDate);
                Instant ct = formattedDate.toInstant();
                FileTime ft = FileTime.from(ct);
                Files.setAttribute(filePath, "creationTime", ft);
                modelMap.put("filename", filename);
            } catch (IOException e) {
                stream.close();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return "admin/utils/change-date-result";
    }
}
