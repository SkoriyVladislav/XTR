package by.Skoriy;

import by.Skoriy.Field.FiniteField;
import by.Skoriy.Polynom.Polynomial;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PermutationSimple {

    public static <T> List<List<T>> combination(List<T> values, int size) {

        if (0 == size) {
            return Collections.singletonList(Collections.<T> emptyList());
        }

        if (values.isEmpty()) {
            return Collections.emptyList();
        }

        List<List<T>> combination = new LinkedList<>();

        T actual = values.iterator().next();
        List<T> subSet = new LinkedList<T>(values);
        subSet.remove(actual);

        List<List<T>> subSetCombination = combination(subSet, size - 1);

        for (List<T> set : subSetCombination) {
            List<T> newSet = new LinkedList<T>(set);
            newSet.add(0, actual);
            combination.add(newSet);
        }

        combination.addAll(combination(subSet, size));
        return combination;
    }

    /* arr[]  ---> Input Array
    data[] ---> Temporary array to store current combination
    start & end ---> Staring and Ending indexes in arr[]
    index  ---> Current index in data[]
    r ---> Size of a combination to be printed */
    private static void combinationUtil(int arr[], int data[], int start, int end, int index, int r, FileWriter writer) throws IOException {
        // Current combination is ready to be printed, print it
        if (index == r) {
            for (int j=0; j<r; j++) {
                writer.write(data[j] + " ");
            }
            writer.write("\n");


            //Main.checkDistance(H, countRow, arr.length, data);

            return;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i=start; i<=end && end-i+1 >= r-index; i++) {
            data[index] = arr[i];
            combinationUtil(arr, data, i+1, end, index+1, r, writer);
        }
    }

    // The main function that prints all combinations of size r
    // in arr[] of size n. This function mainly uses combinationUtil()
    public static void printCombination(int arr[], int n, int r) {
        // A temporary array to store all combination one by one
        int data[] = new int[r];
        //combinationUtil(arr, data, 0, n-1, 0, r, writer);
        File file = null;
        try {
            file = new File("src/main/resources/Combination" + r + ".txt");
            if(file.exists()){
                System.out.println("File exist");
            } else {
                file.createNewFile();

                try (FileWriter writer = new FileWriter(file)) {
                    // Print all combination using temprary array 'data[]'
                    combinationUtil(arr, data, 0, n-1, 0, r, writer);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
