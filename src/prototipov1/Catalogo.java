package prototipov1;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import prototipov1.Utils;

public class Catalogo {

    private List<Curso> listaCursos;

    private List<Disciplina> listaDisciplinasObrigatorias;

    private List<Disciplina> listaDisciplinasEletivas;

    public List<String> listObrg;

    private int numCreditosEletivas;

    private String ano;

    private final String url;

    private final String catalogo;

    private File checkcurso;

    public List<Disciplina> lista;

    public Catalogo(String url, String ano, String catalogo) {
        this.ano = ano;
        this.url = url;
        this.catalogo = catalogo;
        lista = new ArrayList<>();
        listObrg = new ArrayList<>();
        Log.LOG.log(Level.INFO, "Inicializando varredura de {0}", url);
    }

    public int getNumCreditosEletivas() {
        return numCreditosEletivas;
    }

    public void setNumCreditosEletivas(int numCreditosEletivas) {
        this.numCreditosEletivas = numCreditosEletivas;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public void separar() {
        String nome;
        String codigo;
        Document document;
        Elements paragraphs;
        lista.clear();

        try {
            document = Utils.getDocument(url, ano);

            paragraphs = document.select("div.div50b");
            String prevUrlDisc = null;
            Document discPage = null;
            Elements listDisc = null;
            Element discData = null;
            for (Element p : paragraphs) {
                Element link = p.getElementsByTag("a").first();
                codigo = link.text();
                if (!codigo.contains("---")) {
                    nome = p.text().substring(p.text().indexOf(' ') + 1);
                    Disciplina d = new Disciplina(codigo, nome);
                    String urlDisc = link.attr("abs:href");

                    d.setUrl(urlDisc);
                    String urlBase = getUrlBase(urlDisc);
                    if (!urlBase.equals(prevUrlDisc)) {
                        try {
                            discPage = Jsoup.connect(urlBase).get();
                            Log.LOG.log(Level.INFO, "Carregando página {0}", urlBase);
                        } catch (HttpStatusException e) {
                            System.err.println(e);
                        }
                        prevUrlDisc = urlBase;
                    }
                    listDisc = discPage.select("a[name=\"" + codigo + "\"]");
                    try {
                        discData = listDisc.first().parent().nextElementSibling();
                        String vetor = discData.text();
                        d.setEmenta(vetor);
                        d.setHorasTeo(Integer.parseInt(vetor.substring(
                                vetor.indexOf("T:") + 2, vetor.indexOf("P:")).trim()));
                        d.setHorasLab(Integer.parseInt(vetor.substring(
                                vetor.indexOf("L:") + 2, vetor.indexOf("O:")).trim()));
                        d.setHorasOrient(Integer.parseInt(vetor.substring(
                                vetor.indexOf("O:") + 2, vetor.indexOf("D:")).trim()));
                        d.setCreditos(Integer.parseInt(vetor.substring(
                                vetor.indexOf("C:") + 2, vetor.indexOf("AV:")).trim()));
                        d.setPreReq(vetor.substring(vetor.indexOf("Pré-Req.:") + 9));
                        String ementa = discData.nextElementSibling().text().substring("Ementa:".length()).trim();
                        d.setEmenta(ementa);
                    } catch (NullPointerException ex) {
                        Log.LOG.log(Level.INFO, "Conteúdo ignorado após: {0}", listDisc.first());
                    }
                    lista.add(d);
                }
            }

        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void separaObrigatorias(List a) {
        String nome;
        String codigo;
        Document document;
        Elements paragraphs;
        System.out.println(a);
        lista.clear();
       
        try {

            document = Utils.getDocument(url, ano);
            paragraphs = document.select("div.div50b");
            String prevUrlDisc = null;
            Document discPage = null;
            Elements listDisc = null;
            Element discData = null;

            for (Element p : paragraphs) {
                Element link = p.getElementsByTag("a").first();
                codigo = link.text();
                if (!codigo.contains("---")) {
                    nome = p.text().substring(p.text().indexOf(' ') + 1);
                    Disciplina d = new Disciplina(codigo, nome);
                    String urlDisc = link.attr("abs:href");
                    d.setUrl(urlDisc);
                    String urlBase = getUrlBase(urlDisc);
                    if (!urlBase.equals(prevUrlDisc)) {
                        try {
                            discPage = Jsoup.connect(urlBase).get();
                            Log.LOG.log(Level.INFO, "Carregando página {0}", urlBase);
                        } catch (HttpStatusException e) {
                            System.err.println(e);
                        }
                        prevUrlDisc = urlBase;
                    }
                    listDisc = discPage.select("a[name=\"" + codigo + "\"]");
//                
                    try {
                        discData = listDisc.first().parent().nextElementSibling();
                        String vetor = discData.text();
                        d.setEmenta(vetor);
                        d.setHorasTeo(Integer.parseInt(vetor.substring(
                                vetor.indexOf("T:") + 2, vetor.indexOf("P:")).trim()));
                        d.setHorasLab(Integer.parseInt(vetor.substring(
                                vetor.indexOf("L:") + 2, vetor.indexOf("O:")).trim()));
                        d.setHorasOrient(Integer.parseInt(vetor.substring(
                                vetor.indexOf("O:") + 2, vetor.indexOf("D:")).trim()));
                        d.setCreditos(Integer.parseInt(vetor.substring(
                                vetor.indexOf("C:") + 2, vetor.indexOf("AV:")).trim()));
                        d.setPreReq(vetor.substring(vetor.indexOf("Pré-Req.:") + 9));
                        String ementa = discData.nextElementSibling().text().substring("Ementa:".length()).trim();
                        d.setEmenta(ementa);
                    } catch (NullPointerException ex) {
                        Log.LOG.log(Level.INFO, "Conteúdo ignorado após: {0}", listDisc.first());
                    }

                    for (int i = 0; i < a.size(); i++) {
                        if (codigo.equals(a.get(i))) {
                            lista.add(d);
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public List<Disciplina> separarDisciplinasUnicamp(String anoCatalogo) throws IOException {
        String nome;
        String codigo;
        Elements paragraphs;
        String webPage;
        Document document;
        List<Disciplina> listaDisciplinas = new ArrayList<>();

        webPage = "https://www.dac.unicamp.br/sistemas/catalogos/grad/catalogo" + ano + "/cursos.html";

        try {
            document = Utils.getDocument(webPage, ano);

            Elements codigosCursos = document.getElementsByTag("span");

            codigosCursos.remove(0);

            for (Element codigoCurso : codigosCursos) {

                System.out.println("Listando disciplinas do curso: " + codigoCurso.text());

                webPage = "https://www.dac.unicamp.br/sistemas/catalogos/grad/catalogo" + ano + "/curriculoPleno/cp" + codigoCurso.text() + ".html";
                document = Utils.getDocument(webPage, ano);

                paragraphs = document.select("div.div50b");
                String prevUrlDisc = null;
                Document discPage = null;
                Elements listDisc = null;
                Element discData = null;

                try {
                    for (Element p : paragraphs) {

                        Element link = p.getElementsByTag("a").first();

                        codigo = link.text();

                        if (!codigo.contains("-")) {
                            nome = p.text().substring(p.text().indexOf(' ') + 1);
                            Disciplina d = new Disciplina(codigo.replace("F ", "F"), nome);
                            String urlDisc = link.attr("abs:href");
                            d.setUrl(urlDisc);
                            String urlBase = getUrlBase(urlDisc);
                            if (!urlBase.equals(prevUrlDisc)) {
                                try {
                                    discPage = Jsoup.connect(urlBase).get();
                                    Log.LOG.log(Level.INFO, "Carregando página {0}", urlBase);
                                } catch (HttpStatusException e) {
                                    System.err.println(e);
                                }
                                prevUrlDisc = urlBase;
                            }
                            listDisc = discPage.select("a[name=\"" + codigo + "\"]");

                            try {
                                discData = listDisc.first().parent().nextElementSibling();
                                String vetor = discData.text();
                                d.setEmenta(vetor);
                                d.setHorasTeo(Integer.parseInt(vetor.substring(
                                        vetor.indexOf("T:") + 2, vetor.indexOf("P:")).trim()));
                                d.setHorasLab(Integer.parseInt(vetor.substring(
                                        vetor.indexOf("L:") + 2, vetor.indexOf("O:")).trim()));
                                d.setHorasOrient(Integer.parseInt(vetor.substring(
                                        vetor.indexOf("O:") + 2, vetor.indexOf("D:")).trim()));
                                d.setCreditos(Integer.parseInt(vetor.substring(
                                        vetor.indexOf("C:") + 2, vetor.indexOf("AV:")).trim()));
                                String ementa = discData.nextElementSibling().text().substring("Ementa:".length()).trim();
                                d.setEmenta(ementa);
                            } catch (NullPointerException ex) {
                                Log.LOG.log(Level.INFO, "Conteúdo ignorado após: {0}", listDisc.first());
                            }
                            listaDisciplinas.add(d);
                        }
                    }
                } catch (NullPointerException excep) {
                    System.out.println("link nulo");
                }
            }

        } catch (IOException e) {
            System.err.println(e);
        }
        return listaDisciplinas;

    }

    public void obrigatorias() {
        Elements obrg;
        Document document;
        listObrg.clear();
        try {

            document = Utils.getDocument(url, ano);
            obrg = document.select("a");

            for (Element p : obrg.subList(1, obrg.size())) {
                String obg = p.text();
                String obg2;
                if (obg.length() == 9) {
                    obg2 = obg.substring(0, 5);
                    listObrg.add(obg2);
                }
            }

        } catch (IOException e) {
            System.err.println(e);
        }

    }

    public void listaTudo(PrintStream out) {
        lista.forEach((d) -> {
            out.println(d.toString());
        });
    }

    public List<Disciplina> disciplinas() {
        lista = lista.stream().distinct().collect(Collectors.toList());
        return Collections.unmodifiableList(lista);
    }

    private String getUrlBase(String url) {
        if (url.contains("#")) {
            return url.substring(0, url.indexOf('#'));
        }
        return url;
    }

    public List<Disciplina> getList() {
        return this.lista;
    }

    public List<Disciplina> setList(ArrayList a) {
        return a;
    }

    public List<String> getListaObrigatoria() {
        return listObrg;
    }
}
