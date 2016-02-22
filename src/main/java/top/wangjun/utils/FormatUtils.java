package top.wangjun.utils;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 格式化输入输出
 */
public class FormatUtils {

    public final static Whitelist whiteList = Whitelist.basic();

    static {
        whiteList.addTags("embed", "object", "param", "p", "span", "pre", "div", "h1",
                "h2", "h3", "h4", "h5", "h6", "table", "tbody", "tr", "th", "td", "ul", "li",
                "strong", "em", "img");

        whiteList.addAttributes("a", "target");
        whiteList.addAttributes("pre", "class");
        whiteList.addAttributes("img", "src");
    }

    /**
     * 格式化日期显示
     *
     * @param format
     * @param date
     * @return
     */
    public static String date(String format, Date date) {
        if (date == null)
            return "";
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(date);
    }

    /**
     * html转义
     *
     * @param html
     * @return
     */
    public static String html(String html) {
        if (html == null) return null;
        html = StringUtils.replace(html, "&", "&amp;");
        html = StringUtils.replace(html, "'", "&apos;");
        html = StringUtils.replace(html, "\"", "&quot;");
        html = StringUtils.replace(html, "\t", "&nbsp;&nbsp;");// 替换跳格
        html = StringUtils.replace(html, "<", "&lt;");
        html = StringUtils.replace(html, ">", "&gt;");
        return html;
    }

    /**
     * 过滤用户输入信息
     *
     * @param html
     * @return
     */
    public static String filter(String html) {
        if (StringUtils.isBlank(html)) {
            return "";
        }
        return Jsoup.clean(html, whiteList);
    }
}
