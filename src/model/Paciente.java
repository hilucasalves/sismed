package model;

/**
 *
 * @author 3152203415
 */
public class Paciente extends Pessoa {

    private int id;
    private String cpf;
    private String telefone;

    public int getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public Paciente() {

    }
    
    public Paciente(String cpf){
        this.cpf = cpf;
    }
    
    public Paciente(String cpf, String nome){
        super(nome);
        this.cpf = cpf;
    }

    public Paciente(String cpf, String nome, String sexo, int idade, float altura, float peso, String tipoSanguineo, String telefone) {
        super(nome, sexo, idade, altura, peso, tipoSanguineo);
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public Paciente(int id, String cpf, String nome, String sexo, int idade, float altura, float peso, String tipoSanguineo, String telefone) {
        super(nome, sexo, idade, altura, peso, tipoSanguineo);
        this.id = id;
        this.cpf = cpf;
        this.telefone = telefone;
    }
}
