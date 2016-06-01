/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.cbiogres.problem;

import br.ufpr.inf.cbiogres.pojo.Mutant;
import br.ufpr.inf.cbiogres.pojo.TestCase;
import br.ufpr.inf.cbiogres.pojo.TestCaseMutant;
import br.ufpr.inf.cbiogres.reader.Reader;
import br.ufpr.inf.cbiogres.solution.VariableIntegerSolution;
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

    public TestCaseSetSelectionProblem(String matrixPath, String separator) throws FileNotFoundException {
        Reader reader = new Reader(matrixPath, separator);
        reader.read();
        this.testCases = reader.getTestCases();
        this.mutants = reader.getMutants();
        setNumberOfObjectives(2);
        setNumberOfConstraints(0);
        setNumberOfVariables(testCases.size());
        setLowerLimit(testCases.stream().map(value -> 0).collect(Collectors.toList()));
        setUpperLimit(testCases.stream().map(value -> testCases.size() - 1).collect(Collectors.toList()));
        setName("Test Case Set Selection");
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
