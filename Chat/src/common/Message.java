package common;

import java.io.Serializable;

public class Message implements Serializable{
	private MsgType type;//��Ϣ����
    private String content;//��Ϣ����
    private String senderId;//������
    private String senderName;//������
    private String getterId;//������
    private String sendTime;//����ʱ��

    public Message() {
    }

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getGetterId() {
        return getterId;
    }

    public void setGetterId(String getterId) {
        this.getterId = getterId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    
    public String toString() {
        return type + "--"+sendTime + ":" + senderId + "��" + getterId + "˵��" + content;
    }



}
