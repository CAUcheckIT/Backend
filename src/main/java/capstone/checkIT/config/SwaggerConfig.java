package capstone.checkIT.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@Configuration
public class SwaggerConfig {

    private static Components authComponent() {
        return new Components().addSecuritySchemes("session-token",
                new SecurityScheme()
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                        .name("Authorization"));
    }

    @Bean
    public OpenAPI CheckITAPI() {
        Info info = new Info()
                .title("CheckIT API")
                .description("""
                        ### CheckIT REST API 명세 문서입니다.
                        - #### 자물쇠 버튼으로 `Authorization` 헤더 설정이 가능합니다.
                        """)
                .version("v0.1");
        return new OpenAPI()
                .info(info)
                .components(authComponent());
    }
}
