package pl.honestit.demos.spring.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;

@Configuration @Profile("dev")
public class H2DatasourceConfig {

    @Bean(initMethod = "start", destroyMethod = "stop") @Primary
    public Server h2DatabaseServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
    }
}
