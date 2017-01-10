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
public class ExecuteCommands {

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
}
