package org.bloodboneflesh;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestArabicToRoman {
  
    @Test(expected = NumberFormatException.class)
    public void testConvertExceptionCases_1() {
        // "Unsupported. -999999 is less than 1" 
        ArabicToRoman.convert(-999999);
    }
    
    @Test(expected = NumberFormatException.class)
    public void testConvertExceptionCases_2() {
        // "Unsupported. 999999 is more than 3999" 
        ArabicToRoman.convert(999999);
    }
  
    @Test(expected = NumberFormatException.class)
    public void testConvertExceptionCases_3() {
        // "Unsupported. 0 is less than 1" 
        ArabicToRoman.convert(0);
    }
  
    @Test
    public void testConvert(){
      assertEquals("1", "I", ArabicToRoman.convert(1));
      assertEquals("2", "II", ArabicToRoman.convert(2));
      assertEquals("3", "III", ArabicToRoman.convert(3));
      assertEquals("4", "IV", ArabicToRoman.convert(4));
      assertEquals("5", "V", ArabicToRoman.convert(5));
      assertEquals("6", "VI", ArabicToRoman.convert(6));
      assertEquals("7", "VII", ArabicToRoman.convert(7));
      assertEquals("8", "VIII", ArabicToRoman.convert(8)); 
      assertEquals("9", "IX", ArabicToRoman.convert(9));
      assertEquals("10", "X", ArabicToRoman.convert(10));
      assertEquals("20", "XX", ArabicToRoman.convert(20));
      assertEquals("30", "XXX", ArabicToRoman.convert(30));
      assertEquals("40", "XL", ArabicToRoman.convert(40));
      assertEquals("50", "L", ArabicToRoman.convert(50));
      assertEquals("60", "LX", ArabicToRoman.convert(60));
      assertEquals("70", "LXX", ArabicToRoman.convert(70));
      assertEquals("80", "LXXX", ArabicToRoman.convert(80));
      assertEquals("90", "XC", ArabicToRoman.convert(90));
      assertEquals("100", "C", ArabicToRoman.convert(100));
      assertEquals("200", "CC", ArabicToRoman.convert(200));
      assertEquals("300", "CCC", ArabicToRoman.convert(300));
      assertEquals("400", "CD", ArabicToRoman.convert(400));
      assertEquals("500", "D", ArabicToRoman.convert(500));
      assertEquals("600", "DC", ArabicToRoman.convert(600));
      assertEquals("700", "DCC", ArabicToRoman.convert(700));
      assertEquals("800", "DCCC", ArabicToRoman.convert(800));
      assertEquals("900", "CM", ArabicToRoman.convert(900));
      assertEquals("1000", "M", ArabicToRoman.convert(1000));
      assertEquals("2000", "MM", ArabicToRoman.convert(2000));
      assertEquals("3000", "MMM", ArabicToRoman.convert(3000));

      assertEquals("128", "CXXVIII", ArabicToRoman.convert(128));
      assertEquals("1017", "MXVII", ArabicToRoman.convert(1017));
      assertEquals("2001", "MMI", ArabicToRoman.convert(2001));
      assertEquals("3999", "MMMCMXCIX", ArabicToRoman.convert(3999));
    }

    @Test(expected = NumberFormatException.class)
      public void testConvertVisaVersaExceptionCases_1() {
          // "VIMTCZ is not a number" 
          ArabicToRoman.convertVisaVersa("VIMTCZ");
      }
    
    @Test(expected = NumberFormatException.class)
    public void testConvertVisaVersaExceptionCases_2() {
        // "123_.&* is not a number" 
        ArabicToRoman.convertVisaVersa("123_.&*");
    }
    
    @Test(expected = NumberFormatException.class)
    public void testConvertVisaVersaExceptionCases_3() {
        // "Number is empty" 
        ArabicToRoman.convertVisaVersa("  ");
    }
  
    @Test
    public void testConvertVisaVersa(){
      assertEquals("1", 1, ArabicToRoman.convertVisaVersa("I"));
      assertEquals("4", 4, ArabicToRoman.convertVisaVersa("IV"));
      assertEquals("8", 8, ArabicToRoman.convertVisaVersa("VIII"));
      assertEquals("70", 70, ArabicToRoman.convertVisaVersa("LXX"));
      assertEquals("991", 991, ArabicToRoman.convertVisaVersa("CMXCI"));
      assertEquals("3999", 3999, ArabicToRoman.convertVisaVersa("MMMCMXCIX"));
    }
}
