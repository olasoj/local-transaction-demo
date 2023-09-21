package com.example.demo.utils.concurrency;

public class ConcurrencyUtil {

    private ConcurrencyUtil() {
    }

    public static int hash(Object object) {
        int h = object.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    public static int selectLock(final Object key, int numberOfLocks) throws IllegalArgumentException {
        int number = numberOfLocks & (numberOfLocks - 1);
        if (number != 0) {
            throw new IllegalArgumentException("Lock number must be a power of two: " + numberOfLocks);
        }
        if (key == null) {
            return 0;
        } else {
            return hash(key) & (numberOfLocks - 1);
        }
    }

    public static int findNextHighestPowerOfTwo(int num) {
        if (num <= 1) {
            return 1;
        } else if (num >= 0x40000000) {
            return 0x40000000;
        }
        int highestBit = Integer.highestOneBit(num);
        return num <= highestBit ? highestBit : highestBit << 1;
    }
}
