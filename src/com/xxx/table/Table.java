package com.xxx.table;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by yazha on 2017-03-29.
 */
public class Table {

    private int border = 1;
    private int width;
    private int height;

    private int rowLimit;
    private int columnLimit;

    private ArrayList<TableRow> children = new ArrayList<>();

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getRowLimit() {
        return rowLimit;
    }

    public void setRowLimit(int rowLimit) {
        this.rowLimit = rowLimit;
    }

    public int getColumnLimit() {
        return columnLimit;
    }

    public void setColumnLimit(int columnLimit) {
        this.columnLimit = columnLimit;
    }

    public int getBorder() {
        return border;
    }

    public void setBorder(int border) {
        this.border = border;
    }

    public boolean addTableRow(TableRow child){
        if(children.size() < getRowLimit() && !children.contains(child)) {
            children.add(child);
            return true;
        }
        return false;
    }

    public void addTableDataList(ArrayList<TableData> list){
        TableRow tr = null;
        while (list.size() > 0) {
            if (tr == null) {
                tr = new TableRow(this);
            }
            boolean b = tr.addTableData(list.get(0));
            if(b){
                list.remove(0);
            }else{
                addTableRow(tr);
                tr = null;
            }
        }
        // 执行完成之后，如果tr不为空，则添加进去
        if(tr != null)
            addTableRow(tr);
    }

    protected Map<String, Object> getAttributes(){
        Map<String, Object> attrs = new LinkedHashMap<>();
        attrs.put("border", border);
        if(width > 0)
            attrs.put("width", width + "px");
        if(height > 0)
            attrs.put("height", height + "px");
        return attrs;
    }

    @Override
    public String toString() {
        StringBuffer innerHtml = new StringBuffer();
        for (TableRow td : children) {
            innerHtml.append(td);
        }
        return HtmlTag.build("table", getAttributes(), innerHtml.toString());
    }
}
