package com.kervin.utils.validator;

import com.kervin.entity.AuthEntity;
import com.kervin.vo.comn.RequestMsg;


/**
 * 输入项检查接口
 * @author Kervin
 * @since 2018/7/26 23:12
 */
public interface IValidator {

    void validator(AuthEntity entity);
}
