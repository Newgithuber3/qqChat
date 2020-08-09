package client;

import java.util.Hashtable;

public class ManageThread {
	private static Hashtable<String,Connect> threads = new Hashtable<>();

    public static void addThread(String uid,Connect thread){
        threads.put(uid,thread);
    }

    public static Connect getThread(String uid){
        return threads.get(uid);
    }

    public static void removeThread(String uid){
        threads.remove(uid);
    }
}
