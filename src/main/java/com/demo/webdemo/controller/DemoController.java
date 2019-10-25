package com.demo.webdemo.controller;

import com.demo.webdemo.entity.EventBusDao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/SpringBootDemo")
public class DemoController {

    /**
     * 图片请求
     *
     * @param response
     */
    @RequestMapping("requestPic")
    public void requestPic(HttpServletResponse response) {
        try {
            File file = new File("D:\\checked_upload_blue128.png");
            response.setContentType("image/png");
            FileInputStream fis = new FileInputStream(file);
            if (fis != null) {
                int i = fis.available();
                byte[] data = new byte[i];
                fis.read(data);
                fis.close();
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(data);
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回json数据，不带参数
     *
     * @return
     */
    @RequestMapping(value = "/requestJsonContent")
    public String requestJsonContent() {
        EventBusDao dao = new EventBusDao("wyy", 100, "man");
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String content = gson.toJson(dao);
        return content;
    }
}
