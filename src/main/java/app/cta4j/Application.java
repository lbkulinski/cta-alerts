package app.cta4j;

import app.cta4j.module.ApplicationModule;
import app.cta4j.service.AlertService;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Application {
    public static void main(String[] args) {
        ApplicationModule module = new ApplicationModule();

        Injector injector = Guice.createInjector(module);

        AlertService service = injector.getInstance(AlertService.class);

        service.postAlerts();
    }
}
