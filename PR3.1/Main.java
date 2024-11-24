import java.util.Random;
import java.util.Scanner;

class Main {
    public static void main(String[] arg) {
        Scanner s = new Scanner(System.in);

        System.out.print("Enter max i: ");
        int i = Integer.parseInt(s.nextLine());

        System.out.print("Enter max j: ");
        int j = Integer.parseInt(s.nextLine());

        System.out.print("Enter max value: ");
        int val = Integer.parseInt(s.nextLine());

        s.close();

        int[][] arr = generateRandomArray(i, j, val);

        // WorkDealing
        System.out.println("Work dealing method: \n");
        long workDealingStart = System.nanoTime();
        WorkDealing workDealingSolve = new WorkDealing(arr);
        workDealingSolve.Solve();
        long workDealingEnd = System.nanoTime();

        System.out.println(workDealingSolve);
        System.out.println("Time for WorkDealing: " + (workDealingEnd - workDealingStart) / 1_000_000 + " ms\n");

        // WorkStealing
        System.out.println("Work stealing method: \n");
        long workStealingStart = System.nanoTime();
        WorkStealing workStealingSolve = new WorkStealing(arr);
        workStealingSolve.Solve();
        long workStealingEnd = System.nanoTime();

        System.out.println(workStealingSolve);
        System.out.println("Time for WorkStealing: " + (workStealingEnd - workStealingStart) / 1_000_000 + " ms\n");
    }

    public static int[][] generateRandomArray(int maxI, int maxJ, int maxVal) {
        Random r = new Random();
        
        int rows = r.nextInt(maxI - 10) + 10; 
        int cols = r.nextInt(maxJ - 10) + 10; 
        int[][] randomArray = new int[rows][cols];
        for (int i = 0; i < randomArray.length; i++) {
            for (int j = 0; j < randomArray[i].length; j++) {
                randomArray[i][j] = r.nextInt(maxVal);
            }
        }
        return randomArray;
    }
}
