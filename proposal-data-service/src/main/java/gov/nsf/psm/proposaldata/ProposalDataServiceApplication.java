package gov.nsf.psm.proposaldata;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class ProposalDataServiceApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ProposalDataServiceApplication.class);
    }

    public static void main(String[] args) {
        BasicConfigurator.configure();
        setEmbeddedContainerEnvironmentProperties();
        SpringApplication.run(ProposalDataServiceApplication.class, args);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        setExternalContainerEnvironmentProperties();
        super.onStartup(servletContext);
    }

    private static void setEmbeddedContainerEnvironmentProperties() {
        setEnvironmentProperties();
        System.setProperty("server.context-path", "/proposal-data-service");
    }

    private static void setExternalContainerEnvironmentProperties() {
        setEnvironmentProperties();
    }

    private static void setEnvironmentProperties() {
        System.setProperty("spring.config.name", "proposal-data-service");
    }

}
