package com.cocoegg.effectivejava;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author cocoegg
 * @date 2021/1/14 - 20:44
 */
public class _25uncheckedwarnning {
    public static void main(String[] args) {
        Set<Aa> aa = new HashSet();
        @SuppressWarnings("unchecked")
        List<Aa> bb = new ArrayList();

    }
}

class Aa {}
