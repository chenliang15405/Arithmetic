package com.github.bstation.other;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangsong
 * @date 2021/9/5 22:41
 */
public class ParseTree {


    @Test
    public void test() {
        List<Menu> list = new ArrayList<>();
        Menu menu1 = new Menu();
        menu1.setId(1);
        menu1.setName("部门1");
        menu1.setParentId(null);
        Menu menu2 = new Menu();
        menu2.setId(2);
        menu2.setName("部门2");
        menu2.setParentId(1);
        Menu menu3 = new Menu();
        menu3.setId(3);
        menu3.setName("部门3");
        menu3.setParentId(1);
        Menu menu4 = new Menu();
        menu4.setId(4);
        menu4.setName("部门4");
        menu4.setParentId(3);
        Menu menu5 = new Menu();
        menu5.setId(5);
        menu5.setName("部门5");
        menu5.setParentId(4);

        list.add(menu1);
        list.add(menu2);
        list.add(menu3);
        list.add(menu4);
        list.add(menu5);

        System.out.println(parseTree(list));

    }


    private List<Menu> parseTree(List<Menu> list) {
        if(list == null || list.size() == 0) {
            return null;
        }

        // 如果parentId是null的情况，需要先单独遍历并收集parent的list，然后通过parent的id去递归
//        List<Menu> result = new ArrayList<>();
//        for (Menu menu : list) {
//            if(menu.getParentId() == null) {
//                result.add(menu);
//            }
//        }
//
//        for (Menu menu : result) {
//            menu.setChildren(process(list, menu.getId()));
//        }

        // parentId不为null，则直接递归获取
        return process(list, 0);
    }

    private List<Menu> process(List<Menu> list, Integer parentId) {

        List<Menu> childList = new ArrayList<>();
        for (Menu menu : list) {
            if(menu.getParentId().equals(parentId)) {
                childList.add(menu);
                menu.setChildren(process(list, menu.getId()));
            }
        }

        return childList;
    }


    static class Menu {
        // 菜单id
        private Integer id;
        // 菜单名称
        private String name;
        // 父菜单id
        private Integer parentId;
        // 菜单url
        private String url;
        // 菜单图标
        private String icon;
        // 菜单顺序
        private int order;
        // 子菜单
        private List<Menu> children;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getParentId() {
            return parentId;
        }

        public void setParentId(Integer parentId) {
            this.parentId = parentId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public List<Menu> getChildren() {
            return children;
        }

        public void setChildren(List<Menu> children) {
            this.children = children;
        }

        @Override
        public String toString() {
            return "Menu{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", parentId=" + parentId +
                    ", url='" + url + '\'' +
                    ", icon='" + icon + '\'' +
                    ", order=" + order +
                    ", children=" + children +
                    '}';
        }
    }

}
