import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class WorkStealing {
    private int[][] array;
    private ArrayList<AllResult> results = new ArrayList<>();

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

    public WorkStealing(int[][] arr) {
        this.array = arr;
    }

    public void Solve() {
        ForkJoinPool pool = new ForkJoinPool(); // Создаем ForkJoinPool
        ResultTask task = new ResultTask(0, array.length);
        List<AllResult> partialResults = pool.invoke(task); // Запускаем основную задачу

        // Объединяем результаты
        results.addAll(partialResults);
    }

    // Задача для обработки диапазона строк
    private class ResultTask extends RecursiveTask<List<AllResult>> {
        private static final int THRESHOLD = 2; // Граничный размер для разделения
        private int startRow;
        private int endRow;

        public ResultTask(int startRow, int endRow) {
            this.startRow = startRow;
            this.endRow = endRow;
        }

        @Override
        protected List<AllResult> compute() {
            List<AllResult> localResults = new ArrayList<>();

            // Если диапазон мал, выполняем задачу
            if (endRow - startRow <= THRESHOLD) {
                for (int i = startRow; i < endRow; i++) {
                    for (int j = 0; j < array[i].length; j++) {
                        if (i + j == array[i][j]) {
                            localResults.add(new AllResult(i, j, array[i][j]));
                        }
                    }
                }
                return localResults;
            } else {
                
                int midRow = (startRow + endRow) / 2;
                ResultTask leftTask = new ResultTask(startRow, midRow);
                ResultTask rightTask = new ResultTask(midRow, endRow);

                leftTask.fork();    // поток заноситься до пулу, щоб потім, якщо один 
                                    // з потоків стане вільним або був вільним взяти 
                                    // на виконаня метод compute
                List<AllResult> rightResult = rightTask.compute(); 
                List<AllResult> leftResult = leftTask.join();

                // Объединяем результаты
                localResults.addAll(leftResult);
                localResults.addAll(rightResult);
                return localResults;
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
