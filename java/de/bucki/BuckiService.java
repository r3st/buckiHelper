package de.bucki;

import javax.enterprise.context.ApplicationScoped;
import java.net.InetAddress;
import java.net.UnknownHostException;
import de.bucki.AliveHealthCheck;
import de.bucki.DBHealthCheck;
import de.bucki.KafkaHealthCheck;
import de.bucki.AppPartXHealthCheck;
import de.bucki.AppPartYHealthCheck;
import de.bucki.AppPartZHealthCheck;

@ApplicationScoped
public class BuckiService {
   public String getHostname() {
      String hostname;
      try {
         hostname = InetAddress.getLocalHost().getHostName();
      } catch (UnknownHostException e) {
         hostname = "unknown";
         e.printStackTrace();
      }
      return hostname;
   }
    
    public String changeCheck(String name) {
        String changed="change done";
        switch(name) {
            case "ALIVE":
               AliveHealthCheck.changeState();
               break;
            case "DB Connection":
               DBHealthCheck.changeState();
               break;
            case "Kafka Connection":
               KafkaHealthCheck.changeState();
               break;
            case "app part x":
               AppPartXHealthCheck.changeState();
               break;
            case "app part y":
               AppPartYHealthCheck.changeState();
               break;
            case "app part z":
               AppPartZHealthCheck.changeState();
               break;   
            default:
               changed="change failed";
               break;
      }
      return changed;
    }

    public String changeCheckData(String name, String dataKey, String dataValue) {
        String changed="change done";
        switch(name) {
            case "app part x":
               AppPartXHealthCheck.changeData(dataKey, dataValue);
               break;
            case "app part y":
               AppPartYHealthCheck.changeData(dataKey, dataValue);
               break;
            case "app part z":
               AppPartZHealthCheck.changeData(dataKey, dataValue);
               break;
            default:
               changed="change failed";
               break;
        }

        return changed;
    }

    public String deleteCheckData(String name, String dataKey) {
      String changed="change done";
      switch(name) {
          case "app part x":
             AppPartXHealthCheck.deleteData(dataKey);
             break;
          case "app part y":
             AppPartYHealthCheck.deleteData(dataKey);
             break;
          case "app part z":
             AppPartZHealthCheck.deleteData(dataKey);
             break;
          default:
             changed="change failed";
             break;
      }

      return changed;
    } 


    public String checkName(String name) {
        return name;
    }

}