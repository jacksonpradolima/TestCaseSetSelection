/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.runner;

import br.ufpr.inf.gres.exception.TestCaseSetSelectionException;
import br.ufpr.inf.gres.operator.crossover.SinglePointCrossoverVariableLength;
import br.ufpr.inf.gres.operator.mutation.PermutationIntegerMutation;
import br.ufpr.inf.gres.pojo.Mutant;
import br.ufpr.inf.gres.pojo.OptimizationResult;
import br.ufpr.inf.gres.pojo.TestCase;
import br.ufpr.inf.gres.problem.TestCaseSetSelectionProblem;
import br.ufpr.inf.gres.reader.Reader;
import br.ufpr.inf.gres.solution.VariableIntegerSolution;
import br.ufpr.inf.gres.utils.TestCaseSetSelectionUtils;
import br.ufpr.inf.gres.utils.TestCaseUtils;
import com.google.common.base.Preconditions;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;

/**
 *
 * @author Jackson Antonio do Prado Lima <jacksonpradolima at gmail.com>
 * @version 1.0
 */
public class TestCaseSetSelectionBuilder {

    private static final Logger logger = LoggerFactory.getLogger(TestCaseSetSelectionBuilder.class);
    private String dataSeparator;
    private List<Mutant> mutants;
    private List<OptimizationResult> optimizationResults;
    private String path;
    private int runs;
    private Boolean skipHeader;
    private List<TestCase> testCases;

    public TestCaseSetSelectionBuilder(String path, String dataSeparator) {
        this.path = path;
        this.dataSeparator = dataSeparator;
        this.skipHeader = true;
        this.runs = 1;

        this.testCases = new ArrayList<>();
        this.mutants = new ArrayList<>();
        this.optimizationResults = new ArrayList<>();
    }

    /**
     * Compare the optimization result in another matrix to obtain the mutation score
     *
     * @param other
     */
    public void compare(TestCaseSetSelectionBuilder other) {
        List<OptimizationResult> compareResults = new ArrayList<>();

        // Read the informations about the other
        other.read();

        for (int i = 0; i < optimizationResults.size(); i++) {
            OptimizationResult optimizationResult = optimizationResults.get(i);

            // Discovery the mutation score with the Test Cases from Optimization Result in the "other" matrix
            List<TestCase> aux = optimizationResult.getTestCases();

            double size = (double) aux.size();
            
            // Get the mutation score in the other matrix            
            double mutationScore = other.getMutationScore(aux.stream().map(m -> m.getDescription()).collect(Collectors.toList()));

            OptimizationResult optimizationResultAux = new OptimizationResult(size, mutationScore, aux);
            
            compareResults.add(optimizationResultAux);

            logger.debug("Run #" + (i + 1));
            logger.debug(optimizationResultAux.toString());
        }

        logger.info("[Compare] Average Size: " + compareResults.stream().mapToDouble(value -> value.getObjective1Value()).average().getAsDouble());
        logger.info("[Compare] Average Score: " + compareResults.stream().mapToDouble(value -> value.getObjective2Value()).average().getAsDouble());
    }

    /**
     * Get the mutants presents in the matrix
     *
     * @return
     */
    public List<Mutant> getMutants() {
        return this.mutants;
    }

    /**
     * Get the mutation score of a test case list
     *
     * @param testCasesList
     * @return
     */
    public double getMutationScore(List<String> testCasesList) {
        return TestCaseSetSelectionUtils.getMutationScore(TestCaseUtils.getVariables(testCasesList, testCases), this.testCases, this.mutants);
    }

    /**
     * Get the optimization result
     *
     * @return
     */
    public List<OptimizationResult> getResults() {
        return this.optimizationResults;
    }

    /**
     * Get the test cases presents in the matrix
     *
     * @return
     */
    public List<TestCase> getTestCases() {
        return this.testCases;
    }

    /**
     * Read the matrix
     *
     * @return
     */
    public TestCaseSetSelectionBuilder read() {
        try {
            Reader reader = new Reader(path, dataSeparator, skipHeader);
            reader.read();
            this.testCases = reader.getTestCases();
            if (testCases.isEmpty()) {
                throw new TestCaseSetSelectionException("No test cases in problem file. Maybe the format is wrong?");
            }
            this.mutants = reader.getMutants();
            if (mutants.isEmpty()) {
                throw new TestCaseSetSelectionException("No mutants in problem file. Maybe the format is wrong?");
            }
        } catch (FileNotFoundException ex) {
            throw new TestCaseSetSelectionException("Problem file not found. Maybe the path is wrong?");
        }

        return this;
    }

    /**
     * Run the optimization
     *
     * @throws TestCaseSetSelectionException
     */
    public void run() throws TestCaseSetSelectionException {
        Preconditions.checkNotNull(this.testCases.isEmpty(), "The test cases are empty. Try to execute the read command first.");
        Preconditions.checkNotNull(this.mutants.isEmpty(), "The mutants are empty. Try to execute the read command first.");

        for (int i = 0; i < runs; i++) {
            NSGAII<VariableIntegerSolution> algorithm
                    = new NSGAIIBuilder(new TestCaseSetSelectionProblem(this.testCases, this.mutants), new SinglePointCrossoverVariableLength(1.0), new PermutationIntegerMutation(0.05))
                    .setPopulationSize(100)
                    .setMaxEvaluations(1000)
                    .build();

            algorithm.run();

            VariableIntegerSolution result = algorithm.getResult()
                    .stream()
                    .sorted((item, item2) -> Double.compare(item.getObjective(1), item2.getObjective(1)))
                    .collect(Collectors.toList())
                    .get(0);

            OptimizationResult optimizationResult = new OptimizationResult(result.getObjective(0), result.getObjective(1), result.getVariables().stream().map(value -> this.testCases.get(value)).collect(Collectors.toList()));
            optimizationResults.add(optimizationResult);

            logger.debug("Run #" + (i + 1));
            logger.debug(optimizationResult.toString());
        }
        logger.info("Average Size: " + optimizationResults.stream().mapToDouble(value -> value.getObjective1Value()).average().getAsDouble());
        logger.info("Average Score: " + optimizationResults.stream().mapToDouble(value -> value.getObjective2Value()).average().getAsDouble());
    }

    /**
     * Set the matrix path
     *
     * @param path
     * @return
     */
    public TestCaseSetSelectionBuilder setPath(String path) {
        if (path == null || path.isEmpty()) {
            throw new TestCaseSetSelectionException("The path is empty");
        }

        this.path = path;
        return this;
    }

    /**
     * Set the number of optimization runs
     *
     * @param runs
     * @return
     */
    public TestCaseSetSelectionBuilder setRuns(int runs) {
        if (runs < 0) {
            throw new TestCaseSetSelectionException("runs is negative: " + runs);
        }

        this.runs = runs;
        return this;
    }

    /**
     * Set the skip header
     *
     * @param skipHeader
     * @return
     */
    public TestCaseSetSelectionBuilder setSkipHeader(Boolean skipHeader) {
        this.skipHeader = skipHeader;
        return this;
    }
}
