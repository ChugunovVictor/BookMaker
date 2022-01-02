package org.chugunov;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.chugunov.books.Book2;
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
    Logger logger = new Logger();

    public BookMaker(Process process) {
        try(PDDocument document = new PDDocument()){
            Book2 draft = new Novel(document, process);

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
                (process.getBasic().getOutputPath() == null || process.getBasic().getOutputPath().isEmpty())
                    ?  process.getBasic().getTitle() + ".pdf"
                    : process.getBasic().getOutputPath() + "/" +  process.getBasic().getTitle() + ".pdf"
            ));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private String encode(String str){
        return str.replaceAll(" ", "%20");
    }

    private ArrayList <PreText> parseHTTP(Process process, PDDocument document) throws IOException{
        Document doc = Jsoup.connect(process.getContent().getAddressToStart()).timeout(0).get();
        
        int debug_link_counter = 0;

        ArrayList <PreText> materialForBook = new ArrayList<>();

        while ( doc!= null  && debug_link_counter < process.getPreview().getDebugDepth()){  // replace recursively ?
            String new_url = "";
            for (Element current_tag : doc.select(process.getContent().getSelectorNavigationNext())){
                new_url = current_tag.attr("href");
                logger.log(new_url);
            }
            
            String title = "";
            for (Element current_tag : doc.select(process.getContent().getSelectorTitle())){
                title += current_tag.text();
            }

            PreText pt = new PreText();
            pt.setTitle(title);
            for (Element current_tag : doc.select(process.getContent().getSelectorContent())){
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

            if (process.getPreview().isDebug()) debug_link_counter++;
        }

        return materialForBook;
    }
}
