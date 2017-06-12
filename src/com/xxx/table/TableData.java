package com.xxx.table;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by yazha on 2017-03-29.
 */
public class TableData {

    protected String text;
    protected String cls;
    protected int colspan = 1;
    protected int rowspan = 1;

    public String getText() {
        return (text == null || text.trim().length() == 0) ? "&nbsp;" : text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public int getColspan() {
        return colspan < 1 ? 1 : colspan;
    }

    public void setColspan(int colspan) {
        if(colspan > 0)
            this.colspan = colspan;
    }

    public int getRowspan() {
        return rowspan < 1 ? 1 : rowspan;
    }

    public void setRowspan(int rowspan) {
        if(rowspan > 0)
            this.rowspan = rowspan;
    }

    protected Map<String, Object> getAttributes(){
        Map<String, Object> attrs = new LinkedHashMap<>();
        if(colspan > 1)
            attrs.put("colspan", colspan);
        if(rowspan > 1)
            attrs.put("rowspan", rowspan);
        if(cls != null && cls.trim().length() > 0)
            attrs.put("class", cls);
        return attrs;
    }

    @Override
    public String toString() {
        return HtmlTag.build("td", getAttributes(), getText());
    }
}
