package prototipov1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import prototipov1.Disciplina;


public class ConversorRDF {
    
    // Definição atributos
    private final String uriDisciplina = "http://modelo/disciplina#";
    
    // Definição métodos
    public void converterParaRDF(Map<String, Disciplina> disciplinasUnicamp,
            String fileName,
            List<String> obrigatorias,
            List<String> eletivas) throws IOException{
        
        Map<String, Disciplina> mapaObrigatorias = new HashMap<>();
        Map<String, Disciplina> mapaEletivas = new HashMap<>();
        
        
        // mapa de obrigatórias
        try {
            for (String disciplina : obrigatorias) {
                if (disciplinasUnicamp.containsKey(disciplina)) {
                    mapaObrigatorias.put(disciplina, disciplinasUnicamp.get(disciplina));
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Não hà obrigatòrias a serem mostradas");
        }

        //mapa de eletivas
        try {
            for (String disciplina : eletivas) {
                if (disciplinasUnicamp.containsKey(disciplina)) {
                    mapaEletivas.put(disciplina, disciplinasUnicamp.get(disciplina));
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Não hà eletivas a serem mostradas");
        }

        // Cria um modelo padrão
        Model modelDisciplina = ModelFactory.createDefaultModel();

        // Cria um recurso para cada atributo da classe Disciplina
        Resource disciplinasObrigatorias = modelDisciplina.createResource(uriDisciplina + "obrigatoria");
        Resource disciplinasEletivas = modelDisciplina.createResource(uriDisciplina + "eletiva");

        // Define vocabulários RDF para o modelo
        Property propObrigatoria = modelDisciplina.createProperty("SN.DisciplinaObrigatoria");
        Property propEletiva = modelDisciplina.createProperty("SN.DisciplinaEletiva");
        Property propPreReq = modelDisciplina.createProperty("SN.PreRequisito");

        // Adiciona as Propriedades para o Recurso no grafo de obirgatórias
        for (String chave : mapaObrigatorias.keySet()) {

            Disciplina d = disciplinasUnicamp.get(chave);

            Resource disciplina = modelDisciplina.createResource(d.getCodigo());

            if (d.getPreReqs().get(0).equals("Não")) {
                modelDisciplina.add(disciplinasObrigatorias, propObrigatoria, disciplina);
            } else {
                for (String preReq : d.getPreReqs()) {
                    if (preReq.contains("/")) {
                        String[] ar = preReq.split("/");
                        for (int i = 0; i < ar.length; i++) {
                            Resource preRequisito = modelDisciplina.createResource(ar[i]);
                            modelDisciplina.add(preRequisito, propPreReq, disciplina);
                        }
                    } else if (!preReq.equals("")) {
                        Resource preRequisito = modelDisciplina.createResource(preReq);
                        modelDisciplina.add(preRequisito, propPreReq, disciplina);

                    }
                }
            }
        }

        // Adiciona as propriedades para os recursos no grafo de eletivas
        // se a eletiva não tem pré-reqs ela é adicionada
        for (String chave : mapaEletivas.keySet()) {

            Disciplina d = mapaEletivas.get(chave);

            Resource disciplina = modelDisciplina.createResource(d.getCodigo());

            if (d.getPreReqs().get(0).contains("Não")) {
                modelDisciplina.add(disciplinasEletivas, propEletiva, disciplina);
            }
        }

        modelDisciplina.write(System.out, "N-Triples");

        FileWriter out = new FileWriter(fileName);
        try {
            modelDisciplina.write(out, "N-Triples");
            System.out.println("Arquivo " + fileName + " criado");
        } finally {
            try {
                out.close();
            } catch (IOException closeException) {
                System.out.println(closeException.getMessage());
            }
        }
    }
}
