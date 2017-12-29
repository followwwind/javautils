package com.wind.sysinfo;

import com.sun.management.OperatingSystemMXBean;
import com.wind.common.process.ProcessUtil;
import com.wind.sysinfo.bean.DiskInfo;
import com.wind.sysinfo.bean.MemInfo;
import com.wind.sysinfo.bean.MonitorInfo;
import com.wind.common.Const;
import com.wind.common.math.NumberUtil;
import com.wind.common.StringUtil;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.*;

/**
 * Java获取Linux和Window系统CPU、内存和磁盘总使用率的情况
 * @author wind
 */
public class ComputerMonitorUtil {


    private static final int CPUTIME = 500;
    private static final int PERCENT = 100;


    private static boolean isWindow(){
        return Const.OS_NAME.toLowerCase().contains("windows") || Const.OS_NAME.toLowerCase().contains("win");
    }

    /**
     * /**
     * 功能：获取Linux 和 Window CPU利用率
     *
     */
    public static double getCpuInfo(){
        if(isWindow()){
            //第一次读取CPU信息
            /*Map<String, Long> map1 = readWinCpuInfo();
            //睡500ms
            try {
                Thread.sleep(CPUTIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //第二次读取CPU信息
            Map<String, Long> map2 = readWinCpuInfo();
            Long c0 = map1.get("idletime");
            Long c1 = map1.get("kneltime");

            Long c2 = map2.get("idletime");
            Long c3 = map2.get("kneltime");
            if (c0 != null && c1 != null && c2 != null && c3 != null) {
                //空闲时间
                long idleTime = c2 - c0;
                //使用时间
                long busyTime = c3 - c1;
                return getDouble(PERCENT * busyTime * 1.0 / (busyTime + idleTime));
            } else {
                return 0.0;
            }*/
            return 0.0;
        }else{
            Map<String, String> map1 = readLinuxCpuInfo();
            try {
                Thread.sleep(CPUTIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Map<String, String> map2 = readLinuxCpuInfo();

            long user1 = Long.parseLong(map1.get("user").trim());
            long nice1 = Long.parseLong(map1.get("nice").trim());
            long system1 = Long.parseLong(map1.get("system").trim());
            long idle1 = Long.parseLong(map1.get("idle").trim());

            long user2 = Long.parseLong(map2.get("user").trim());
            long nice2 = Long.parseLong(map2.get("nice").trim());
            long system2 = Long.parseLong(map2.get("system").trim());
            long idle2 = Long.parseLong(map2.get("idle").trim());

            long total1 = user1 + system1 + nice1;
            long total2 = user2 + system2 + nice2;
            float total = total2 - total1;

            long totalIdle1 = user1 + nice1 + system1 + idle1;
            long totalIdle2 = user2 + nice2 + system2 + idle2;
            float totalidle = totalIdle2 - totalIdle1;
            return NumberUtil.getDouble((total / totalidle) * 100, 2);
        }
    }


    /**
     * 采集网络带宽使用率
     */
    public static float getNetInfo(){
        float netUsage = 0.0f;
        if(!isWindow()){
            //第一次采集流量数据
            long startTime = System.currentTimeMillis();
            Map<String, Long> first = readNetInfo();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //第二次采集流量数据
            long endTime = System.currentTimeMillis();
            Map<String, Long> two = readNetInfo();

            long in1 = first.get("receive");
            long out1 = first.get("transmit");

            long in2 = two.get("receive");
            long out2 = two.get("transmit");

            if(in1 != 0 && in2 != 0 && out1 != 0 && out2 != 0){
                float interval = (float)(endTime - startTime)/1000;
                //网口传输速度,单位为bps
                float curRate = (float)(in2 - in1 + out2 - out1)*8/(1000000*interval);
                netUsage = curRate/Const.TOTAL_BAND_WIDTH;
            }

        }
        return netUsage;
    }


    /**
     * 功能：Linux 和 Window 内存使用率  linux单元单位:kb ，win单元单位:b
     * @return
     */
    public static MemInfo getMemInfo(){
        MemInfo memInfo = null;

        if(isWindow()){
            OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
                    .getOperatingSystemMXBean();
            // 总的物理内存+虚拟内存
            long total = osmxb.getTotalSwapSpaceSize();
            // 剩余的物理内存
            long free = osmxb.getFreePhysicalMemorySize();
            memInfo = new MemInfo(osmxb.getTotalPhysicalMemorySize(), free, NumberUtil.getPercent(free, total));
        }else{
            List<String> lists = ProcessUtil.getProcessInfo(Const.LINUX_MEM_PROC);
            if(lists != null && lists.size() > 0){
                Map<String, Long> map = new HashMap<>(16);
                for(String line : lists){
                    int beginIndex = 0;
                    int endIndex = line.indexOf(":");
                    if (endIndex != -1) {
                        String key = line.substring(beginIndex, endIndex);
                        beginIndex = endIndex + 1;
                        endIndex = line.length();
                        String memory = line.substring(beginIndex, endIndex);
                        String value = memory.toLowerCase().replace(Const.LINUX_UNIT_SIZE, Const.STR_SPACE).trim();
                        map.put(key, Long.parseLong(value));
                    }
                }
                long total = map.get("MemTotal");
                long free = map.get("MemFree");
                long buffers = map.get("Buffers");
                long cached = map.get("Cached");
                double percent = NumberUtil.getDouble((total - free - buffers - cached) / total * 100, 2);
                memInfo = new MemInfo(total, free, percent);
            }
        }
        return memInfo;

    }

    /**
     * Window 和Linux 得到磁盘的使用率 linux单元单位:kb ，win单元单位:b
     * @return
     */
    public static DiskInfo getDiskInfo(){
        List<DiskInfo> childInfos = new ArrayList<>();
        long allTotal = 0;
        long allFree = 0;
        if(isWindow()){
            for (char c = Const.CH_A; c <= Const.CH_Z; c++) {
                String dirName = c + ":/";
                File win = new File(dirName);
                if (win.exists()) {
                    long total = win.getTotalSpace();
                    long free = win.getFreeSpace();
                    double percent = NumberUtil.getPercent(free, total);
                    childInfos.add(new DiskInfo(total, free, percent, dirName));
                    allTotal = allTotal + total;
                    allFree = allFree + free;
                }
            }
        }else {
            List<String> lists = ProcessUtil.getProcessInfo(Const.LINUX_DISK_PROC);
            if (lists != null && lists.size() > 0) {
                int count = -1;
                for (String str : lists) {
                    String[] strArray = StringUtil.split(str, Const.STR_SPACE);
                    if (++count > 0) {
                        if (strArray.length > 5) {
                            long total = Long.parseLong(strArray[1].trim());
                            long free = Long.parseLong(strArray[3].trim());
                            double percent = NumberUtil.getPercent(free, total);
                            childInfos.add(new DiskInfo(total, free, percent, strArray[5].trim()));
                            allTotal = allTotal + total;
                            allFree = allFree + free;
                        }
                    }
                }
            }
        }
        DiskInfo diskInfo = new DiskInfo(allTotal, allFree, NumberUtil.getPercent(allFree, allTotal), "all");
        diskInfo.setChildInfos(childInfos);
        return diskInfo;
    }

    /**
     * 获取总线程数
     * @return
     */
    public static int getTotalThread(){
        // 获得线程总数
        ThreadGroup parentThread;

        for (parentThread = Thread.currentThread().getThreadGroup(); parentThread
                .getParent() != null; parentThread = parentThread.getParent()){

        }
        int totalThread = parentThread.activeCount();

        return totalThread;
    }

    /**
     * 获取磁盘IO利用率
     * @return
     */
    public static float getIoRatio(){
        float ioUsage = 0.0f;
        if(!isWindow()){
            List<String> lists = ProcessUtil.getProcessInfo(Const.LINUX_IO_PROC);
            if(lists != null && lists.size() > 3){
                String line = lists.get(3);
                String[] temp = line.split("\\s+");
                if(temp.length > 1){
                    float util =  Float.parseFloat(temp[temp.length-1]);
                    ioUsage = (ioUsage > util) ? ioUsage : util;
                }
            }
        }
        return ioUsage;
    }

    /**
     * 采集win CPU数据
     */
    private static Map<String, Long> readWinCpuInfo(){
        Map<String, Long> map = new HashMap<>();
        List<String> lists = ProcessUtil.getProcessInfo(Const.WIN_CPU_PROC);
        if(lists != null && lists.size() > 0){
            long idletime = 0;
            //读取物理设备时间
            long kneltime = 0;
            //执行代码占用时间
            long usertime = 0;
            String firstLine = lists.get(0);

            int capidx = firstLine.indexOf("Caption");
            int cmdidx = firstLine.indexOf("CommandLine");
            int rocidx = firstLine.indexOf("ReadOperationCount");
            int umtidx = firstLine.indexOf("UserModeTime");
            int kmtidx = firstLine.indexOf("KernelModeTime");
            int wocidx = firstLine.indexOf("WriteOperationCount");
            for(int i = 1; i< lists.size(); i++){
                String line = lists.get(i);
                if (line.length() < wocidx) {
                    continue;
                }
                // 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount
                String caption = StringUtil.substring(line, capidx, cmdidx - 1).trim();

                String cmd = StringUtil.substring(line, cmdidx, kmtidx - 1).trim();

                if (cmd.indexOf("wmic.exe") >= 0) {
                    continue;
                }
                String s1 = StringUtil.substring(line, kmtidx, rocidx - 1).trim();
                String s2 = StringUtil.substring(line, umtidx, wocidx - 1).trim();
                List<String> digitS1 = getDigit(s1);
                List<String> digitS2 = getDigit(s2);

                if (caption.equals("System Idle Process") || caption.equals("System")) {
                    if (s1.length() > 0) {
                        if (!digitS1.get(0).equals("") && digitS1.get(0) != null) {
                            idletime += Long.valueOf(digitS1.get(0)).longValue();
                        }
                    }
                    if (s2.length() > 0) {
                        if (!digitS2.get(0).equals("") && digitS2.get(0) != null) {
                            idletime += Long.valueOf(digitS2.get(0)).longValue();
                        }
                    }
                    continue;
                }
                if (s1.length() > 0) {
                    if (!digitS1.get(0).equals("") && digitS1.get(0) != null) {
                        kneltime += Long.valueOf(digitS1.get(0)).longValue();
                    }
                }

                if (s2.length() > 0) {
                    if (!digitS2.get(0).equals("") && digitS2.get(0) != null) {
                        kneltime += Long.valueOf(digitS2.get(0)).longValue();
                    }
                }
            }

            map.put("idletime", idletime);
            map.put("kneltime", kneltime + usertime);
        }

        return map;
    }

    /**
     * 采集linux CPU数据
     * CPU 以及CPU0、CPU1、CPU2、CPU3每行的每个参数意思（以第一行为例）为：
     * 参数 解释
     * user (432661) 从系统启动开始累计到当前时刻，用户态的CPU时间（单位：jiffies） ，不包含 nice值为负进程。1jiffies=0.01秒
     * nice (13295) 从系统启动开始累计到当前时刻，nice值为负的进程所占用的CPU时间（单位：jiffies）
     * system (86656) 从系统启动开始累计到当前时刻，核心时间（单位：jiffies）
     * idle (422145968) 从系统启动开始累计到当前时刻，除硬盘IO等待时间以外其它等待时间（单位：jiffies）
     * iowait (171474) 从系统启动开始累计到当前时刻，硬盘IO等待时间（单位：jiffies） ，
     * irq (233) 从系统启动开始累计到当前时刻，硬中断时间（单位：jiffies）
     * softirq (5346) 从系统启动开始累计到当前时刻，软中断时间（单位：jiffies）

     * CPU时间=user+system+nice+idle+iowait+irq+softirq

     * “intr”这行给出中断的信息，第一个为自系统启动以来，发生的所有的中断的次数；然后每个数对应一个特定的中断自系统启动以来所发生的次数。
     * “ctxt”给出了自系统启动以来CPU发生的上下文交换的次数。
     * “btime”给出了从系统启动到现在为止的时间，单位为秒。
     * “processes (total_forks) 自系统启动以来所创建的任务的个数目。
     * “procs_running”：当前运行队列的任务的数目。
     * “procs_blocked”：当前被阻塞的任务的数目。

     * 那么CPU利用率的计算方法：可以使用取两个采样点，计算其差值的办法。
     * CPU利用率 = 1- (idle2-idle1)/(cpu2-cpu1)
     */
    private static Map<String, String> readLinuxCpuInfo(){
        List<String> lists = ProcessUtil.getProcessInfo(Const.LINUX_CPU_PROC);
        Map<String, String> map = new HashMap<>(16);
        if(lists != null && lists.size() > 0){
            for(String line : lists){
                if (line.startsWith("cpu")) {
                    StringTokenizer tokenizer = new StringTokenizer(line);
                    List<String> temp = new ArrayList<>();
                    while (tokenizer.hasMoreElements()) {
                        String value = tokenizer.nextToken();
                        temp.add(value);
                    }
                    map.put("user", temp.get(1));
                    map.put("nice", temp.get(2));
                    map.put("system", temp.get(3));
                    map.put("idle", temp.get(4));
                    map.put("iowait", temp.get(5));
                    map.put("irq", temp.get(6));
                    map.put("softirq", temp.get(7));
                    map.put("stealstolen", temp.get(8));
                    break;
                }
            }
        }
        return map;
    }


    /**
     * 采集流量数据
     */
    private static Map<String, Long> readNetInfo(){
        Map<String, Long> map = new HashMap<>(2);
        List<String> lists = ProcessUtil.getProcessInfo(Const.LINUX_NET_PROC);
        if(lists != null && lists.size() > 0){
            for(String line : lists){
                line = line.trim();
                if(line.startsWith("eth0")){
                    String[] temp = line.split("\\s+");
                    //Receive bytes,单位为Byte
                    long inSize = Long.parseLong(temp[0].substring(5).trim());
                    //Transmit bytes,单位为Byte
                    long outSize = Long.parseLong(temp[8].trim());
                    map.put("receive", inSize);
                    map.put("transmit", outSize);
                    break;
                }
            }
        }
        return map;

    }


    /**
     * 从字符串文本中获得数字
     *
     * @param text
     * @return
     */
    private static List<String> getDigit(String text) {
        List<String> digitList = new ArrayList<>();
        digitList.add(text.replaceAll("\\D", ""));
        return digitList;
    }


    public static void main(String[] args) {

        MonitorInfo monitorInfo = new MonitorInfo();
        monitorInfo.setOsName(Const.OS_NAME);
        monitorInfo.setCpuRatio(getCpuInfo());
        monitorInfo.setTotalThread(getTotalThread());
        monitorInfo.setDiskInfo(getDiskInfo());
        monitorInfo.setMemInfo(getMemInfo());
        monitorInfo.setNetRatio(getNetInfo());
        monitorInfo.setUnitSize(isWindow() ? Const.WIN_UNIT_SIZE : Const.LINUX_UNIT_SIZE);
        monitorInfo.setIoRatio(getIoRatio());
        System.out.println(monitorInfo);
    }

}
