package com.arithmetic.features.lb;

import java.util.List;

/**
 * @author alan.chen
 * @date 2020/7/25 7:26 PM
 */
public interface LoadBalance {

    public void choose(List<String> list);
}
