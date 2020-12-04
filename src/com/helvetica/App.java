package com.helvetica;

import com.helvetica.process.ForkJoinProcess;
import com.helvetica.util.ArrayUtils;

import static com.helvetica.util.Constants.SIZE;

public class App {

    public static void main(String[] args) {

        ForkJoinProcess forkJoinProcess = new ForkJoinProcess();
        Integer[] res = forkJoinProcess.executeExample();

        ArrayUtils.printFlattened(res, SIZE);

    }
}
