package com.demo.webdemo.controller;

import com.demo.webdemo.common.Constant;
import com.demo.webdemo.entity.EventBusDao;
import com.demo.webdemo.entity.request.UploadInfoRequest;
import com.demo.webdemo.entity.response.SimpeResponseDao;
import com.demo.webdemo.util.MathUtil;
import com.demo.webdemo.util.PrintUtil;
import com.demo.webdemo.util.TimeUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/SpringBootDemo")
public class DemoController implements Constant {

    /**
     * 图片请求
     *
     * @param response
     */
    @RequestMapping("requestPic")
    public void requestPic(HttpServletResponse response) {
        try {
            File file = new File("E:/IdeaProjects/webPicFromAndroid/demo_pic.jpg");
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
        PrintUtil.d("DemoController requestJsonContent (DemoController.java : 56)", dao);
        return content;
    }

    /**
     * 返回json数据，带参数
     *
     * @return
     */
    @RequestMapping(value = "/requestJsonContentByParams")
    public String requestJsonContentByParams(@RequestParam(name = "name") String name, @RequestParam(name = "age") int age, @RequestParam(name = "sex") String sex) {
        EventBusDao dao = new EventBusDao(name + "_" + TimeUtil.getCurrentDateFormate(),
                age, sex);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String content = gson.toJson(dao);
        PrintUtil.d("DemoController requestJsonContentByParams (DemoController.java line:72)", dao);
        return content;
    }

    /**
     * 返回json数据，带参数，url中带id
     *
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(value = "/requestJsonContentByParamsWithId/{id}")
    public String requestJsonContentByParamsWithId(@PathVariable(name = "id") String id,
                                                   @RequestParam(name = "name") String name) {
        EventBusDao dao = new EventBusDao("id = " + id,
                MathUtil.getRandom(), MathUtil.getRandom() == 0 ? "man" : "woman");
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String content = gson.toJson(dao);
        PrintUtil.d("DemoController requestJsonContentByParamsWithId (DemoController.java)", dao);
        return content;
    }

    /**
     * 获取json格式内容（带json参数）
     *
     * @param dao
     * @return
     */
    @RequestMapping(value = "/requestJsonContentByJsonParams")
    public SimpeResponseDao requestJsonContentByJsonParams(@RequestBody EventBusDao dao) {
        EventBusDao eventBusDao = dao;
        SimpeResponseDao responseDao = new SimpeResponseDao();
        if ("today".equals(eventBusDao.getName()) && 99 == eventBusDao.getAge() && "man-woman".equals(eventBusDao.getSex())) {
            responseDao.setCode("0000");
            responseDao.setMsg("提交成功~");
        } else {
            responseDao.setCode("0001");
            responseDao.setMsg("提交失败！");
        }
        PrintUtil.d("DemoController requestJsonContentByJsonParams (DemoController.java)", eventBusDao);
        return responseDao;
    }

    /**
     * 获取文件，并保存到本地
     *
     * @return
     */
    @RequestMapping(value = "/uploadFiles")
    public SimpeResponseDao uploadFiles(HttpServletRequest httpServletRequest) {
        MultipartHttpServletRequest request = (MultipartHttpServletRequest) httpServletRequest;
        List<MultipartFile> files = request.getFiles("files");
        SimpeResponseDao responseDao = new SimpeResponseDao();
        boolean isSuccess = true;
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            FileOutputStream outputStream = null;

            try {
                String pathName = SAVE_FILE_PATH + file.getOriginalFilename();
                File file1 = new File(pathName);
                if (file1.exists()) {
                    file1.delete();
                }
                outputStream = new FileOutputStream(pathName);
                outputStream.write(file.getBytes());
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
                isSuccess = false;
            } finally {
                try {
                    if (outputStream != null) outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (isSuccess) {
            responseDao.setCode("0000");
            responseDao.setMsg("上传成功");
        } else {
            responseDao.setCode("0001");
            responseDao.setMsg("上传失败");
        }
        return responseDao;
    }

    /**
     * 上传带参数的文件
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/uploadParamsAndFiles")
    public SimpeResponseDao uploadParamsAndFiles(HttpServletRequest httpServletRequest) {
        SimpeResponseDao responseDao = new SimpeResponseDao();

        MultipartHttpServletRequest fileRequest = (MultipartHttpServletRequest) httpServletRequest;
        String content = fileRequest.getParameter("params");

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        UploadInfoRequest infoRequest = gson.fromJson(content, UploadInfoRequest.class);
        PrintUtil.d("DemoController uploadParamsAndFiles (DemoController.java line:178)", infoRequest.getMsg());

        int count = Integer.valueOf(infoRequest.getNum());
        List<MultipartFile> files = fileRequest.getFiles("files");
        if (count == files.size()) {
            boolean isSuccess = true;
            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                String pathName = SAVE_FILE_PATH + file.getOriginalFilename();
                FileOutputStream outputStream = null;
                try {
                    File file1 = new File(pathName);
                    if (file1.exists()) {
                        file1.delete();
                    }
                    outputStream = new FileOutputStream(pathName);
                    outputStream.write(file.getBytes());
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    isSuccess = false;
                } finally {
                    try {
                        if (outputStream != null) outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (isSuccess) {
                responseDao.setCode("0000");
                responseDao.setMsg("上传成功");
            } else {
                responseDao.setCode("0001");
                responseDao.setMsg("上传失败");
            }
        } else {
            responseDao.setCode("0001");
            responseDao.setMsg("上传失败");
        }
        return responseDao;
    }
}
