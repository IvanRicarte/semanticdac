package tcc.ui;

import prototipov1.Historico;
import prototipov1.Utils;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import prototipov1.Catalogo;
import prototipov1.Disciplina;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import prototipov1.Equivalencia;
import prototipov1.ConversorRDF;
import prototipov1.MapeadorDisciplinasUnicamp;

public class Tela2Controller implements Initializable {

    @FXML
    private TextField integralizacao;

    private String anoCatalogo;

    @FXML
    private ComboBox CodigoBox;

    @FXML
    private ComboBox<Disciplina> disciplinasBox;

    @FXML
    private Button upload;

    @FXML
    private Button uploadeq;

    private FileChooser fileChooser;
    private FileChooser fileChooser1;
    private File arquivo;
    private File arquivo1;

    @FXML
    private Button ObrigatoriasButton;

    @FXML
    private Button EletivasButton;

    @FXML
    private Button generaterdf;

    ArrayList<String> listaObrigatorias = new ArrayList<String>();

    ArrayList<String> listaUtilizada = new ArrayList<String>();
    List<String> list2;
    List<String> listeq;
    List<String> list;

    public void initialize(URL url, ResourceBundle rb) {
        ObrigatoriasButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disciplinasBox.getItems().clear();

                arquivo = getArquivo();
                arquivo1 = getArquivoeq();
                if (arquivo != null && arquivo1 != null) {
                    Historico h = new Historico();
                    System.out.println("Arquivo com historico com txt");
                    try {
                        h.extract(arquivo.toPath());
                        obrigatoriaseHistorico(event);

                        List<String> list = new ArrayList<String>(Arrays.asList(h.StringObrigatorias().split("  ")));
                        Set<String> intersect = new HashSet<String>(listaObrigatorias);
                        intersect.retainAll(list);

                        listaObrigatorias.removeAll(list);

                        String codigotemp = (String) CodigoBox.getValue();
                        String numcurso = codigotemp.substring(codigotemp.indexOf('(') + 1, codigotemp.indexOf(')'));
                        String curso = numcurso;

                        String url2 = "https://www.dac.unicamp.br/sistemas/catalogos/grad/catalogo"
                                + anoCatalogo + "/curriculoPleno/cp"
                                + curso + ".html";

                        String url = "https://www.dac.unicamp.br/sistemas/catalogos/grad/catalogo"
                                + anoCatalogo + "/proposta/sug"
                                + curso + ".html";

                        Catalogo catalogo2 = new Catalogo(url2, anoCatalogo, curso);

                        Catalogo catalogo = new Catalogo(url, anoCatalogo, curso);

                        list2 = catalogo.getListaObrigatoria();
                        catalogo.obrigatorias();
                        
                        listeq = new Equivalencia(arquivo1).getEquivalencia();

                        list2.removeAll(list);
                        list2.removeAll(listeq);

                        setList2(list2);

                        catalogo2.separaObrigatorias(list2);
                        disciplinasBox.getItems().clear();
                        disciplinasBox.getItems().setAll(catalogo2.disciplinas());

                    } catch (IOException ex) {

                        Logger.getLogger(Tela2Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (arquivo1 == null && arquivo != null) {
                    Historico h = new Historico();
                    System.out.println("Arquivo com historico sem txt");

                    try {
                        h.extract(arquivo.toPath());
                        obrigatoriaseHistorico(event);

                        List<String> list = new ArrayList<String>(Arrays.asList(h.StringObrigatorias().split("  ")));
                        Set<String> intersect = new HashSet<String>(listaObrigatorias);
                        intersect.retainAll(list);

                        listaObrigatorias.removeAll(list);

                        String codigotemp = (String) CodigoBox.getValue();
                        String numcurso = codigotemp.substring(codigotemp.indexOf('(') + 1, codigotemp.indexOf(')'));
                        String curso = numcurso;

                        String url2 = "https://www.dac.unicamp.br/sistemas/catalogos/grad/catalogo"
                                + anoCatalogo + "/curriculoPleno/cp"
                                + curso + ".html";

                        String url = "https://www.dac.unicamp.br/sistemas/catalogos/grad/catalogo"
                                + anoCatalogo + "/proposta/sug"
                                + curso + ".html";

                        Catalogo catalogo2 = new Catalogo(url2, anoCatalogo, curso);

                        Catalogo catalogo = new Catalogo(url, anoCatalogo, curso);

                        list2 = catalogo.getListaObrigatoria();
                        catalogo.obrigatorias();

                        list2.removeAll(list);

                        catalogo2.separaObrigatorias(list2);
                        disciplinasBox.getItems().clear();
                        disciplinasBox.getItems().setAll(catalogo2.disciplinas());
                    } catch (IOException ex) {

                        Logger.getLogger(Tela2Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                if (arquivo1 != null & arquivo == null) {
                    Historico h = new Historico();
                    System.out.println("Arquivo sem historico com txt");
                    try {

                        String codigotemp = (String) CodigoBox.getValue();
                        String numcurso = codigotemp.substring(codigotemp.indexOf('(') + 1, codigotemp.indexOf(')'));
                        String curso = numcurso;

                        String url2 = "https://www.dac.unicamp.br/sistemas/catalogos/grad/catalogo"
                                + anoCatalogo + "/curriculoPleno/cp"
                                + curso + ".html";

                        String url = "https://www.dac.unicamp.br/sistemas/catalogos/grad/catalogo"
                                + anoCatalogo + "/proposta/sug"
                                + curso + ".html";

                        Catalogo catalogo2 = new Catalogo(url2, anoCatalogo, curso);

                        Catalogo catalogo = new Catalogo(url, anoCatalogo, curso);

                        List<String> list2 = catalogo.getListaObrigatoria();
                        catalogo.obrigatorias();

                        System.out.println("Currículo pleno");
                        System.out.println(list2);

                        listeq = new Equivalencia(arquivo1).getEquivalencia();

                        list2.removeAll(listeq);

                        catalogo2.separaObrigatorias(list2);
                        disciplinasBox.getItems().clear();
                        disciplinasBox.getItems().setAll(catalogo2.disciplinas());
                    } catch (IOException ex) {

                        Logger.getLogger(Tela2Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (arquivo == null && arquivo1 == null) {
                    try {
                        disciplinasBox.getItems().clear();
                        System.out.println("Arquivo sem historico sem txt");
                        loadDisc(event);
                    } catch (IOException ex) {
                        Logger.getLogger(Tela2Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

        }
        );

        EletivasButton.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event
            ) {
                disciplinasBox.getItems().clear();

                arquivo = getArquivo();
                if (arquivo != null) {
                    Historico h = new Historico();
                    try {
                        h.extract(arquivo.toPath());
                        obrigatoriaseHistorico(event);

                        List<String> list = new ArrayList<String>(Arrays.asList(h.StringObrigatorias().split("  ")));
                        Set<String> intersect = new HashSet<String>(listaObrigatorias);
                        intersect.retainAll(list);

                        listaObrigatorias.removeAll(list);

                        String codigotemp = (String) CodigoBox.getValue();
                        String numcurso = codigotemp.substring(codigotemp.indexOf('(') + 1, codigotemp.indexOf(')'));
                        String curso = numcurso;

                        String url2 = "https://www.dac.unicamp.br/sistemas/catalogos/grad/catalogo"
                                + anoCatalogo + "/curriculoPleno/cp"
                                + curso + ".html";

                        String url = "https://www.dac.unicamp.br/sistemas/catalogos/grad/catalogo"
                                + anoCatalogo + "/proposta/sug"
                                + curso + ".html";

                        Catalogo catalogo2 = new Catalogo(url2, anoCatalogo, curso);

                        Catalogo catalogo = new Catalogo(url, anoCatalogo, curso);

                        ArrayList<String> listnull = new ArrayList<String>();

                        catalogo2.setList(listnull).clear();

                        list2 = catalogo.getListaObrigatoria();
                        catalogo.obrigatorias();

                        list2.removeAll(list);

                        listaObrigatorias.removeAll(list2);
                        catalogo2.separaObrigatorias(listaObrigatorias);
                        listaObrigatorias.removeAll(list);
                        System.out.println(listaObrigatorias);
                        setListEle(listaObrigatorias);
                        disciplinasBox.getItems().clear();
                        disciplinasBox.getItems().setAll(catalogo2.disciplinas());

                    } catch (IOException ex) {

                        Logger.getLogger(Tela2Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        geraEletivas(event);
                    } catch (IOException ex) {
                        Logger.getLogger(Tela2Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }
        );

        disciplinasBox.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event
            ) {
                mostraDisciplina(disciplinasBox.getValue());
            }

        }
        );

        fileChooser = new FileChooser();

        upload.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event
            ) {
                File file = fileChooser.showOpenDialog(null);
                setArquivo(file);
                upload.setText(file.getName());
                Historico h = new Historico();
                try {
                    h.extract(file.toPath());

                } catch (IOException ex) {
                    Logger.getLogger(Tela2Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        );

        fileChooser1 = new FileChooser();

        uploadeq.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event
            ) {
                File file1 = fileChooser1.showOpenDialog(null);
                setArquivoeq(file1);
                uploadeq.setText(file1.getName());

            }

        }
        );

        generaterdf.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event
            ) {
                List<String> list1;
                List<String> listObrg;
                List<String> listEle;
                String codigotemp = (String) CodigoBox.getValue();
                String numcurso = codigotemp.substring(codigotemp.indexOf('(') + 1, codigotemp.indexOf(')'));
                String curso = numcurso;
                String url = "https://www.dac.unicamp.br/sistemas/catalogos/grad/catalogo"
                        + anoCatalogo + "/curriculoPleno/cp"
                        + curso + ".html";
                Catalogo catalogo = new Catalogo(url, anoCatalogo, curso);
                try {
                    List<Disciplina> listaDisciplina = catalogo.separarDisciplinasUnicamp(anoCatalogo);
                    Map<String, Disciplina> disciplinasUnicamp = new HashMap<>();
                    Map<String, String> preReqs = new HashMap<>();
                    MapeadorDisciplinasUnicamp mapeador = new MapeadorDisciplinasUnicamp();
                    listObrg = list2;
                    disciplinasUnicamp = mapeador.mapearDisciplinas(anoCatalogo);
                    preReqs = mapeador.listarPreRequisitos(anoCatalogo);
                    Map<String, List<String>> preReqsConvertidos = mapeador.converterPreReqs(preReqs);

                    for (String chave1 : disciplinasUnicamp.keySet()) {
                        if (preReqsConvertidos.keySet().contains(chave1)) {

                            Disciplina d = disciplinasUnicamp.get(chave1);

                            d.setPreReqs(preReqsConvertidos.get(chave1));

                            disciplinasUnicamp.replace(chave1, d);
                        }
                    }

                    for (String chave : disciplinasUnicamp.keySet()) {
                        for (Disciplina d : listaDisciplina) {
                            if (d.getCodigo().equals(chave)) {
                                disciplinasUnicamp.get(chave).setCodigo(d.getCodigo());
                                disciplinasUnicamp.get(chave).setCreditos(d.getCreditos());
                                disciplinasUnicamp.get(chave).setEmenta(d.getEmenta());
                                disciplinasUnicamp.get(chave).setHorasLab(d.getHorasLab());
                                disciplinasUnicamp.get(chave).setHorasOrient(d.getHorasOrient());
                                disciplinasUnicamp.get(chave).setHorasPrat(d.getHorasPrat());
                                disciplinasUnicamp.get(chave).setHorasTeo(d.getHorasTeo());

                            }
                        }
                    }

                    for (String chave : preReqsConvertidos.keySet()) {
                        for (Disciplina d : listaDisciplina) {
                            if (d.getCodigo().equals(chave)) {
                                d.setPreReqs(preReqsConvertidos.get(chave));
                            }
                        }
                    }

                    for (Disciplina d : listaDisciplina) {
                        disciplinasUnicamp.replace(d.getCodigo(), d);
                    }

                    list1 = getList2();
                    listEle = getListEle();

                    System.out.println(list1);
                    System.out.println(listEle);

                    File f = new File(System.getProperty("user.dir") + "/tmp/teste4.ntriples");
                    if (!f.getParentFile().exists()) {
                        f.getParentFile().mkdirs();
                    }

                    try {
                        if (f.exists() == true) {
                            String nomearquivo = f.getAbsolutePath();
                            System.out.println("Caminho do arquivo: " + nomearquivo);
                            ConversorRDF crdf = new ConversorRDF();
                            crdf.converterParaRDF(disciplinasUnicamp, nomearquivo, list1, listEle);
                        } else {             
                            String nomearquivo = f.getAbsolutePath();
                            System.out.println("Caminho do arquivo: " + nomearquivo);
                            ConversorRDF crdf = new ConversorRDF();
                            crdf.converterParaRDF(disciplinasUnicamp, nomearquivo, list1, listEle);

                        }
                    } catch (IOException ex) {
                        System.err.println(ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Tela2Controller.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
        );

    }

    public String Resultado() {
        String s = "";
        for (String d : listaObrigatorias) {
            s = s + d + "  ";
        }
        return s;
    }

    public int getSimilarItems(List one, List two) {
        int initial = two.size();

        two.removeIf(one::contains);
        System.out.println(initial - two.size());
        return initial - two.size();
    }

    public void enviaText(String message) {
        anoCatalogo = message;
    }

    private void loadDisc(ActionEvent event) throws IOException {
        obrigatoriaseHistorico(event);
        disciplinasBox.getItems().clear();

        String codigotemp = (String) CodigoBox.getValue();
        String numcurso = codigotemp.substring(codigotemp.indexOf('(') + 1, codigotemp.indexOf(')'));
        String curso = numcurso;
        String url = "https://www.dac.unicamp.br/sistemas/catalogos/grad/catalogo"
                + anoCatalogo + "/curriculoPleno/cp"
                + curso + ".html";
        Catalogo catalogo = new Catalogo(url, anoCatalogo, curso);
        catalogo.separar();

        String url2 = "https://www.dac.unicamp.br/sistemas/catalogos/grad/catalogo"
                + anoCatalogo + "/proposta/sug"
                + curso + ".html";

        Catalogo catalogo2 = new Catalogo(url2, anoCatalogo, curso);

        catalogo2.obrigatorias();
        list2 = catalogo2.getListaObrigatoria();
        catalogo.separaObrigatorias(list2);

        disciplinasBox.getItems().setAll(catalogo.disciplinas());

    }

    private void geraEletivas(ActionEvent event) throws IOException {
        obrigatoriaseHistorico(event);
        disciplinasBox.getItems().clear();

        String codigotemp = (String) CodigoBox.getValue();
        String numcurso = codigotemp.substring(codigotemp.indexOf('(') + 1, codigotemp.indexOf(')'));
        String curso = numcurso;
        String url = "https://www.dac.unicamp.br/sistemas/catalogos/grad/catalogo"
                + anoCatalogo + "/curriculoPleno/cp"
                + curso + ".html";
        Catalogo catalogo = new Catalogo(url, anoCatalogo, curso);
        catalogo.separar();

        String url2 = "https://www.dac.unicamp.br/sistemas/catalogos/grad/catalogo"
                + anoCatalogo + "/proposta/sug"
                + curso + ".html";

        Catalogo catalogo2 = new Catalogo(url2, anoCatalogo, curso);

        catalogo2.obrigatorias();
        list2 = catalogo2.getListaObrigatoria();
        listaObrigatorias.removeAll(list2);
        catalogo.separaObrigatorias(listaObrigatorias);
        System.out.println(catalogo.disciplinas());

        disciplinasBox.getItems().setAll(catalogo.disciplinas());

    }

    public void pegaDisciplina(String ano) throws IOException {

        //Limpa ComboBox
        CodigoBox.getItems().clear();

        String webPage;
        Document document;

        webPage = "https://www.dac.unicamp.br/sistemas/catalogos/grad/catalogo" + ano + "/cursos.html";
        document = Utils.getDocument(webPage, ano);

        Elements spans, texto;
        Map<String, String> mapadisciplinas = new HashMap<String, String>();

        spans = document.select("a");

        texto = document.select("span");

        ArrayList<String> listanomecurso = new ArrayList<String>();
        ArrayList<String> listanumerocurso = new ArrayList<String>();

        for (Element numerocurso : texto) {
            String temp2 = numerocurso.text();
            if (!"Cursos".equals(temp2)) {
                listanumerocurso.add(temp2);
            }
        }

        for (Element nomecurso : spans) {
            String temp = nomecurso.text();
            if (!"Área de Artes".equals(temp) && !"Coordenadorias".equals(temp) && !"Regimento".equals(temp) && !"Instruções".equals(temp) && !"Legenda".equals(temp) && !"Disciplinas".equals(temp) && !"Área de Ciências Biológicas e Profissões da Saúde".equals(temp) && !"Área de Ciências Exatas, Tecnológicas e da Terra".equals(temp) && !"Área de Ciências Humanas".equals(temp) && !"Programas Especiais".equals(temp)) {
                listanomecurso.add(temp);
            }
        }

        System.out.println(listanomecurso);
        System.out.println(listanumerocurso);

        assert (listanomecurso.size() == listanumerocurso.size());

        List<String> listaTemp = new ArrayList<>();
        for (int y = 0; y < listanomecurso.size(); y++) {
            listaTemp.add(listanomecurso.get(y).concat(" (").concat(listanumerocurso.get(y)).concat(")"));
        }

        listaTemp.sort(String.CASE_INSENSITIVE_ORDER);
        CodigoBox.getItems().addAll(listaTemp);
    }

    private void obrigatoriaseHistorico(ActionEvent event) {
        disciplinasBox.getItems().clear();
        System.out.println(anoCatalogo);
        String codigotemp = (String) CodigoBox.getValue();
        String numcurso = codigotemp.substring(codigotemp.indexOf('(') + 1, codigotemp.indexOf(')'));
        String curso = numcurso;
        String url = "https://www.dac.unicamp.br/sistemas/catalogos/grad/catalogo"
                + anoCatalogo + "/curriculoPleno/cp"
                + curso + ".html";
        Catalogo catalogo = new Catalogo(url, anoCatalogo, curso);
        catalogo.separar();

        List<Disciplina> str = catalogo.disciplinas();
        for (Disciplina listDisciciplinas : str) {
            listaObrigatorias.add(listDisciciplinas.getCodigo());
        }
    }

    private void mostraDisciplina(Disciplina d) {
        Alert dados = new Alert(Alert.AlertType.INFORMATION);
        dados.setTitle(d.getNome());
        dados.setHeaderText(d.getCodigo());
        dados.setContentText("Créditos: " + d.getCreditos() + "\n"
                + "Horas teóricas: " + d.getHorasTeo() + "\n"
                + "Horas de laboratório: " + d.getHorasLab() + "\n"
                + "Horas de orientação: " + d.getHorasOrient() + "\n"
                + "Pré-requisitos: " + d.getPreReq() + "\n\n"
                + "Ementa: " + d.getEmenta()
        );
        dados.showAndWait();
    }

    private File getArquivo() {
        return this.arquivo;
    }

    private File getArquivoeq() {
        return this.arquivo1;
    }

    public File setArquivo(File arquivo) {
        return this.arquivo = arquivo;
    }

    public File setArquivoeq(File arquivo) {
        return this.arquivo1 = arquivo;
    }

    public List getList2() {
        return this.list2;
    }

    public List setList2(List a) {
        return this.list2 = a;
    }

    public List getListeq() {
        return this.listeq;
    }

    public List setListeq(List b) {
        return this.listeq = b;
    }

    public List getListEle() {
        return this.list;
    }

    public List setListEle(List c) {
        return this.list = c;
    }
}
