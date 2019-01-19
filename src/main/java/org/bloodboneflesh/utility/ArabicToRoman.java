package org.bloodboneflesh.utility;
import java.util.*;
/*
    https://en.wikipedia.org/wiki/Roman_numerals
    Symbol   I   V   X   L   C   D   M     
    Value    1   5   10  50  100 500 1000 
*/
public class ArabicToRoman {
    static Map<Integer, String> letters = new HashMap<>();
    static {
        letters.put(1, "I");     letters.put(5, "V");
        letters.put(10, "X");    letters.put(50, "L");
        letters.put(100, "C");   letters.put(500, "D");
        letters.put(1000, "M");
    }

    public static String convert(int number) throws NumberFormatException {
        if (number > 3999)  throw new NumberFormatException("Unsupported. " + number + " is more than 3999");
        if (number < 1) throw new NumberFormatException("Unsupported. " + number + " is less than 1");
        
        Map<Integer, int[]> mapper = new HashMap<>();
        mapper.put(1, new int[]{1});         mapper.put(2, new int[]{1, 1});          mapper.put(3, new int[]{1, 1, 1});
        mapper.put(4, new int[]{1, 5});      mapper.put(5, new int[]{5});             mapper.put(6, new int[]{5, 1});
        mapper.put(7, new int[]{5, 1, 1});   mapper.put(8, new int[]{5, 1, 1, 1});    mapper.put(9, new int[]{1, 10});

        String result = "";  int rank = 1;   int current_number = number;
        while (current_number != 0) {
            int current = current_number % (rank * 10) / rank;
            if (current != 0) {
                int[] notation = mapper.get(current);
                String roman_digit = "";
                for (int i = 0; i < notation.length; i++) {
                    roman_digit += letters.get(notation[i] * rank);
                }
                result = roman_digit + result;
                current_number -= current * rank;
            }
            rank *= 10;
        }
        return result;
    }

    public static int convertVisaVersa(String number) throws NumberFormatException {
        if (number.trim().isEmpty()) throw new NumberFormatException("Number is empty");
        
        int result = 0;
        String prev = null, current = null;
        for (int i = 0; i < number.length(); i++) {
            current = String.valueOf(number.charAt(i));
            if (prev != null) {
                Integer current_value = (Integer) getKeyFromValue(letters, current);
                Integer prev_value = (Integer) getKeyFromValue(letters, prev);

                if (current_value == null || prev_value == null) throw new NumberFormatException(number + " is not a number");
                
                if (prev_value < current_value) {
                    result += current_value - prev_value;
                    current = null;
                } else {
                    result += prev_value;
                }
            }
            prev = current;
        }
        if (current != null) {
            int current_value = (Integer) getKeyFromValue(letters, current);
            result += current_value;
        }
        return result;
    }

    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet())
            if (hm.get(o).equals(value)) return o;
        return null;
    }
}
