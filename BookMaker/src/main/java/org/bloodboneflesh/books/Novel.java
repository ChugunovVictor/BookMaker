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
import static org.bloodboneflesh.books.Book.getStringWidth;
import org.bloodboneflesh.utility.PostText;
import org.bloodboneflesh.utility.Text;

public class Novel extends Book{

    List<PostText> paragraph;
    List<PostText> toc;
    
    private List<PDPage> createTableOfContents(){  
        List<PDPage> result = new ArrayList<>();
        toc = prepare(paragraph);
        for(PostText a: toc)
            a.context = null;
        
        return result;
    }
    
    private List<PDPage> createPages(boolean is_toc){
        List<PDPage> result = new ArrayList<>();
        for(PostText p: is_toc ? toc : paragraph)
            result.addAll(createParagraph(p, is_toc));
        return result;
    }
    
    private List<PDPage> createParagraph(PostText p, boolean is_toc){
        List<PDPage> result = new ArrayList<>();
        int page_counter = p.page_number;
        
        int place_for_title = 2;
        int strings_left = p.context.size() + place_for_title;
        int from = 0;
        int to = number_of_rows_on_page - place_for_title; 
        
        while( strings_left > number_of_rows_on_page ){
            result.add(createPage( p.title, page_counter, p.context.subList(from, to),
                (number_of_rows_on_page - 2) == (to - from), is_toc
            ));
            from = to;
            to += number_of_rows_on_page;
            strings_left -= number_of_rows_on_page;
            page_counter ++;
        }
        if (strings_left != 0)
            result.add(createPage( p.title, page_counter, p.context.subList(from, p.context.size()),
                strings_left == (p.context.size() + place_for_title), is_toc    
            ));
            
        return result;
    }
     
    private PDPage createPage(String title, int page_number, List<String> strings, 
            boolean is_Head_Start, boolean is_toc){
        PDPage page = pf.createPDPage();
        try (PDPageContentStream contentStream = (PDPageContentStream)cf.getBeanWithParameters
            (PDPageContentStream.class, doc, page)) {
        
            PDRectangle mediabox = page.getMediaBox();
                    
            printDecor(contentStream, mediabox, title, page_number);
        
            /* print title if it is necessary */
            if (is_Head_Start) {
                printText(new String[]{ title }, font_bold, 20, mediabox.getLowerLeftX() + margin, 
                    mediabox.getUpperRightY() - margin * 1.4f , 0, contentStream);
            }

            /* print content text */
            float leading = 1.5f * fontSize;
              
            if(is_toc)
                printLink(strings.toArray(new String[]{}), font_standart, fontSize, 
                    mediabox.getLowerLeftX() + margin, 
                    mediabox.getUpperRightY() - margin * 1.4f - (is_Head_Start ?  leading*2 : 0),
                    -leading, contentStream);
            else    
                printText(strings.toArray(new String[]{}), font_standart, fontSize, 
                    mediabox.getLowerLeftX() + margin, 
                    mediabox.getUpperRightY() - margin * 1.4f - (is_Head_Start ?  leading*2 : 0),
                    -leading, contentStream);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return page;
    }
    
    @Override
    public List<PDPage> createContent(ArrayList <PreText> materialForBook) {
        List<PDPage> result = new ArrayList<>();
        paragraph = prepare(materialForBook);
        result.addAll(createTableOfContents());
        result.addAll(createPages(false));
        return result;
    }
    
    public void printDecor(PDPageContentStream contentStream, PDRectangle mediabox, 
            String title, int page_number) throws IOException{
        /* draw decor top line with paragraph title */
        int decor_text_font_size = 9;
        contentStream.setLineWidth(0.5f);
        contentStream.drawLine(margin / 2, mediabox.getHeight() - margin * 0.8f, 
                mediabox.getWidth() - margin / 2, mediabox.getHeight() - margin * 0.8f);
        
        printText(new String[]{ title }, font_italic, decor_text_font_size, 
                mediabox.getWidth() - margin - getStringWidth(font_italic, title) / 1000 * decor_text_font_size, 
                mediabox.getHeight() - margin * 0.8f + 5, 0, contentStream);
        
        /* print page number */
        printText(new String[]{ String.valueOf(page_number) }, font_standart, fontSize, 
                mediabox.getWidth() / 2, margin * 0.5f, 0, contentStream);
    }
    
    public List<PostText> prepare(List <? extends Text> materialForBook){
        int page_counter = 1;
        int pages_to_toc = calculatePages(materialForBook.size(), number_of_rows_on_page);
        List<PostText> prepare_paragraph = new ArrayList<>();
        
        page_counter += pages_to_toc;
        
        for(Text p : materialForBook){
            List<String> strings = new ArrayList<>();
            for(String c : p.context){
                strings.addAll(calculateLines(font_standart, c));
            }
            prepare_paragraph.add(new PostText(page_counter, p.title, strings));
            page_counter += calculatePages(strings.size(), number_of_rows_on_page);
        }
        
        return prepare_paragraph;
    }
        
    public int calculatePages(int rows_count, int number_of_rows_on_page){
        float current_value = (float)( rows_count + 2 ) / number_of_rows_on_page; /* +2 for title which takes 2 rows*/
        int pages_to_chapter = Math.round(current_value) ;
        if (pages_to_chapter > current_value) return pages_to_chapter;
        else return pages_to_chapter + 1;
    }

}
