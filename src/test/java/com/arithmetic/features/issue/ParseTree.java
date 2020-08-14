package com.arithmetic.features.issue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author alan.chen
 * @date 2020/6/2 6:18 PM
 */
public class ParseTree {


    @Test
    public void test() {
        List<Menu> list = new ArrayList<>();

        List<Menu> result = new ArrayList<>();
        for (Menu menu : list) {
            if(menu.getParentId() == null) {
                result.add(menu);
            }
        }

        for (Menu menu : result) {
            List<Menu> childList = getChildList(menu.getParentId(), list);
            menu.setChildren(childList);
        }
        System.out.println(result);
    }

    private List<Menu> getChildList(String parentId, List<Menu> list) {
        List<Menu> childList = new ArrayList<>();
        for (Menu menu : list) {
            if(menu.getParentId().equals(parentId)) {
                childList.add(menu);
            }
        }

        for (Menu menu : childList) {
            menu.setChildren(getChildList(menu.getId(), list));
        }
        return childList;
    }


    /**
     * jdk8 stream表达式
     */
    public void menu() {
        List<Menu> allList = new ArrayList<>();

        allList.stream().filter(menu -> {
            return menu.getParentId() == null;
        }).map(root -> {
            root.setChildren(getChild(root, allList));
            return root;
        }).sorted((a, b) -> a.getOrder() - b.getOrder()).collect(Collectors.toList());

    }

    private List<Menu> getChild(Menu root, List<Menu> allList) {
        if(root == null) {
            return null;
        }

        List<Menu> childList = allList.stream().filter(menu ->
            menu.getParentId().equals(root.getId())
        ).map(item -> {
            item.setChildren(getChild(item, allList));
            return item;
        }).sorted(Comparator.comparingInt(Menu::getOrder)).collect(Collectors.toList());

        return childList;
    }


}

class Menu {
    // 菜单id
    private String id;
    // 菜单名称
    private String name;
    // 父菜单id
    private String parentId;
    // 菜单url
    private String url;
    // 菜单图标
    private String icon;
    // 菜单顺序
    private int order;
    // 子菜单
    private List<Menu> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
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
}
