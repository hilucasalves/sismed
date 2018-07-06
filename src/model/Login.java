package model;

/**
 *
 * @author INFOLUCK
 */
public class Login {

    private int id;
    private String usuario;
    private String senha;

    public int getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

    public Login() {

    }

    public Login(int id, String usuario, String senha) {
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
    }

    public Login(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }
}
