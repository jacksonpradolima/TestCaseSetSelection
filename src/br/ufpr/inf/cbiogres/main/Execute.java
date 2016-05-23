package br.ufpr.inf.cbiogres.main;

import br.ufpr.inf.cbiogres.operator.crossover.SinglePointCrossoverVariableLength;
import br.ufpr.inf.cbiogres.operator.mutation.PermutationIntegerMutation;
import br.ufpr.inf.cbiogres.problem.TestCaseSetSelectionProblem;
import br.ufpr.inf.cbiogres.solution.VariableIntegerSolution;
import java.io.FileNotFoundException;
import java.util.stream.Collectors;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.util.AlgorithmRunner;

public class Execute {

    public static void main(String[] args) throws FileNotFoundException {
        TestCaseSetSelectionProblem problem = new TestCaseSetSelectionProblem(args[0], args[1]);
        SinglePointCrossoverVariableLength crossoverOperator = new SinglePointCrossoverVariableLength(1.0);
        PermutationIntegerMutation mutationOperator = new PermutationIntegerMutation(0.05);

        NSGAIIBuilder<VariableIntegerSolution> builder = new NSGAIIBuilder(problem, crossoverOperator, mutationOperator);
        builder.setPopulationSize(100)
                .setMaxIterations(1000);

        NSGAII<VariableIntegerSolution> algorithm = builder.build();

        AlgorithmRunner runner = new AlgorithmRunner.Executor(algorithm).execute();

        VariableIntegerSolution result = algorithm.getResult()
                .stream()
                .sorted((item, item2) -> Double.compare(item.getObjective(1), item2.getObjective(1)))
                .collect(Collectors.toList())
                .get(0);

        System.out.println("Test Cases: " + result.getVariables().stream().map(value -> String.valueOf(value)).collect(Collectors.joining(" ")));
        System.out.println("Size: " + result.getObjective(0));
        System.out.println("Score: " + result.getObjective(1));
    }

}
