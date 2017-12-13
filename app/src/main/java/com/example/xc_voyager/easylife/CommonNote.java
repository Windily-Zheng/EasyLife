package com.example.xc_voyager.easylife;

/**
 * Created by xc_voyager on 2017/12/12.
 */

public class CommonNote{
    private String text_name_1;
    private String text_name_2;
    private String text_name_3;
    private int image_id_1;
    private int image_id_2;
    public CommonNote(String text_name_1, String text_name_2, String text_name_3, int image_id_1, int image_id_2){
        this.text_name_1 = text_name_1;
        this.text_name_2 = text_name_2;
        this.text_name_3 = text_name_3;
        this.image_id_1 = image_id_1;
        this.image_id_2 = image_id_2;
    }
    public String getTextName(int i){
        switch(i){
            case 1: return text_name_1;
            case 2: return text_name_2;
            case 3: return text_name_3;
            default:return "No this string.";
        }
    }
    public int getImageId(int i){
        switch(i){
            case 1: return image_id_1;
            case 2: return image_id_2;
            default:return -1;
        }
    }
}
