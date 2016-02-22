package top.wangjun.utils;

import java.util.regex.Pattern;

/**
 * velocity页面上页码处理
 */
public class PageUtils {


    //起始的页码
    public int start(int page, int count, int pageLength) {
        int start = Math.round(pageLength / 2) + page - pageLength;
        if (start < 1 || count <= pageLength) {
            start = 1;
        } else if (start + pageLength > count) {
            start = count;
        }
        return start;
    }

    //结束的页码
    public int end(int start, int count, int pageLength) {
        int end = start + pageLength - 1;
        return end < count ? end : count;
    }

    public String url(String url, String pageKey, int page) {
        String pattern = "(([\\?&])" + pageKey + "=\\d+)";
        boolean pageKeyExist = Pattern.compile(pattern).matcher(url).find();
        if (!pageKeyExist) {
            return url + (url.indexOf("?") > 0 ? "&" : "?") + pageKey + "=" + page;
        }
        return url.replaceAll(pattern, "$2" + pageKey + "=" + page);
    }

    public static void main(String[] args) {
        PageUtils utils = new PageUtils();
        String url = "http://localhost:8080/asset/income.htm?pageNum=1&pageSize=2";
        System.out.println(utils.url(url, "pageNum", 3));
    }
}
