package com.kervin.utils.validator.impl;

import com.kervin.enumerate.ValidatorEnum;
import com.kervin.utils.validator.IValidator;
import com.kervin.utils.validator.ValidatorExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 转账服务输入项检查规则链
 * @author Kervin
 * @since 2018/7/26 23:55
 */
public class TransferValidatorExecutor extends ValidatorExecutor {

    public TransferValidatorExecutor() {
        this.validatorsList = getValidators();
    }

    public List<IValidator> getValidators() {
        return new ArrayList<IValidator>(
                Arrays.asList(new IValidator[]{
                        ValidatorFactory.getValidator(ValidatorEnum.SysEvtTraceIdValidator),
                        ValidatorFactory.getValidator(ValidatorEnum.SysSndSerialNoValidator),
                        ValidatorFactory.getValidator(ValidatorEnum.CardNumValidator),
                        ValidatorFactory.getValidator(ValidatorEnum.TxnAmtValidator),
                        ValidatorFactory.getValidator(ValidatorEnum.TxnCcyValidator),
                        ValidatorFactory.getValidator(ValidatorEnum.OpntCardNumValidator)}));
    }
}
