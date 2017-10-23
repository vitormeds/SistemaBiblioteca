/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Editora;
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
public class DAOEditora {
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
    
    public boolean insereEditora(Editora editora){
        
        conectar();
        String sql = "INSERT INTO EDITORA(nome, endereco, "
                + "telefone, cnpj, email) VALUES(?,?,?,?,?)";
        
        try{
            comando = con.prepareStatement(sql);
            comando.setString(1, editora.getNome());
            comando.setString(2, editora.getEndereco());
            comando.setString(3, editora.getTelefone());
            comando.setString(4, editora.getCnpj());
            comando.setString(5, editora.getEmail());
            comando.execute();
            return true;
        }catch(SQLException e){
            imprimeErro("Erro ao inserir Editora", e.getMessage());
        }finally{
            fechar();
        }
        return false;
    }
    
    public  ArrayList<Editora> selecionarTodosRegistros()
    {
        conectar();
        //interface utilizada pra guardar dados vindos de um banco de dados
        ResultSet rs;
        String sql = "SELECT * FROM EDITORA";
        //lista que conterá todas as informações de editoras no banco de dados
        ArrayList<Editora> listaEditoras = new ArrayList();
        try{
            comando = con.prepareStatement(sql);
            rs = comando.executeQuery();
            while(rs.next())
            {
                Editora editora = new Editora();
                editora.setNome(rs.getString("NOME"));
                editora.setEndereco(rs.getString("ENDERECO"));
                editora.setTelefone(rs.getString("TELEFONE"));
                editora.setCnpj(rs.getString("CNPJ"));
                editora.setEmail(rs.getString("EMAIL"));
                listaEditoras.add(editora);
            }
            fechar();
            return listaEditoras;
        }catch(SQLException e){
            imprimeErro("Erro ao buscar editoras(s)", e.getMessage());
            fechar();
            return null;
        }
            
    }
    
    public boolean removeEditora(String cnpj){
        conectar();
        String sql = "DELETE FROM EDITORA WHERE CNPJ=?";
        try{
            comando = con.prepareStatement(sql);
            comando.setString(1, cnpj);
            comando.executeUpdate();
            return true;
        }catch(SQLException e){
            imprimeErro("Erro ao excluir Editora", e.getMessage());
        }finally{
            fechar();
        }
        return false;
    }
    
    public boolean alteraEditora(Editora editora){
        conectar();
         String sql = "UPDATE EDITORA SET NOME = ?, ENDERECO = ?, TELEFONE = ?, CNPJ = ?, "
                 + "EMAIL = ?"
                 + " WHERE CNPJ = ?";
         try{
            comando = con.prepareStatement(sql);
            comando.setString(1, editora.getNome());
            comando.setString(2, editora.getEndereco());
            comando.setString(3, editora.getTelefone());
            comando.setString(4, editora.getCnpj());
            comando.setString(5, editora.getEmail());
            comando.setString(6, editora.getCnpj());
            comando.executeUpdate();
            return true;
        }catch(SQLException e){
            imprimeErro("Erro ao atualizar Editora", e.getMessage());
        }finally{
            fechar();
        }
         return false;
    }
}
