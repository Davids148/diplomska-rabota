package com.david.spring.entity;

public class Image {

    private String encodedImage;

    public Image(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    public Image(){}
    public String getEncodedImage() { return encodedImage; }
    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }
}
