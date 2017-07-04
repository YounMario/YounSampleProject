package com.younchen.younsampleproject;

import junit.framework.Assert;

import org.junit.Test;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

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

    @Test
    public void getFirstCharOfString() {
        ArrayList<String> words = new ArrayList<>();
        words.add("ㄱ");
        words.add("ㅂ");
        words.add("ㅇ");
        words.add("");
        words.add("ㄷ");
        words.add("ㄱ");
        words.add("ㅊ");
        Collator collator = Collator.getInstance(Locale.KOREA);
        Collections.sort(words, collator);
        System.out.print(words);
    }

    @Test
    public void getArrayGap() {
        int maxBatchCount = 300;
        ArrayList<Integer> integers = new ArrayList<>();
        ArrayList<Integer> totalDeleteArray = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            integers.add(i);
        }

        int section = 0;
        boolean isRemain = integers.size() > 0;
        while (isRemain) {
            ArrayList<Integer> itemToDelete = new ArrayList<>();
            int begin = section * maxBatchCount;
            int remain = Math.min(integers.size() - section * maxBatchCount, maxBatchCount);
            int end = begin + remain;
            for (int i = begin; i < end; i++) {
                itemToDelete.add(i);
            }
            isRemain = remain > 0;
            section++;
            totalDeleteArray.addAll(itemToDelete);
        }

        Assert.assertEquals(totalDeleteArray.size(), integers.size());
    }
}