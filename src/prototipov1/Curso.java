package prototipov1;

import java.util.Collections;
import java.util.List;

public class Curso {
    private String nome;
    private int codigo;
    private String emailCoordenacao;
    private String turno;
    private String descricao;
    private int creditosIntegralizacao;
    private int limiteCreditosSemestrais;

    private List<Disciplina> listaDisciplinasObrigatorias;
    
    private List<Disciplina> listaDisciplinasEletivas;

    public Curso(String nome, int codigo, String emailCoordenacao, String turno, String descricao,
            int creditosIntegralizacao, int limiteCreditosSemestrais) {
        this.nome = nome;
        this.codigo = codigo;
        this.emailCoordenacao = emailCoordenacao;
        this.turno = turno;
        this.descricao = descricao;
        this.creditosIntegralizacao = creditosIntegralizacao;
        this.limiteCreditosSemestrais = limiteCreditosSemestrais; 
    }

    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }
    
    public void setCodigo(int codigo){
        this.codigo = codigo;
    }

    public String getEmailCoordenacao() {
        return emailCoordenacao;
    }
    
    public void setEmailCoordenacao(String emailCoordenacao){
        this.emailCoordenacao = emailCoordenacao;
    }

    public String getTurno() {
        return turno;
    }
    
    public void setTurno(String turno){
        this.turno = turno;
    }

    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public int getcreditosIntegralizacao() {
        return creditosIntegralizacao;
    }
    
    public void setCreditosIntegralizacao(int creditosIntegralizacao){
        this.creditosIntegralizacao = creditosIntegralizacao;
    }

    public int getLimiteCreditosSemestrais() {
        return limiteCreditosSemestrais;
    }
    
    public void setLimiteCreditosSemestrais(int limiteCreditosSemestrais){
        this.limiteCreditosSemestrais = limiteCreditosSemestrais;
    }
    
    public void listarDisciplinas(List<Disciplina> listaDisciplina){
        listaDisciplina.forEach((d) -> {
            System.out.println(d.getCodigo() + ": " + d.getNome());
        });
    }
    
    public List<Disciplina> getDisciplinas(List<Disciplina> listaDisciplinas){
        return Collections.unmodifiableList(listaDisciplinas);
    }
}
