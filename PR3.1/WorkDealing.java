import java.util.ArrayList;

class WorkDealing {
    private int[][] array;
    private ArrayList<AllResult> results = null;

    // Вложенный класс для хранения результата
    private class AllResult {
        int i;
        int j;
        int val;

        public AllResult(int i, int j, int val) {
            this.i = i;
            this.j = j;
            this.val = val;
        }

        public String getResult() {
            return String.format("Element under indexes (%d, %d) is %d%n", i, j, val);
        }
    }

    public WorkDealing(int[][] arr) {
        array = arr;
    }

    public void Solve() {
        for (int i = 0; i < array.length; i++) {
            
            int row = i; 
            Thread thread = new Thread(() -> {
                for (int j = 0; j < array[row].length; j++) {
                    if (row + j == array[row][j]) {
                        synchronized (results) { 
                            results.add(new AllResult(row, j, array[row][j]));
                        }
                    }
                }
            });
            thread.start(); 
            try {
                thread.join(); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (var res : results) {
            result.append(res.getResult());
        }
        return result.toString();
    }
}
