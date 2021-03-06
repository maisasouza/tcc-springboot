package tcc;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ApplicationController {
	
	@Autowired
	private PDFReaderService pdfService;
	
	@Autowired
	private ElasticSearchService elasticService;
	
    
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> indexarDocumento(@RequestPart("quantidade") ArquivoDTO quantidade,
            @RequestPart(value = "file", required = false) MultipartFile arquivoPdf) throws IOException, Exception {
    	
    	System.out.println("Quantidade recebida: " + quantidade.getQuantidade() + ", numero de vezes que o arquivo será indexado na base.");
    	
    	pdfService.extractPDFWords(arquivoPdf.getInputStream(), 
    			arquivoPdf.getOriginalFilename(), 
    			Integer.parseInt(quantidade.getQuantidade().toString()));
    	
    	
        return ResponseEntity.ok().body(null); 
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping("/pesquisa/{termo}")
    public ResponseEntity<RespostaBusca> pesquisar(@PathVariable("termo") String termo ) {
    	
    	System.out.println("Termo a ser pesquisado: " + termo);
    	
    	RespostaBusca resultado = elasticService.pesquisaTermo(termo);
    	
        return ResponseEntity.ok().body(resultado);
    }
    
    
    
    
}
