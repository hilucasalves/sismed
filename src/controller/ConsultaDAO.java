package controller;

import java.awt.HeadlessException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 *
 * @author INFOLUCK
 */
public class ConsultaDAO {

    ConnectionFactory cf = new ConnectionFactory();

    model.Consulta mConsulta;
    model.Paciente mPaciente;
    model.Medico mMedico;
    model.Especialidade mEspecialidade;

    view.Consulta vConsulta;

    public ConsultaDAO(view.Consulta vConsulta) {
        this.vConsulta = vConsulta;
    }

    public void get() {
        String paciente = mPaciente.getCpf();

        Date dh = (Date) vConsulta.getInputData().getModel().getValue();
        SimpleDateFormat d = new SimpleDateFormat("dd/MM/YYYY HH:mm");
        String dataHora = d.format(dh);
        long crm = ((model.Medico) vConsulta.getComboMedico().getSelectedItem()).getCrm();
        String obs = vConsulta.getInputObs().getText();

        mConsulta = new model.Consulta(dataHora, obs);
        mMedico = new model.Medico(crm);
        mPaciente = new model.Paciente(paciente);
    }

    public void clean() {
        vConsulta.getInputPaciente().setText("");
        vConsulta.getInputObs().setText("");
    }

    public void validaPaciente(DocumentEvent documentEvent) {
        Runnable dovalidaPaciente;
        dovalidaPaciente = new Runnable() {
            @Override
            public void run() {
                Document source = documentEvent.getDocument();
                try {
                    String cpf = source.getText(0, source.getLength());
                    if (cpf.length() == 11 && soContemNumeros(cpf)) {
                        cf.openConnection();
                        String sql = "SELECT id, cpf, nome from paciente WHERE cpf LIKE ?";
                        cf.setPstm(cf.getConn().prepareStatement(sql));
                        cf.getPstm().setString(1, cpf + "%");
                        cf.setRs(cf.getPstm().executeQuery());
                        if (cf.getRs().next()) {
                            mPaciente = new model.Paciente(cpf, cf.getRs().getString("nome"));
                            source.insertString(source.getLength(), cf.getRs().getString("nome"), null);
                            source.remove(0, cpf.length());
                            cf.clearResult();
                            cf.closeConnection();
                        } else {
                            JOptionPane.showMessageDialog(null, "Não foram encontrados pacientes com este CPF.");
                        }

                    }
                } catch (SQLException | HeadlessException | BadLocationException | ArrayIndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        };
        SwingUtilities.invokeLater(dovalidaPaciente);
    }

    public static boolean soContemNumeros(String texto) {
        if (texto == null) {
            return false;
        }
        for (char letra : texto.toCharArray()) {
            if (letra < '0' || letra > '9') {
                return false;
            }
        }
        return true;
    }

    public void add() {
        try {
            get();
            DefaultTableModel m = (DefaultTableModel) vConsulta.getTabela().getModel();
            cf.openConnection();
            cf.setPstm(cf.getConn().prepareStatement("INSERT INTO consulta (dataHora, crmMedico, idPaciente, obs) VALUES (?, ?, ?, ?)"));
            cf.getPstm().setString(1, mConsulta.getDataHora());
            cf.getPstm().setLong(2, mMedico.getCrm());
            cf.getPstm().setString(3, mPaciente.getCpf());
            cf.getPstm().setString(4, mConsulta.getObs());
            cf.getPstm().executeUpdate();
            cf.closeConnection();
            JOptionPane.showMessageDialog(null, "Consulta adicionada");
            tabela();
            clean();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    public void tabela() {
        try {
            cf.openConnection();
            String sql = "SELECT c.id as 'idConsulta', c.dataHora as 'dataHora', c.crmMedico as 'crmMedico', m.nome as 'nomeMedico', e.id as 'idEspecialidade', e.descricao as 'descricaoEspecialidade', c.idPaciente 'cpfPaciente', p.nome as 'nomePaciente', c.obs as 'obs' FROM consulta c JOIN paciente p JOIN medico m JOIN especialidade e ON p.cpf = c.idPaciente AND m.crm = c.crmMedico AND m.idEspecialidade = e.id";
            cf.setPstm(cf.getConn().prepareStatement(sql));
            cf.setRs(cf.getPstm().executeQuery());
            DefaultTableModel m = (DefaultTableModel) vConsulta.getTabela().getModel();
            m.setNumRows(0);
            while (cf.getRs().next()) {
                mPaciente = new model.Paciente(cf.getRs().getString("cpfPaciente"), cf.getRs().getString("nomePaciente"));
                mMedico = new model.Medico(cf.getRs().getLong("crmMedico"), cf.getRs().getString("nomeMedico"));
                mEspecialidade = new model.Especialidade(cf.getRs().getInt("idEspecialidade"), cf.getRs().getString("descricaoEspecialidade"));

                m.addRow(new Object[]{cf.getRs().getInt("idConsulta"), mPaciente, cf.getRs().getString("dataHora"), mMedico, mEspecialidade});
            }
            cf.clearResult();
            cf.closeConnection();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vConsulta, ex.getMessage());
        }
    }

}
