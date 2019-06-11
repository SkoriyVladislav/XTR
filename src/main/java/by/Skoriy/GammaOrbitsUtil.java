package by.Skoriy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GammaOrbitsUtil {
    public static List<int[]> getGammaOrbits(int size) {
        List<int[]> gammaOrbits = new ArrayList<>();
        Set<List<Integer>> combinations = new HashSet<>();
        int sizeOfIOrbits = 0;
        for (int i = 9; i <= size; i++) {

            int[] arr = new int[Main.N];
            for (int j = 0; j < Main.N; j++) {
                arr[j] = j;
            }
            int n = arr.length;
            for (int j = size; j < n; j++) {
                gammaOrbits = PermutationSimple.getAllGammaOrbits(arr, j, i, gammaOrbits, combinations);
            }
            System.out.println(i + "-ых ошибок = " + (gammaOrbits.size() - sizeOfIOrbits));
            sizeOfIOrbits = gammaOrbits.size();
        }

        return gammaOrbits;
    }
}
