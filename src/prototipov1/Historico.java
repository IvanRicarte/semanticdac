package prototipov1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.FileChooser;

public class Historico {

    private String ra;
    private String rg;
    private String nome;
    private int curso;
    private int catalogo;
    public List<String> obrigatorias;
    private List<String> eletivas;
    private List<String> extracurriculares;

    public Historico() {
        obrigatorias = new ArrayList<>();
        eletivas = new ArrayList<>();
        extracurriculares = new ArrayList<>();
    }

    public void extract(Path from) throws IOException {
        byte[] lines = Files.readAllBytes(from);
        String allFile = new String(lines);

        String raField = "mico: ";
        int posRa = allFile.indexOf(raField) + raField.length();
        ra = allFile.substring(posRa, posRa + 6);

        String rgField = "RG.: ";
        int posRg = allFile.indexOf(rgField) + rgField.length();
        rg = allFile.substring(posRg, allFile.indexOf('\n', posRg));

        String nomeField = "Nome..............: ";
        int posNome = allFile.indexOf(nomeField) + nomeField.length();
        nome = allFile.substring(posNome, allFile.indexOf('\n', posNome)).trim();

        String cursoField = "Curso: ";
        int posCurso = allFile.indexOf(cursoField) + cursoField.length();
        curso = Integer.parseInt(allFile.substring(posCurso, posCurso + 3).trim());

        String catalogoField = "logo: ";
        int posCatalogo = allFile.indexOf(catalogoField) + catalogoField.length();
        catalogo = Integer.parseInt(allFile.substring(posCatalogo, posCatalogo + 4));

        String comecoDisciplinas = "rico atual:";
        int posDisciplina = allFile.indexOf(comecoDisciplinas);
        posDisciplina = allFile.indexOf('\n', posDisciplina) + 1;
        String sigla = allFile.substring(posDisciplina, posDisciplina + 5).trim();
        int coluna = 0;
        while (!sigla.isEmpty()) {
            String codigo = allFile.substring(posDisciplina + 13, posDisciplina + 16).trim();
            if (codigo.equals("*")) {
                eletivas.add(sigla);
            } else if (codigo.equals("+")) {
                obrigatorias.add(sigla);
            } else if (codigo.equals("X")) {
                extracurriculares.add(sigla);
            }
            if (coluna == 0) {
                posDisciplina = posDisciplina + 40;
                coluna = 1;
            }
            else {
                posDisciplina = allFile.indexOf('\n', posDisciplina) + 1;
                coluna = 0;
            }
            sigla = allFile.substring(posDisciplina, posDisciplina + 5).trim();
        }
        //System.out.println(obrigatorias);
    }

    @Override
    public String toString() {
        String s = "Historico{ ";
        s = s + "ra=" + ra + ", rg=" + rg + ", nome=" + nome + ", curso=" + curso + ", catalogo=" + catalogo + "\n";
        s = s + "\tObrigatorias: ";
        for (String d : obrigatorias) 
            s = s + d + "  ";
        s = s + "\n\tEletivas: ";
        for (String d : eletivas)
            s = s + d + "  ";
        s = s + "\n\tExtracurriculares: ";
        for (String d : extracurriculares)
            s = s + d + "  ";       
        s = s + "\n}";
        return s;
    }
    
    public String StringObrigatorias() {
        String s = "";
        for (String d : obrigatorias) 
            s = s + d + "  ";
        return s;
    }

    public void extract(FileChooser file) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
