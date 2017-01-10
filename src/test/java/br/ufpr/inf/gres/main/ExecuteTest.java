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
public class ExecuteTest {

    public ExecuteTest() {
    }

    @Test
    public void testMatrixState() {
        Execute.main(new String[]{
            "-p=src\\main\\resources\\matrixState.csv",
            "-ds=;",
            "-sh=true",
            "-r=1"
        });
    }

    @Test
    public void testMatrixNoHeader() {
        Execute.main(new String[]{
            "-p=src\\main\\resources\\matrixNoHeader.csv",
            "-ds=;",
            "-sh=false",
            "-r=1"
        });
    }
}
