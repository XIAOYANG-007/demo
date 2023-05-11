package com.example.demo.upload;

import java.io.InputStream;

public class FtpBean{

    //获取ip地址
    private String address;
    //端口号
    private String port;
    //用户名
    private String username;
    //密码
    private String password;
    //文件名称
    private String fileName;
    //基本路径
    private String basepath;
    //文件输入流
    private InputStream inputStream;


    public String getAddress() {
        return address == null ? "127.0.0.1":address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPort() {
        return port == null ? "21":port;
    }
    public void setPort(String port) {
        this.port = port;
    }
    public String getUsername() {
        return username == null ? "maple":username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password == null ?"xxxxxxx":password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFileName() {
        return fileName == null ?"未命名":fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getBasepath() {
        return basepath == null ?"/local":basepath;
    }
    public void setBasepath(String basepath) {
        this.basepath = basepath;
    }
    public InputStream getInputStream() {
        return inputStream;
    }
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
