/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.utils;

import br.ufpr.inf.gres.exception.TestCaseSetSelectionException;
import br.ufpr.inf.gres.pojo.TestCase;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jackson Antonio do Prado Lima <jacksonpradolima at gmail.com>
 * @version 1.0
 */
public class TestCaseUtils {

    private static final Logger logger = LoggerFactory.getLogger(TestCaseUtils.class);

    /**
     * Get the test cases selected indexes in relation the test case set
     *
     * @param testCasesSelected The test cases that I want discovery the indexes
     * @param testCases The test cases set
     * @return
     * @throws TestCaseSetSelectionException
     */    
    public static List<Integer> getVariables(List<String> testCasesSelected, List<TestCase> testCases) throws TestCaseSetSelectionException {

        return Arrays.asList(ArrayUtils.toObject(IntStream.range(0, testCases.size())
                .filter(i -> testCasesSelected.contains(testCases.get(i).getDescription()))
                .toArray()));
    }
}
