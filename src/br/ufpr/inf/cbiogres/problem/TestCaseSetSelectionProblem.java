/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.cbiogres.problem;

import br.ufpr.inf.cbiogres.pojo.Mutant;
import br.ufpr.inf.cbiogres.pojo.Product;
import br.ufpr.inf.cbiogres.pojo.ProductMutant;
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

    private List<Product> products;
    private List<Mutant> mutants;

    public TestCaseSetSelectionProblem(String matrixPath, String separator) throws FileNotFoundException {
        Reader reader = new Reader(matrixPath, separator);
        reader.read();
        this.products = reader.getProducts();
        this.mutants = reader.getMutants();
        setNumberOfObjectives(2);
        setNumberOfConstraints(0);
        setNumberOfVariables(products.size());
        setLowerLimit(products.stream().map(value -> 0).collect(Collectors.toList()));
        setUpperLimit(products.stream().map(value -> products.size() - 1).collect(Collectors.toList()));
        setName("Test Case Set Selection");
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Mutant> getMutants() {
        return mutants;
    }

    @Override
    public void evaluate(VariableIntegerSolution solution) {
        List<Integer> variables = solution.getVariables();
        HashSet<Mutant> hash = new HashSet<>();
        for (Integer productIndex : variables) {
            Product product = products.get(productIndex);
            for (ProductMutant productMutant : product.getProductMutantList()) {
                if (productMutant.isKilled()) {
                    hash.add(productMutant.getMutant());
                }
            }
        }

        solution.setObjective(0, variables.size());
        solution.setObjective(1, (double) hash.size() / (double) mutants.size() * -1.0);
    }

}
