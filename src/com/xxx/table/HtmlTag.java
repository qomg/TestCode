package com.xxx.table;

import java.util.Map;

/**
 * Created by yazha on 2017-03-29.
 */
public class HtmlTag {

    public static String build(String tag, Map<String, Object> attrs, String innerText){
        StringBuffer sb = new StringBuffer();
        sb.append("\n<").append(tag);
        if (attrs != null) {
            for (String key : attrs.keySet()) {
                sb.append(" ").append(key).append("=\"").append(attrs.get(key)).append("\"");
            }
        }
        sb.append(">").append(innerText);
        //如果里面的内容是html，添加一个换行符，使显示格式更合理
        if(isHtml(innerText))
            sb.append("\n");
        sb.append("</").append(tag).append(">");
        return sb.toString();
    }

    public static boolean isHtml(String text){
        return text.matches("[\\s\\S]*<[\\w]+[\\s\\S]*>[\\s\\S]*</[\\w]+>[\\s\\S]*");
    }
}
