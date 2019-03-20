package com.harmonycloud.exception;

import com.github.jedis.lock.JedisLock;

public class BffSyncException extends BffException {
    public BffSyncException(JedisLock lock, String message) {
        super(message);
        lock.release();
    }
}
