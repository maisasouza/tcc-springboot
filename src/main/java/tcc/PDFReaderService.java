package tcc;

import java.io.InputStream;
import java.util.Date;

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

	@Async
	public void extractPDFWords(InputStream pdfFile, String filename, Integer quantidadeASerIndexada) {
		
		Date tempoInicial = new Date(); 
		
		try (PDDocument document = PDDocument.load(pdfFile)) {
            document.getClass();

            if (!document.isEncrypted()) {
			
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(false);

                PDFTextStripper tStripper = new PDFTextStripper();

                String pdfFileInText = tStripper.getText(document);
                
                this.elasticService.createClient();
                for(int i = 0; i < quantidadeASerIndexada ; i++) {
                	this.elasticService.createDocument(filename, "123", pdfFileInText);	
                }
                this.elasticService.closeClient();

            }
            
           Date tempoFinal = new Date();
           Long diferenca = tempoFinal.getTime() - tempoInicial.getTime();
           System.out.println("Diferença em milisegundos: " + diferenca.toString());           

        } catch (Exception ex) {
        	ex.printStackTrace();
        }
	}
}
