import by.Skoriy.Field.FiniteField;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FiniteFieldTest {

    @Test
    public void testSize() {

        FiniteField finiteField = new FiniteField(2,7, "src/main/resources/FiniteField.txt");
        assertEquals(finiteField.getSize(), Math.pow(2, 7));
    }
}
