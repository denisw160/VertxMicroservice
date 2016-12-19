package de.netpage.vertx.service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.HashMap;
import java.util.Map;

import static io.vertx.core.http.HttpHeaders.CACHE_CONTROL;
import static io.vertx.core.http.HttpHeaders.CONTENT_TYPE;
import static io.vertx.core.http.HttpMethod.GET;

/**
 * This is the main service, which return the status object.
 *
 * @author denisw
 * @version 1.0
 * @since 19.12.16
 */
public class StatusVerticle extends AbstractVerticle {

    private static final int PORT = 9999;

    @Override
    public void start(Future<Void> fut) {
        Router router = Router.router(vertx);
        router.route(GET, "/status").
                produces("application/json").
                handler(this::healthRest);

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(PORT);
    }

    private void healthRest(final RoutingContext context) {
        final Map<String, Object> body = new HashMap<>();
        Status status = new Status();

        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        status.setLoad(osBean.getSystemLoadAverage());

        body.put("status", status);

        context.request().response().
                putHeader(CONTENT_TYPE, context.getAcceptableContentType()).
                putHeader(CACHE_CONTROL, "no-cache").
                end(new JsonObject(body).encode());
    }

}
