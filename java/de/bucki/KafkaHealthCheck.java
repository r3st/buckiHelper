package de.bucki;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;

@Readiness
@ApplicationScoped  
public class KafkaHealthCheck implements HealthCheck {
    private static boolean up = true;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("Kafka Connection");

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