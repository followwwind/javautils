package org.cls.javautils.common;

import org.springframework.cglib.beans.BeanCopier;

/**
 * @Title: BeanUtil
 * @Package com.wind.common
 * @Description: 字节码java bean拷贝, 位于spring core包内
 * @author wind
 * @date 2018/10/13 11:08
 * @version V1.0
 */
public class BeanUtil {

    /**
     * 对象拷贝复制
     * @param source
     * @param target
     */
    public static void copy(Object source, Object target){
        if(source != null && target != null){
            BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            copier.copy(source, target, null);
        }
    }
}
