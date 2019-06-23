package prototipov1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MapeadorDisciplinasUnicamp {

    // Atributos da classe
    private Document doc;
    private Document doc2;

    private Elements listaSiglas;
    private Elements listaDisciplinas;

    private final Map<String, Disciplina> mapaDisciplina = new HashMap<>();
    private final Map<String, String> mapaPreReq = new LinkedHashMap<>();

    // Método da classe
    public Map<String, Disciplina> mapearDisciplinas(String anoCatalogo) throws IOException {

        doc = Jsoup.connect("https://www.dac.unicamp.br/sistemas/catalogos/grad/catalogo"
                + anoCatalogo
                + "/TiposDisciplinas.html").get();

        listaSiglas = doc.getElementsByClass("div10b");
        for (Element sigla : listaSiglas) {
            // Conecta com cada página na página de siglas, caso uma sigla tenha apenas uma letra, o método faz a verificação
            if (sigla.text().length() == 1) {
                doc2 = Jsoup.connect("https://www.dac.unicamp.br/sistemas/catalogos/grad/catalogo"
                        + anoCatalogo
                        + "/disciplinas/tipo"
                        + sigla.text()
                        + "%20.html").get();
            } else {
                doc2 = Jsoup.connect("https://www.dac.unicamp.br/sistemas/catalogos/grad/catalogo"
                        + anoCatalogo
                        + "/disciplinas/tipo"
                        + sigla.text()
                        + ".html").get();
            }

            listaDisciplinas = doc2.getElementsByClass("div10b");
            for (Element disciplina : listaDisciplinas) {
                Disciplina disciplinaValor = new Disciplina(disciplina.text(), "nome da disciplina de código " + disciplina.text());
                mapaDisciplina.put(disciplina.text(), disciplinaValor);
            }
        }

        return mapaDisciplina;
    }

    // Método de teste para ver se os valores estão sendo armazenados corretamente
    public void printMap(Map<String, Disciplina> mapa) {
        for (String chave : mapa.keySet()) {
            System.out.println(mapa.get(chave));
        }
    }

    public Map<String, String> listarPreRequisitos(String anoCatalogo) throws IOException {
        doc = Jsoup.connect("https://www.dac.unicamp.br/sistemas/catalogos/grad/catalogo"
                + anoCatalogo
                + "/coordenadorias.html").get();

        List<String> listaDisciplinas = new LinkedList<>();
        List<String> listaPreReqs = new LinkedList<>();

        int count = 0;

        // Armazena os números de cada coordenadoria em uma lista de objetos da classe Element
        Elements coordenadorias = doc.getElementsByAttributeValueContaining("href", "coordenadorias/");
        for (Element coordenadoria : coordenadorias) {

            doc2 = Jsoup.connect("https://www.dac.unicamp.br/sistemas/catalogos/grad/catalogo"
                    + anoCatalogo
                    + "/coordenadorias/00"
                    + coordenadoria.attr("href").substring(17, 19)
                    + "/00" + coordenadoria.attr("href").substring(17, 19)
                    + ".html").get();

            Elements disciplinas = doc2.getElementsByAttribute("name");
            Elements preReqs = doc2.getElementsByTag("p");

            // Remove aspectos do HTML que não contém pré-requisitos
            preReqs.removeIf(preReq -> !preReq.text().contains("OF:"));

            // Adiciona os elementos das lista de Elementos para as listas de Strings
            for (Element disciplina : disciplinas) {
                listaDisciplinas.add(disciplina.text().substring(0, 5));
            }

            for (Element preReq : preReqs) {
                String valor = preReq.text().replace("/ ", "/").replace("*", "").replace("F ", "F");

                if (preReq.text().contains("F ")) {
                    valor.replace("F ", "F");
                    if (preReq.text().contains("100%")) {
                        listaPreReqs.add(valor.substring(84));
                    } else {
                        listaPreReqs.add(valor.substring(83));
                    }
                } else {
                    if (preReq.text().contains("100%")) {
                        listaPreReqs.add(valor.substring(85));
                    } else {
                        listaPreReqs.add(valor.substring(84));
                    }
                }

                count++;
            }
        }

        System.out.println(count);

        // Adiciona elementos no mapa de Pré-Requisitos
        for (int i = 0; i < count; i++) {
            mapaPreReq.put(listaDisciplinas.get(i), listaPreReqs.get(i));
        }

        return mapaPreReq;
    }

    public Map<String, List<String>> converterPreReqs(Map<String, String> mapa) {
        Map<String, List<String>> mapaConvertido = new HashMap<>();

        for (String chave : mapa.keySet()) {
            mapaConvertido.put(chave, Arrays.asList(mapa.get(chave).split(" ")));
        }

        return mapaConvertido;
    }

    public void printPreReqs(Map<String, String[]> mapa) {

        for (String chave : mapa.keySet()) {
            System.out.println("chave:" + chave);
            System.out.println("Com pré-requisitos: ");
            for (String valor : mapa.get(chave)) {
                System.out.println(valor);
            }
        }

    }

    // método para obter as disciplinas obrigatórias de um curso
    public List<String> getObrigatorias(String anoCatalogo, String curso) throws IOException {
        List<String> disciplinasObrigatorias = new ArrayList<>();

        Document doc = Jsoup.connect("https://www.dac.unicamp.br/sistemas/catalogos/grad/catalogo"
                + anoCatalogo
                + "/curriculoPleno/cp"
                + curso
                + ".html").get();

        Elements disciplinas = doc.getElementsByClass("div50b");

        for (Element disciplina : disciplinas) {
            if (!disciplina.text().contains("-")) {
                System.out.println(disciplina.text().substring(0, 5));
                disciplinasObrigatorias.add(disciplina.text().substring(0, 5));
            }
        }

        return disciplinasObrigatorias;
    }

}
