package cvut.fel.sit.nss.vlak.config;

import cvut.fel.sit.nss.vlak.components.Abstract.Pipe;
import cvut.fel.sit.nss.vlak.components.Pipes.ConnectionPipe;
import cvut.fel.sit.nss.vlak.components.Pipes.FormPipe;
import cvut.fel.sit.nss.vlak.components.Pipes.PipeImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Pipe pipe() {
        return new PipeImpl(); // Assuming Pipe is a class in your project
    }
    @Bean
    public FormPipe formPipe() {
        return new FormPipe();
    }

    @Bean
    public ConnectionPipe connectionPipe() {
        return new ConnectionPipe();
    }
}
