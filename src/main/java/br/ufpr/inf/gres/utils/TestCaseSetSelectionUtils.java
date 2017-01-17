/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.utils;

import br.ufpr.inf.gres.pojo.Mutant;
import br.ufpr.inf.gres.pojo.TestCase;
import br.ufpr.inf.gres.pojo.TestCaseMutant;
import java.util.HashSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jackson Antonio do Prado Lima <jacksonpradolima at gmail.com>
 * @version 1.0
 */
public class TestCaseSetSelectionUtils {

    private static final Logger logger = LoggerFactory.getLogger(TestCaseSetSelectionUtils.class);

    public static double getMutationScore(List<Integer> variables, List<TestCase> testCases, List<Mutant> mutants) {
        HashSet<Mutant> hash = new HashSet<>();
        for (Integer testCaseIndex : variables) {
            TestCase testCase = testCases.get(testCaseIndex);
            for (TestCaseMutant testCaseMutant : testCase.getTestCaseMutantList()) {
                if (testCaseMutant.isKilled()) {
                    hash.add(testCaseMutant.getMutant());
                }
            }
        }

        return (double) hash.size() / (double) mutants.size();
    }       
}
