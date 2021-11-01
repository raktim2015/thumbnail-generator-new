package org.madu.thumbnail;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.renderer.IRenderer;

import java.util.List;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;


public class CreatePDFDocument {

    Config config;
    PdfDocument pdf;
    CreateThumbnail thumbnail;
    CreatePDFDocument(List <HashMap <String, String> > images) throws Exception{
        
        config = new Config();
        pdf = new PdfDocument(new PdfWriter("result.pdf"));

        PdfPage page = pdf.addNewPage(PageSize.A4);
        Document doc = new Document(pdf);

        PdfCanvas canvas = new PdfCanvas(page);
        
        int x = config.PAGE_BORDER_X;
        int y = config.PAGE_BORDER_Y;

        for(HashMap <String, String> image : images) {
            
            String imgdir = config.IMAGE_DIR_PATH + "" + image.get("subDir");
            Path path = Paths.get(imgdir);
            if(!Files.exists(path)) {
                // change the position and dont display anything
                x = x + config.OUTER_RECTANGLE_WIDTH + config.IMAGE_GAP_X;
                if((x+config.OUTER_RECTANGLE_WIDTH+config.PAGE_BORDER_X) > config.A4_PDF_DIM[0]) {
                    x = config.PAGE_BORDER_X;
                    y = y + config.OUTER_RECTANGLE_HEIGHT + config.IMAGE_GAP_Y; 
                }
                continue;
            }

            String imagePath = imgdir + "\\" + image.get("imageFile");
            String imageDescPath = imgdir + "\\" + image.get("imageDesc");
            


            thumbnail = new CreateThumbnail();
            Image resizedImage = thumbnail.scaleImage(imagePath);


            // rectangle draw start

            canvas.moveTo(x, config.A4_PDF_DIM[1]-config.RESIZED_IMAGE_HEIGHT-y);
            canvas.lineTo(x+config.OUTER_RECTANGLE_WIDTH,config.A4_PDF_DIM[1]-config.RESIZED_IMAGE_HEIGHT-y);
            canvas.closePathStroke();

            canvas.moveTo(x, config.A4_PDF_DIM[1]-config.RESIZED_IMAGE_HEIGHT-y);
            canvas.lineTo(x, config.A4_PDF_DIM[1]-config.RESIZED_IMAGE_HEIGHT-y+config.OUTER_RECTANGLE_HEIGHT);
            canvas.closePathStroke();

            canvas.moveTo(x, config.A4_PDF_DIM[1]-y+4);
            canvas.lineTo(x+config.OUTER_RECTANGLE_WIDTH, config.A4_PDF_DIM[1]-y+4);
            canvas.closePathStroke();

            canvas.moveTo(x+config.OUTER_RECTANGLE_WIDTH, config.A4_PDF_DIM[1]-config.RESIZED_IMAGE_HEIGHT-y);
            canvas.lineTo(x+config.OUTER_RECTANGLE_WIDTH, config.A4_PDF_DIM[1]-y+4);
            canvas.closePathStroke();

            // rectangle draw end

            Paragraph paragraph = thumbnail.addText(imageDescPath);
            //get paragraph height
            IRenderer paragraphRenderer = paragraph.createRendererSubTree();
            LayoutResult result = paragraphRenderer.setParent(doc.getRenderer()).
                layout(new LayoutContext(new LayoutArea(1, new com.itextpdf.kernel.geom.Rectangle(x, config.A4_PDF_DIM[1]-config.OUTER_RECTANGLE_HEIGHT-y+4, config.OUTER_RECTANGLE_WIDTH-config.RESIZED_IMAGE_WIDTH-2, config.OUTER_RECTANGLE_HEIGHT))));


            float para_height = result.getOccupiedArea().getBBox().getHeight();
            float gap = (config.RESIZED_IMAGE_HEIGHT-para_height);

            paragraph.setFixedPosition(x+2, (config.A4_PDF_DIM[1]-(y+config.RESIZED_IMAGE_HEIGHT)+gap-2), config.OUTER_RECTANGLE_WIDTH-config.RESIZED_IMAGE_WIDTH-2);        
            resizedImage.setFixedPosition(x+(config.OUTER_RECTANGLE_WIDTH-config.RESIZED_IMAGE_WIDTH-2), config.A4_PDF_DIM[1]-config.OUTER_RECTANGLE_HEIGHT-y+6);
            
            doc.add(resizedImage);
            doc.add(paragraph);
            
            // change the position
            x = x + config.OUTER_RECTANGLE_WIDTH + config.IMAGE_GAP_X;
            if((x+config.OUTER_RECTANGLE_WIDTH+config.PAGE_BORDER_X) > config.A4_PDF_DIM[0]) {
                x = config.PAGE_BORDER_X;
                y = y + config.OUTER_RECTANGLE_HEIGHT + config.IMAGE_GAP_Y; 
            }
        }
        doc.close();    
    }
    
}
