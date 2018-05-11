package org.bloodboneflesh.books;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDBorderStyleDictionary;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageXYZDestination;
import org.bloodboneflesh.utility.PreText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.Main;

public abstract class Book {
    
    public interface PDPageFactory{
        public PDPage createPDPage();
    }
    
    @Value("${book_author}") String book_author;
    @Value("${book_title}")  String book_title;
    @Value("${book_site}")  String book_site;
    
    public int number_of_rows_on_page = 38;
    public int number_of_images_on_page = 3;
    float fontSize = 12;
    float margin = 50;
    float width = 620 - 2*margin;

    public static PDFont font_standart = PDType1Font.TIMES_ROMAN;    //PDType0Font.load(document, new File("times.ttf")); 
    public static PDFont font_italic   = PDType1Font.TIMES_ITALIC;   //PDType0Font.load(document, new File("timesi.ttf")); 
    public static PDFont font_bold     = PDType1Font.TIMES_BOLD;     //PDType0Font.load(document, new File("timesbd.ttf"));
    
    @Autowired PDDocument doc;
    @Autowired Book.PDPageFactory pf;
    @Autowired Main.ContextFactory cf;
    
    public PDPage createTitlePage(){ 
        PDPage page = pf.createPDPage();
        try (PDPageContentStream contentStream = (PDPageContentStream)cf.getBeanWithParameters
            (PDPageContentStream.class, doc, page)) {
            
            PDRectangle mediabox = page.getMediaBox();
            
            /* print author */
            float authorWidth = font_standart.getStringWidth(book_author) / 1000 * fontSize;
            printText(new String[]{ book_author }, font_standart, fontSize, mediabox.getUpperRightX() - margin - authorWidth,
                    mediabox.getUpperRightY() - margin, 0, contentStream);
            
            /* print title */
            float titleWidth = font_bold.getStringWidth(book_title) / 1000 * fontSize * 5;
            printText(new String[]{ book_title }, font_bold, fontSize * 5, 
                    (mediabox.getWidth() - titleWidth)/2 , mediabox.getHeight()/2, 0, contentStream);
            
            /* print notes */
            printText(new String[]{ 
                "Made with https://jsoup.org/", 
                "Made with https://pdfbox.apache.org/",
                "Using materials from " + book_site
            }, font_italic, fontSize, 
                    margin + mediabox.getLowerLeftX() , 26 + margin + mediabox.getLowerLeftY(), -13, contentStream);

        } catch(Exception ex){
            ex.printStackTrace();
        }
        return page;
    }
    
    abstract public List<PDPage> createContent(ArrayList <PreText> materialForBook);
    
    
    public void addHiperLink(PDPageContentStream contents, PDPage target_page, float offset_y, String link_text, int page_number, PDPage link_to) throws IOException{
        List<PDAnnotation> annotations = target_page.getAnnotations();
        
        // First add some text, two lines we'll add some annotations to this later
        PDFont font = PDType1Font.TIMES_ROMAN;
            
        printText(new String[]{ link_text }, font, fontSize, margin, offset_y, 0, contents);
        printText(new String[]{ String.valueOf(page_number) }, font, fontSize, 
            target_page.getMediaBox().getWidth() - margin - getStringWidth(font, 
            String.valueOf(page_number)) / 1000 * fontSize, offset_y, 0, contents);
                
        float max_width = target_page.getMediaBox().getWidth() - margin * 2;
        float still_free = max_width - getStringWidth(font, String.valueOf(page_number)) / 1000 * fontSize - getStringWidth(font, link_text) / 1000 * fontSize;
        float point_width = getStringWidth(font, ".")  / 1000 * fontSize;
        
        String points = "";
        for (int i =0 ; i < still_free / point_width; i++){
            points += ".";
        }
        
        printText(new String[]{ points }, font, fontSize, margin + getStringWidth(font, link_text) / 1000 * fontSize, offset_y, 0, contents);
                
        // Now add the link annotation, so the click on "Jump to page three" works
        PDAnnotationLink pageLink = new PDAnnotationLink();

        // Set the rectangle containing the link
        float textWidth = getStringWidth(font, link_text) / 1000 * fontSize;
        PDRectangle position = new PDRectangle();
        position.setLowerLeftX(margin);
        position.setLowerLeftY(offset_y);  // down a couple of points
        position.setUpperRightX(target_page.getMediaBox().getWidth() - margin);
        position.setUpperRightY(offset_y + 10);
        pageLink.setRectangle(position);
        // add the GoTo action
        PDActionGoTo actionGoto = new PDActionGoTo();
        // see javadoc for other types of PDPageDestination
        //PDPageDestination dest = new PDPageFitWidthDestination();
        PDPageXYZDestination dest = new PDPageXYZDestination();
        // do not use setPageNumber(), this is for external destinations only
        dest.setPage(link_to);
        dest.setLeft(0);
        dest.setTop((int)link_to.getMediaBox().getHeight());
        
        actionGoto.setDestination(dest);
        pageLink.setAction(actionGoto);
        
        PDBorderStyleDictionary border = new PDBorderStyleDictionary();
        border.setWidth( 0f );
        pageLink.setBorderStyle( border );

        annotations.add(pageLink);      
    }
    
