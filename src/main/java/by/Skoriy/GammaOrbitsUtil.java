package by.Skoriy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GammaOrbitsUtil {
    public static List<int[]> getGammaOrbits(int size) {
        List<int[]> gammaOrbits = new ArrayList<>();
        Set<List<Integer>> combinations = new HashSet<>();
        for (int i = 1; i <= size; i++) {

            int[] arr = new int[Main.N];
            for (int j = 0; j < Main.N; j++) {
                arr[j] = j;
            }
            int n = arr.length;
            gammaOrbits = PermutationSimple.getAllGammaOrbits(arr, n, i, gammaOrbits, combinations);
        }

        return gammaOrbits;
    }
}
