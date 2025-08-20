package run.itlife.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.security.core.context.SecurityContextHolder;
import static run.itlife.utils.Properties.Paths.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;

import static run.itlife.utils.Properties.Files.*;

public class ZXingQR {
    public static byte[] qrcode(HttpServletResponse response) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
            String path = "wishlist.top" + SEPARATOR + "wishlist_subscriber" + SEPARATOR + username;
            response.setContentType("image/png");
            return ZXingQR.getQRCodeImage(path, WIDTH_QR_CODE, HEIGHT_QR_CODE);
    }

    public static byte[] getQRCodeImage(String text, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }
}
