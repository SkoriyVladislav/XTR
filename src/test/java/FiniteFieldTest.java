import by.Skoriy.Main;
import org.junit.Test;

import static by.Skoriy.Main.multiplyByMatrix;

public class FiniteFieldTest {

    /*@Test
    public void testSize() {

        FiniteField finiteField = new FiniteField(2,7, "src/main/resources/FiniteField.txt");
        assertEquals(finiteField.getSize(), Math.pow(2, 7));
    }

    @Test
    public void testTr() {
        FiniteField finiteField = new FiniteField(2, 4, "src/main/resources/FiniteField.txt");
        HashMap<Polynomial, Polynomial> hashMap = finiteField.getField();
        for (Map.Entry e : hashMap.entrySet()) {
            System.out.println("Tr( " + e.getValue() + ") = " + finiteField.trace((Polynomial)e.getValue()));
        }
    }*/

    @Test
    public void testMultiplyByMatrix() {

        int[][] array = Main.H1H3;

        int[][] array2 = getColumnFromRow(Main.message_kul);

        int[][] arr = multiplyByMatrix(array, array2);

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array2[0].length; j++) {
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
    }

    private static int[][] getColumnFromRow(int[] matrix) {
        int[][] arr = new int[matrix.length][1];

        for(int i = 0; i < matrix.length; i++) {
            arr[i][0] = matrix[i];
        }

        return arr;
    }
}
