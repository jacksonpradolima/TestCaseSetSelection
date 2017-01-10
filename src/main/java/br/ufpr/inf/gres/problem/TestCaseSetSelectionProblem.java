/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.problem;

import br.ufpr.inf.gres.exception.TestCaseSetSelectionException;
import br.ufpr.inf.gres.pojo.Mutant;
import br.ufpr.inf.gres.pojo.TestCase;
import br.ufpr.inf.gres.pojo.TestCaseMutant;
import br.ufpr.inf.gres.reader.Reader;
import br.ufpr.inf.gres.solution.VariableIntegerSolution;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author giovaniguizzo
 */
public class TestCaseSetSelectionProblem extends AbstractVariableIntegerProblem {

    private List<TestCase> testCases;
    private List<Mutant> mutants;

    public TestCaseSetSelectionProblem(String matrixPath, String separator, Boolean complexHeader) throws TestCaseSetSelectionException {
        try {
            Reader reader = new Reader(matrixPath, separator, complexHeader);
            reader.read();
            this.testCases = reader.getTestCases();
            if (testCases.isEmpty()) {
                throw new TestCaseSetSelectionException("No test cases in problem file. Maybe the format is wrong?");
            }
            this.mutants = reader.getMutants();
            if (mutants.isEmpty()) {
                throw new TestCaseSetSelectionException("No mutants in problem file. Maybe the format is wrong?");
            }
            setNumberOfObjectives(2);
            setNumberOfConstraints(0);
            setNumberOfVariables(testCases.size());
            setLowerLimit(testCases.stream().map(value -> 0).collect(Collectors.toList()));
            setUpperLimit(testCases.stream().map(value -> testCases.size() - 1).collect(Collectors.toList()));
            setName("Test Case Set Selection");
        } catch (FileNotFoundException ex) {
            throw new TestCaseSetSelectionException("Problem file not found!");
        }
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public List<Mutant> getMutants() {
        return mutants;
    }

    @Override
    public void evaluate(VariableIntegerSolution solution) {
        List<Integer> variables = solution.getVariables();
        HashSet<Mutant> hash = new HashSet<>();
        for (Integer testCaseIndex : variables) {
            TestCase testCase = testCases.get(testCaseIndex);
            for (TestCaseMutant testCaseMutant : testCase.getTestCaseMutantList()) {
                if (testCaseMutant.isKilled()) {
                    hash.add(testCaseMutant.getMutant());
                }
            }
        }

        solution.setObjective(0, variables.size());
        solution.setObjective(1, (double) hash.size() / (double) mutants.size() * -1.0);
    }

}
