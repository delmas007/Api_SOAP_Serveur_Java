package org.example.Server;

import jakarta.xml.ws.Endpoint;
import org.example.Service.Cmu;

public class ServerCmu {
    public static void main(String[] args){
        String url = "http://localhost:8084/";

        try {
            Endpoint.publish(url, new Cmu());
            System.out.println("Service bien déployé");
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
