package org.bloodboneflesh.books;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.bloodboneflesh.PreText;

public class Comics extends Book{
    public void printParagraph( PreText current, PDDocument doc, boolean isTOC, ArrayList<PreText> material) throws IOException {
        int page_counter = current.page_for_table_of_content;
        
        if(isTOC){
            for (int i=0; i< material.size();  )
            {
                if ( i == 0 ) // add string with paragraph title 
                {
                    /* -2 because title has x2 font */
                    int finalDestination = i + number_of_rows_on_page - 1;
                    if ( finalDestination > material.size() ) finalDestination = material.size();

                    current.pages.add( printTOCPage(doc, material.subList(i, finalDestination ), fontSize, margin , current.title, page_counter, true));
                    
                    page_counter++;

                    i+= number_of_rows_on_page - 1;
                }else{
                    int finalDestination = i + number_of_rows_on_page;
                    if ( finalDestination > material.size() ) finalDestination = material.size();

                    current.pages.add(printTOCPage(doc, material.subList(i, finalDestination ), fontSize, margin , current.title, page_counter, false));
                    
                    page_counter++;

                    i+= number_of_rows_on_page;
                }
            }
        }else{
            for (int i=0; i< current.contextImages.size();  )
            {
                PDPage page = new PDPage();
                try (PDPageContentStream contentStream = new PDPageContentStream(doc, page)) {
                    contentStream.drawImage(current.contextImages.get(i), 70, 250);
                }catch(Error ex){}
                current.pages.add(page);
            }
        }
    }
}
