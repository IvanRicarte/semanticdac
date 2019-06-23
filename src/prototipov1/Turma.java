package prototipov1;

public class Turma {
    int vagas;
    char letra;
    String horario;
    String professor;
    String monitor;
    String sala;


public Turma(int vagas, char letra, String horario, String professor, String monitor, String sala) {
        this.vagas = vagas;
        this.letra = letra;
        this.horario = horario;
        this.professor = professor;
        this.monitor = monitor;
        this.sala = sala;
    }
    
    public int getVagas() {
        return vagas;
    }
    
    public void setVagas(int vagas){
        this.vagas = vagas;
    }
    
    public char getLetra() {
        return letra;
    }
    
    public void setLetra(char letra){
        this.letra = letra;
    }
    
    public String getHorario() {
        return horario;
    }
    
    public void setHorario(String horario){
        this.horario = horario;
    }

    public String getProfessor() {
        return professor;
    }
    
    public void setProfessor(String professor){
        this.professor = professor;
    }
    
    public String getMonitor() {
        return monitor;
    }
    
    public void setMonitor(String monitor){
        this.monitor = monitor;
    }

    public String getSala() {
        return sala;
    }
    
    public void setSala(String sala){
        this.sala = sala;
    }

}