package client;

import java.io.*;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.JOptionPane;

import common.Message;
import common.MsgType;

public class Connect extends Thread {
    private volatile boolean isRunning;
	Socket mysocket = null;
	DataInputStream in = null;
	DataOutputStream out = null;
	Thread readData;
	public Connect(Socket socket) {
		mysocket = socket;
		isRunning = true;
	}
	public Socket getSocket() {
		return mysocket;
	}
	public void Stop() {
		isRunning = false;
	}
	public void run() {
		try {
			while(isRunning) {
				ObjectInputStream input = new ObjectInputStream(mysocket.getInputStream());
				Message msg = (Message)input.readObject();
				
				if(msg.getType()==MsgType.RET_ONLINE_FRIENDS) {
					String uid = msg.getGetterId();
                    System.out.println("find FriendList uid="+uid);
                    FriendList fl = ManageFriendListFrame.getFriendListFrame(uid);
                    if(fl != null) {
                    	fl.updateOnlineFriends(msg);
                    }
				}
				
				else if(msg.getType() == MsgType.COMMON_MESSAGE) {
                    String frameName = msg.getGetterId()+msg.getSenderId();
                    System.out.println("find Chat framename="+frameName);
                    ManageChatFrame.getChatFrame(frameName).showMessage(msg,false);
                }
				
				else if(msg.getType() == MsgType.NOT_ONLINE) {
                    Chat chat = ManageChatFrame.getChatFrame(msg.getSenderId() + msg.getGetterId());
                    JOptionPane.showMessageDialog(chat, "该好友未上线，暂未实现离线聊天功能！");
                }
				
				else if(msg.getType() == MsgType.SERVER_CLOSE){
                    String toId = msg.getGetterId();
                    //自动下线
                    ManageFriendListFrame.getFriendListFrame(toId).sendUnloadMsgToServer();
                    ManageThread.removeThread(toId);
                    ManageFriendListFrame.removeFriendListFrame(toId);
                }					
		  }		
	}
	   catch(Exception e) {
		System.out.println("服务器已断开"+e);
	   }

	}
}

