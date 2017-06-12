package com.xxx.table;

import java.util.ArrayList;

/**
 * Created by yazha on 2017-03-29.
 */
public class TableRow {

    private final Table table;
    private ArrayList<TableData> children = new ArrayList<>();

    public TableRow(Table table) {
        this.table = table;
    }

    public boolean addTableData(TableData child) {
        if (!children.contains(child)) {
            int count = 0;
            for (TableData td : children) {
                count += td.getColspan();
            }
            count += child.getColspan();
            if(count <= table.getColumnLimit()) {
                children.add(child);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuffer innerHtml = new StringBuffer();
        int count = 0;
        for (TableData td : children) {
            innerHtml.append(td);
            count += td.getColspan();
        }
        if(count < table.getColumnLimit()){
            TableData td = new TableData();
            td.setColspan(table.getColumnLimit() - count);
            innerHtml.append(td);
        }
        return HtmlTag.build("tr", null, innerHtml.toString());
    }
}
