package pk.edu.budget_app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Budget App API",
                version = "1.0",
                description = "Simple budgeting app for tracking income and expenses.",
                license = @License(name = "MIT", url = "https://opensource.org/licenses/MIT")
        ),
        servers = @Server(url = "http://localhost:8080")
)
public class OpenApiConfig {
}
