/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.main;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import java.util.List;

/**
 *
 * @author Jackson Antonio do Prado Lima <jacksonpradolima at gmail.com>
 * @version 1.0
 */
@Parameters(separators = " =")
public class CalculateMutationScoreCommands {

    @Parameter(names = {"-help", "-h"}, help = true)
    public boolean help;

    @Parameter(names = {"--path", "-p"}, description = "The path to the problem file.", required = true)
    public String path;

    @Parameter(names = {"--dataSeparator", "-ds"}, description = "Data separator.", required = true)
    public String dataSeparator;
    
    @Parameter(names = {"--skipHeader", "-sh"}, description = "Skip header?", required = true, arity = 1)
    public boolean skipHeader;
    
    @Parameter(names = {"-testCases", "-tc"}, description = "Test cases", required = true, variableArity = true)
    public List<String> testCases;
    
    @Override
    public String toString() {
        return "CalculateMutationScoreCommands = [path=" + path + ", dataSeparator=" + dataSeparator + ", skipHeader=" + skipHeader + ", testCases={" + (testCases == null ? "" : String.join(";", testCases)) + "}]";
    }
}
