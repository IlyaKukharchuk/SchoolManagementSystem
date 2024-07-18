package com.example.scmgsys.login;

import com.example.scmgsys.dbUtil.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    Connection connection;

    public LoginModel(){
        try {
            this.connection = DbConnection.getConnection();
        }catch (SQLException ex){
            ex.printStackTrace();;
        }
        if (this.connection == null){
            System.exit(1);
        }
    }
    public boolean isDatabaseConnected(){
        return this.connection != null;
    }

    public boolean isLogin(String username, String password, String opt) throws Exception{

        PreparedStatement prep_stnt = null;
        ResultSet res_set = null;

        String sql = "SELECT * FROM login WHERE username = ? and password = ? and division = ?";
        try {
            prep_stnt = this.connection.prepareStatement(sql);
            prep_stnt.setString(1, username);
            prep_stnt.setString(2, password);
            prep_stnt.setString(3, opt);

            res_set = prep_stnt.executeQuery();



            if(res_set.next()){
                return true;
            }
            return false;
        }catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }

        finally {
            {
                prep_stnt.close();
                res_set.close();
            }
        }
    }
}
