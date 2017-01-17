/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.problem;

import br.ufpr.inf.gres.exception.TestCaseSetSelectionException;
import br.ufpr.inf.gres.pojo.Mutant;
import br.ufpr.inf.gres.pojo.TestCase;
import br.ufpr.inf.gres.solution.VariableIntegerSolution;
import br.ufpr.inf.gres.utils.TestCaseSetSelectionUtils;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author giovaniguizzo
 */
public class TestCaseSetSelectionProblem extends AbstractVariableIntegerProblem {

    private List<TestCase> testCases;
    private List<Mutant> mutants;

    public TestCaseSetSelectionProblem(List<TestCase> testCases, List<Mutant> mutants) throws TestCaseSetSelectionException {
        this.testCases = testCases;
        this.mutants = mutants;
        
        setNumberOfObjectives(2);
        setNumberOfConstraints(0);
        setNumberOfVariables(testCases.size());
        setLowerLimit(testCases.stream().map(value -> 0).collect(Collectors.toList()));
        setUpperLimit(testCases.stream().map(value -> testCases.size() - 1).collect(Collectors.toList()));
        setName("Test Case Set Selection");
    }

    @Override
    public void evaluate(VariableIntegerSolution solution) {
        List<Integer> variables = solution.getVariables();
        solution.setObjective(0, variables.size());
        solution.setObjective(1, TestCaseSetSelectionUtils.getMutationScore(variables, testCases, mutants) * -1.0);
    }
}
