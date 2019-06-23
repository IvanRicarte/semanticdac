
package prototipov1;

import java.io.IOException;

public class Prototipov1 {


    public static void main(String[] args) throws IOException {
        String curso = "94";
        String anoCatalogo = "2018";
        
        if (args.length > 0) {
            curso = args[0];
            if (args.length > 1) 
                anoCatalogo = args[1];
        }
        String urlBase = "https://www.dac.unicamp.br/sistemas/catalogos/grad/catalogo" +
                anoCatalogo + "/curriculoPleno/cp" +
                curso + ".html";
        Catalogo catalogo = new Catalogo(urlBase, anoCatalogo, curso);
        
            catalogo.separar();
            catalogo.listaTudo(System.out);
            
            
            
        
    }
            
}
