package de.bucki;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Liveness;

import java.util.Arrays;
import java.util.HashMap; 

import javax.enterprise.context.ApplicationScoped;

@Liveness
@ApplicationScoped
public class AppPartZHealthCheck implements HealthCheck {
    private static boolean up = true;
    private static HashMap<String, String> strData = new HashMap<String, String>();

    static {
        strData.put("z1", "1000");
        strData.put("z2", "foobar");
    }

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("app part z");

        if (up) {
            responseBuilder.up();
        } else {
            responseBuilder.down();
        }

        for (String key : strData.keySet()) {
            responseBuilder.withData(key, strData.get(key));
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

    public static void changeData(String dataKey, String dataValue) {
        strData.put(dataKey, dataValue);  
    }

    public static void deleteData(String dataKey) {
        strData.remove(dataKey);
    }
}