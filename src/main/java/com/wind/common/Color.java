package com.wind.common;

/**
 * 十进制RGB颜色参照表
 * @author wind
 */
public interface Color {
    int GRAY = 11184810;
    int LIGHTGRAY = 12303291;
    int DARKGRAY = 4473924;
    int BLACK = 0;
    /**
     * 十六进制值：#FFFFFF RGB值：255,255,255
     *  十进制 16777215  15*(1 - 16^6)/(1 - 16) = 16^6 - 1 = 16777215(十六进制转十进制)
     */
    int WHITE = 16777215;
    int BLUE = 8947967;
    int GREEN = 8978312;
    int LIGHTBLUE = 12303359;
    int LIGHTGREEN = 12320699;
    int RED = 267946120;
    int ORANGE = 16777096;
    int LIGHTRED = 16759739;
}
