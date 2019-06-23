package prototipov1;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Aluno extends Pessoa {
    
    private String usuario;
    private String senha;
    private String ra;
    
    private List<Disciplina> disciplinasFaltantes;

    private List<Disciplina> disciplinasConcluidas;
    
    private List<Disciplina> disciplinasAtuais;

    public Aluno(String nome, String eMail, String usuario, String senha, String ra) {
        super (nome, eMail);
        this.usuario = usuario;
        this.ra = ra;
        this.senha = senha;
    }

    public String getUsuario() {
        return usuario;
    }
   
    public void setUsuario(String usuario){
        this.usuario = usuario;
    }
    
    public String getSenha() {
        return senha;
    }
    
    public void setSenha(String senha){
        this.senha = senha;
    }
    
    public String getRa(){
        return ra;
    }
    
    public void setRa(String ra){
        this.ra = ra;
    }
    
    public void listarDisciplinas(List<Disciplina> listaDisciplinas){
        listaDisciplinas.forEach((d) -> {
            System.out.println(d.getCodigo() + ": " + d.getNome());
        });
    }
    
    public List<Disciplina> getDisciplinas(List<Disciplina> listaDisciplina) {
        return Collections.unmodifiableList(listaDisciplina);
    }
}
