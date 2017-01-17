package br.ufpr.inf.gres.exception;

import java.io.Serializable;

/**
 * Test Case Set Selection exception class
 *
 * @author Jackson Antonio do Prado Lima <jacksonpradolima at gmail.com>
 */
@SuppressWarnings("serial")
public class TestCaseSetSelectionException extends RuntimeException implements Serializable {

    /**
     * Creates a new instance of <code>TestCaseSetSelectionException</code> without detail message.
     */
    public TestCaseSetSelectionException() {
    }

    /**
     * Constructs an instance of <code>TestCaseSetSelectionException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public TestCaseSetSelectionException(String msg) {
        super(msg);
    }
}
