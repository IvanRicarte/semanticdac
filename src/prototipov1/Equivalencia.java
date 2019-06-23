package prototipov1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Equivalencia {

    List<String> listaequivalencia;

    public Equivalencia(File nomedoarquivo) throws IOException {
        listaequivalencia = new ArrayList<>();
        listaequivalencia = Files.readAllLines(nomedoarquivo.toPath());
    }

    public List<String> getEquivalencia() {

        return listaequivalencia;
    }

}
