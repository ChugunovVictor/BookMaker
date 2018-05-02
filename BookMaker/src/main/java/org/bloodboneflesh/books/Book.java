package org.bloodboneflesh.books;

import java.io.File;
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
import org.bloodboneflesh.PreText;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public abstract class Book {
    String book_author;
    String book_title;
    
    int number_of_rows_on_page = 38;
    int number_of_images_on_page = 3;
    float fontSize = 12;
    float margin = 50;
    float width = 620 - 2*margin;

    public static PDFont font_standart = PDType1Font.TIMES_ROMAN;    //PDType0Font.load(document, new File("times.ttf")); 
    public static PDFont font_italic   = PDType1Font.TIMES_ITALIC;   //PDType0Font.load(document, new File("timesi.ttf")); 
    public static PDFont font_bold     = PDType1Font.TIMES_BOLD;     //PDType0Font.load(document, new File("timesbd.ttf"));
    
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
            offset_y += offset_dy;
        }
        contentStream.endText();
    }
    
    public void addHiperLink(PDDocument target_document, PDPage target_page, float offset_y, String link_text, int page_number, PDPage link_to) throws IOException{
        List<PDAnnotation> annotations = target_page.getAnnotations();
        
        // First add some text, two lines we'll add some annotations to this later
        PDFont font = PDType1Font.TIMES_ROMAN;
        PDPageContentStream contents = new PDPageContentStream(target_document, target_page, true, true);
            
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
        
        contents.close();
        
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
    
    public void createBook(PDDocument document, ArrayList <PreText> materialForBook) throws IOException{
        
        document.addPage( createTitlePage(document, book_author, book_title) );
        prepare(materialForBook, document);
        
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
    
    public void prepare(ArrayList <PreText> materialForBook, PDDocument doc) throws IOException{
        int page_counter = 1;
        int pages_to_toc = calculatePages(materialForBook.size(), number_of_rows_on_page);
        
        page_counter += pages_to_toc;
        
        for(PreText a : materialForBook) {
            a.page_for_table_of_content =  page_counter + 1; /* next after last page before */
            int pages_to_chapter = calculatePages(a.lines.size(), number_of_rows_on_page); 
            page_counter += pages_to_chapter;
            printParagraph(a, doc, false, null);
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
            for (int i=0; i< current.lines.size();  )
            {
                if ( i == 0 ) // add string with paragraph title 
                {
                    /* -2 because title has x2 font */
                    int finalDestination = i + number_of_rows_on_page - 2;
                    if ( finalDestination > current.lines.size() ) finalDestination = current.lines.size();

                    current.pages.add(addPage( doc, current.lines.subList(i, finalDestination ), fontSize, margin , current.title, page_counter, true));
                    
                    page_counter++;

                    i+= number_of_rows_on_page - 2;
                }else{
                    int finalDestination = i + number_of_rows_on_page;
                    if ( finalDestination > current.lines.size() ) finalDestination = current.lines.size();

                    current.pages.add(addPage( doc, current.lines.subList(i, finalDestination ), fontSize, margin , current.title, page_counter, false));
                    page_counter++;

                    i+= number_of_rows_on_page;
                }
            }
        }
    }
    
    public PDPage createTitlePage(PDDocument doc, String author, String title) throws IOException{
        PDPage page = new PDPage();
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);

        PDRectangle mediabox = page.getMediaBox();
        
        /* print author */
        contentStream.beginText();
        contentStream.setFont(font_standart, fontSize);
        float authorWidth = font_standart.getStringWidth(author) / 1000 * fontSize;
        contentStream.newLineAtOffset(mediabox.getUpperRightX() - margin - authorWidth, 
                mediabox.getUpperRightY() - margin );
        contentStream.showText(author);
        contentStream.endText();
        
        /* print title */
        contentStream.beginText();
        contentStream.setFont(font_bold, fontSize * 5);
        float titleWidth = font_bold.getStringWidth(title) / 1000 * fontSize * 5;
        contentStream.newLineAtOffset( (mediabox.getWidth() - titleWidth)/2 , mediabox.getHeight()/2);
        contentStream.showText(title);
        contentStream.endText();
        
        /* print notes */
        contentStream.beginText();
        contentStream.setFont(font_italic, fontSize);
        contentStream.newLineAtOffset(margin + mediabox.getLowerLeftX() , 26 + margin + mediabox.getLowerLeftY());
        contentStream.showText("Made with https://jsoup.org/");
        contentStream.newLineAtOffset(0, -13);
        contentStream.showText("Made with https://pdfbox.apache.org/");
        contentStream.newLineAtOffset(0, -13);
        contentStream.showText("Using materials from https://parahumans.wordpress.com/");
        contentStream.endText();
        
        contentStream.close();
        return page;
    }
    
    public PDPage printTOCPage(PDDocument doc, List<PreText> lines, float fontSize, float margin, String title, int number, 
            boolean is_Head_Start) throws IOException{
        PDPage page = new PDPage();
        //doc.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);

        float leading = 1.5f * fontSize;
        
        PDRectangle mediabox = page.getMediaBox();
        
        /* draw decor top line with paragraph title */
        int decor_text_font_size = 9;
        contentStream.setLineWidth(0.5f);
        contentStream.drawLine(margin / 2, mediabox.getHeight() - margin * 0.8f, mediabox.getWidth() - margin / 2, mediabox.getHeight() - margin * 0.8f);

        printText(new String[]{ title }, font_italic, decor_text_font_size, 
                mediabox.getWidth() - margin - font_italic.getStringWidth(title) / 1000 * decor_text_font_size, 
                mediabox.getHeight() - margin * 0.8f + 5, 0, contentStream);
        
        /* print title if it is necessary */
        
        if (is_Head_Start) {
            printText(new String[]{ title }, font_bold, 20, 
                mediabox.getLowerLeftX() + margin, mediabox.getUpperRightY() - margin * 1.4f , 0, contentStream);
        }
        /* print content text */

        float offset_y;
        if (!is_Head_Start) {        
            offset_y = mediabox.getUpperRightY() - margin * 1.4f;
        }else {
            offset_y = mediabox.getUpperRightY() - margin * 1.4f - leading*1.5f ;
        }
        
        /* print page number */
        printText(new String[]{ String.valueOf(number) }, font_standart, fontSize, 
                mediabox.getWidth() / 2, margin * 0.5f, 0, contentStream);
        
        contentStream.close();
        
        for (PreText alpha: lines){
            addHiperLink(doc, page, offset_y, alpha.title , alpha.page_for_table_of_content, alpha.pages.get(0));
            offset_y -= leading;
        }
        
        return page;
    }
    
    public List<String> calculateLines(PDFont font, String input) throws IOException{
        List<String> lines = new ArrayList<String>();
        int lastSpace = -1;

        String [] newLines = input.split("\n");
        for(String text : newLines){
            text = "    " + text; /* new line indent */
            while (text.length() > 0)
            {
                int spaceIndex = text.indexOf(' ', lastSpace + 1);
                if (spaceIndex < 0)
                    spaceIndex = text.length();
                String subString = text.substring(0, spaceIndex);
                
                float size = fontSize * getStringWidth(font, subString) / 1000;

                if (size > width)
                {
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
                else if (spaceIndex == text.length())
                {
                    lines.add(text);
                    text = "";
                }
                else
                {
                    lastSpace = spaceIndex;
                }
            }
        }
        return lines;
    }

    
    public int calculatePages(int rows_count, int number_of_rows_on_page){
        float current_value = (float)( rows_count + 2 ) / number_of_rows_on_page; /* +2 for title which takes 2 rows*/
        int pages_to_chapter = Math.round(current_value) ;
        if (pages_to_chapter > current_value) return pages_to_chapter;
        else return pages_to_chapter + 1;
    }
   
/**
 * Add new page with content to document 
 * only print calculated before text
 *
 * @param doc  target document
 * @param lines  text content
 * @param fontSize  font size for text content
 * @param margin  margin from right / left / top / bottom
 * @param title  name of the paragraph - to use it with decor and for titling
 * @param number  page number
 * @param is_Head_Start  show if paragraph start with this page - so if we need to print it or not
 */
    public PDPage addPage(PDDocument doc, List<String> lines, float fontSize, float margin, String title, int number, 
            boolean is_Head_Start) throws IOException{
        
        PDPage page = new PDPage();
        //doc.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);

        float leading = 1.5f * fontSize;
        
        PDRectangle mediabox = page.getMediaBox();
        
        /* draw decor top line with paragraph title */
        int decor_text_font_size = 9;
        contentStream.setLineWidth(0.5f);
        contentStream.drawLine(margin / 2, mediabox.getHeight() - margin * 0.8f, mediabox.getWidth() - margin / 2, mediabox.getHeight() - margin * 0.8f);
        
        printText(new String[]{ title }, font_italic, decor_text_font_size, 
                mediabox.getWidth() - margin - getStringWidth(font_italic, title) / 1000 * decor_text_font_size, 
                mediabox.getHeight() - margin * 0.8f + 5, 0, contentStream);
        
        /* print title if it is necessary */
        
        if (is_Head_Start) {
            printText(new String[]{ title }, font_bold, 20, 
                mediabox.getLowerLeftX() + margin, mediabox.getUpperRightY() - margin * 1.4f ,
                0, contentStream);
        }
        
        /* print content text */
        
        contentStream.beginText();
        contentStream.setFont(font_standart, fontSize);
        
        if (!is_Head_Start) {        
            contentStream.newLineAtOffset(mediabox.getLowerLeftX() + margin, mediabox.getUpperRightY() - margin * 1.4f );
        }else {
            contentStream.newLineAtOffset(mediabox.getLowerLeftX() + margin, mediabox.getUpperRightY() - margin * 1.4f - leading*2 );
        }
        for (String line: lines)
        {
            try{
                contentStream.showText(line);
            }catch(java.lang.IllegalArgumentException ex){
                contentStream.showText(excludeUnsupportedSymbols(font_standart, line)); 
            }
            if (line.trim().isEmpty()) 
                contentStream.newLineAtOffset(0, -leading/2);
            else    
                contentStream.newLineAtOffset(0, -leading);
        }
        contentStream.endText(); 
        
        /* print page number */
        printText(new String[]{ String.valueOf(number) }, font_standart, fontSize, 
                mediabox.getWidth() / 2, margin * 0.5f, 0, contentStream);
       
        contentStream.close();
        return page;
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
    
    public static float getStringWidth(PDFont font, String text) throws IOException{
        try{
            return font.getStringWidth(text);
        } catch (java.lang.IllegalArgumentException ex){
            return font.getStringWidth(excludeUnsupportedSymbols(font, text));
        }
    }
    
    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

}