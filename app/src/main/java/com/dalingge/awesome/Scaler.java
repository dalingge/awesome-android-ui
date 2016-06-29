package com.dalingge.awesome;

/**
 * FileName: Scaler.java
 * description:
 * Author: dingby(445850053@qq.com)
 * Date: 2016/6/6
 */
public interface Scaler {
    public void setShouldRestore(boolean restore);
    public float getCurrentScale();
    public void cancelAnimations();
    public int getInitialParentBottom();
    public boolean isShouldRestore();
    public boolean isRetracting();
    public float getScale();
    public void updateViewScale();
    public void retractScale();
    public void setScale(float scale);
    public void obtainInitialValues();
}
