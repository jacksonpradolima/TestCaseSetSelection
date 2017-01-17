/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.main;

import br.ufpr.inf.gres.exception.TestCaseSetSelectionException;
import br.ufpr.inf.gres.runner.TestCaseSetSelectionBuilder;
import com.beust.jcommander.JCommander;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class calculates the mutation score for a matrix in relation with the test cases passed
 *
 * @author Jackson Antonio do Prado Lima <jacksonpradolima at gmail.com>
 * @version 1.0
 */
public class CalculateMutationScore {

    private static final Logger logger = LoggerFactory.getLogger(CalculateMutationScore.class);

    public static void main(String[] args) {
        try {
            CalculateMutationScoreCommands jct = new CalculateMutationScoreCommands();
            JCommander jCommander = new JCommander(jct, args);
            jCommander.setProgramName(CalculateMutationScore.class.getSimpleName());

            if (jct.help) {
                jCommander.usage();
                return;
            }

            logger.info(jct.toString());
            
            TestCaseSetSelectionBuilder builder = new TestCaseSetSelectionBuilder(jct.path, jct.dataSeparator)
                    .setSkipHeader(jct.skipHeader)
                    .read();

            logger.info("Mutation Score: " + builder.getMutationScore(jct.testCases));            
        } catch (TestCaseSetSelectionException ex) {
            logger.error(ex.getMessage(), ex);            
        } 
    }
}
