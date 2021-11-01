package org.madu.thumbnail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class App 
{
    
    static List <HashMap<String, String> > images;
    static Config config; 
    App() {

        config = new Config();
        images = new ArrayList <HashMap<String, String> > ();
        for(int i=1;i<=6;i++) {
            HashMap <String, String> imageInfo = new HashMap<String,String>();
            imageInfo.put("subDir",i+"");
            imageInfo.put("imageFile","image"+i+".jpg");
            imageInfo.put("imageDesc","desc"+i+".txt");
            imageInfo.put("imageOutput","image"+i+"_out.jpg");
            images.add(imageInfo);
        }

        //insert garbage at random pos
        Random rand = new Random();
        for(int i=1;i<=5;i++) {
            int pos = rand.nextInt(images.size()-1);
            HashMap <String, String> imageInfo = new HashMap<String,String>();
            imageInfo.put("subDir","12345");
            imageInfo.put("imageFile","image"+i+".jpg");
            imageInfo.put("imageDesc","desc"+i+".txt");
            imageInfo.put("imageOutput","image"+i+"_out.jpg");
            images.add(pos, imageInfo);

        }
    }
    public static void main ( String[] args ) throws Exception
    {
        App obj = new App();
        new CreatePDFDocument(images);
    }
}
