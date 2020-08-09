package Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import common.User;

public class JDBC_control {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	JDBC_control(){
		try {
			conn = JDBCUtils.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String checkUserInfo(User u){
        String qname = null;
        int uid = Integer.parseInt(u.getUid());
        String pwd = u.getPwd();
        String sql = "select uname from  t_user where uid=? and pwd=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1,uid);
            ps.setString(2,pwd);
            rs = ps.executeQuery();
            while (rs.next()){
                qname = rs.getString("uname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JDBCUtils.close(conn, ps,rs);
        }
        return qname;
    }
    /**
     * 返回全部好友列表
     * @return
     */
    public String getFriendsList(String uid){
        StringBuilder contents = new StringBuilder();
        int mqq = Integer.parseInt(uid);
        String sql = "select fname,fqq from  t_friends where mqq=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1,mqq);
            rs = ps.executeQuery();
            while(rs.next()){
                contents.append(rs.getString("fname")+"("+rs.getString("fqq")+") ");
            }
            return contents.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
        	 JDBCUtils.close(conn, ps,rs);
        }
        return null;
    }
}
