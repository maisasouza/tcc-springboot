package tcc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

import javassist.NotFoundException;

@SpringBootApplication
@PropertySource("application.properties")
@EnableAsync
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        // scriptTeste();
        // SpringApplication.exit(ctx, () -> 0);
    }

    public static void scriptTeste() {
        File arquivoTeste = new File("dict.pdf");
        PDFReaderService pdfService = new PDFReaderService();

        try {
            InputStream arquivoTesteStream = new FileInputStream(arquivoTeste);
            pdfService.extractPDFWords(arquivoTesteStream, "dict.pdf", 500000);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
