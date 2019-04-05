package org.bloodboneflesh;

import org.bloodboneflesh.utility.Print;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class TestPrint {
    @Test
    public void testSort(){
        assertArrayEquals("1-4", new int[]{
                4, 1, 2, 3
        }, Print.sort(1,4));
        assertArrayEquals("5-8", new int[]{
                8, 5, 6, 7
        }, Print.sort(5,8));

        assertArrayEquals("1-3", new int[]{
                -1, 1, 2, 3
        }, Print.sort(1,3));
        assertArrayEquals("1-2", new int[]{
                -1, 1, 2, -1
        }, Print.sort(1,2));
        assertArrayEquals("1-1", new int[]{
                -1, 1, -1, -1
        }, Print.sort(1,1));

        assertArrayEquals("1-8", new int[]{
                8, 1, 2, 7,
                6, 3, 4, 5
        }, Print.sort(1,8));
        assertArrayEquals("1-7", new int[]{
                -1, 1, 2, 7,
                6, 3, 4, 5
        }, Print.sort(1,7));
        assertArrayEquals("1-6", new int[]{
                -1, 1, 2, -1,
                6, 3, 4, 5
        }, Print.sort(1,6));
        assertArrayEquals("1-5", new int[]{
                -1, 1, 2, -1,
                -1, 3, 4, 5
        }, Print.sort(1,5));
        assertArrayEquals("1-13", new int[]{
                -1, 1, 2, -1,
                -1, 3, 4, 13,
                12, 5, 6, 11,
                10, 7, 8, 9
        }, Print.sort(1,13));

        assertArrayEquals("1-20", new int[]{
                20, 1, 2, 19,
                18, 3, 4, 17,
                16, 5, 6, 15,
                14, 7, 8, 13,
                12, 9, 10, 11
        }, Print.sort(1,20));
        assertArrayEquals("21-40", new int[]{
                40, 21, 22, 39,
                38, 23, 24, 37,
                36, 25, 26, 35,
                34, 27, 28, 33,
                32, 29, 30, 31
        }, Print.sort(21,40));
    }
}
