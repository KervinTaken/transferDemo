package com.kervin.exception;

import com.kervin.enumerate.SvcRspCdEnum;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

/**
 * 自定义运行时异常，保持错误码及错误描述
 * @author Kervin
 * @since 2018/7/26 21:26
 */
public class CommonRuntimeException extends RuntimeException {

    private String svcRspCd;
    private String errMessage;
    private String[] params;

    public CommonRuntimeException(String svcRspCd, String errMessage, Throwable e, String... params) {
        super(e);
        this.svcRspCd = svcRspCd;
        this.errMessage = errMessage;
        this.params = params;
    }

    public CommonRuntimeException(SvcRspCdEnum svcRspCd, Throwable e, String... params) {
        super(e);
        this.svcRspCd = svcRspCd.getCode();
        this.errMessage = svcRspCd.getDesc();
        this.params = params;
    }

    public CommonRuntimeException(String svcRspCd, String errMessage, String... params) {
        this(svcRspCd, errMessage, null, params);
    }

    public CommonRuntimeException(SvcRspCdEnum svcRspCd, String... params) {
        this(svcRspCd, null, params);
    }

    public CommonRuntimeException(SvcRspCdEnum svcRspCd) {
        this(svcRspCd, null, null);
    }

    public String getSvcRspCd() {
        return svcRspCd;
    }

    public String getErrMessage() {
        return getLogMessage();
    }

    private String getLogMessage() {
        String message = errMessage;
        if ((message != null) && (params != null) && (params.length > 0)) {
            MessageFormat mf = new MessageFormat(message);
            message = mf.format(params);
        }

        return StringUtils.isBlank(message) ? "" : message;
    }


}
