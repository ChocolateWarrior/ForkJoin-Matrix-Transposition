package com.helvetica.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.helvetica.util.Constants.SIZE;

public class RowTranspositionTask extends RecursiveTask<Integer[]> {

    private final Integer[] originalMatrix;
    private Integer[] currentMatrix;
    private int startingIndexInOriginalMatrix;

    public RowTranspositionTask(Integer[] originalMatrix, Integer[] currentMatrix, int startingIndexInOriginalMatrix) {
        this.originalMatrix = originalMatrix;
        this.currentMatrix = currentMatrix;
        this.startingIndexInOriginalMatrix = startingIndexInOriginalMatrix;
    }

    private Integer[] transpose() {
        int workerMatrixSize = currentMatrix.length;
        Integer[] result = new Integer[workerMatrixSize];

        for (int i = 0; i < workerMatrixSize; i++) {
            int currentElementNum = startingIndexInOriginalMatrix + i;
            result[i] = originalMatrix[(currentElementNum * SIZE %
                    (SIZE * SIZE)) + (int) Math.floor(currentElementNum / SIZE)];
        }
        return result;
    }

    private List<RecursiveTask<Integer[]>> createSubtasks() {

        int chunkSize = currentMatrix.length / 2;

        return IntStream.range(0, 2)
                .mapToObj(index -> new RowTranspositionTask(originalMatrix,
                        getSubMatrix(index, chunkSize), getSubIndex(index, chunkSize)))
                .collect(Collectors.toList());
    }

    private int getSubIndex(int index, int chunkSize) {
        return index * chunkSize + startingIndexInOriginalMatrix;
    }

    private Integer[] getSubMatrix(final int index, final int chunkSize) {

        final int lowerBound = index * chunkSize;
        final int upperBound = Math.min(lowerBound + chunkSize, currentMatrix.length);

        Integer[] chunkMatrix = Arrays.copyOfRange(currentMatrix, lowerBound, upperBound);
        return chunkMatrix;
    }

    @Override
    protected Integer[] compute() {

        if (currentMatrix.length <= SIZE) {
            return transpose();
        }
        int chunkSize = currentMatrix.length / 2;

        RowTranspositionTask firstTask = new RowTranspositionTask(originalMatrix,
                getSubMatrix(0, chunkSize), getSubIndex(0, chunkSize));
        firstTask.fork();

        RowTranspositionTask secondTask = new RowTranspositionTask(originalMatrix,
                getSubMatrix(1, chunkSize), getSubIndex(1, chunkSize));

        Integer[] secondTaskResult = secondTask.compute();
        Integer[] firstTaskResult = firstTask.join();

        List<Integer> firstTaskResultList = new ArrayList<>(List.of(firstTaskResult));
        List<Integer> secondTaskResultList = new ArrayList<>(List.of(secondTaskResult));
        firstTaskResultList.addAll(secondTaskResultList);

        return firstTaskResultList.toArray(Integer[]::new);
    }
}
