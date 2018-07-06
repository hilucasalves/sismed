package controller;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author INFOLUCK
 */
public class PacienteDAO {

    controller.ConnectionFactory cf = new controller.ConnectionFactory();

    model.Paciente mPaciente;

    view.Paciente vPaciente;
    
    public PacienteDAO(){

    }
    
    public PacienteDAO(view.Paciente vPaciente) {
        this.vPaciente = vPaciente;
    }

    public void clean() {
        vPaciente.getInputNome().setText("");
        vPaciente.getInputCpf().setText("");
        vPaciente.getComboSexo().setSelectedIndex(0);
        vPaciente.getInputIdade().setText("");
        vPaciente.getInputAltura().setText("");
        vPaciente.getInputPeso().setText("");
        vPaciente.getComboTipoSanguineo().setSelectedIndex(0);
        vPaciente.getInputTelefone().setText("");
    }

    public void get() {
        String cpf = vPaciente.getInputCpf().getText();
        String nome = vPaciente.getInputNome().getText();
        String sexo = vPaciente.getComboSexo().getSelectedItem().toString();
        int idade = Integer.parseInt(vPaciente.getInputIdade().getText());
        float altura = Float.parseFloat(vPaciente.getInputAltura().getText().replace(",", "."));
        float peso = Float.parseFloat(vPaciente.getInputPeso().getText().replace(",", "."));
        String tipoSanguineo = vPaciente.getComboTipoSanguineo().getSelectedItem().toString();
        String telefone = vPaciente.getInputTelefone().getText();
        mPaciente = new model.Paciente(cpf, nome, sexo, idade, altura, peso, tipoSanguineo, telefone);
    }

    public void getTabela() {
        vPaciente.getInputCpf().setText((String) vPaciente.getTabela().getValueAt(vPaciente.getTabela().getSelectedRow(), 1));
        vPaciente.getInputNome().setText((String) vPaciente.getTabela().getValueAt(vPaciente.getTabela().getSelectedRow(), 2));
        vPaciente.getComboSexo().setSelectedItem(vPaciente.getTabela().getValueAt(vPaciente.getTabela().getSelectedRow(), 3));
        vPaciente.getInputIdade().setText(vPaciente.getTabela().getValueAt(vPaciente.getTabela().getSelectedRow(), 4).toString());
        vPaciente.getInputAltura().setText(vPaciente.getTabela().getValueAt(vPaciente.getTabela().getSelectedRow(), 5).toString());
        vPaciente.getInputPeso().setText(vPaciente.getTabela().getValueAt(vPaciente.getTabela().getSelectedRow(), 6).toString());
        vPaciente.getComboTipoSanguineo().setSelectedItem(vPaciente.getTabela().getValueAt(vPaciente.getTabela().getSelectedRow(), 7).toString());
        vPaciente.getInputTelefone().setText(vPaciente.getTabela().getValueAt(vPaciente.getTabela().getSelectedRow(), 8).toString());
    }

    public void add() {
        try {
            get();
            cf.openConnection();
            cf.setPstm(cf.getConn().prepareStatement("INSERT INTO paciente (cpf, nome, sexo, idade, altura, peso, tipoSanguineo, telefone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"));
            cf.getPstm().setString(1, mPaciente.getCpf());
            cf.getPstm().setString(2, mPaciente.getNome());
            cf.getPstm().setString(3, mPaciente.getSexo());
            cf.getPstm().setInt(4, mPaciente.getIdade());
            cf.getPstm().setDouble(5, mPaciente.getAltura());
            cf.getPstm().setDouble(6, mPaciente.getPeso());
            cf.getPstm().setString(7, mPaciente.getTipoSanguineo());
            cf.getPstm().setString(8, mPaciente.getTelefone());
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
            cf.setPstm(cf.getConn().prepareStatement("DELETE FROM paciente WHERE id = ?"));
            cf.getPstm().setInt(1, (int) vPaciente.getTabela().getValueAt(vPaciente.getTabela().getSelectedRow(), 0));
            cf.getPstm().executeUpdate();
            cf.closeConnection();
            JOptionPane.showMessageDialog(vPaciente, "Paciente excluso com sucesso");
            tabela();
            clean();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void push() {
        try {
            get();
            DefaultTableModel m = (DefaultTableModel) vPaciente.getTabela().getModel();
            cf.openConnection();
            cf.setPstm(cf.getConn().prepareStatement("UPDATE paciente SET cpf = ?, nome = ?, sexo = ?, idade = ?, altura = ?, peso = ?, tipoSanguineo = ?, telefone = ? WHERE id = ?"));
            cf.getPstm().setString(1, mPaciente.getCpf());
            cf.getPstm().setString(2, mPaciente.getNome());
            cf.getPstm().setString(3, mPaciente.getSexo());
            cf.getPstm().setInt(4, mPaciente.getIdade());
            cf.getPstm().setDouble(5, mPaciente.getAltura());
            cf.getPstm().setDouble(6, mPaciente.getPeso());
            cf.getPstm().setString(7, mPaciente.getTipoSanguineo());
            cf.getPstm().setString(8, mPaciente.getTelefone());
            cf.getPstm().setInt(9, (int) vPaciente.getTabela().getValueAt(vPaciente.getTabela().getSelectedRow(), 0));
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
            String sql = "SELECT id, cpf, nome, sexo, idade, altura, peso, tipoSanguineo, telefone FROM paciente";
            cf.setPstm(cf.getConn().prepareStatement(sql));
            cf.setRs(cf.getPstm().executeQuery());
            DefaultTableModel m = (DefaultTableModel) vPaciente.getTabela().getModel();
            m.setNumRows(0);
            while (cf.getRs().next()) {
                m.addRow(new Object[]{cf.getRs().getInt("id"), cf.getRs().getString("cpf"), cf.getRs().getString("nome"), cf.getRs().getString("sexo"), cf.getRs().getInt("idade"), cf.getRs().getDouble("altura"), cf.getRs().getDouble("peso"), cf.getRs().getString("tipoSanguineo"), cf.getRs().getString("telefone")});
            }
        } catch (SQLException sqlException) {
            sqlException.getMessage();
        }
    }
}
