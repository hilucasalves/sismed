package controller;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author INFOLUCK
 */
public class EspecialidadeDAO {

    controller.ConnectionFactory cf = new controller.ConnectionFactory();

    model.Especialidade mEspecialidade;
    view.Especialidade vEspecialidade;

    public EspecialidadeDAO(){
    
    }
    
    public EspecialidadeDAO(view.Especialidade vEspecialidade) {
        this.vEspecialidade = vEspecialidade;
    }

    public void clean() {
        vEspecialidade.getInputDescricao().setText("");
    }

    public void get() {
        mEspecialidade = new model.Especialidade(vEspecialidade.getInputDescricao().getText());
    }

    public void getTabela() {
        vEspecialidade.getInputDescricao().setText((String) vEspecialidade.getTabela().getValueAt(vEspecialidade.getTabela().getSelectedRow(), 1));
    }

    public void add() {
        try {
            get();
            cf.openConnection();
            cf.setPstm(cf.getConn().prepareStatement("INSERT INTO especialidade (descricao) VALUES (?)"));
            cf.getPstm().setString(1, mEspecialidade.getEspecialidade());
            cf.getPstm().executeUpdate();
            cf.closeConnection();
            tabela();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void del() {
        try {
            cf.openConnection();
            cf.setPstm(cf.getConn().prepareStatement("DELETE FROM especialidade WHERE id = ?"));
            cf.getPstm().setInt(1, (int) vEspecialidade.getTabela().getValueAt(vEspecialidade.getTabela().getSelectedRow(), 0));
            cf.getPstm().executeUpdate();
            cf.closeConnection();
            JOptionPane.showMessageDialog(vEspecialidade, "Especialidade exclusa com sucesso");
            tabela();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void push() {
        try {
            DefaultTableModel m = (DefaultTableModel) vEspecialidade.getTabela().getModel();
            if (vEspecialidade.getInputDescricao().getText() != m.getValueAt(vEspecialidade.getTabela().getSelectedRow(), 2)) {
                cf.openConnection();
                cf.setPstm(cf.getConn().prepareStatement("UPDATE especialidade SET descricao = ? WHERE id = ?"));
                cf.getPstm().setString(1, vEspecialidade.getInputDescricao().getText());
                cf.getPstm().setInt(2, (int) m.getValueAt(vEspecialidade.getTabela().getSelectedRow(), 0));
                cf.getPstm().executeUpdate();
                cf.closeConnection();
                JOptionPane.showMessageDialog(null, "Informações atualizadas.");
                vEspecialidade.getInputPesquisa().setText(vEspecialidade.getInputDescricao().getText());
                vEspecialidade.getInputDescricao().setText(null);
                tabela();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void tabela() {
        try {
            cf.openConnection();
            String sql = "SELECT e.id, e.descricao, COUNT(m.crm) as 'medicos' from especialidade e LEFT JOIN medico m ON m.idEspecialidade = e.id WHERE 1";
            if (!vEspecialidade.getInputPesquisa().getText().isEmpty()) {
                sql += " AND descricao LIKE '" + vEspecialidade.getInputPesquisa().getText() + "%'";
            }
            sql += " GROUP BY e.id";
            cf.setPstm(cf.getConn().prepareStatement(sql));
            cf.setRs(cf.getPstm().executeQuery());
            DefaultTableModel m = (DefaultTableModel) vEspecialidade.getTabela().getModel();
            m.setNumRows(0);
            while (cf.getRs().next()) {
                m.addRow(new Object[]{cf.getRs().getInt("id"), cf.getRs().getString("descricao"), cf.getRs().getString("medicos")});
            }
        } catch (SQLException sqlException) {
            sqlException.getMessage();
        }
    }

    public void comboBox(javax.swing.JComboBox combo) {
        try {
            cf.openConnection();
            cf.setPstm(cf.getConn().prepareStatement("SELECT id, descricao FROM especialidade"));
            cf.setRs(cf.getPstm().executeQuery());
            combo.removeAllItems();

            while (cf.getRs().next()) {
                combo.addItem(new model.Especialidade(cf.getRs().getInt("id"), cf.getRs().getString("descricao")));
            }
            cf.clearResult();
            cf.closeConnection();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }
}
