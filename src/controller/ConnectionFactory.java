package controller;

import java.awt.HeadlessException;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author 3152203415
 */
public class ConnectionFactory {

    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;
    private String arquivo;

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public PreparedStatement getPstm() {
        return pstm;
    }

    public void setPstm(PreparedStatement pstm) {
        this.pstm = pstm;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public void openConnection() {
        try {
            setArquivo("clinica.db");
            File db = new File(getArquivo());
            boolean arquivoExiste;
            arquivoExiste = db.exists();
            if (arquivoExiste == false) {
                setArquivo("../clinica.db");
            }
            Class.forName("org.sqlite.JDBC");
            setConn(DriverManager.getConnection("jdbc:sqlite:" + getArquivo()));
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void closeConnection() {
        try {
            getPstm().close();
            getConn().close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void clearResult() {
        try {
            getRs().close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void reset() {
        try {
            int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja limpar os dados e registros armazenados ?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirma == 0) {
                JOptionPane.showMessageDialog(null, "O aplivativo se encerrará para que suas alterações tenham efeito.");
                openConnection();
                String tables[] = {"login", "paciente", "especialidade", "medico", "consulta", "sqlite_sequence"};
                for (String table : tables) {
                    setPstm(getConn().prepareStatement("DELETE FROM " + table + ";\n" + "DELETE FROM SQLITE_SEQUENCE WHERE name='" + table + "';"));
                    getPstm().executeUpdate();
                }
                setPstm(getConn().prepareStatement("INSERT INTO login (usuario, senha) VALUES ('sismed', 'admin')"));
                getPstm().executeUpdate();
                closeConnection();
                System.exit(0);
            }
        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
