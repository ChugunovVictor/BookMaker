package org.bloodboneflesh.utility;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;

public class Print {

    public static int[] sort(int start, int end){
        // 4 pages on each list of a4
        //1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
        //20 1 2 19   18 3 4 17   16 5 6 15   14 7 8 13   12 9 10 11

        // -1 will symbolized empty

        int pieces_of_paper = (( end - start ) / 4 ) + (( end - start ) % 4 == 0 ? 0 : 1) ;
        if( start == end ) pieces_of_paper = 1;

        int sorted[] = new int[ pieces_of_paper * 4 ];
        int end_pointer = end , start_pointer = start;
        for( int i = 0; i < pieces_of_paper; i++ ){
            int remains = end_pointer - start_pointer + 1;
            sorted[ i * 4 ]     = ( remains >= 4 ) ? end_pointer-- : -1;
            sorted[ i * 4 + 1 ] = start_pointer++;
            sorted[ i * 4 + 2 ] = ( remains >= 2 ) ? start_pointer++ : -1;
            sorted[ i * 4 + 3 ] = ( remains >= 3 ) ? end_pointer-- : -1;
        }

        return sorted;
    }

    public static void prepareToPrint( String in, String out ){
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

        try {
            PDDocument old_document = PDDocument.load(new File(in));
            PDDocument new_document = new PDDocument();

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

            new_document.save(new File(out));
            new_document.close();

            old_document.close();

        }catch (Exception ex){

        }
    }
}
