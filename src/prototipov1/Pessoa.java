package prototipov1;

public class Pessoa {
    String nome;
    String eMail;

    public Pessoa(String nome, String eMail) {
        this.nome = nome;
        this.eMail = eMail;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public String getEmail() {
        return eMail;
    }
    
    public void setEmail(String eMail){
        this.eMail = eMail;
    } 
}
