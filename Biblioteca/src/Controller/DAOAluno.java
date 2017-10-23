/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Aluno;
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
public class DAOAluno {
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
    
    public boolean insereAluno(Aluno aluno){
        
        conectar();
        String sql = "INSERT INTO ALUNO(nome, cpf, endereco, telefone) VALUES(?,?,?,?)";
        
        try{
            comando = con.prepareStatement(sql);
            comando.setString(1, aluno.getNome());
            comando.setString(2, aluno.getCpf());
            comando.setString(3, aluno.getEndereco());
            comando.setString(4, aluno.getTelefone());
            comando.execute();
            return true;
        }catch(SQLException e){
            imprimeErro("Erro ao inserir Aluno", e.getMessage());
        }finally{
            fechar();
        }
        return false;
    }
    
    public  ArrayList<Aluno> selecionarTodosRegistros()
    {
        conectar();
        //interface utilizada pra guardar dados vindos de um banco de dados
        ResultSet rs;
        String sql = "SELECT * FROM ALUNO";
        //lista que conterá todas as informações de alunos no banco de dados
        ArrayList<Aluno> listaAlunos = new ArrayList();
        try{
            comando = con.prepareStatement(sql);
            rs = comando.executeQuery();
            while(rs.next())
            {
                Aluno aluno = new Aluno();
                aluno.setNome(rs.getString("NOME"));
                aluno.setCpf(rs.getString("CPF"));
                aluno.setEndereco(rs.getString("ENDERECO"));
                aluno.setTelefone(rs.getString("TELEFONE"));
                listaAlunos.add(aluno);
            }
            fechar();
            return listaAlunos;
        }catch(SQLException e){
            imprimeErro("Erro ao buscar alunos(s)", e.getMessage());
            fechar();
            return null;
        }
            
    }
    
    public boolean removeAluno(String codigo){
        conectar();
        String sql = "DELETE FROM ALUNO WHERE CPF=?";
        try{
            comando = con.prepareStatement(sql);
            comando.setString(1, codigo);
            comando.executeUpdate();
            return true;
        }catch(SQLException e){
            imprimeErro("Erro ao excluir Aluno", e.getMessage());
        }finally{
            fechar();
        }
        return false;
    }
    
    public boolean alteraAluno(Aluno aluno){
        conectar();
         String sql = "UPDATE ALUNO SET NOME = ?, CPF = ?, ENDERECO = ?, TELEFONE= ?"
                 + " WHERE CPF = ?";
         try{
            comando = con.prepareStatement(sql);
            comando.setString(1, aluno.getNome());
            comando.setString(2, aluno.getCpf());
            comando.setString(3, aluno.getEndereco());
            comando.setString(4, aluno.getTelefone());
            comando.setString(5, aluno.getCpf());
            comando.executeUpdate();
            return true;
        }catch(SQLException e){
            imprimeErro("Erro ao atualizar Aluno", e.getMessage());
        }finally{
            fechar();
        }
         return false;
    }
}
