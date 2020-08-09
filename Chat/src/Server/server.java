package Server;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import common.Message;
import common.MsgType;
import common.User;

public class server implements Runnable{
	 private ServerSocket server;
	 private Socket client;
	 private ObjectInputStream input;
	 private ObjectOutputStream output;
	 private volatile boolean isRunning;
	 private int port =9999;
	server(){
		System.out.println("-------------server("+port+")-----------------");
		isRunning = true;
		new Thread(this).start();
	}
	
	public void Stop() {
		isRunning = false;
		close(server);
	}
	
	public void run() {
		try {
			server = new ServerSocket(port);
			while(isRunning) {
				client = server.accept();
				System.out.println("---一个用户已连接---");
				input = new ObjectInputStream(client.getInputStream());
				output = new ObjectOutputStream(client.getOutputStream());
				User user = (User)input.readObject();
				System.out.println(user.toString()+"sever");
				doUserLogin(user);
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	public void doUserLogin(User u) {
		Message msg = new Message();
		JDBC_control jc = new JDBC_control();
		if(null == ManageClientThread.getClientThread(u.getUid())){
            try{
                String qname = jc.checkUserInfo(u);
                if(null != qname){//登录成功
                    msg.setType(MsgType.LOGIN_SUCCEED);
                    msg.setContent(qname + "-" + jc.getFriendsList(u.getUid()));
                    output.writeObject(msg);
                    //客户端连接成功就为其创建线程保持与服务器端通讯
                    ServerConClientThread th = new ServerConClientThread(client);
                    th.start();
                    //将其添加到线程集合
                    ManageClientThread.addClientThread(u.getUid(),th);
                    //通知其他用户
                    th.notifyOthers(u.getUid());
                    ServerFrame.showMsg("用户"+u.getUid()+"成功登录！");
                }else{
                    msg.setType(MsgType.LOGIN_FAILED);
                    output.writeObject(msg);
                    close(output,input,client);
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }else{//该用户已登录
            try {
                msg.setType(MsgType.ALREADY_LOGIN);
                output.writeObject(msg);
                close(output,input,client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
	}
	public void close(Closeable...ios) {
		for(Closeable io: ios) {
            try {
                if(null != io)
                    io.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

}
