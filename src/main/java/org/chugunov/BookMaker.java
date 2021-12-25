package org.chugunov;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.chugunov.books.Book;
import org.chugunov.books.Comics;
import org.chugunov.books.Novel;
import org.chugunov.model.Process;
import org.chugunov.utility.PreText;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookMaker {
    public BookMaker(Process process) {
        try(PDDocument document = new PDDocument()){
            Book draft = process.getType().createBook(document, process);

            // createBookContent
            PDPage title_page = draft.createTitlePage();
            List<PDPage> content = draft.createContent(parseHTTP(process, document));

            // composeBookParts
            document.addPage(title_page);

            for (PDPage page : content){
                document.addPage(page);
            }

            // finalise
            document.save(new File(
                (process.getOutputPath() == null || process.getOutputPath().isEmpty())
                    ?  process.getTitle() + ".pdf"
                    : process.getOutputPath() + "/" +  process.getTitle() + ".pdf"
            ));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private String encode(String str){
        return str.replaceAll(" ", "%20");
    }

    private ArrayList <PreText> parseHTTP(Process process, PDDocument document) throws IOException{
        Document doc = Jsoup.connect(process.getAddressToStart()).timeout(0).get();
        
        int debug_link_counter = 0;

        ArrayList <PreText> materialForBook = new ArrayList<>();

        while ( doc!= null  && debug_link_counter < process.getDebugDepth()){  // replace recursively ?
            String new_url = "";
            for (Element current_tag : doc.select(process.getSelectorNavigationNext())){
                 new_url = current_tag.attr("href");
                 System.out.println(new_url);
            }
            
            String title = "";
            for (Element current_tag : doc.select(process.getSelectorTitle())){
                title += current_tag.text();
            }

            PreText pt = new PreText();
            pt.setTitle(title);
            for (Element current_tag : doc.select(process.getSelectorContent())){
                pt.addText( "    " + current_tag.text() );
                String url = current_tag.absUrl("src");
                pt.addImage( encode(url), document );
            }
            
            materialForBook.add( pt );
            
            if( new_url != "" ){ 
                doc = null;
                int reconnect_attempt_count = 0;
                while(doc == null && reconnect_attempt_count < 5){
                    try{
                        //doc = Jsoup.parse(new URL(new_url).openStream(), "UTF-8", new_url);
                        doc = Jsoup.connect(new_url).timeout(0).get(); 
                    }catch(Exception ex){
                        reconnect_attempt_count++;
                    }
                }
            } else break;

            if (process.isDebug()) debug_link_counter++;
        }

        return materialForBook;
    }
}
