package prototipov1;

import java.util.List;
import java.util.logging.Level;

public class Disciplina {

    private String codigo;
    private String nome;
    private String url;
    private String ementa;
    private int horasTeo;
    private int horasPrat;
    private int horasLab;
    private int horasOrient;
    private int creditos;
    private String preReq;
    private List<String> preReqs;

    public Disciplina(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
        Log.LOG.log(Level.INFO, "Criando objeto para disciplina {0}", codigo);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmenta() {
        return ementa;
    }

    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }

    @Override
    public String toString() {
        return codigo + ": " + nome;
    }

    public int getHorasTeo() {
        return horasTeo;
    }

    public void setHorasTeo(int horasTeo) {
        this.horasTeo = horasTeo;
    }

    public int getHorasPrat() {
        return horasPrat;
    }

    public void setHorasPrat(int horasPrat) {
        this.horasPrat = horasPrat;
    }

    public int getHorasLab() {
        return horasLab;
    }

    public void setHorasLab(int horasLab) {
        this.horasLab = horasLab;
    }

    public int getHorasOrient() {
        return horasOrient;
    }

    public void setHorasOrient(int horasOrient) {
        this.horasOrient = horasOrient;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public String getPreReq() {
        return preReq;
    }

    public void setPreReq(String preReq) {
        this.preReq = preReq;
    }

    public List<String> getPreReqs() {
        return preReqs;
    }

    public void setPreReqs(List<String> preReqs) {
        this.preReqs = preReqs;
    }

}
