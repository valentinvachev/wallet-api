package bg.wallet.www.project.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Working");
    }
}
