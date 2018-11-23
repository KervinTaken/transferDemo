package com.kervin.utils.validator;

import com.kervin.entity.AuthEntity;

import java.util.List;


/**
 * 输入项检查规则调用Excutor
 * @author Kervin
 * @since 2018/7/26 23:12
 */
public class ValidatorExecutor {

    protected List<IValidator> validatorsList;

    public void setValidatorsList(List<IValidator> validatorsList) {
        this.validatorsList = validatorsList;
    }

    public void invokeValidators(AuthEntity e) {
        if (this.validatorsList != null) {
            for (IValidator validator : validatorsList) {
                validator.validator(e);
            }
        }
    }
}
