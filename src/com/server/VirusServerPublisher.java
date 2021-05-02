package com.server;

import com.service.VirusService;
import jakarta.xml.ws.Endpoint;

import java.io.FileInputStream;
import java.util.Properties;

public class VirusServerPublisher {
    public static void main(String[] args) {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(System.getProperty("user.dir") + "/src/com/sample.config"));
            String serverEndpoint = properties.getProperty("server.endpoint");
            Endpoint ep = Endpoint.create(new VirusService());
            ep.publish(serverEndpoint);

            System.out.println("Server started at " + serverEndpoint);
            System.out.println("Press 'q' to quit.");

            char userInput = 'w';
            while (userInput != 'q') {
                userInput = (char) System.in.read();
            }
            ep.stop();
            System.out.println("Server stopped...");
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
