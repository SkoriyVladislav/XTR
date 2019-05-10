package by.Skoriy.Syndroms;

import by.Skoriy.Field.FiniteField;
import by.Skoriy.GammaOrbitsUtil;
import by.Skoriy.Main;
import by.Skoriy.Polynom.Polynomial;

import java.util.*;
import java.util.stream.IntStream;

import static java.lang.Integer.compare;


public class GeneratorSyndrome {
    private static Map<int[], Syndrome> generatorsSyndrome = new TreeMap<>((o1, o2) -> {
        if (o1.length == o2.length) {
            int[] arr = IntStream.range(0, o1.length)
                    .map(i -> compare(o1[i], o2[i]))
                    .toArray();
            int returned = IntStream.range(0, arr.length).filter(value -> arr[value] != 0).findFirst().orElse(0);
            return returned;
        } else {
            return o1.length - o2.length;
        }
    });
    static {
        List<int[]> orbits = GammaOrbitsUtil.getGammaOrbits(Main.SIZE_GAMMA_ORBITS);
        for (int[] orbit : orbits) {
            int[] coeff = new int[Main.N];
            for(int index : orbit) {
                coeff[index] = 1;
            }

            List<int[]> syndromes = getSyndrome(coeff);
            List<int[]> normOfSyndrome = getNormOfSyndrome(syndromes);

            Syndrome syndrome = new Syndrome(syndromes, normOfSyndrome);

            generatorsSyndrome.put(orbit, syndrome);
        }
    }

    public static Map<int[], Syndrome> getGeneratorsSyndrome() {
        return generatorsSyndrome;
    }

    public static Syndrome getSyndromeMistakes(int[] coeff) {
        List<int[]> syndromes = getSyndrome(coeff);
        List<int[]> normOfSyndrome = getNormOfSyndrome(syndromes);

        Syndrome syndrome = new Syndrome(syndromes, normOfSyndrome);

        return syndrome;
    }

    private static List<int []> getSyndrome(int[] coeff) {
        List<int[]> syndromes = new ArrayList<>();
        int[] syndrome = getRowFromColumn(Main.multiplyByMatrix(Main.H1H3, getColumnFromRow(coeff)));
        for (int j = 0; j < syndrome.length; j += Main.M) {
            int[] subSyndrome = Arrays.copyOfRange(syndrome, j, j + Main.M);
            syndromes.add(subSyndrome);
        }
        return syndromes;
    }

    private static List<int []> getNormOfSyndrome(List<int[]> syndromes) {
        List<int[]> normOfSyndrome = new ArrayList<>();
        for (int i = 0; i < syndromes.size(); i++) {
            normOfSyndrome = getNormsOfSyndrome(syndromes, normOfSyndrome, i);
        }

        return normOfSyndrome;
    }

    private static List<int[]> getNormsOfSyndrome(List<int[]> syndromes, List<int[]> normOfSyndrome, int i) {
        int[] s1 = syndromes.get(i);
        for (int j = i + 1; j < syndromes.size(); j++) {
            int[] si = syndromes.get(j);
            int[] result;

            Polynomial temp = new Polynomial(s1);
            Polynomial p1 = FiniteField.divideByBase(FiniteField.getPolynomInDegree(temp, j * 2 + 1, Main.M, Main.BASE), Main.BASE).reduction(Main.BASE);
            Polynomial p2 = new Polynomial(si);

            int degree = 0;
            Map<Polynomial, Polynomial> finiteField = FiniteField.getField();
            if (finiteField.entrySet().stream()
                    .anyMatch(entry -> entry.getValue().equals(p1))) {
                degree = (Main.N - finiteField.entrySet().stream()
                        .filter(entry -> entry.getValue().equals(p1))
                        .findFirst().get().getKey().degree()) % Main.N;
            }

            Polynomial reversed = finiteField.get(new Polynomial(1, degree));
            Polynomial res = FiniteField.times(p2, reversed, Main.M, Main.BASE);
            result = res.getCoef();
            normOfSyndrome.add(result);
        }

        return normOfSyndrome;
    }

    public static int[] getRowFromColumn(int[][] matrix) {
        int[] arr = new int[matrix.length];

        for(int i = 0; i < matrix.length; i++) {
            arr[i] = matrix[i][0];
        }

        return arr;
    }

    public static int[][] getColumnFromRow(int[] matrix) {
        int[][] arr = new int[matrix.length][1];

        for(int i = 0; i < matrix.length; i++) {
            arr[i][0] = matrix[i];
        }

        return arr;
    }
}
