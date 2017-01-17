/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.main;

import org.junit.Test;

/**
 *
 * @author Jackson Antonio do Prado Lima <jacksonpradolima at gmail.com>
 */
public class CalculateMutationScoreTest {

    @Test
    public void testMatrixState() {
        CalculateMutationScore.main(new String[]{
            "-p=src\\main\\resources\\matrixState.csv",
            "-ds=;",
            "-sh=true",
            "-testCases=testcase6", "testcase5", "testcase3"
        });
    }

    @Test
    public void testMatrixNoHeader() {
        CalculateMutationScore.main(new String[]{
            "-p=src\\main\\resources\\matrixNoHeader.csv",
            "-ds=;",
            "-sh=false",
            "-testCases=testcase6", "testcase5", "testcase1"
        });
    }
}
