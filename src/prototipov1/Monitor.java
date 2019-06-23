package prototipov1;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Monitor extends Pessoa {
    private String ra;
    
    private List<Disciplina> listaDisciplinas;
    
    public Monitor(String nome, String ra, String eMail) {
        super(nome, eMail);
        this.ra = ra;
    }
    
    public List<Disciplina> getDisciplinas() {
        return Collections.unmodifiableList(listaDisciplinas);
    }
    
    public void listarDisciplinas(){
        listaDisciplinas.forEach((d) -> {
            System.out.println(d.getCodigo() + ": " + d.getNome());
        });
    }
    
    public String getRa(){
        return ra;
    }
    
    public void setRa(String ra){
        this.ra = ra;
    }
}
