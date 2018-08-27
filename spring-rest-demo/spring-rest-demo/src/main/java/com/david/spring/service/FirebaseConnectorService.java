package com.david.spring.service;

import com.david.spring.entity.Image;

import java.util.List;

public interface FirebaseConnectorService {

    void connectToFirebase();
    List<Image> getFirebaseData(String referenceName);
    void saveFirebaseData(String referenceName, Image encodedImage);
}
