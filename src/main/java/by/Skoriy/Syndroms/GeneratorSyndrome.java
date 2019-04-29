package by.Skoriy.Syndroms;

import by.Skoriy.Field.FiniteField;
import by.Skoriy.Main;
import by.Skoriy.Polynom.Polynomial;

import java.util.*;


public class GeneratorSyndrome {
    private static Map<Integer, List<int[]> > generatorsSyndrome = new HashMap<>();
    static {
        for (int i = 1; i < Main.N / 2 + 1; i++) {

            int[] coeff = new int[Main.N];
            coeff[0] = 1;
            coeff[i] = 1;

            List<int[]> syndroms = new ArrayList<>();
            int[] syndrome = getRowFromColumn(Main.multiplyByMatrix(Main.H1H3, getColumnFromRow(coeff)));
            for (int j = 0; j < syndrome.length; j += Main.M) {
                int[] subSyndrome = Arrays.copyOfRange(syndrome, j, j + Main.M);
                syndroms.add(subSyndrome);
            }

            generatorsSyndrome.put(i, syndroms);
        }
    }

    private static Map<Integer, List<int[]> > generatorsNorms = new HashMap<>();
    static {
        //TODO сделать для общего случая
        List<int[]> normOfSyndrome = new ArrayList<>();
        int[] s1 = generatorsSyndrome.get(1).get(0);
        int[] s2 = generatorsSyndrome.get(1).get(1);
        int[] result;
        Polynomial p2 = FiniteField.getPolynomInDegree(new Polynomial(s2), 3, Main.M, Main.BASE);
        Polynomial res = new Polynomial(s1).remainder(p2);
        result = res.getCoef();
        normOfSyndrome.add(result);
        generatorsNorms.put(1, normOfSyndrome);
    }

    public static Map<Integer, List<int[]>> getGeneratorsNorms() {
        return generatorsNorms;
    }

    public static Map<Integer, List<int[]>> getGeneratorsSyndrome() {
        return generatorsSyndrome;
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
