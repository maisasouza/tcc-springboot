package hello;

import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PDFReaderService {
	
	@Autowired
	private ElasticSearchService elasticService;

	public void extractPDFWords(InputStream pdfFile, String filename, Integer quantidadeASerIndexada) {
		try (PDDocument document = PDDocument.load(pdfFile)) {

            document.getClass();

            if (!document.isEncrypted()) {
			
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);

                PDFTextStripper tStripper = new PDFTextStripper();

                String pdfFileInText = tStripper.getText(document);
                
                this.elasticService.createClient();
                for(int i = 0; i < quantidadeASerIndexada ; i++) {
                	this.elasticService.createDocument(filename, "123", pdfFileInText);	
                }
                this.elasticService.closeClient();
                //System.out.println("Text:" + st);

				// split by whitespace
//                String lines[] = pdfFileInText.split("\\r?\\n");
//                for (String line : lines) {
//                    System.out.println(line);
//                }

            }

        } catch (Exception ex) {
        	ex.printStackTrace();
        }
	}
}
