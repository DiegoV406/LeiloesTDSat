/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement st;
    ResultSet rs;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    
    public int cadastrarProduto(ProdutosDTO produto) {
        int status;
        try {
             
            st = conn.prepareStatement("INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)");
            st.setString(1, produto.getNome());
            
            st.setDouble(2, produto.getValor());

            st.setString(3, produto.getStatus());

            status = st.executeUpdate();
            return status;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar: " + ex.getMessage());
            return ex.getErrorCode();
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        
        return listagem;
    }
    
    
   public boolean conectar() {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/uc11?useSSL=false&allowPublicKeyRetrieval=true",
            "root",
            "Campinas1*"
        );
        return true;
    } catch (ClassNotFoundException | SQLException ex) {
        JOptionPane.showMessageDialog(null, "Erro ao conectar: " + ex.getMessage());
        return false;
    }
}
    
    public void desconectar() {
        try {
            conn.close();
        } catch (SQLException ex) {

        }
    }
        
}

