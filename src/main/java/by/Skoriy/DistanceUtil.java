package by.Skoriy;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DistanceUtil {

    public static void getDistanceUsingFiles(int[][] H, int countRow, int N) {
        for (int i = 1; i < N; i++) {

            int[] arr = new int[N];
            for (int j = 0; j < N; j++) {
                arr[j] = j;
            }
            int n = arr.length;
            PermutationSimple.printCombinationInFile(arr, n, i); //print all combination of size i in a file.

            if (checkDistanceFromFile(H, countRow, i)) {
                System.out.print("Distance = " + i);
                break;
            }
        }
    }

    public static boolean checkDistanceFromFile(int[][] H, int countRow, int distance) {
        String fileName = "C://Users//uskory//Desktop//XTR//src//main//resources//Combination" + distance + ".txt";
        //read file into stream, try-with-resources
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {

            return br.lines().anyMatch(s -> {

                String[] arr = s.split(" ");
                int[] indexes = new int[distance];
                for (int i = 0; i < distance; i++) {
                    indexes[i] = Integer.parseInt(arr[i]);
                }                                               // get array of indexes from one line from file

                boolean flag = check(H, indexes, countRow);
                if (flag) {
                    for (int i = 0; i < distance; i++) {
                        System.out.print(indexes[i] + " ");
                    }
                    System.out.println();
                    return true;
                }
                return false;
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void getDistance(int[][] H, int countRow, int N) {

        for (int i = 1; i < N; i++) {

            int[] arr = new int[N];
            for (int j = 0; j < N; j++) {
                arr[j] = j;
            }
            int n = arr.length;
            if (PermutationSimple.checkDistanceUsingAllCombination(arr, n, i, H, countRow)) { //print all combination of size i in a file.
                System.out.println("Distance = " + i);
                break;
            } else {
                System.out.println("Distance != " + i);
            }
        }
    }

    public static boolean check(int[][] array, int[] indexes, int countRow) {
        boolean flag = false;
        for (int i = 0; i < countRow; i++) {
            int sum = 0;

            for (int index : indexes) {
                sum += array[i][index];
            }

            if (sum % 2 == 1) {
                return false;
            } else {
                flag = true;
            }
        }

        return flag;
    }
}
