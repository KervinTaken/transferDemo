package com.kervin.utils.validator.impl;

import com.kervin.exception.CommonRuntimeException;
import com.kervin.entity.AuthEntity;
import com.kervin.enumerate.ExceptionParams;
import com.kervin.enumerate.SvcRspCdEnum;
import com.kervin.enumerate.ValidatorEnum;
import com.kervin.utils.validator.IValidator;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * 输入项检查规则工厂
 * @author Kervin
 * @since 2018/7/26 23:55
 */
public class ValidatorFactory {

    /**
     * 获取不带参数的校验规则
     */
    public static IValidator getValidator(ValidatorEnum validatorName) {
        IValidator validator;
        switch (validatorName) {
            case SysEvtTraceIdValidator:
                validator = new SysEvtTraceIdValidator();
                break;
            case SysSndSerialNoValidator:
                validator = new SysSndSerialNoValidator();
                break;
            case CardNumValidator:
                validator = new CardNumValidator();
                break;
            case TxnAmtValidator:
                validator = new TxnAmtValidator();
                break;
            case TxnCcyValidator:
                validator = new TxnCcyValidator();
                break;
            case OpntCardNumValidator:
                validator = new OpntCardNumValidator();
                break;
            default:
                throw new CommonRuntimeException(SvcRspCdEnum.A00000000001);
        }
        return validator;
    }

    // 全局流水号必输及格式检查
    public static class SysEvtTraceIdValidator implements IValidator {

        @Override
        public void validator(AuthEntity entity) {

            if (StringUtils.isBlank(entity.getSysEvtTraceId())) {
                throw new CommonRuntimeException(


                        SvcRspCdEnum.A00000000002, ExceptionParams.SysEvtTraceId.getMessageZH());
            }

            if (!StringUtils.isNumeric(entity.getSysEvtTraceId())
                    || entity.getSysEvtTraceId().length() != 25) {
                throw new CommonRuntimeException(
                        SvcRspCdEnum.A00000000001, ExceptionParams.SysEvtTraceId.getMessageZH());
            }
        }
    }

    // 子交易序号必输及格式检查
    public static class SysSndSerialNoValidator implements IValidator {

        @Override
        public void validator(AuthEntity entity) {

            if (StringUtils.isBlank(entity.getSysSndSerialNo())) {
                throw new CommonRuntimeException(
                        SvcRspCdEnum.A00000000002, ExceptionParams.SysSndSerialNo.getMessageZH());
            }

            if (!StringUtils.isNumeric(entity.getSysSndSerialNo())
                    || entity.getSysSndSerialNo().length() != 4) {
                throw new CommonRuntimeException(
                        SvcRspCdEnum.A00000000001, ExceptionParams.SysSndSerialNo.getMessageZH());
            }
        }
    }

    // 卡号必输及格式检查
    public static class CardNumValidator implements IValidator {

        @Override
        public void validator(AuthEntity entity) {

            if (StringUtils.isBlank(entity.getCardNum())) {
                throw new CommonRuntimeException(
                        SvcRspCdEnum.A00000000002, ExceptionParams.CardNum.getMessageZH());
            }

            if (!StringUtils.isNumeric(entity.getCardNum())
                    || entity.getCardNum().length() != 16) {
                throw new CommonRuntimeException(
                        SvcRspCdEnum.A00000000001, ExceptionParams.CardNum.getMessageZH());
            }
        }
    }

    // 交易金额必输及格式检查
    public static class TxnAmtValidator implements IValidator {

        @Override
        public void validator(AuthEntity entity) {

            if (entity.getTxnAmt() == null) {
                throw new CommonRuntimeException(
                        SvcRspCdEnum.A00000000002, ExceptionParams.TxnAmt.getMessageZH());
            }

            if (entity.getTxnAmt().compareTo(BigDecimal.ZERO) != 1) {
                throw new CommonRuntimeException(SvcRspCdEnum.A00000000005);
            }
        }
    }

    // 交易币种必输及格式检查
    public static class TxnCcyValidator implements IValidator {

        @Override
        public void validator(AuthEntity entity) {

            if (StringUtils.isBlank(entity.getTxnCcy())) {
                throw new CommonRuntimeException(
                        SvcRspCdEnum.A00000000002, ExceptionParams.TxnCcy.getMessageZH());
            }

            if (!StringUtils.isNumeric(entity.getTxnCcy()) || entity.getTxnCcy().length() != 3) {
                throw new CommonRuntimeException(
                        SvcRspCdEnum.A00000000001, ExceptionParams.TxnCcy.getMessageZH());
            }
        }
    }

    // 对手账号必输及格式检查
    public static class OpntCardNumValidator implements IValidator {

        @Override
        public void validator(AuthEntity entity) {

            if (StringUtils.isBlank(entity.getTxnOpntCardNum())) {
                throw new CommonRuntimeException(
                        SvcRspCdEnum.A00000000002, ExceptionParams.OpntCardNum.getMessageZH());
            }

            if (!StringUtils.isNumeric(entity.getTxnOpntCardNum())
                    || entity.getTxnOpntCardNum().length() != 16) {
                throw new CommonRuntimeException(
                        SvcRspCdEnum.A00000000001, ExceptionParams.OpntCardNum.getMessageZH());
            }
        }
    }

}
