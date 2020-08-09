package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import common.Message;
import common.MsgType;
import common.User;

public class LoginUser {

    private Socket client;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public LoginUser() {
        try {
            client = new Socket("localhost", 9999);
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            System.out.println("���ӷ�����ʧ�ܣ�");
            e.printStackTrace();
        }
    }

    /**
     * ����û�������Ϣ�Ƿ����
     * �����򽫵�¼��Ϣ��浽�����з���
     * ����������ʾ��ʾ��Ϣ
     *
     * @param f
     * @param uid
     * @param pwd
     * @return
     */
    private User checkLoginInfo(JFrame f, String uid, String pwd) {
        User u = null;
        if (!uid.equals("") && !pwd.equals("")) {
            String pattern = "[0-9]{3,10}";//�˺�Ϊ1-10λ����
            if (Pattern.matches(pattern, uid)) {//�˺źϷ�
                u = new User(uid, pwd);
            } else {//�˺Ų��Ϸ�
                JOptionPane.showMessageDialog(f, "�˺�Ϊ3��10λ�������У����������룡");
            }
        } else if (uid.equals("")) {//�˺�Ϊ��
            JOptionPane.showMessageDialog(f, "�������˺ţ�");
        } else if (pwd.equals("")) {//����Ϊ��
            JOptionPane.showMessageDialog(f, "���������룡");
        }
        return u;
    }

    /**
     * ��ͨ��У��ĵ�¼��Ϣ���͵�������
     * �����õ�����Ϣ������(������ǰ�û������к���)
     *
     * @param f
     * @param uid
     * @param pwd
     */
    public Message sendLoginInfoToServer(JFrame f, String uid, String pwd) {
        User u = checkLoginInfo(f, uid, pwd);
        if (u != null) {
            try {
                output.writeObject(u);//���͵�������
                System.out.println("ok " + u.toString());
                Message msg = (Message) input.readObject();//���շ��ؽ��
                System.out.println("loading!");
                if (msg.getType() == MsgType.LOGIN_SUCCEED) {//��¼�ɹ�
                	System.out.println("successful!");
                    Connect th = new Connect(client);
                    th.start();//�����������ͨ���߳�
                    ManageThread.addThread(uid, th);
                    return msg;
                } else if (msg.getType() == MsgType.LOGIN_FAILED) {
                    JOptionPane.showMessageDialog(f, "�˺Ż���������������������룡");
                } else if (msg.getType() == MsgType.ALREADY_LOGIN) {
                    JOptionPane.showMessageDialog(f, "���û��ѵ�¼�������ظ�������");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}

