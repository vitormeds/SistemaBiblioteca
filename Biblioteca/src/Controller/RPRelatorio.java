/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vitor
 */
public class RPRelatorio {
    
    private String relatorio;
    public void gerarRelatorioTotal (String caminhoRelatorio)
    {
        this.relatorio = caminhoRelatorio;
        InputStream dadosEntrada;
        try {
            dadosEntrada = new FileInputStream(new File(caminhoRelatorio));
            Map parametros = new HashMap();
            FabricaRelatorio.abreRelatorio("Relatório Geral", dadosEntrada, 
                parametros, FabricaConexao.conexao());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RPRelatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
}
