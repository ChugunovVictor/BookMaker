package org.chugunov;

import org.chugunov.utility.PreText;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.contentstream.PDContentStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.chugunov.books.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class BookMaker {
    Book draft;

    boolean debug;
    String address_to_start;
    String selector_content;
    String selector_title;
    String selector_navigation_next;
    
    PDDocument document;
    PDContentStream contentStream;
    
    ArrayList <PreText> materialForBook = new ArrayList<>();
    
    PreText.PreTextFactory ptf;
    
    private void parseHTTP() throws IOException{
        Document doc = Jsoup.connect(address_to_start).timeout(0).get();
        
        int debug_link_counter = 3;        
    
        while ( doc!= null  && debug_link_counter != 0 ){  // replace recursively ? 
            String new_url = "";
            for (Element current_tag : doc.select(selector_navigation_next)){
                 new_url = current_tag.attr("href");
                 System.out.println(new_url);
            }
            
            String title = "";
            for (Element current_tag : doc.select(selector_title)){
                title += current_tag.text();
            }

            PreText pt = ptf.createPreText();
            pt.setTitle(title);
            for (Element current_tag : doc.select(selector_content)){
                pt.addText( "    " + current_tag.text() );
                pt.addImage( current_tag.absUrl("src") );
            }
            
            materialForBook.add( pt );
            
            if( new_url != "" ){ 
                doc = null;
                while(doc == null){
                    try{
                        //doc = Jsoup.parse(new URL(new_url).openStream(), "UTF-8", new_url);
                        doc = Jsoup.connect(new_url).timeout(0).get(); 
                    }catch(Exception ex){
                        // try connect again and again until success    
                    }
                }
            }else break;
            
            if (debug) debug_link_counter--;
        }
    }

    PDPage title_page;
    List<PDPage> content;
        
    private void createBookContent(){
        this.title_page = draft.createTitlePage();
        this.content = draft.createContent(materialForBook);
    }
    
    private void composeBookParts(){
        document.addPage(title_page);
     
        for (PDPage page : content){
            document.addPage(page);
        }
    }
    
    String book_file;
    
    private void createFile() throws IOException{
        document.save(new File(book_file));
        document.close();
    }
    
    public void createBook(){
        try{
            parseHTTP();
            createBookContent();
            composeBookParts();
            createFile();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
