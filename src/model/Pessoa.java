package model;

/**
 *
 * @author 3152203415
 */
public class Pessoa {

    String nome;
    private String sexo;
    private int idade;
    private float altura;
    private float peso;
    private String tipoSanguineo;

    public String getNome() {
        return nome;
    }

    public String getSexo() {
        return sexo;
    }

    public int getIdade() {
        return idade;
    }

    public float getAltura() {
        return altura;
    }

    public float getPeso() {
        return peso;
    }

    public String getTipoSanguineo() {
        return tipoSanguineo;
    }

    public Pessoa() {

    }
    
    public Pessoa(String nome){
        this.nome = nome;
    }

    public Pessoa(String nome, String sexo, int idade) {
        this.nome = nome;
        this.sexo = sexo;
        this.idade = idade;
    }

    public Pessoa(String nome, String sexo, int idade, float altura, float peso, String tipoSanguineo) {
        this.nome = nome;
        this.sexo = sexo;
        this.idade = idade;
        this.altura = altura;
        this.peso = peso;
        this.tipoSanguineo = tipoSanguineo;
    }

    public void createPessoaPaciente(String nome, String sexo, int idade, float altura, float peso, String tipoSanguineo) {
        this.nome = nome;
        this.sexo = sexo;
        this.idade = idade;
        this.altura = altura;
        this.peso = peso;
        this.tipoSanguineo = tipoSanguineo;
    }

    public void createPessoaMedico(String nome, String sexo, int idade) {
        this.nome = nome;
        this.sexo = sexo;
        this.idade = idade;
    }
    
    @Override
    public String toString() {
        return nome;
    }
}
