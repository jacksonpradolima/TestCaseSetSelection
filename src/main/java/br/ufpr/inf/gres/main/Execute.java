package br.ufpr.inf.gres.main;

import br.ufpr.inf.gres.exception.TestCaseSetSelectionException;
import br.ufpr.inf.gres.operator.crossover.SinglePointCrossoverVariableLength;
import br.ufpr.inf.gres.operator.mutation.PermutationIntegerMutation;
import br.ufpr.inf.gres.problem.TestCaseSetSelectionProblem;
import br.ufpr.inf.gres.solution.VariableIntegerSolution;
import com.beust.jcommander.JCommander;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;

public class Execute {

    public static void main(String[] args) {
        try {
            ExecuteCommands jct = new ExecuteCommands();
            JCommander jCommander = new JCommander(jct, args);
            jCommander.setProgramName(Execute.class.getSimpleName());

            if (jct.help) {
                jCommander.usage();
                return;
            }

            jct.runs = jct.runs < 1 ? 1 : jct.runs;

            List<Double> objective1Values = new ArrayList<>();
            List<Double> objective2Values = new ArrayList<>();
            for (int i = 0; i < jct.runs; i++) {
                TestCaseSetSelectionProblem problem = new TestCaseSetSelectionProblem(jct.path, jct.dataSeparator, jct.skipHeader);
                SinglePointCrossoverVariableLength crossoverOperator = new SinglePointCrossoverVariableLength(1.0);
                PermutationIntegerMutation mutationOperator = new PermutationIntegerMutation(0.05);

                NSGAIIBuilder<VariableIntegerSolution> builder = new NSGAIIBuilder(problem, crossoverOperator, mutationOperator);
                builder.setPopulationSize(100).setMaxEvaluations(1000);

                NSGAII<VariableIntegerSolution> algorithm = builder.build();

                algorithm.run();

                VariableIntegerSolution result = algorithm.getResult()
                        .stream()
                        .sorted((item, item2) -> Double.compare(item.getObjective(1), item2.getObjective(1)))
                        .collect(Collectors.toList())
                        .get(0);
                System.out.println("Run #" + (i + 1));
                System.out.println("\tTest Cases: " + result.getVariables().stream().map(value -> problem.getTestCases().get(value).getDescription()).collect(Collectors.joining(" ")));
                System.out.println("\tSize: " + result.getObjective(0));
                System.out.println("\tScore: " + result.getObjective(1));

                objective1Values.add(result.getObjective(0));
                objective2Values.add(result.getObjective(1));
            }
            System.out.println("");
            System.out.println("Average Size: " + objective1Values.stream().mapToDouble(value -> value).average().getAsDouble());
            System.out.println("Average Score: " + objective2Values.stream().mapToDouble(value -> value).average().getAsDouble());
        } catch (TestCaseSetSelectionException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("You must inform 3 arguments. First the problem file path, second the data separator, and third if the matrix has a special header with 'Mutant', 'Total' and 'State' keywords."
                    + "\nPlease, use quotes for the arguments.");
            System.exit(1);
        }
    }

}
