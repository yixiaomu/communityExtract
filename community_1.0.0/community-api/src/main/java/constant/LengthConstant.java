package constant;

/**
 * @ClassName: LengthConstant
 * @Description: 字段长度常量类
 * @author tailinliu
 * @date 2015年7月2日 
 */
public final class LengthConstant {
	
	/* version 版本最大长度 */
	public static final int VERSION_MAX_LENGTH = 8;
	
	/* bizCode 业务线编号最大长度 */
	public static final int BIZ_CODE_MAX_LENGTH = 16;
	
	/* merchantCode 商户编号最大长度 */
	public static final int MERCHANT_CODE_MAX_LENGTH = 16;
	
	/* userId 用户ID最大长度 */
	public static final int USER_ID_MAX_LENGTH = 100;
	
	/* assetType 资产类型最大长度 */
	public static final int ASSET_TYPE_MAX_LENGTH = 2;
	
	/* sign 签名最大长度 */
	public static final int SIGN_MAX_LENGTH = 128;
	
	/* auth_level 认证级别最大长度*/
	public static final int AUTH_LEVEL_MAX_LENGTH = 2;
	
	/*customerName 客户姓名最大长度*/
	public static final int CUSTOMERNAME_MAX_LENGTH = 64;
	
	/*idType 证件类型最大长度*/
	public static final int IDTYPE_MAX_LENGTH = 3;
	
	/*idNo 证件号码最大长度*/
	public static final int IDNO_MAX_LENGTH = 32;
	
	/*phone 手机号最大长度*/
	public static final int PHONE_MAX_LENGTH = 20;
	
	/*cardNo 卡号最大长度*/
	public static final int CARDNO_MAX_LENGTH = 32;
	
	/*bankCode 银行编号最大长度*/
	public static final int BANKCODE_MAX_LENGTH = 8;
	
	/*bankName 银行名称最大长度*/
	public static final int BANKNAME_MAX_LENGTH = 32;
	
	/*orderNo 商户订单号最大长度*/
        public static final int ORDERNO_MAX_LENGTH = 32;
        
        /*curId 货币种类最大长度*/
        public static final int CURID_MAX_LENGTH = 3;
        
        /*source 来源（PC/APP）最大长度*/
        public static final int SOURCE_MAX_LENGTH = 3;
        
        /*业务类型【票支付申请、piaoDBConfirm】最大长度*/
        public static final int BIZ_TYPE_MAX_LENGTH = 16;
        
        /*后台回调地址最大长度*/
        public static final int BG_RET_MAX_LENGTH = 128;
        
        /*交易过期时间最大长度*/
        public static final int VALIDTIMEMAX_LENGTH = 14;
        
        /*交易类型最大长度*/
        public static final int TRXMODE_MAX_LENGTH = 2;
        
        /*支付类型最大长度*/
        public static final int PAYTYPE_MAX_LENGTH = 2;
        
        /*银行省、银行市最多长度*/
        public static final int PROVINCEORCITY_MAX_LENGTH = 6;
        
        /*交易流水号最多长度*/
        public static final int UNI_TRADE_NO_MAX_LANGTH = 32;
        
        /*退款请求号最多长度*/
        public static final int REFUND_NO_MAX_LENGTH = 50;
        
        /*商户名称最大长度*/
        public static final int MERCHANT_NAME_MAX_LENGTH = 64;
        
        /*商户描述最大长度*/
        public static final int MERCHANT_DESCRIPTION_MAX_LENGTH = 1024;
}