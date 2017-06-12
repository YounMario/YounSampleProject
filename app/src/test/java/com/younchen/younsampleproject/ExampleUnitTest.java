package com.younchen.younsampleproject;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        StringBuilder ids = new StringBuilder();
        ids.append("(");
        for (int i = 0; i < 10; i++) {
            //ids.append(i).append(",");
            ids.append('\'').append(i).append('\'').append(",");
        }
        ids.deleteCharAt(ids.length() - 1).append(")");
        System.out.println(ids.toString());
    }
}