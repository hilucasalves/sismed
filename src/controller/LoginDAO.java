package controller;

import java.awt.HeadlessException;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author INFOLUCK
 */
public class LoginDAO {

    model.Login mLogin;

    controller.ConnectionFactory cf;
    controller.Util util;

    view.Login vLogin;

    public LoginDAO(view.Login vLogin) {
        this.vLogin = vLogin;
    }

    public void get() {
        cf = new controller.ConnectionFactory();
        util = new controller.Util();
        String usuario = vLogin.getInputUsuario().getText();
        String senha = vLogin.getInputSenha().getText();
        mLogin = new model.Login(usuario, senha);
    }

    public void clearLogin() {
        vLogin.getInputUsuario().setText("");
        vLogin.getInputSenha().setText("");
    }

    public void logar() {
        try {
            get();
            cf.openConnection();
            cf.setPstm(cf.getConn().prepareStatement("SELECT COUNT(id) as 'qtd' FROM login WHERE usuario = ? AND senha = ?"));
            cf.getPstm().setString(1, mLogin.getUsuario());
            cf.getPstm().setString(2, mLogin.getSenha());
            cf.setRs(cf.getPstm().executeQuery());
            cf.getRs().next();

            if (Integer.valueOf(cf.getRs().getString("qtd")).equals(0)) {
                JOptionPane.showMessageDialog(vLogin, "Verifique usuário e senha.");
            } else {
                util.frame(vLogin, new view.menuPrincipal());
            }
            cf.clearResult();
            cf.closeConnection();
        } catch (SQLException | NumberFormatException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
