package com.example.xc_voyager.easylife;

/**
 * Created by xc_voyager on 2017/12/12.
 */

public class CommonNote{
    private String text_name;
    private String image_text;
    public CommonNote(String text_name, String image_text){
        this.text_name = text_name;
        this.image_text = image_text;
    }
    public String getTextName(){
        return text_name;
    }
    public String getImagetext(){
        return image_text;
    }
}
