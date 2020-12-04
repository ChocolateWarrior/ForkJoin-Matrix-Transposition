package com.helvetica.process;

import com.helvetica.task.RowTranspositionTask;
import com.helvetica.util.ArrayUtils;
import com.helvetica.util.Constants;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import static com.helvetica.util.Constants.SIZE;

public class ForkJoinProcess {


    public Integer[] executeExample() {

        final Integer[][] matrix = Constants.EXAMPLE_FORK_JOIN;

        Integer[] originalMatrix = ArrayUtils.flattenArray(matrix, SIZE, SIZE);
        final RecursiveTask<Integer[]> task =
                new RowTranspositionTask(originalMatrix, originalMatrix, 0);

        final Integer[] result = new ForkJoinPool().invoke(task);
        return result;
    }

}
