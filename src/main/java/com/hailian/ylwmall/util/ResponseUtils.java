package com.hailian.ylwmall.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zhp.dts
 * @date 2018/11/26.
 */
public class ResponseUtils {
    /**
     * 回传指定的json数据
     * @param response
     * @param data
     */
    public static void backData(HttpServletResponse response, String data){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(data);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
