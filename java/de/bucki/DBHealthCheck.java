package de.bucki;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;

@Liveness
@ApplicationScoped  
public class DBHealthCheck implements HealthCheck {
    private static boolean up = true;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("DB Connection");

        if (up) {
            responseBuilder.up();
        } else {
            responseBuilder.down();
        }

        return responseBuilder.build();
    }

    public static void changeState() {
        if (up) {
            up = false;
        } else {
            up = true;
        }
    }
}