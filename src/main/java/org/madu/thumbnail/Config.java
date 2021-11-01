package org.madu.thumbnail;

import com.itextpdf.kernel.geom.PageSize;

public class Config {

    public String IMAGE_DIR_PATH = "C:\\Users\\rakti\\Documents\\Work\\Upwork\\thumbnail-generator-new\\src\\assets\\";
    public int IMAGE_GAP_X = 10;
    public int IMAGE_GAP_Y = 10;
    public int PAGE_BORDER_X = 50;
    public int PAGE_BORDER_Y = 20;
    public int A4_PDF_DIM[] = new int[]{(int)PageSize.A4.getRight(), (int)PageSize.A4.getTop()};    //size in pixels. A4 size pdf with 300dpi

    public int OUTER_RECTANGLE_WIDTH = 120;
    public int OUTER_RECTANGLE_HEIGHT = 60;
    public int TEXT_CONTAINER_WIDTH;
    public int TEXT_CONTAINER_HEIGHT;
    public int COLUMNS;
    public int RESIZED_IMAGE_WIDTH;
    public int RESIZED_IMAGE_HEIGHT;

    Config() {
        
        // calculate no. of columns
        int tempcols = 100;
        int required_width = 0;
        do {
            tempcols--;
            required_width = PAGE_BORDER_X*2 + IMAGE_GAP_X*(tempcols-1) + tempcols*OUTER_RECTANGLE_WIDTH;
        }while (required_width>A4_PDF_DIM[0]);

        COLUMNS = tempcols;
        
        //calcuate resized square image size.
        RESIZED_IMAGE_HEIGHT = OUTER_RECTANGLE_HEIGHT-4;
        RESIZED_IMAGE_WIDTH = OUTER_RECTANGLE_HEIGHT-4;

    }
    
    // public int COLUMNS = 4;
    // public int IMAGE_WIDTH = 250;
    // public int IMAGE_HEIGHT = 250;
    // public int OUTER_RECTANGLE_WIDTH = (int)((A4_PDF_DIM[0]-(2*PAGE_BORDER_X)-((COLUMNS-1)*IMAGE_GAP_X))/COLUMNS);


}
