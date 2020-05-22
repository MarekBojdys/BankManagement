package pl.marekbojdys.bankmanagement.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
        return new OpenAPI()
                .info(new Info()
                        .title("BankManagement API")
                        .version(appVersion)
                        .description("BankManagement lets you add bank account, get bank account and modify balance of account")
                        .license(new License().name("Marek Bojdys")));
    }
}
