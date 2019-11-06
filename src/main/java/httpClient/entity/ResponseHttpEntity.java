package httpClient.entity;

/**
 * @Author yangwen-bo
 * @Date 2019/11/5.
 * @Version 1.0
 *
 * 处理后，响应的实体类
 */
public class ResponseHttpEntity {
    private String seriaNbr;//流水号
    private String sendSms;//是否发送短信
    private String rspCode;//响应码

    public ResponseHttpEntity(String seriaNbr, String sendSms, String rspCode) {
        this.seriaNbr = seriaNbr;
        this.sendSms = sendSms;
        this.rspCode = rspCode;
    }

    public ResponseHttpEntity() {
    }

    public String getSeriaNbr(){
        return seriaNbr;
    }
    public void setSeriaNbr(String seriaNbr){
        this.seriaNbr = seriaNbr;
    }

    public String getSendSms(){
        return sendSms;
    }
    public void setSendSms(String sendSms){
        this.sendSms = sendSms;
    }

    public String getRspCode(){
        return rspCode;
    }
    public void setRspCode(String rspCode){
        this.rspCode = rspCode;
    }

}
