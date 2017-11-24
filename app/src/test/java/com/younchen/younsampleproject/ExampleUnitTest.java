package com.younchen.younsampleproject;

import junit.framework.Assert;

import org.junit.Test;

import java.text.Collator;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

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

    @Test
    public void testFormat() {
        float sum = 30000;
        for(int i =0 ;i<4 ;i++) {
            sum = sum + sum * 0.005f;
        }
        System.out.print(sum);
    }

    @Test
    public void testMod() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        ArrayList<String> list = new ArrayList<>();
        MyThread.create(list).start();
        MyPrintThread2.create(list).start();
        latch.await();

    }

    static class MyThread extends Thread {

        private ArrayList mList;

        public static MyThread create(ArrayList list) {
            return new MyThread(list);
        }

        public MyThread(ArrayList list) {
            mList = list;
        }

        @Override
        public void run() {
            while(true){
                System.out.println("add elements");
                mList.add("xxx");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class MyPrintThread1 extends Thread {

        private ArrayList mList;

        public static MyPrintThread1 create(ArrayList list) {
            return new MyPrintThread1(list);
        }

        public MyPrintThread1(ArrayList list) {
            mList = list;
        }

        @Override
        public void run() {
            while(true){
                try {
                    for(int i=0;i< mList.size() ;i++){
                        System.out.println("----------------");
                        System.out.println("printer 1:"+ mList.get(i));
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class MyPrintThread2 extends Thread {

        private ArrayList mList;

        public static MyPrintThread2 create(ArrayList list) {
            return new MyPrintThread2(list);
        }

        public MyPrintThread2(ArrayList list) {
            mList = list;
        }

        @Override
        public void run() {
            while(true){
                try {
                    for (Object obj: mList) {
                        System.out.println("*********************");
                        System.out.println("printer 2:"+ obj);
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}