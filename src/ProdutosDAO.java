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
import java.util.List;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement st;
    ResultSet rs;
    
    
    
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
    
    public List<ProdutosDTO> listarProdutos(){
        String sql = "SELECT * FROM produtos ";

        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            List<ProdutosDTO> listaProduto = new ArrayList<>();

            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));

                listaProduto.add(produto);
            }
            return listaProduto;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao tentar listar os produtos");
            return new ArrayList<>();
        }
        
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
      
    public void venderProduto(int idProduto, String novoStatus) {
    try {
        PreparedStatement stmt = conn.prepareStatement(
            "UPDATE produtos SET status = ? WHERE id = ?"
        );
        stmt.setString(1, novoStatus);
        stmt.setInt(2, idProduto);

        int linhasAfetadas = stmt.executeUpdate();
        stmt.close();

        if (linhasAfetadas == 0) {
            JOptionPane.showMessageDialog(null, 
                "Nenhum produto foi atualizado. Verifique o ID informado.");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao atualizar estoque: " + e.getMessage());
        e.printStackTrace();
    }
}
    
    public List<ProdutosDTO> listarPorStatus(){
        String sql = "SELECT * FROM produtos WHERE status = \"Vendido\" ";

        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            List<ProdutosDTO> listaProduto = new ArrayList<>();

            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));

                listaProduto.add(produto);
            }
            return listaProduto;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao tentar listar os produtos");
            return new ArrayList<>();
        }
        
    }
    
}

