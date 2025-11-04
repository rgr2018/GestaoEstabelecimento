package br.com.fiap.gestaoestabelecimento.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {
    public OpenAPI gestaoEstabelecimento(){
        return new OpenAPI()
                .info(
                        new Info()
                                .title("GestaoEstabelecimento Api")
                                .description("Projeto desenvolvido no curso Spring MVC para fazer gestão de estabelecimentos")
                                .version("v0.0.1")
                                .license(new License().name("Apache 2.0").url("da definir o git"))
                );
    }
}
