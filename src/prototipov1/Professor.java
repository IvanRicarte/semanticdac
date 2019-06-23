
package prototipov1;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Professor extends Pessoa{
    
    private List<Disciplina> listaDisciplinasLecionadas;

    public Professor(String nome, String eMail) {
        super(nome, eMail);
    }
    
    public List<Disciplina> getDisciplinas() {
        return Collections.unmodifiableList(listaDisciplinasLecionadas);
    }
    
    public void listarDisciplinas(){
        listaDisciplinasLecionadas.forEach((d) -> {
            System.out.println(d.getCodigo() + ": " + d.getNome());
        });
    }


}
