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
public class ExecuteComparationTest {
    
    @Test
    public void testMatrixState() {
        ExecuteComparation.main(new String[]{
            "-p=src\\main\\resources\\matrixState.csv",            
            "-ds=;",
            "-sh=true",
            "-r=10",
            "-op=src\\main\\resources\\matrixNoHeader.csv",
            "-ods=;",
            "-osh=false"
        });
    }

    @Test
    public void testMatrixNoHeader() {
        ExecuteComparation.main(new String[]{
            "-p=src\\main\\resources\\matrixNoHeader.csv",            
            "-ds=;",
            "-sh=false",
            "-r=10",
            "-op=src\\main\\resources\\matrixState.csv",
            "-ods=;",
            "-osh=true"
        });
    }    
}
