package model;

/**
 *
 * @author 3152203415
 */
public class Medico extends Pessoa {

    private long crm;
    private int idEspecialidade;
    private String telefone;

    public long getCrm() {
        return crm;
    }

    public int getidEspecialidade() {
        return idEspecialidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public Medico() {

    }
    
    public Medico(long crm){
        this.crm = crm;
    }
    
    public Medico(long crm, String nome){
        this.crm = crm;
        this.nome = nome;
    }

    public Medico(long crm, int idEspecialidade, String nome, String sexo, int idade, String telefone) {
        super(nome, sexo, idade);
        this.crm = crm;
        this.idEspecialidade = idEspecialidade;
        this.telefone = telefone;
    }
}
