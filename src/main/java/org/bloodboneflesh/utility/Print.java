package org.bloodboneflesh.utility;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;

public class Print {
    public static void prepareToPrint( String in, String out, boolean just_sort ){
        class PrintTemplate{
            PDPage[] pages;

            public PrintTemplate(int size){
                this.pages = new PDPage[size];
            }

            void push( PDPage p ){
                if( this.pages[1] == null ){        this.pages[1] = p;
                } else if( this.pages[2] == null ){ this.pages[2] = p;
                } else if( this.pages[3] == null ){ this.pages[3] = p;
                } else {                            this.pages[0] = p;
                }
            }

            boolean next(){
                for( int i = 0; i < this.pages.length; i ++ ) if( this.pages[i] == null ) return false;
                return true;
            }

            void empty(){
                for ( int i = 0; i < this.pages.length; i ++ ) this.pages[i] = null;
            }
        }

        /*  A4 pdf to A5 printable
                     *  pages 1,2, 3, 4,     5, 6, 7, 8,      9   ...     ->     4, 1, 2, 3,   8, 5, 6, 7   ,  ...
                     *  */
        try {
            PDDocument old_document = PDDocument.load(new File(in));
            PDDocument new_document = new PDDocument();

            if( just_sort ){
                PrintTemplate pt = new PrintTemplate(4);
                for( int i = 0; i < old_document.getNumberOfPages(); i++ ){
                    pt.push(old_document.getPage(i));
                    if( pt.next() ){
                        System.out.println("print page " + i);
                        for( int j = 0; j < pt.pages.length; j++ ){
                            new_document.addPage(pt.pages[j]);
                        }
                        pt.empty();
                    }
                }
                if( !pt.next() ){
                    System.out.println("add last");
                    for( int j = 0; j < pt.pages.length; j++ ){
                        if (pt.pages[j] == null){
                            System.out.println("add empty");
                            new_document.addPage(new PDPage());
                        }else {
                            new_document.addPage(pt.pages[j]);
                        }
                    }
                }
            }else{

            }

            new_document.save(new File(out));
            new_document.close();

            old_document.close();

        }catch (Exception ex){

        }
    }
}
