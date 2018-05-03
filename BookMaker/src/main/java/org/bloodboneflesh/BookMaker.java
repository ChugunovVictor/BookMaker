package org.bloodboneflesh;

import java.awt.Image;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.pdfbox.contentstream.PDContentStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.bloodboneflesh.books.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BookMaker {
    Book draft;
    
    PDDocument document;
    PDContentStream contentStream;
        
    boolean debug;
    String address_to_start;
    String selector_content;
    String selector_title;
    String selector_navigation_next;
    
    private void parseHTTP() throws IOException{
        Document doc = Jsoup.connect(address_to_start).timeout(0).get();
        
        int debug_link_counter = 3;        
    
        while ( doc!= null  && debug_link_counter != 0 ){
            
            Elements selected_url_tags = doc.select(selector_navigation_next);
            String new_url = "";
            for (Element current_tag : selected_url_tags){
                 new_url = current_tag.attr("href");
                 System.out.println(new_url);
            }
            
            Elements selected_title_tags = doc.select(selector_title);
              
            String title = "";
            for (Element current_tag : selected_title_tags){
                title += current_tag.text();
            }

            String content_text = "";
            List <PDImageXObject> content_images = new ArrayList<>();
            Elements selected_tags = doc.select(selector_content);
            for (Element current_tag : selected_tags){
                content_text += "    " + current_tag.text() + "\n";
                try{
                    content_images.add( getImage(document, current_tag.absUrl("src")) );
                } catch (Exception ex){
                
                }
            }
            
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
    List<PDPage> table_of_contents;
    List<PDPage> content;
        
    private void createBookContent(){
        this.title_page = draft.createTitlePage();
        this.content = draft.createContent();
        this.table_of_contents = draft.createTableOfContents();
    }
    
    private void composeBookParts(){
        document.addPage(title_page);
        
        for (PDPage page : table_of_contents){
            setPageNumber(page);
            document.addPage(page);
        }
        
        for (PDPage page : content){
            setPageNumber(page);
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
    
    private void setPageNumber(PDPage page){}
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void createBook2() throws IOException{
        int link_counter = 5;
        
        Document doc = Jsoup.connect(address_to_start).timeout(0).get();
        //Document doc = Jsoup.parse(new URL(adress_to_start).openStream(), "UTF-8", adress_to_start);
        PDDocument document = new PDDocument();
        
        ArrayList <PreText> materialForBook = new ArrayList<>();
            
        while ( doc!= null  && link_counter != 0 ){
            
            Elements selected_url_tags = doc.select(selector_navigation_next);
            String new_url = "";
            for (Element current_tag : selected_url_tags){
                 new_url = current_tag.attr("href");
                 System.out.println(new_url);
            }
            
            Elements selected_title_tags = doc.select(selector_title);
              
            String title = "";
            for (Element current_tag : selected_title_tags){
                title += current_tag.text();
            }

            String content_text = "";
            List <PDImageXObject> content_images = new ArrayList<>();
            Elements selected_tags = doc.select(selector_content);
            for (Element current_tag : selected_tags){
                content_text += "    " + current_tag.text() + "\n";
                try{
                    content_images.add( getImage(document, current_tag.absUrl("src")) );
                } catch (Exception ex){
                
                }
            }
            
            materialForBook.add( 
                    new PreText(title, content_text, content_images, draft.calculateLines(Book.font_standart, content_text))
            );
            // addParagraph(document, title , content_text, standart_font, courcive_font, bold_font);
            
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
            link_counter--;
        }
        try {
            draft.createBook(document, materialForBook);
        } catch (Exception ex) {
            ex.printStackTrace();
        }    
        
        document.save(new File(book_file));
        document.close();
    }
    
    public PDImageXObject getImage(PDDocument doc, String src) throws Exception{
        URL url = new URL(src);
        InputStream in = url.openStream();
        //Image result = ImageIO.read(in);
        PDImageXObject result = JPEGFactory.createFromStream(doc, in);
        in.close();
        return result;
    }
    
    
    public void getImage2(String src) throws Exception{
        URL url = new URL(src);
        InputStream in = url.openStream();
        OutputStream out = new BufferedOutputStream(new FileOutputStream( "awds"));
        for (int b; (b = in.read()) != -1;) {
            out.write(b);
        }
        out.close();
        in.close();
    }
    
    
    
    
    
    
    
    public void setDebug(boolean debug) {
        this.debug = debug;
    }
        
    public void setDraft(Book draft) {
        this.draft = draft;
    }
    
    public void setBook_file(String book_file) {
        this.book_file = book_file;
    }
    
    public void setAddress_to_start(String address_to_start) {
        this.address_to_start = address_to_start;
    }

    public void setSelector_content(String selector_content) {
        this.selector_content = selector_content;
    }

    public void setSelector_title(String selector_title) {
        this.selector_title = selector_title;
    }

    public void setSelector_navigation_next(String selector_navigation_next) {
        this.selector_navigation_next = selector_navigation_next;
    }
}
