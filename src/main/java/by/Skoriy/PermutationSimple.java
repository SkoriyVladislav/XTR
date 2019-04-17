package by.Skoriy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PermutationSimple {

    /* arr[]  ---> Input Array
    data[] ---> Temporary array to store current combination
    start & end ---> Staring and Ending indexes in arr[]
    index  ---> Current index in data[]
    r ---> Size of a combination to be printed */
    private static void combinationFilesUtil(int arr[], int data[], int start, int end, int index, int r, FileWriter writer) throws IOException {
        // Current combination is ready to be printed, print it
        if (index == r) {
            for (int j=0; j<r; j++) {
                writer.write(data[j] + " ");
            }
            writer.write("\n");

            //Main.checkDistanceFromFile(H, countRow, arr.length, data);
            return;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i=start; i<=end && end-i+1 >= r-index; i++) {
            data[index] = arr[i];
            combinationFilesUtil(arr, data, i+1, end, index+1, r, writer);
        }
    }

    // The main function that prints all combinations of size r
    // in arr[] of size n. This function mainly uses combinationFilesUtil()
    public static void printCombinationInFile(int arr[], int n, int r) {
        // A temporary array to store all combination one by one
        int data[] = new int[r];
        //combinationFilesUtil(arr, data, 0, n-1, 0, r, writer);
        File file = null;
        try {
            file = new File("src/main/resources/Combination" + r + ".txt");
            if(file.exists()){
                System.out.println("File exist");
            } else {
                file.createNewFile();

                try (FileWriter writer = new FileWriter(file)) {
                    // Print all combination using temprary array 'data[]'
                    combinationFilesUtil(arr, data, 0, n-1, 0, r, writer);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkDistanceUsingAllCombination(int arr[], int n, int r, int[][] H, int countRow) {
        // A temporary array to store all combination one by one
        int data[] = new int[r];
        return combinationUtil(arr, data, 0, n-1, 0, r, H, countRow);
    }

    private static boolean combinationUtil(int arr[], int data[], int start, int end, int index, int r, int[][] H, int countRow) {
        // Current combination is ready to be printed, print it
        if (index == r) {
            //System.out.println("Check " + data);

            /*if(data.length == 6 && data[0] == 0 && data[1] == 1 && data[2] == 3 && data[3] == 4 && data[4] == 10 && data[5] == 37) {
                System.out.println("ALKSJdhlkajsdhlkj");
                boolean flag = DistanceUtil.check(H, data, countRow);
                System.out.println(flag);
            }*/

            boolean flag = DistanceUtil.check(H, data, countRow);
            if (flag) {
                for (int j=0; j<r; j++) {
                    System.out.print(data[j] + " ");
                }
                System.out.println();
            }
            return flag;
        }

        boolean flag = false;

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i=start; i<=end && end-i+1 >= r-index; i++) {
            data[index] = arr[i];
            flag = combinationUtil(arr, data, i+1, end, index+1, r, H, countRow);
            if (flag) {
                return true;
            }
        }
        return false;
    }
}
