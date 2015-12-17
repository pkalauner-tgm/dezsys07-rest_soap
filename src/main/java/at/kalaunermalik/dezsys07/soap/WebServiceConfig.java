package at.kalaunermalik.dezsys07.soap;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * This class configurates the Webservice. Especially configurations necessary
 * information for the wsdl-file is set.
 */
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    /**
     * Creates a @see messageDispatcherServlet and sets necessary configurations
     * @param applicationContext the given applicationContext
     * @return the ServletRegistrationBean with /ws/* as URL-mapping
     */
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    /**
     * Creates a configurated wsdldefinition. It sets the namespace port and location of the wsdl with the given dataschema (xsd)
     * @param dataSchema the xsd of the webservice
     * @return the configurated wsdldefinition
     */
    @Bean(name = "data")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema dataSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("DataPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("dezsys07.kalaunermalik.at");
        wsdl11Definition.setSchema(dataSchema);
        return wsdl11Definition;
    }

    /**
     * Creates the xsd-schema from the provided xsd-file
     * @return the simple xsd-schema generated from the file
     */
    @Bean
    public XsdSchema dataSchema() {
        return new SimpleXsdSchema(new ClassPathResource("data.xsd"));
    }
}