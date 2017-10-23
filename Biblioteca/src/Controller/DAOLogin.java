/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;



import Model.Login;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Vitor
 */
public class DAOLogin {
    
    private Connection con;
    //Pre-compila a query para o banco de dados
    private PreparedStatement comando;
    
    public void imprimeErro(String msg1, String errorSystem){
        JOptionPane.showMessageDialog(null, msg1, "Erro", JOptionPane.ERROR_MESSAGE, null);
        System.err.println(""+errorSystem);
    }
    
    private void conectar(){
        con = FabricaConexao.conexao();
    }
    
     private void fechar(){
        try{
            comando.close();
            con.close();
        }catch(SQLException e){
            imprimeErro("Erro ao fechar conexão", e.getMessage());
        }
    }
     
    public  ArrayList<Login> selecionarTodosRegistros()
    {
        conectar();
        //interface utilizada pra guardar dados vindos de um banco de dados
        ResultSet rs;
        String sql = "SELECT * FROM USUARIO";
        //lista que conterá todas as informações de logins no banco de dados
        ArrayList<Login> listaLogins = new ArrayList();
        try{
            comando = con.prepareStatement(sql);
            rs = comando.executeQuery();
            while(rs.next())
            {
                Login login = new Login();
                login.setUsuario(rs.getString("NOME"));
                login.setSenha(rs.getString("SENHA"));
                listaLogins.add(login);
            }
            fechar();
            return listaLogins;
        }catch(SQLException e){
            imprimeErro("Erro ao buscar logins(s)", e.getMessage());
            fechar();
            return null;
        }
            
    }
    
    public boolean insereLogin(Login login){
        
        conectar();
        String sql = "INSERT INTO USUARIO(nome, senha) VALUES(?,?)";
        
        try{
            comando = con.prepareStatement(sql);
            comando.setString(1, login.getUsuario());
            comando.setString(2, login.getSenha());
            comando.execute();
            return true;
        }catch(SQLException e){
            imprimeErro("Erro ao inserir Login", e.getMessage());
        }finally{
            fechar();
        }
        return false;
    }
    public boolean removeLogin(String nome){
        conectar();
        String sql = "DELETE FROM USUARIO WHERE NOME=?";
        try{
            comando = con.prepareStatement(sql);
            comando.setString(1, nome);
            comando.executeUpdate();
            return true;
        }catch(SQLException e){
            imprimeErro("Erro ao excluir Login", e.getMessage());
        }finally{
            fechar();
        }
        return false;
    }
    
    public boolean alteraLogin(Login login){
        conectar();
         String sql = "UPDATE USUARIO SET NOME = ?, SENHA = ?"
                 + " WHERE NOME = ?";
         try{
            comando = con.prepareStatement(sql);
            comando.setString(1, login.getUsuario());
            comando.setString(2, login.getSenha());
            comando.setString(3, login.getUsuario());
            comando.executeUpdate();
            return true;
        }catch(SQLException e){
            imprimeErro("Erro ao atualizar Login", e.getMessage());
        }finally{
            fechar();
        }
         return false;
    }
            
}
    

