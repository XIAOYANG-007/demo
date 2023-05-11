package com.example.demo.upload;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
//import weaver.general.BaseBean;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.UUID;


@RestController
@RequestMapping("/ChartUpload")
public class ChartUploadFanwei {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");

    /**
     * 附件所在的文件夹名称
     */
    String ATTACHMENT_FOLDER = "attachments";

    @PostMapping("/uploadAttachment")
    public String uploadAttachment(@RequestPart MultipartFile attachment) throws Exception{
        //(new BaseBean()).writeLog("风险管控图文件上传方法+uploadAttachment");
        System.out.println("风险管控图文件上传方法+uploadAttachment");
        try {
            if (null != attachment && attachment.getSize() > 0) {
                //if (!org.springframework.util.ObjectUtils.isEmpty(attachment) && attachment.getSize() > 0) {
                //上传到项目resources下
                String path = getSavePath(ATTACHMENT_FOLDER);
                String storageName = UUID.randomUUID().toString();
                File file = new File(path, storageName);
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdir();
                }
                attachment.transferTo(file);
                return storageName;
            }
        } catch (IOException io) {
            return ("IO操作异常：" + io);
        } catch (Exception ex) {
            return ("程序操作异常：" + ex);
        }
        return "文件为空";
    }

    /**
     * @return 获取文件存放路径
     */
    private String getSavePath(String folder) {
/*        String os = System.getProperty("os.name");
        Boolean linux;
        if (os != null && os.toLowerCase().startsWith("windows")) {
            linux = false;
        } else if (os != null && os.toLowerCase().startsWith("linux")) {
            linux = true;
        } else {
            linux = false;
        }*/
        String path;
        path="D:\\ABCD\\EFG\\"+folder+"\\";
        /*ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        if (linux) {
            path = applicationHome.getDir().getAbsolutePath() + "/" + folder + "/";
        } else {
           path = applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath()
                    + "\\src\\main\\resources\\" + folder + "\\";
        }*/
        return path;
    }

    /**
     * 附件下载
     *
     * @param response
     * @param fxgktID    风险管控图ID
     * @param storageName  目前就是附件存储的名字
     */
    @PostMapping("/geAttachment")
    public void geAttachment(HttpServletResponse response, @RequestParam("fxgktID") String fxgktID,@RequestParam("storageName") String storageName) {
        if (StringUtils.isEmpty(fxgktID)&&StringUtils.isEmpty(storageName)) {
            //风险管控图ID不允许为空
            //附件ID不允许为空
        }
       //上传文件后会存储入一个表，用ID去那个表找到地址等等信息
        /*AttachmentEntity entity = attachmentRepository.selectById(attachmentId);
        if (ObjectUtils.isEmpty(entity)) {
            log.info(ResultEnum.ATTACHMENT_ID_INVALID.getMessage());
            throw new ParamInvalidException(ResultEnum.ATTACHMENT_ID_INVALID);
        }*/

        FileInputStream fis = null;
        ServletOutputStream sos = null;
        String path = getSavePath(ATTACHMENT_FOLDER);

        //存储时的文件名
        File file = new File(path + storageName);
        //File file = new File(path + fxgktID);
        try {
            //设置响应头
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(storageName, "UTF-8"));
            fis = new FileInputStream(file.getAbsoluteFile());
            sos = response.getOutputStream();
            IOUtils.copy(fis, sos);
        } catch (Exception ex) {
            //打印下载附件失败,ex
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (sos != null) {
                    sos.flush();
                    sos.close();
                }
            } catch (IOException ex) {
                //"文件流OutputStream关闭出错！", ex
            }
        }
    }
}
