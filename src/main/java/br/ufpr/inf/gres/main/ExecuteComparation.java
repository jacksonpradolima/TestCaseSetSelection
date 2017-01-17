/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.main;

import br.ufpr.inf.gres.runner.TestCaseSetSelectionBuilder;
import br.ufpr.inf.gres.exception.TestCaseSetSelectionException;
import com.beust.jcommander.JCommander;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class run the optimization in a matrix and compare it results in a second matrix
 *
 * @author Jackson Antonio do Prado Lima <jacksonpradolima at gmail.com>
 * @version 1.0
 */
public class ExecuteComparation {

    private static final Logger logger = LoggerFactory.getLogger(ExecuteComparation.class);

    public static void main(String[] args) {
        try {
            ExecuteComparationCommands jct = new ExecuteComparationCommands();
            JCommander jCommander = new JCommander(jct, args);
            jCommander.setProgramName(ExecuteComparation.class.getSimpleName());

            if (jct.help) {
                jCommander.usage();
                return;
            }
            
            logger.info(jct.toString());
            
            TestCaseSetSelectionBuilder builder = new TestCaseSetSelectionBuilder(jct.path, jct.dataSeparator)
                    .setSkipHeader(jct.skipHeader)
                    .setRuns(jct.runs < 1 ? 1 : jct.runs)
                    .read();

            // Obtain the results 
            builder.run();            
            
            //Compare the results obtained with the other matrix
            builder.compare(new TestCaseSetSelectionBuilder(jct.otherPath, jct.otherDataSeparator).setSkipHeader(jct.otherSkipHeader));                        
        } catch (TestCaseSetSelectionException ex) {
            logger.error(ex.getMessage(), ex);            
        } 
    }
}
