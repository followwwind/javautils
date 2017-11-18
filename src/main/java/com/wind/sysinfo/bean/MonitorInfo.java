package com.wind.sysinfo.bean;

/**
 * 系统监控信息
 * @author wind
 */
public class MonitorInfo {

    /** 线程总数. */
    private int totalThread;

    /** cpu利用率. */
    private double cpuRatio;

    /** 采集网络带宽使用率*/
    private float netRatio;

    /** 磁盘IO利用率*/
    private float ioRatio;

    /** 操作系统*/
    private String osName;

    /** 磁盘容量信息*/
    private DiskInfo diskInfo;

    /** 内存信息*/
    private MemInfo memInfo;

    /** 系统单元容量单位*/
    private String unitSize;

    public MonitorInfo() {
    }

    public MonitorInfo(int totalThread, double cpuRatio, float netRatio, float ioRatio, String osName, DiskInfo diskInfo, MemInfo memInfo, String unitSize) {
        this.totalThread = totalThread;
        this.cpuRatio = cpuRatio;
        this.netRatio = netRatio;
        this.osName = osName;
        this.diskInfo = diskInfo;
        this.memInfo = memInfo;
        this.unitSize = unitSize;
        this.ioRatio = ioRatio;
    }

    public int getTotalThread() {
        return totalThread;
    }

    public void setTotalThread(int totalThread) {
        this.totalThread = totalThread;
    }

    public double getCpuRatio() {
        return cpuRatio;
    }

    public void setCpuRatio(double cpuRatio) {
        this.cpuRatio = cpuRatio;
    }

    public float getNetRatio() {
        return netRatio;
    }

    public void setNetRatio(float netRatio) {
        this.netRatio = netRatio;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public DiskInfo getDiskInfo() {
        return diskInfo;
    }

    public void setDiskInfo(DiskInfo diskInfo) {
        this.diskInfo = diskInfo;
    }

    public MemInfo getMemInfo() {
        return memInfo;
    }

    public void setMemInfo(MemInfo memInfo) {
        this.memInfo = memInfo;
    }

    public String getUnitSize() {
        return unitSize;
    }

    public void setUnitSize(String unitSize) {
        this.unitSize = unitSize;
    }

    public float getIoRatio() {
        return ioRatio;
    }

    public void setIoRatio(float ioRatio) {
        this.ioRatio = ioRatio;
    }

    @Override
    public String toString() {
        return "MonitorInfo{" +
                "totalThread=" + totalThread +
                ", cpuRatio=" + cpuRatio +
                ", netRatio=" + netRatio +
                ", ioRatio=" + ioRatio +
                ", osName='" + osName + '\'' +
                ", diskInfo=" + diskInfo +
                ", memInfo=" + memInfo +
                ", unitSize='" + unitSize + '\'' +
                '}';
    }
}
