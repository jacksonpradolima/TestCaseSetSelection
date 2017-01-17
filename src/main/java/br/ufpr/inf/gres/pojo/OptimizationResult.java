/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.pojo;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents a optimization result
 * @author Jackson Antonio do Prado Lima <jacksonpradolima at gmail.com>
 * @version 1.0
 */
public class OptimizationResult {

    private static final Logger logger = LoggerFactory.getLogger(OptimizationResult.class);

    private Double size;
    private Double score;
    private List<TestCase> testCases;

    public OptimizationResult(Double size, Double score, List<TestCase> testCases) {
        this.size = size;
        this.score = score;
        this.testCases = testCases;
    }

    /**
     * @return the size
     */
    public Double getObjective1Value() {
        return size;
    }

    /**
     * @return the score
     */
    public Double getObjective2Value() {
        return score;
    }

    /**
     * @return the testCases
     */
    public List<TestCase> getTestCases() {
        return testCases;
    }

    @Override
    public String toString() {
        return "Result = [testcases={" + testCases.stream().map(m -> m.getDescription()).collect(Collectors.joining(","))
                + "}, size=" + size
                + ", score=" + score + "]";
    }
}
