package com.david.spring.controller;


import com.david.spring.entity.Image;
import com.david.spring.service.FirebaseConnectorService;
import com.david.spring.service.ProcessImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class HomeController {

    private static final String IMAGE_TABLE_NAME = "images";

    @Autowired
    private ProcessImageService processImageService;

    @Autowired
    private FirebaseConnectorService firebaseConnectorService;

    @PostMapping("/security-image")
    public String uploadImage2(@RequestParam("encodedImage") String imageValue) {

        try {

//          String imageValue = processImageService.encodeFileToBase64Binary();
            byte[] imageByte = processImageService.decodeBase64String(imageValue);
            processImageService.writeImageToFileSystem(imageByte);

            return "success ";
        } catch (Exception e) {
            return "error = " + e;
        }
    }


    @GetMapping("/connect-firebase")
    public void testFirebaseConnection() {

        firebaseConnectorService.connectToFirebase();
    }

    @GetMapping("/get-data-firebase")
    public List<Image> getFirebaseData() {

        return firebaseConnectorService.getFirebaseData("images");
    }

    @PostMapping(value = "save-data-firebase", consumes = "application/json", produces = "application/json")
    public void saveFirebaseData(@RequestBody Image encodedImage) {

        firebaseConnectorService.saveFirebaseData(IMAGE_TABLE_NAME, encodedImage);
    }
}
