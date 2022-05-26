package br.com.sw2you.realmeet.config;

import java.io.IOException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class JasperReportsConfiguration {
    private static final String JASPER_FOLDER = "/jasper";//local do arquivo

    @Bean
    public JasperReport allocationReport() {
        return getReport("/allocation-report.jrxml");//nome do arquivo
    }

    private JasperReport getReport(String reportName) {
        try {//compilar o relatório
            return JasperCompileManager.compileReport(
                    new ClassPathResource(JASPER_FOLDER + reportName).getInputStream()
            );
        } catch (JRException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}