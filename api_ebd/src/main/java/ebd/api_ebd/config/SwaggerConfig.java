package ebd.api_ebd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EBD API")
                        .version("1.0")
                        .description("API para gerenciamento de Escola Bíblica Dominical")
                        .contact(new Contact()
                                .name("EBD Team")
                                .email("contato@ebd.com")));
    }
}
