import by.Skoriy.Field.FiniteField;
import by.Skoriy.Polynom.Polynomial;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FiniteFieldTest {

    @Test
    public void testSize() {

        FiniteField finiteField = new FiniteField(2,7, "src/main/resources/FiniteField.txt");
        assertEquals(finiteField.getSize(), Math.pow(2, 7));
    }

    @Test
    public void testTr() {
        FiniteField finiteField = new FiniteField(2, 7, "src/main/resources/FiniteField.txt");
        HashMap<Polynomial, Polynomial> hashMap = finiteField.getField();
        for (Map.Entry e : hashMap.entrySet()) {
            System.out.println("Tr( " + e.getValue() + ") = " + finiteField.tr((Polynomial)e.getValue()));
        }
    }
}
