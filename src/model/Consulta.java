package model;

/**
 *
 * @author 3152203415
 */
public class Consulta {

    private int id;
    private String dataHora;
    private String obs;

    public int getId() {
        return id;
    }

    public String getDataHora() {
        return dataHora;
    }

    public String getObs() {
        return obs;
    }

    public Consulta() {

    }

    public Consulta(int id, String dataHora) {
        this.id = id;
        this.dataHora = dataHora;
    }

    public Consulta(String dataHora, String obs) {
        this.dataHora = dataHora;
        this.obs = obs;
    }
}
