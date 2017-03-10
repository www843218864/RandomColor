package com.bwie.uu.randomcolor;

/**
 * Created by 闫雷 on 2017/3/10.
 */
public class ThreadUtil extends Thread {
    private boolean isStart=true;

    public ThreadUtil(Runnable runnable, boolean isStart) {
        super(runnable);
        this.isStart = isStart;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }
}
