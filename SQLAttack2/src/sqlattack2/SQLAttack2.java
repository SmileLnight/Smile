/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlattack2;

import com.mysql.jdbc.PreparedStatement;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


/**
 *
 * @author LEM
 */
public class SQLAttack2 {
    //private final String prop_fife = "mysql.ini";
    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/sqltest";
    private String user = "root" ;
    private String pass = "";
    private String name = ""; 
    private String password = ""; 
    //登录界面的GUI组件
    private JFrame jf = new JFrame ("测试");
    private JTextField userField = new JTextField(20);
    private JTextField passField = new JPasswordField(20);
    private JButton loginButton = new JButton("登录");

    /**
     *
     * @throws Exception
     */
    public void init()throws Exception
    {
    Properties connProp = new Properties();
    //connProp.load(new FileInputStream(prop_fife));
         driver = connProp.getProperty("driver");
         url = connProp.getProperty("url");
         user = connProp.getProperty("user");
         pass = connProp.getProperty("pass"); 
         Class.forName("com.mysql.jdbc.Driver");
         //为登录按钮添加事件监听器
         loginButton.addActionListener(e -> {
        try {
            //登录成功显示“登录成功”
            if (validate(userField.getText(), passField.getText()))
            {
                JOptionPane.showMessageDialog(jf, "登录成功");
            }
            //失败显示“登录失败”
            else
            {
                JOptionPane.showMessageDialog(jf, "登录失败");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLAttack2.class.getName()).log(Level.SEVERE, null, ex);
        }
         });
         jf.add(userField,BorderLayout.NORTH);
         jf.add(passField);
         jf.add(loginButton,BorderLayout.SOUTH);
         jf.pack();
         jf.setVisible(true);
    }
    private boolean validate(String name, String password) throws SQLException
    {
        String sql = "select * from user where name=? and password=?";
        System.out.println(sql);
        try(
               
                 Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqltest", "root", "");
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, name);
            pstmt.setString(2, password);
        try(
            ResultSet rs = pstmt.executeQuery())
            {
              if (rs.next()) 
              {                                //如果可以查询到，则表示合法用户   
                    return true;                                
                }
            } 
            catch (Exception e) 
                    {
                e.printStackTrace();
            } 
            return false;
            }
    }
    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception 
    {
         new SQLAttack2().init();
    }
    
}
