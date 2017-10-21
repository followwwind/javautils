package com.wind.bean;

/**
 * 系统内存信息
 * @author wind
 */
public class MemInfo {
    /** 总物理内存*/
    private long memTotal;
    /** 空闲物理内存*/
    private long memFree;
    /** 缓冲区存储*/
    private long buffers;
    /** 缓存大小*/
    private long cached;
    /** 物理内存使用率*/
    private double memRatio;

    public MemInfo() {
    }

    public MemInfo(long memTotal, long memFree, double memRatio) {
        this.memTotal = memTotal;
        this.memFree = memFree;
        this.memRatio = memRatio;
    }

    public MemInfo(long memTotal, long memFree, long buffers, long cached, double memRatio) {
        this.memTotal = memTotal;
        this.memFree = memFree;
        this.buffers = buffers;
        this.cached = cached;
        this.memRatio = memRatio;
    }

    public long getMemTotal() {
        return memTotal;
    }

    public void setMemTotal(long memTotal) {
        this.memTotal = memTotal;
    }

    public long getMemFree() {
        return memFree;
    }

    public void setMemFree(long memFree) {
        this.memFree = memFree;
    }

    public long getBuffers() {
        return buffers;
    }

    public void setBuffers(long buffers) {
        this.buffers = buffers;
    }

    public long getCached() {
        return cached;
    }

    public void setCached(long cached) {
        this.cached = cached;
    }

    public double getMemRatio() {
        return memRatio;
    }

    public void setMemRatio(double memRatio) {
        this.memRatio = memRatio;
    }

    @Override
    public String toString() {
        return "MemInfo{" +
                "memTotal=" + memTotal +
                ", memFree=" + memFree +
                ", buffers=" + buffers +
                ", cached=" + cached +
                ", memRatio=" + memRatio +
                '}';
    }
}
