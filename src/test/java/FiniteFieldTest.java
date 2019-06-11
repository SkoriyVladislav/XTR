import by.Skoriy.Field.FiniteField;
import by.Skoriy.Main;
import by.Skoriy.Polynom.Polynomial;
import by.Skoriy.Syndroms.GeneratorSyndrome;
import by.Skoriy.Syndroms.Syndrome;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


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
    public void BCH64() {
        FiniteField finiteField = new FiniteField(2, 6);
        int[] right_message_for_63 = Main.right_message_for_63;
        Map<Polynomial, Polynomial> polynomials = finiteField.getField();
        Map<int[], Syndrome> syndromes = GeneratorSyndrome.getGeneratorsSyndrome();

        for (int times = 0; times < 100; times++) {
            int[] message = Main.generateMistakes(right_message_for_63, 3);

            Syndrome syndromeMistakes = GeneratorSyndrome.getSyndromeMistakes(message);
            Map<int[], Syndrome> mist = new HashMap<>();
            mist.put(new int[]{0, 0, 0}, syndromeMistakes);


            Main.printSyndromes(mist, polynomials);

            int[] vector = null;
            Syndrome syndrome = null;
            Optional<Map.Entry<int[], Syndrome>> optional = syndromes.entrySet().stream()
                    .filter(
                            entry -> {
                                boolean flag = false;
                                for (int i = 0; i < entry.getValue().getNormOfSyndrome().size() && i < syndromeMistakes.getNormOfSyndrome().size(); i++) {
                                    if (Arrays.equals(entry.getValue().getNormOfSyndrome().get(i), syndromeMistakes.getNormOfSyndrome().get(i))) {
                                        flag = true;
                                    } else {
                                        return false;
                                    }
                                }
                                return flag;
                            }
                    ).findFirst();

            if (optional.isPresent()) {
                vector = optional.get().getKey();
                syndrome = optional.get().getValue();
            }

            if (vector != null) {

                int[] partOfSyndrome = syndrome.getSyndromes().get(0);
                Polynomial findPolynome = new Polynomial(partOfSyndrome);
                int power = polynomials.entrySet().stream()
                        .filter(entry -> entry.getValue().equals(findPolynome))
                        .findFirst().get().getKey().degree();

                int[] partOfSyndromeMistake = syndromeMistakes.getSyndromes().get(0);
                Polynomial findPolynomeMistake = new Polynomial(partOfSyndromeMistake);
                int power_of_mistake = polynomials.entrySet().stream()
                        .filter(entry -> entry.getValue().equals(findPolynomeMistake))
                        .findFirst().get().getKey().degree();

                int[] rightMessage = Arrays.copyOf(message, message.length);
                int sdvig = (power_of_mistake - power + message.length) % message.length;
                for (int i : vector) {
                    rightMessage[(i + sdvig) % message.length] = (rightMessage[(i + sdvig) % message.length] + 1) % 2;
                }

                assertArrayEquals(right_message_for_63, rightMessage);

                for (int i = 0; i < message.length; i++) {
                    if (i != 0 && i % 3 == 0) {
                        System.out.print(" ");
                    }
                    System.out.print(message[i]);
                }
                System.out.println();
                for (int i = 0; i < message.length; i++) {
                    if (i != 0 && i % 3 == 0) {
                        System.out.print(" ");
                    }
                    System.out.print(rightMessage[i]);
                }
                System.out.println();
            } else {
                //assertEquals(true, false);
            }


        }
    }

    @Test
    public void BCH32() {

    }
}
