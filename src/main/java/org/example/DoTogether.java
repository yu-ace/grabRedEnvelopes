package org.example;


import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class DoTogether {
    static volatile int size = -1;

    static synchronized void add(){
            size++;
    }

    public static void main(String[] args) {
        System.out.println("请输入需要创建的红包数量");
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        System.out.println("请输入需要创建的红包金额");
        int count = scanner.nextInt();

        ArrayList<Integer> integers = groupNumber(number, count);
        System.out.println("请输入要创建的线程数量");
        int nextInt = scanner.nextInt();
        try {
            get(integers, nextInt);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("结束");
    }

    public static ArrayList<Integer> groupNumber(int number,int count) {
        int remaining = number * 100;
        Random random = new Random();
        ArrayList<Integer> integers = new ArrayList<>();

        for (int i = 0; i < count - 1; i++) {
            int maxSplit = remaining - (count - 1 - i);
            int split = random.nextInt(maxSplit) + 1;
            integers.add(split);
            remaining = remaining - split;
        }
        integers.add(remaining);
        return integers;
    }

    public static void get(ArrayList<Integer> integers ,Integer n) throws InterruptedException {
        for(int i = 0;i < n;i ++){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if(size < integers.size() - 1){
                        add();
                        Integer integer = integers.get(size);
                        System.out.println((double)integer/100);
                    }else {
                        System.out.println("数据已经没有了");
                    }
                }
            });
            thread.start();
        }
    }
}
