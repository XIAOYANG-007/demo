package com.example.demo.upload;

/**
 * All rights Reserved, Designed By
 *
 * @Title: Constant.java
 * @Package com.ssnc.gov.tech.constants
 * @Description: TODO
 * @Author: 黄康
 * @Version: v1.0.0
 * @Date: 2022/8/17 11:07
 */
public interface Constant {

    /**
     * system prod settings filename
     */
    String ENV_PROFILE_NAME = "prod";

    /**
     * 数据  status 状态 1启用、0停用
     */
    Long DATA_NORMAL_STATUS = 1L;

    /**
     * 数据删除  is_del 状态 1是、0否
     */
    Long DATA_DEL_STATUS = 1L;

    /**
     * 附件所在的文件夹名称
     */
    String ATTACHMENT_FOLDER = "attachments";
}
