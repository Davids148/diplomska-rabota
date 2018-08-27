package com.david.spring.service;

import com.david.spring.entity.Image;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.*;

@Service
public class FirebaseConnectorServiceImpl implements FirebaseConnectorService {

    private static final String ACCOUNT_SERVICE_JSON_KEY = "D:\\Diplomska-Rabota v1.1\\Diplomska-Rabota\\davidfirebase-7b3fc-firebase-adminsdk-dpeie-507c197d7a.json";
    private static final String DATABASE_URL = "https://davidfirebase-7b3fc.firebaseio.com";

    @Override
    public void connectToFirebase() {

        FirebaseOptions options = null;
        try {
            FileInputStream serviceAccount = new FileInputStream(ACCOUNT_SERVICE_JSON_KEY);

            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(DATABASE_URL)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (options != null) {
            FirebaseApp.initializeApp(options);
        }
    }

    public List<Image> getFirebaseData(String referenceName) {

        List<Image> imageList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(referenceName);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {



            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getChildren().forEach(e -> imageList.add(e.getValue(Image.class)));
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        return imageList;
    }

    @Override
    public void saveFirebaseData(String referenceName, Image encodedImage) {

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference().child(referenceName).push();

        ref.setValue(encodedImage, null);
    }
}
