package com.example.xc_voyager.easylife;

/**
 * Created by xc_voyager on 2017/12/12.
 */

public class CommonNote{
    private String text_name;
    private int image_id;
    public CommonNote(String text_name, int image_id){
        this.text_name = text_name;
        this.image_id = image_id;
    }
    public String getTextName(){
        return text_name;
    }
    public int getImageId(){
        return image_id;
    }
}
