package org.madu.thumbnail;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;


public class CreateThumbnail {

    Graphics2D g2d;
    BufferedImage container;
    Config config;

    CreateThumbnail() throws Exception {

        config = new Config();

    }

    // public Image drawRectangle() throws Exception {

    //     container = new BufferedImage(config.OUTER_RECTANGLE_WIDTH+1,config.OUTER_RECTANGLE_HEIGHT+1, BufferedImage.TYPE_INT_ARGB);
    //     g2d = container.createGraphics();

    //     //draw rectangle
    //     g2d.drawRect(0,0,config.OUTER_RECTANGLE_WIDTH,config.OUTER_RECTANGLE_HEIGHT);
        

    //     ByteArrayOutputStream baos = new ByteArrayOutputStream();
    //     ImageIO.write(container, "png", baos);
    //     Image rect = new Image(ImageDataFactory.create(baos.toByteArray()));
    //     rect.setMargins(0, 0, 0,0);

    //     // Rectangle rect = new Rectangle(0,0,120,80);
    //     // rect.setBackgroundColor(BaseColor.BLUE);
    //     // rect.setBorder(Rectangle.BOX);
    //     // rect.setBorderColor(BaseColor.BLACK);
    //     // rect.setBorderWidth(2);
        
    //     return rect;
    // }

    public Paragraph addText(String imageDescPath) throws Exception {

        String text = new String(Files.readAllBytes(Paths.get(imageDescPath)));
        Paragraph par = new Paragraph(text);
        par.setMargin(0);
        par.setPadding(0);
        par.setFontSize(5.0f);
        return par;
    }

    public Image scaleImage(String imagePath) throws Exception{

        
        ImageData data = ImageDataFactory.create(imagePath);
        
        // scale image
        Image image = new Image(data);
        image.scaleAbsolute(config.RESIZED_IMAGE_WIDTH, config.RESIZED_IMAGE_HEIGHT);
        image.setMargins(0, 0, 0, 0);
        return image;

    }
  
}