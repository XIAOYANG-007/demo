package com.example.demo.upload;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FtpUtil {

    public static FtpBean ftpSet(){
        //设置FTP上传的基本信息，可直接初始化数据
        FtpBean ftp = new FtpBean();
        //配置服务器信息
        ftp.setAddress("192.168.18.174");
        ftp.setPort("21");
        ftp.setUsername("ftptest");
        ftp.setPassword("ftptest");
        return ftp;
    }

    public static boolean uploadFile(FtpBean ftpBean) throws IOException {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
//			System.out.println(ftpBean.getAddress()+"====="+ftpBean.getPort());
            ftp.connect(ftpBean.getAddress(), Integer.valueOf(ftpBean.getPort()));// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
//			System.out.println(ftpBean.getUsername()+"======"+ftpBean.getPassword());
            ftp.login(ftpBean.getUsername(), ftpBean.getPassword());// 登录
            reply = ftp.getReplyCode();
            System.out.println("状态码"+reply);
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }

            String tempPath = ftpBean.getBasepath();
            String fileName = ftpBean.getFileName();

            if (!ftp.changeWorkingDirectory(ftpBean.getBasepath())) {
                //判断目录是否存在，如果目录不存在创建目录，目录存在则跳转到此目录下
                String []tempPathList = tempPath.split("/");
                for (String dir : tempPathList) {
                    if(dir != null && dir != ""){
                        if (!ftp.changeWorkingDirectory(dir)) {
                            if (!ftp.makeDirectory(dir)) {
                                return result;
                            } else {
                                ftp.changeWorkingDirectory(dir);
                            }
                        }
                    }
                }
            }

            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //设置模式很重要
            ftp.enterLocalActiveMode();
            //上传文件
            result = ftp.storeFile(fileName, ftpBean.getInputStream());
            if(!result){
                return result;
            }

            ftpBean.getInputStream().close();
            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

    public static boolean downFile(FtpBean ftpBean) throws IOException {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
//			System.out.println(ftpBean.getAddress()+"====="+ftpBean.getPort());
            ftp.connect(ftpBean.getAddress(), Integer.valueOf(ftpBean.getPort()));// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
//			System.out.println(ftpBean.getUsername()+"======"+ftpBean.getPassword());
            ftp.login(ftpBean.getUsername(), ftpBean.getPassword());// 登录
            reply = ftp.getReplyCode();
            System.out.println("状态码" + reply);
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            ftp.changeWorkingDirectory("/upload");
            FTPFile[] remoteFiles = ftp.listFiles();
            for(FTPFile rf:remoteFiles){
                if(rf.getName().equals(ftpBean.getFileName())){
                    // 创建本地存储路径
                    File lf = new File(ftpBean.getBasepath() + File.separator + rf.getName());
                    FileOutputStream lfos = new FileOutputStream(lf);
                    // 通过文件检索系统,将文件写入流
                    ftp.retrieveFile(rf.getName(),lfos);
                    System.out.println("下载完毕");
                    lfos.close();
                }
            }
            result=true;
        }catch (Exception e){

        }
        ftp.logout();
        return result;
    }

}
