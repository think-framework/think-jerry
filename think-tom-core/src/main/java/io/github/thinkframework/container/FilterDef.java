package io.github.thinkframework.container;

import javax.servlet.Filter;

public class FilterDef {

    private String name;

    private Filter filter;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}
