package com.kervin.enumerate;

/**
 * 服务响应状态枚举
 * @author Kervin
 * @since 2018/7/25 22:27
 */
public enum SysTxStatusEnum {

	/**
	 * 交易成功
	 */
	Success("00"),

	/**
	 * 正交易响应: 交易失败，后端不需冲正或后端已经完成冲正动作，前端节点不需再向其发起冲正
	 */
	Fail("02");

	//响应码
	private final String code;

	SysTxStatusEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
