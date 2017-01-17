package br.ufpr.inf.gres.main;

import br.ufpr.inf.gres.runner.TestCaseSetSelectionBuilder;
import br.ufpr.inf.gres.exception.TestCaseSetSelectionException;
import com.beust.jcommander.JCommander;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class execute the optimization by command line
 *
 * @author Giovani Guizzo <gguizzo at gmail.com>
 */
public class Execute {

    private static final Logger logger = LoggerFactory.getLogger(Execute.class);

    public static void main(String[] args) {
        try {
            ExecuteCommands jct = new ExecuteCommands();
            JCommander jCommander = new JCommander(jct, args);
            jCommander.setProgramName(Execute.class.getSimpleName());

            if (jct.help) {
                jCommander.usage();
                return;
            }
            
            logger.info(jct.toString());

            new TestCaseSetSelectionBuilder(jct.path, jct.dataSeparator)
                    .setSkipHeader(jct.skipHeader)
                    .setRuns(jct.runs < 1 ? 1 : jct.runs)
                    .read()
                    .run();
        } catch (TestCaseSetSelectionException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
