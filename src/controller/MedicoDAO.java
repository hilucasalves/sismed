package controller;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 3152203415
 */
public class MedicoDAO {

    ConnectionFactory cf = new ConnectionFactory();

    model.Medico mMedico;
    view.Medico vMedico;
    
    public MedicoDAO(){
    
    }
    
    public MedicoDAO(view.Medico vMedico) {
        this.vMedico = vMedico;
    }

    public void clean() {
        vMedico.getInputCRM().setText("");
        vMedico.getComboEspecialidade().setSelectedIndex(0);
        vMedico.getInputNome().setText("");
        vMedico.getComboSexo().setSelectedIndex(0);
        vMedico.getInputIdade().setText("");
        vMedico.getInputTelefone().setText("");
    }

    public void get() {
        long crm = Long.parseLong(vMedico.getInputCRM().getText());
        int idEspecialidade = ((model.Especialidade) vMedico.getComboEspecialidade().getSelectedItem()).getId();
        String nome = vMedico.getInputNome().getText();
        String sexo = (String) vMedico.getComboSexo().getSelectedItem();
        int idade = Integer.parseInt(vMedico.getInputIdade().getText());
        String telefone = vMedico.getInputTelefone().getText();

        mMedico = new model.Medico(crm, idEspecialidade, nome, sexo, idade, telefone);
    }

    public void getTabela() {
        vMedico.getInputCRM().setText(vMedico.getTabela().getValueAt(vMedico.getTabela().getSelectedRow(), 0).toString());
        vMedico.getComboEspecialidade().getModel().setSelectedItem(((model.Especialidade) vMedico.getTabela().getValueAt(vMedico.getTabela().getSelectedRow(), 1)));
        vMedico.getInputNome().setText((String) vMedico.getTabela().getValueAt(vMedico.getTabela().getSelectedRow(), 2));
        vMedico.getComboSexo().setSelectedItem(vMedico.getTabela().getValueAt(vMedico.getTabela().getSelectedRow(), 3));
        vMedico.getInputIdade().setText(vMedico.getTabela().getValueAt(vMedico.getTabela().getSelectedRow(), 4).toString());
        vMedico.getInputTelefone().setText(vMedico.getTabela().getValueAt(vMedico.getTabela().getSelectedRow(), 5).toString());
    }

    public void add() {
        try {
            get();
            cf.openConnection();
            cf.setPstm(cf.getConn().prepareStatement("INSERT INTO medico (crm, idEspecialidade, nome, sexo, idade, telefone) VALUES (?, ?, ?, ?, ?, ?)"));
            cf.getPstm().setLong(1, mMedico.getCrm());
            cf.getPstm().setInt(2, mMedico.getidEspecialidade());
            cf.getPstm().setString(3, mMedico.getNome());
            cf.getPstm().setString(4, mMedico.getSexo());
            cf.getPstm().setInt(5, mMedico.getIdade());
            cf.getPstm().setString(6, mMedico.getTelefone());
            cf.getPstm().executeUpdate();
            cf.closeConnection();
            tabela();
            clean();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void del() {
        try {
            cf.openConnection();
            cf.setPstm(cf.getConn().prepareStatement("DELETE FROM medico WHERE crm = ?"));
            cf.getPstm().setInt(1, (int) vMedico.getTabela().getValueAt(vMedico.getTabela().getSelectedRow(), 0));
            cf.getPstm().executeUpdate();
            cf.closeConnection();
            JOptionPane.showMessageDialog(vMedico, "Médico excluso com sucesso");
            tabela();
            clean();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void push() {
        try {
            get();
            DefaultTableModel m = (DefaultTableModel) vMedico.getTabela().getModel();
            cf.openConnection();
            cf.setPstm(cf.getConn().prepareStatement("UPDATE medico SET idEspecialidade = ?, nome = ?, sexo = ?, idade = ?, telefone = ? WHERE crm = ?"));
            cf.getPstm().setInt(1, mMedico.getidEspecialidade());
            cf.getPstm().setString(2, mMedico.getNome());
            cf.getPstm().setString(3, mMedico.getSexo());
            cf.getPstm().setInt(4, mMedico.getIdade());
            cf.getPstm().setString(5, mMedico.getTelefone());
            cf.getPstm().setInt(6, (int) vMedico.getTabela().getValueAt(vMedico.getTabela().getSelectedRow(), 0));
            cf.getPstm().executeUpdate();
            cf.closeConnection();
            JOptionPane.showMessageDialog(null, "Informações atualizadas.");
            tabela();
            clean();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void tabela() {
        try {
            cf.openConnection();
            String sql = "SELECT crm, e.descricao as 'descricao', idEspecialidade, nome, sexo, idade, telefone FROM medico m JOIN especialidade e ON m.idEspecialidade = e.id";
            cf.setPstm(cf.getConn().prepareStatement(sql));
            cf.setRs(cf.getPstm().executeQuery());
            DefaultTableModel m = (DefaultTableModel) vMedico.getTabela().getModel();
            m.setNumRows(0);
            while (cf.getRs().next()) {
                model.Especialidade especialidade = new model.Especialidade(cf.getRs().getInt("idEspecialidade"), cf.getRs().getString("descricao"));

                m.addRow(new Object[]{cf.getRs().getInt("crm"), especialidade, cf.getRs().getString("nome"), cf.getRs().getString("sexo"), cf.getRs().getInt("idade"), cf.getRs().getString("telefone")});
            }
            cf.clearResult();
            cf.closeConnection();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vMedico, ex.getMessage());
        }
    }
    
        public void combobox(javax.swing.JComboBox combo, int itemDinamico) {
        try {
            cf.openConnection();
            cf.setPstm(cf.getConn().prepareStatement("SELECT crm, nome FROM medico WHERE idEspecialidade = ?"));
            cf.getPstm().setInt(1, itemDinamico);
            cf.setRs(cf.getPstm().executeQuery());
            combo.removeAllItems();
            while (cf.getRs().next()) {
                combo.addItem(new model.Medico(cf.getRs().getInt("crm"), cf.getRs().getString("nome")));
            }
            cf.clearResult();
            cf.closeConnection();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }
}
