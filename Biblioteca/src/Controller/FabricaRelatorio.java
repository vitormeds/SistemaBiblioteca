/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.awt.BorderLayout;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author Vitor
 */
public class FabricaRelatorio {
 
     public static void abreRelatorio(String titulo, InputStream dadosEntrada, 
            Map parametros, Connection con)
    {
        try {
            //classe que representa o relatório gerado com todos seus dados
            JasperPrint relatorio = JasperFillManager.fillReport
                    (dadosEntrada, parametros, con);
            viewRelatorio(titulo, relatorio);
        } catch (JRException ex) {
            Logger.getLogger(FabricaRelatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void viewRelatorio(String titulo, JasperPrint relatorio) 
    {
        //Representa um componente visual embutido para gerar relatórios dentro de um Frame
        JRViewer relatorioVisual = new JRViewer(relatorio);
        JFrame frameRelatorio = new JFrame(titulo);
        frameRelatorio.add(relatorioVisual, BorderLayout.CENTER);
        frameRelatorio.setSize(500,500);
        //permite que o Frame adapte-se e extenda conforme necessidade de tamanho
        //do relatório
        frameRelatorio.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frameRelatorio.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameRelatorio.setVisible(true);
    }
    
}
