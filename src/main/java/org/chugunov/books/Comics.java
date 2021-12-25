package org.chugunov.books;

import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.chugunov.utility.PreText;
import org.chugunov.model.Process;

public class Comics extends Book{

    public Comics(PDDocument document, Process process) {
        super(document, process);
    }

    @Override
    public List<PDPage> createContent(ArrayList <PreText> materialForBook) {
        ArrayList<PDPage> result_content = new ArrayList<>();
        
        // Split all images in a groups of < number_of_images_on_page >
        List< PDImageXObject[] > listOfArraysOfImagesForEveryPage = new ArrayList<>();
        PDImageXObject[] current = new PDImageXObject[process.getNumberOfBlocksOnPage()];
        int counterInArray = 0; int counterPerPage = process.getNumberOfBlocksOnPage();
        for(PreText pt : materialForBook){
            for( int i=0; i<pt.contextImages.size(); i++ ){
                if( counterPerPage == 0 ){
                    listOfArraysOfImagesForEveryPage.add(current);
                    current = new PDImageXObject[process.getNumberOfBlocksOnPage()];
                    counterInArray = 0; 
                    counterPerPage = process.getNumberOfBlocksOnPage();
                }
                current[counterInArray] = pt.contextImages.get(i);
                counterInArray++; counterPerPage--;
            }
        }
        listOfArraysOfImagesForEveryPage.add(current);
        
        // Calculate once position of images on the page
        //PDPage etalonPage = pf.createPDPage();
        PDImageXObject etalonImage = listOfArraysOfImagesForEveryPage.get(0)[0];
        if(etalonImage == null) return result_content;
        
        int padding = 15;
        
        float w_scale = etalonImage.getWidth() / ( etalonPage.getMediaBox().getWidth() - padding * 2 );
        float h_scale = etalonImage.getHeight() / ( etalonPage.getMediaBox().getHeight() / process.getNumberOfBlocksOnPage() - padding * ( 2 + process.getNumberOfBlocksOnPage() - 1 ));
        float scale = w_scale /*> h_scale ? w_scale : h_scale*/;
        float w_size = etalonImage.getWidth() / scale;
        float h_size = etalonImage.getHeight() / scale;
        
        float space = (etalonPage.getMediaBox().getHeight() - h_size * process.getNumberOfBlocksOnPage()) / process.getNumberOfBlocksOnPage();
        float vertical_padding = etalonPage.getMediaBox().getHeight() - space * 0.7f - h_size;
        float interval = space * 0.6f;
                
        // Now place groups of images on the pages
        for(PDImageXObject[] arrayOfImages : listOfArraysOfImagesForEveryPage){
            PDPage page = new PDPage();
            try (PDPageContentStream contentStream = new PDPageContentStream(doc, page)) {
                float offset_y = vertical_padding;
                for(int i=0; i <arrayOfImages.length; i++){
                    arrayOfImages[i].setHeight((int) h_size);
                    arrayOfImages[i].setWidth((int) w_size);
                    contentStream.drawImage(arrayOfImages[i], padding, offset_y);

                    offset_y -= h_size + interval;
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
            result_content.add(page);
        }
        return result_content;
    }
}
