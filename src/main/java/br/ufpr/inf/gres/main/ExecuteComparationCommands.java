/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.main;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 *
 * @author Jackson Antonio do Prado Lima <jacksonpradolima at gmail.com>
 * @version 1.0
 */
@Parameters(separators = " =")
public class ExecuteComparationCommands {

    @Parameter(names = {"-help", "-h"}, help = true)
    public boolean help;

    @Parameter(names = {"--path", "-p"}, description = "The path to the problem file.", required = true)
    public String path;

    @Parameter(names = {"--dataSeparator", "-ds"}, description = "Data separator.", required = true)
    public String dataSeparator;

    @Parameter(names = {"--skipHeader", "-sh"}, description = "Skip header?", required = true, arity = 1)
    public boolean skipHeader;

    @Parameter(names = {"--runs", "-r"}, description = "The number of runs to average the results", required = false)
    public int runs;

    @Parameter(names = {"--otherPath", "-op"}, description = "The other path to the problem file.", required = true)
    public String otherPath;

    @Parameter(names = {"--otherDataSeparator", "-ods"}, description = "Other data separator.", required = true)
    public String otherDataSeparator;

    @Parameter(names = {"--otherSkipHeader", "-osh"}, description = "Skip header?", required = true, arity = 1)
    public boolean otherSkipHeader;

    @Override
    public String toString() {
        return "ExecuteComparationCommands = [path=" + path + ", dataSeparator=" + dataSeparator + ", skipHeader=" + skipHeader + ", runs=" + runs
                + ", otherPath=" + otherPath + ", otherDataSeparator=" + otherDataSeparator + ", otherSkipHeader=" + otherSkipHeader + "]";
    }
}
