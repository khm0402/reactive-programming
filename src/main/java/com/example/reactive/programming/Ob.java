package com.example.reactive.programming;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Iterable <--> Observable. 상대성(duality) 이다.
// Iterable   = Pull 하는 방식. 그 다음 녀석을 나에게 줘(next).
// Observable = Push 하는 방식.
public class Ob {

//    public static void main(String[] args) {
//        Iterable<Integer> iterable = () ->
//                new Iterator<>() {
//                    int i = 0;
//                    static final int MAX = 10;
//
//                    public boolean hasNext() {
//                        return i < 10;
//                    }
//
//                    public Integer next() {
//                        return ++i;
//                    }
//                };
//
//        for (Integer i : iterable) {
//            System.out.println(i);
//        }
//    }


    // Observable 이란?
    // Source -> Event/Data -> Observer.
    // 니가 발생시키는 이벤트나 시그널을 나에게 던저줘. (notify)
    // Observable observable = new Observable()
    @SuppressWarnings("deprecation")
    static class IntObservable extends Observable implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                setChanged();
                notifyObservers(i);   // return 값이 없다. push 개념.
                // int i = it.next(); // return 값이 있다. pull 개념.
            }
        }
    }

    @SuppressWarnings("deprecation")
    public static void main(String[] args) {

        Observer ob = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println(Thread.currentThread().getName() + " " + arg);
            }
        };

        IntObservable io = new IntObservable();
        io.addObserver(ob);

        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(io);

        System.out.println(Thread.currentThread().getName() + " EXIT");
        es.shutdown();
    }

}
