// (Recycler View phase)(Fragments phase)
// we are using this to parse the data when we get it from firebase


package com.zshan.koqzs.androidonlinequizapp.Model;
public class Category {

    private  String Name;
    private String Image;

    public Category() {
    }

    public Category(String name, String image) {
        Name = name;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
