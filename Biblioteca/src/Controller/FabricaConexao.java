/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vitor
 */
public class FabricaConexao {
    public static Connection conexao(){
        Connection con = null;
        try {
            //fazendo a chamada para carregar o drive do MySql
            Class.forName("com.mysql.jdbc.Driver");
//            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            String url = ("jdbc:mysql://127.0.0.1/biblioteca");
            //estabelecendo conexão com o caminho, usuário e senha
            con = DriverManager.getConnection(url, "root", "");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FabricaConexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex) {
            Logger.getLogger(FabricaConexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
}
