package com.wind.sysinfo.bean;

import java.util.List;

/**
 * 系统磁盘即物理存储信息
 * @author wind
 */
public class DiskInfo {
    /** 总物理容量*/
    private long totalSize;
    /** 空闲容量*/
    private long freeSize;
    /** 物理容量使用率*/
    private double diskRatio;

    /** 磁盘或挂载盘名称*/
    private String name;

    /** 各分盘具体信息*/
    private List<DiskInfo> childInfos;

    public DiskInfo() {
    }

    public DiskInfo(long totalSize, long freeSize, double diskRatio) {
        this.totalSize = totalSize;
        this.freeSize = freeSize;
        this.diskRatio = diskRatio;
    }

    public DiskInfo(long totalSize, long freeSize, double diskRatio, String name) {
        this(totalSize, freeSize, diskRatio);
        this.name = name;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public long getFreeSize() {
        return freeSize;
    }

    public void setFreeSize(long freeSize) {
        this.freeSize = freeSize;
    }

    public double getDiskRatio() {
        return diskRatio;
    }

    public void setDiskRatio(double diskRatio) {
        this.diskRatio = diskRatio;
    }

    public List<DiskInfo> getChildInfos() {
        return childInfos;
    }

    public void setChildInfos(List<DiskInfo> childInfos) {
        this.childInfos = childInfos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DiskInfo{" +
                "totalSize=" + totalSize +
                ", freeSize=" + freeSize +
                ", diskRatio=" + diskRatio +
                ", name='" + name + '\'' +
                ", childInfos=" + childInfos +
                '}';
    }
}
