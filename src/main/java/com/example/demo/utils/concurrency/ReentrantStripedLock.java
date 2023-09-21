package com.example.demo.utils.concurrency;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantStripedLock {

    private final ReentrantLock[] locks;


    public ReentrantStripedLock() {
        this(128);
    }

    public ReentrantStripedLock(int lockCount) {
        locks = new ReentrantLock[ConcurrencyUtil.findNextHighestPowerOfTwo(lockCount)];
        for (int i = 0; i < locks.length; ++i) {
            locks[i] = new ReentrantLock();
        }
    }

    public ReentrantLock getLock(Object key) {

        int lockNumber = ConcurrencyUtil.selectLock(key, locks.length);
        return locks[lockNumber];
    }

}
