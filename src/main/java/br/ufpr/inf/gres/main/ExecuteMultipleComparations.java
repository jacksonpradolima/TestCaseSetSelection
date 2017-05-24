/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.main;

import br.ufpr.inf.gres.runner.TestCaseSetSelectionBuilder;
import br.ufpr.inf.gres.exception.TestCaseSetSelectionException;
import br.ufpr.inf.gres.pojo.OptimizationResult;
import br.ufpr.inf.gres.utils.FilesUtils;
import com.beust.jcommander.JCommander;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class run the optimization in a matrix and compare it results in a second matrix
 *
 * @author Jackson Antonio do Prado Lima <jacksonpradolima at gmail.com>
 * @version 1.0
 */
public class ExecuteMultipleComparations {

    private static final Logger logger = LoggerFactory.getLogger(ExecuteMultipleComparations.class);

    public static void main(String[] args) {
        try {
            ExecuteMultipleComparationsCommands jct = new ExecuteMultipleComparationsCommands();
            JCommander jCommander = new JCommander(jct, args);
            jCommander.setProgramName(ExecuteMultipleComparations.class.getSimpleName());

            if (jct.help) {
                jCommander.usage();
                return;
            }

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            logger.info("Started in " + dateFormat.format(new Date()));
            logger.info(jct.toString());

            // Get all csv file from folder
            List<Path> csvFiles = FilesUtils.getFileTree(Paths.get(jct.folder)).stream().filter(p -> p.toString().endsWith(".csv")).collect(Collectors.toList());

            List<OptimizationResult> compareResults = new ArrayList<>();

            double percentualProgress = 0.0;
            double percentualProgressAux = 0.0;

            jct.runs = jct.runs < 1 ? 1 : jct.runs;
            // For each csv file run the optimization and save the result
            for (int i = 0; i < csvFiles.size(); i++) {
                Path csvFile = csvFiles.get(i);

                // Run the optimization
                TestCaseSetSelectionBuilder builder = new TestCaseSetSelectionBuilder(csvFile.toString(), jct.dataSeparator)
                        .setSkipHeader(jct.skipHeader)
                        .setRuns(jct.runs);

                try {
                    builder.read();

                    // Obtain the results 
                    builder.run();

                    //Compare the results obtained with the other matrix and save them
                    compareResults.addAll(builder.compare(new TestCaseSetSelectionBuilder(jct.otherPath, jct.otherDataSeparator).setSkipHeader(jct.otherSkipHeader)));
                } catch (TestCaseSetSelectionException ex) {
                    // If all mutants are alive so the result is zero
                    if (builder.getMutants().isEmpty()) {
                        for (int j = 0; j < jct.runs; j++) {                                                    
                            compareResults.add(new OptimizationResult(0.0, 0.0, new ArrayList<>()));
                        };
                    } else {
                        throw ex;
                    }
                }

                percentualProgress = (i * 100) / csvFiles.size();

                if (percentualProgress != percentualProgressAux) {
                    logger.info(percentualProgress + "% in " + dateFormat.format(new Date()));
                    percentualProgressAux = percentualProgress;
                }
            }

            logger.info("[Compare] Average Size: " + compareResults.stream().mapToDouble(value -> value.getObjective1Value()).average().getAsDouble());
            logger.info("[Compare] Average Score: " + compareResults.stream().mapToDouble(value -> value.getObjective2Value()).average().getAsDouble());
            logger.info("Finished in " + dateFormat.format(new Date()) + "\n\n\n");
        } catch (TestCaseSetSelectionException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