    public void printLink(String[] texts, PDFont font, float fontSize, float offset_x, 
            float offset_y, float offset_dy, PDPageContentStream contentStream) throws IOException{
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        for( String text : texts ){
            contentStream.newLineAtOffset(offset_x, offset_y);
            try{
                //addHiperLink(page, offset_y, title , page_number, alpha.pages.get(0));
                
                contentStream.showText(text);
            }catch(java.lang.IllegalArgumentException ex){
                contentStream.showText(excludeUnsupportedSymbols(font, text)); 
            }
            offset_x = 0;
            offset_y = text.trim().isEmpty() ? offset_dy / 2 : offset_dy;
        }
        contentStream.endText();
        
    }        
    
    public void printText(String[] texts, PDFont font, float fontSize, float offset_x, 
            float offset_y, float offset_dy, PDPageContentStream contentStream) throws IOException{
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        for( String text : texts ){
            contentStream.newLineAtOffset(offset_x, offset_y);
            try{
                contentStream.showText(text);
            }catch(java.lang.IllegalArgumentException ex){
                contentStream.showText(excludeUnsupportedSymbols(font, text)); 
            }
            offset_x = 0;
            offset_y = text.trim().isEmpty() ? offset_dy / 2 : offset_dy;
        }
        contentStream.endText();
    }











    
    
    public void createBook(PDDocument document, ArrayList <PreText> materialForBook) throws IOException{
        
        //document.addPage( createTitlePage(document, book_author, book_title) );
        //prepare(materialForBook, document);
        
        // print table of contents 
        PreText TOC = new PreText ("Table Of Contents", new ArrayList<String>(){}, 2 );
        printParagraph(TOC, document, true, materialForBook);
        
        for(PDPage a : TOC.pages){
            document.addPage(a);
        }
        
        for (PreText b: materialForBook)
            for(PDPage a : b.pages){
                document.addPage(a);
            }
    }
    
    
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

                    //current.pages.add( printTOCPage(doc, material.subList(i, finalDestination ), fontSize, margin , current.title, page_counter, true));
                    
                    page_counter++;

                    i+= number_of_rows_on_page - 1;
                }else{
                    int finalDestination = i + number_of_rows_on_page;
                    if ( finalDestination > material.size() ) finalDestination = material.size();

                    //current.pages.add(printTOCPage(doc, material.subList(i, finalDestination ), fontSize, margin , current.title, page_counter, false));
                    
                    page_counter++;

                    i+= number_of_rows_on_page;
                }
            }
        }else{
            for (int i=0; i< current.lines.size();  )
            {
                if ( i == 0 ) // add string with paragraph title 
                {
                    /* -2 because title has x2 font */
                    int finalDestination = i + number_of_rows_on_page - 2;
                    if ( finalDestination > current.lines.size() ) finalDestination = current.lines.size();

                   // current.pages.add(addPage( doc, current.lines.subList(i, finalDestination ), fontSize, margin , current.title, page_counter, true));
                    
                    page_counter++;

                    i+= number_of_rows_on_page - 2;
                }else{
                    int finalDestination = i + number_of_rows_on_page;
                    if ( finalDestination > current.lines.size() ) finalDestination = current.lines.size();

                    //current.pages.add(addPage( doc, current.lines.subList(i, finalDestination ), fontSize, margin , current.title, page_counter, false));
                    page_counter++;

                    i+= number_of_rows_on_page;
                }
            }
        }
    }
    




    
    
   
    
    
    
    
    
    
    
    
    
    
    
    /**
     * If there were an exception about symbol w/o glyph - exclude this symbol
     * @param font - target font 
     * @param text - text with problem symbol
     * @return new text w/o corrupted symbol
     * @throws IOException 
     */
    public static String excludeUnsupportedSymbols(PDFont font, String text) throws IOException{
        String new_text = "";
        char[] a = text.toCharArray();

        for (char b : a) {
            try{
                font.encode(String.valueOf(b));
                new_text += String.valueOf(b);
            } catch(java.lang.IllegalArgumentException e){
                // just pass
            }
        }
        return new_text;
    }
    
    public static float getStringWidth(PDFont font, String text){
        try{
            try{
                return font.getStringWidth(text);
            } catch (java.lang.IllegalArgumentException ex){
                return font.getStringWidth(excludeUnsupportedSymbols(font, text));
            } 
        } catch (IOException s){
            s.printStackTrace();
            return 1;
        }
    }

    public List<String> calculateLines(PDFont font, String input){
        List<String> lines = new ArrayList<String>();
        int lastSpace = -1;

        String [] newLines = input.split("\n");
        for(String text : newLines){
            text = "    " + text; /* new line indent */
            while (text.length() > 0) {
                int spaceIndex = text.indexOf(' ', lastSpace + 1);
                if (spaceIndex < 0)
                    spaceIndex = text.length();
                String subString = text.substring(0, spaceIndex);
                
                float size = fontSize * getStringWidth(font, subString) / 1000;

                if (size > width){
                    if (lastSpace < 0)
                        lastSpace = spaceIndex;
                    
                    if (lastSpace < text.length()) {
                        subString = text.substring(0, lastSpace);
                        lines.add(subString);
                        text = text.substring(lastSpace).trim();
                        lastSpace = -1;
                    } else {
                        subString = text.trim();
                        lines.add(subString);
                        text = text.trim();
                        lastSpace = -1;
                    }
                }
                else if (spaceIndex == text.length()) {
                    lines.add(text);
                    text = "";
                } else {
                    lastSpace = spaceIndex;
                }
            }
        }
        return lines;
    }

}