package com.example.demo;

import com.jspsmart.upload.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/UploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet {
    int file_Counter = 0;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SmartUpload myUpload = new SmartUpload();
        ServletConfig config = getServletConfig();
        myUpload.initialize(config, request, response);
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<h2>处理上传的文件</h2>");
        out.println("<hr>");

        try {
            myUpload.setMaxFileSize(1024 * 1024);//限制每个上传文件的最大长度
            myUpload.setTotalMaxFileSize(5 * 1024 * 1024);//限制总上传数据的长度
            myUpload.setAllowedFilesList("jpg,png,jpeg");// 设定允许上传的文件扩展名
            myUpload.setDeniedFilesList("exe,bat,jsp,htm,html");// 设定禁止上传的文件扩展名
            myUpload.upload();//上传文件

            int count = myUpload.getFiles().getCount();//统计上传文件的总数
            Request myRequest = myUpload.getRequest();//取得 Request 对象
            String fileName, fileExtName, filePath, memo, fileReName;
            int fileSize;

            for (int i = 0; i < count; i++) {//逐一提取上传文件信息，同时可保存文件
                File file = myUpload.getFiles().getFile(i);//取得一个上传文件
                if (file.isMissing()) continue;//若文件不存在则继续

                fileName = file.getFileName();//取得文件名
                fileExtName = file.getFileExt();//取得文件扩展名

                filePath = "D:\\demo\\src\\main\\webapp\\uploads\\" + fileName; // 绝对路径
                String shortpath = "uploads/" + fileName; //相对路径
                memo = myRequest.getParameter("memo" + i);
                fileSize = file.getSize();

                String view = "http://localhost:8080/"+shortpath;
                // 显示当前文件信息
                out.println("第" + (i + 1) + "个文件的文件信息：<br>");
                out.println(" 文件名为：" + fileName + "<br>");
                out.println(" 文件扩展名为：" + fileExtName + "<br>");
                out.println(" 文件大小为：" + fileSize + "字节<br>");
                out.println(" 文件备注为：" + memo + "<br>");
                out.println(" 文件绝对路径为：" + filePath + "<br><br>");
                out.println(" 文件访问路径为：" + view + "<br><br>");
                out.println("<img src='" + view + "'/>");


                String filePath2 = "D:\\demo\\target\\demo-1.0-SNAPSHOT\\uploads\\" + fileName; // 绝对路径
                // 将文件保存到指定路径
                file.saveAs(filePath);
                file.saveAs(filePath2);

                PhotoBean photoBean = new PhotoBean();
                photoBean.storePhoto(filePath, memo, fileName, fileExtName, file.getSize(), shortpath);
                file_Counter++; // 递增计数器
            }
            out.println(count + "个文件上传成功！<br>");
        } catch (Exception ex) {
            out.println("上传文件超过了限制条件，上传失败!<br>");
            out.println("错误原因：<br>" + ex.toString());
        } finally {
            out.flush();
            out.close();
        }
    }

}
