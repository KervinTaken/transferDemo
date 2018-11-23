package com.kervin.vo.comn;

import com.kervin.enumerate.SvcRspCdEnum;
import com.kervin.enumerate.SysTxStatusEnum;

/**
 * 返回Head头
 * @author Kervin
 * @since 2018/7/25 22:27
 */
public class RspMsgHead {

    // 服务响应状态
    private String svcRspStatus;

    // 服务响应码
    private String svcRspCd;

    // 服务响应描述
    private String sysRespDesc;

    public RspMsgHead() {
        this.svcRspStatus = SysTxStatusEnum.Success.getCode();
        this.svcRspCd = SvcRspCdEnum.SUCCESS.getCode();
        this.sysRespDesc = SvcRspCdEnum.SUCCESS.getDesc();
    }

    public String getSvcRspStatus() {
        return svcRspStatus;
    }

    public void setSvcRspStatus(String svcRspStatus) {
        this.svcRspStatus = svcRspStatus;
    }

    public String getSvcRspCd() {
        return svcRspCd;
    }

    public void setSvcRspCd(String svcRspCd) {
        this.svcRspCd = svcRspCd;
    }

    public String getSysRespDesc() {
        return sysRespDesc;
    }

    public void setSysRespDesc(String sysRespDesc) {
        this.sysRespDesc = sysRespDesc;
    }
}
