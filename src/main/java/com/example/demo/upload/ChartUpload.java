package com.example.demo.upload;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/uploadTest")
public class ChartUpload {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");


    @PostMapping("/shangchuanwenjian")
    private String uploadAttachment(@RequestPart(required = false) MultipartFile attachment) throws IOException {
        if (!org.springframework.util.ObjectUtils.isEmpty(attachment) && attachment.getSize() > 0) {
            //上传到项目resources下
            String path = getSavePath("attachments");
            String storageName = UUID.randomUUID().toString();
            File file = new File(path, storageName);
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdir();
            }
            attachment.transferTo(file);
            return storageName;
        }
        return null;
    }

    /**
     * @return 获取文件存放路径
     */
    private String getSavePath(String folder) {
        String os = System.getProperty("os.name");
        Boolean linux;
        if (os != null && os.toLowerCase().startsWith("windows")) {
            linux = false;
        } else if (os != null && os.toLowerCase().startsWith("linux")) {
            linux = true;
        } else {
            linux = false;
        }

        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String path;
        if (linux) {
            path = applicationHome.getDir().getAbsolutePath() + "/" + folder + "/";
        } else {
           path = applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath()
                    + "\\src\\main\\resources\\" + folder + "\\";
           //path="D:\\weaver\\ecology\\resource\\"+folder+"\\";
        }
        return path;
    }

    /**
     * 附件下载
     *
     * @param response
     * @param fxgktID
     * @param storageName
     */
    @PostMapping("/xiazaiwenjian")
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
        String path = getSavePath(Constant.ATTACHMENT_FOLDER);

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

    @PostMapping("/multipartFile")
    public void uploadImg(@RequestParam("file")MultipartFile file){
        String name = file.getOriginalFilename();//获取文件的名字
        String base = "D:\\"+name;//要上传到本地的地址
        File file1 = new File(base);//创建一个file对象
        try {
            file.transferTo(file1);//将前端上传的MultipartFile对象复制到file1中
            System.out.println(file1.getAbsoluteFile());//获取文件的绝对路径
            System.out.println(file1.getPath());//获取文件的相对路径
        } catch (IOException e) {

        }
    }
}
