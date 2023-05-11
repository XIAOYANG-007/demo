package com.example.demo.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

public class FtpDemo {


    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();    //获取开始时间
        //文件路径
        String path="E:\\Users\\kaka.png";
        String suffix = path.substring(path.lastIndexOf(".") + 1);//获取文件的后缀名

        //获取本地需要上传的文件，此处只用作测试
        File file = new File(path);

        if (file.isFile() && file.exists()) {

            //配置FTP服务器
            FtpBean ftp=FtpUtil.ftpSet();
            ftp.setBasepath("E:\\MyDrivers");
            ftp.setFileName("20230426004006089317.png");

            FtpUtil.downFile(ftp);
            /*//自动生成路径，默认取源文件路径，为FILETYPE时，取文件类型做路径
            //如使用自己的路径，直接ftp.setBasepath赋值即可
            //文件上传路径
            ftp.setBasepath("/upload");

            SimpleDateFormat sdfms = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String date = sdfms.format(System.currentTimeMillis());
            //添加三位自动生成的数字，防止重复
            int j = (int) (Math.random() * 900) + 100;
            String fileName = date + j + "." + suffix;
            ftp.setFileName(fileName);

            try {
                //将文件转换成一个输入流
                InputStream in = new FileInputStream(file);
                ftp.setInputStream(in);

                //传入文件名称，和文件输入流，上传至FTP服务器
                boolean isOk = FtpUtil.uploadFile(ftp);
                if (isOk) {
                    System.out.println(Thread.currentThread().getName()+"文件上传成功");
                } else {
                    System.out.println(Thread.currentThread().getName()+"文件上传失败");
                }
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        } else {
            System.out.println("文件不存在");
        }
        long endTime = System.currentTimeMillis();    //获取结束时间

        System.out.println(Thread.currentThread().getName()+"程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
    }


}
