package com.david.spring.service;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

@Service
public class ProcessImageServiceImpl implements ProcessImageService {

    @Override
    public String encodeFileToBase64Binary() {
        File file = new File("C:\\Users\\dstojano\\Documents\\car.jpg");
        String encodedFile = null;

        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            encodedFile = Base64.getEncoder().encodeToString(bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return encodedFile;
    }

    @Override
    public byte[] decodeBase64String(String base64String) {
        return Base64.getDecoder().decode(base64String);
    }

    @Override
    public void writeImageToFileSystem(byte[] imageByte) throws IOException {

        BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageByte));
        File outputfile = new File("D:\\Diplomska-Rabota\\result.jpg");
        ImageIO.write(img, "jpg", outputfile);
    }
}
