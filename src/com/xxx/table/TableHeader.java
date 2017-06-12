package com.xxx.table;

/**
 * Created by yazha on 2017-03-29.
 */
public class TableHeader extends TableData{



    @Override
    public String toString() {
        String innerText = getText();
        innerText = HtmlTag.build("h", null, innerText);
        return HtmlTag.build("td", getAttributes(), innerText);
    }
}
