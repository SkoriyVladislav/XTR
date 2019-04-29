package by.Skoriy.Syndroms;

import by.Skoriy.Field.FiniteField;
import by.Skoriy.Main;
import by.Skoriy.Polynom.Polynomial;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class GeneratorSyndrome {
    private static Map<Integer, Syndrome> generatorsSyndrome = new HashMap<>();
    static {
        for (int i = 1; i < Main.N / 2 + 1; i++) {

            int[] coeff = new int[Main.N];
            coeff[0] = 1;
            coeff[i] = 1;

            List<int[]> syndromes = getSyndrome(coeff);
            List<int[]> normOfSyndrome = getNormeOfSyndrome(syndromes);

            Syndrome syndrome = new Syndrome(syndromes, normOfSyndrome);

            generatorsSyndrome.put(i, syndrome);
        }
    }

    public static Map<Integer, Syndrome> getGeneratorsSyndrome() {
        return generatorsSyndrome;
    }

    private static List<int []> getSyndrome(int... coeff) {
        List<int[]> syndromes = new ArrayList<>();
        int[] syndrome = getRowFromColumn(Main.multiplyByMatrix(Main.H1H3, getColumnFromRow(coeff)));
        for (int j = 0; j < syndrome.length; j += Main.M) {
            int[] subSyndrome = Arrays.copyOfRange(syndrome, j, j + Main.M);
            syndromes.add(subSyndrome);
        }
        return syndromes;
    }

    private static List<int []> getNormeOfSyndrome(List<int[]> syndromes) {
        //TODO refactor this method
        List<int[]> normOfSyndrome = new ArrayList<>();
        int[] s1 = syndromes.get(0);
        int[] s2 = syndromes.get(1);
        int[] result;

        Polynomial temp = new Polynomial(s1);
        Polynomial p1 = FiniteField.divideByBase(FiniteField.getPolynomInDegree(temp, 3, Main.M, Main.BASE), Main.BASE).reduction(Main.BASE);
        Polynomial p2 = new Polynomial(s2);

        Map<Polynomial, Polynomial> finiteField = FiniteField.getField();
        int degree = 30 - finiteField.entrySet().stream().filter(
                entry -> entry.getValue().equals(p1)).findFirst().get().getKey().degree() + 1 ;

        Polynomial test = new Polynomial(1, degree);
        Polynomial reversed = finiteField.get(test);

        System.out.println(FiniteField.times(FiniteField.getField().get(new Polynomial(1, 16)), FiniteField.getField().get(new Polynomial(1, 15))
                , Main.M
                , Main.BASE));

        Polynomial res = FiniteField.times(p2, reversed, Main.M, Main.BASE);
        res = FiniteField.divideByBase(res, Main.BASE);
        result = res.getCoef();
        normOfSyndrome.add(result);

        return normOfSyndrome;
    }

    private static int[] getRowFromColumn(int[][] matrix) {
        int[] arr = new int[matrix.length];

        for(int i = 0; i < matrix.length; i++) {
            arr[i] = matrix[i][0];
        }

        return arr;
    }

    private static int[][] getColumnFromRow(int[] matrix) {
        int[][] arr = new int[matrix.length][1];

        for(int i = 0; i < matrix.length; i++) {
            arr[i][0] = matrix[i];
        }

        return arr;
    }
}
