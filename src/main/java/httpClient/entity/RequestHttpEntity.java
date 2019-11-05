package httpClient.entity;

/**
 * @Author yangwen-bo
 * @Date 2019/11/5.
 * @Version 1.0
 *
 * 请求实体类对象（）
 */
public class RequestHttpEntity {
    private String mobile;//手机号
    private String cardNo;//交易卡号
    private String seriaNbr;//交易流水
    private String source;//请求来源


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getSeriaNbr() {
        return seriaNbr;
    }

    public void setSeriaNbr(String seriaNbr) {
        this.seriaNbr = seriaNbr;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
