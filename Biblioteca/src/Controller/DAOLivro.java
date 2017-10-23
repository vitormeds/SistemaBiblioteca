/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Livro;
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
public class DAOLivro {
    
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
    
    public boolean insereLivro(Livro livro){
        
        conectar();
        String sql = "INSERT INTO LIVRO(nome, editora, "
                + "edicao, autor, categoria, quantidade, codigo) VALUES(?,?,?,?,?,?,?)";
        
        try{
            comando = con.prepareStatement(sql);
            comando.setString(1, livro.getNome());
            comando.setString(2, livro.getEditora());
            comando.setString(3, livro.getEdicao());
            comando.setString(4, livro.getAutor());
            comando.setString(5, livro.getCategoria());
            comando.setInt(6, livro.getQuantidade());
            comando.setInt(7, livro.getCodigo());
            comando.execute();
            return true;
        }catch(SQLException e){
            imprimeErro("Erro ao inserir Livro", e.getMessage());
        }finally{
            fechar();
        }
        return false;
    }
    
    public  ArrayList<Livro> selecionarTodosRegistros()
    {
        conectar();
        //interface utilizada pra guardar dados vindos de um banco de dados
        ResultSet rs;
        String sql = "SELECT * FROM LIVRO";
        //lista que conterá todas as informações de livros no banco de dados
        ArrayList<Livro> listaLivros = new ArrayList();
        try{
            comando = con.prepareStatement(sql);
            rs = comando.executeQuery();
            while(rs.next())
            {
                Livro livro = new Livro();
                livro.setNome(rs.getString("NOME"));
                livro.setEditora(rs.getString("EDITORA"));
                livro.setEdicao(rs.getString("EDICAO"));
                livro.setAutor(rs.getString("AUTOR"));
                livro.setCategoria(rs.getString("CATEGORIA"));
                livro.setQuantidade(rs.getInt("QUANTIDADE"));
                livro.setCodigo(rs.getInt("CODIGO"));
                listaLivros.add(livro);
            }
            fechar();
            return listaLivros;
        }catch(SQLException e){
            imprimeErro("Erro ao buscar livros(s)", e.getMessage());
            fechar();
            return null;
        }
            
    }
    
    public boolean removeLivro(int codigo){
        conectar();
        String sql = "DELETE FROM LIVRO WHERE CODIGO=?";
        try{
            comando = con.prepareStatement(sql);
            comando.setInt(1, codigo);
            comando.executeUpdate();
            return true;
        }catch(SQLException e){
            imprimeErro("Erro ao excluir Livro", e.getMessage());
        }finally{
            fechar();
        }
        return false;
    }
    
    public boolean alteraLivro(Livro livro){
        conectar();
         String sql = "UPDATE LIVRO SET NOME = ?, EDITORA = ?, EDICAO = ?, AUTOR= ?, CATEGORIA = ?, QUANTIDADE = ?"
                 + " WHERE CODIGO = ?";
         try{
            comando = con.prepareStatement(sql);
            comando.setString(1, livro.getNome());
            comando.setString(2, livro.getEditora());
            comando.setString(3, livro.getEdicao());
            comando.setString(4, livro.getAutor());
            comando.setString(5, livro.getCategoria());
            comando.setInt(6, livro.getQuantidade());
            comando.setInt(7, livro.getCodigo());
            comando.executeUpdate();
            return true;
        }catch(SQLException e){
            imprimeErro("Erro ao atualizar Livro", e.getMessage());
        }finally{
            fechar();
        }
         return false;
    }
    
      public boolean somaLivro(String codigo){
        conectar();
         String sql = "UPDATE livro SET quantidade=quantidade+1 where codigo=?";
         try{
            comando = con.prepareStatement(sql);
            comando.setString(1, codigo);
            comando.executeUpdate();
            return true;
        }catch(SQLException e){
            imprimeErro("Erro ao atualizar Livro", e.getMessage());
        }finally{
            fechar();
        }
         return false;
    }
}
