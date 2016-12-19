package de.netpage.vertx;

import de.netpage.vertx.service.StatusVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.ext.dropwizard.DropwizardMetricsOptions;

/**
 * This is the main class.
 *
 * @author denisw
 * @version 1.0
 * @since 19.12.16
 */
public class Application {

    private Application() {
        // only static methods
    }

    /**
     * Starts the application.
     *
     * @param args Command Line Arguments
     */
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx(new VertxOptions().setMetricsOptions(
                new DropwizardMetricsOptions()
                        .setEnabled(true)
                        .setJmxEnabled(true)
                        .setJmxDomain("vertx")
        ));
        vertx.deployVerticle(new StatusVerticle());
    }

}
