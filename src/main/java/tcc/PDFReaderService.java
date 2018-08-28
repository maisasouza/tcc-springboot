package tcc;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class PDFReaderService {
	
	@Autowired
    private ElasticSearchService elasticService;
    
    public PDFReaderService() {
        this.elasticService = new ElasticSearchService();
    }

	@Async
	public void extractPDFWords(InputStream pdfFile, String filename, Integer quantidadeASerIndexada) {
        List<String> listToSearch = Arrays.asList("PDF", "files", "Exchange", "actually", "Document", 
            "Format", "proprietary", "eletronic", "documents", "cost", "virtually", "business", 
            "reason", "quickly", "information", "manipulation", "devices", "banana", "coisa", "teste", "paradigma",
            "transposição", "viagem", "maduro", "prestado", "portador", "abacaxi", "comum", "salame", "morango",
            "jogos", "trabalho", "elastico", "tempo", "comando", "globo", "monstruoso");
            
		Date tempoInicial = new Date(); 
		
		try (PDDocument document = PDDocument.load(pdfFile)) {
            document.getClass();

            if (!document.isEncrypted()) {
			
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(false);

                PDFTextStripper tStripper = new PDFTextStripper();

                String pdfFileInText = tStripper.getText(document);
                // System.out.println(pdfFileInText);
                
                this.elasticService.createClient();
                int palavraEscolhida = 0;
                for(int i = 0; i < quantidadeASerIndexada ; i++) {
                    // System.out.print(i + " ");
                    this.elasticService.createDocument(filename, "123", pdfFileInText);	

                    if (i % 10000 == 0) {
                        Date tempoBuscaInicial = new Date();
                        elasticService.pesquisaTermo(listToSearch.get(palavraEscolhida));
                        Date tempoBuscaFinal = new Date();
                        Long diferencaBusca = tempoBuscaFinal.getTime() - tempoBuscaInicial.getTime();
                        System.out.println("i: " + i + " Termo da pesquisa: " + listToSearch.get(palavraEscolhida) + " Busca em milisegundos: " + diferencaBusca.toString());
                        palavraEscolhida++;
                        palavraEscolhida = palavraEscolhida % (listToSearch.size()-1);
                    }

                }
                this.elasticService.closeClient();

            }
            
           Date tempoFinal = new Date();
           Long diferenca = tempoFinal.getTime() - tempoInicial.getTime();
        //    System.out.println("Diferença em milisegundos: " + diferenca.toString());           

        } catch (Exception ex) {
        	ex.printStackTrace();
        }
	}
}
