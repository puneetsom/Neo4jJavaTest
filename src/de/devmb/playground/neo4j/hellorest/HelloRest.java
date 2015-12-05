package de.devmb.playground.neo4j.hellorest;

import javax.ws.rs.core.MediaType; 
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;

public class HelloRest {

  public static String SERVER_ROOT_URI = "http://localhost:7474/db/data/";

  public static void main(String[] args) {
    Client client = Client.create();
    client.addFilter(new LoggingFilter(System.out));
    WebResource cypher = client.resource(SERVER_ROOT_URI + "cypher");
//    String request = "{\"query\":\"MATCH (n) RETURN n\"}";
    
    String Query = "{\"query\":\"MATCH (n:Library) RETURN n\"}";
    
    ClientResponse cypherResponse = cypher.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, Query);
    cypherResponse.close();
    
    System.out.println(cypherResponse);
  }
}