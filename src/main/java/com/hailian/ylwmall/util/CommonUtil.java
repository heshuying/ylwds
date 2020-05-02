package com.hailian.ylwmall.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 19033323
 */
public class CommonUtil {
    private final static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * list数据循环copyProperties
     */
    public static <S, T> List<T> convertBeanList(List<S> sources, Class<T> clazz) {
        return sources.stream().map(source -> convertBean(source, clazz)).collect(Collectors.toList());
    }

    /**
     * 简单属性copy
     */
    public static <S, T> T convertBean(S s, Class<T> clazz) {
        if (s == null) {
            return null;
        }
        try {
            T t = clazz.newInstance();
            BeanUtils.copyProperties(s, t);
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("拷贝属性异常", e);
            throw new RuntimeException("拷贝属性异常");
        }
    }

}
