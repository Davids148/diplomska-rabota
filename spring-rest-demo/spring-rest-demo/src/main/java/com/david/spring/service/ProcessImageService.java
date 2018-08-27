package com.david.spring.service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public interface ProcessImageService {

    /**
     * Encodes a gives image into a base64 string - only for testing purposes the real base64 will be sent by the python script
     * @return encodede base64 string
     */
    String encodeFileToBase64Binary();

    byte[] decodeBase64String(String base64String);
    void writeImageToFileSystem(byte[] imageByte) throws IOException;
}
