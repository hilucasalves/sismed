package model;

/**
 *
 * @author 3152203415
 */
public class Especialidade {

    private int id;
    private String descricao;

    public int getId() {
        return id;
    }

    public String getEspecialidade() {
        return descricao;
    }

    public Especialidade() {

    }

    public Especialidade(String descricao) {
        this.descricao = descricao;
    }

    public Especialidade(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }

}
