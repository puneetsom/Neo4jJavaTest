package com.neo4j.puneet.query;

import java.util.Iterator;
import java.util.Map;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class JavaNeo4jCQLRetrivalTest {
    
	private Node node;;
   public static void main(String[] args) {
      GraphDatabaseFactory graphDbFactory = new GraphDatabaseFactory();

//      GraphDatabaseService graphDb = graphDbFactory.newEmbeddedDatabase("C:/TPNeo4jDB");
      GraphDatabaseService graphDb = graphDbFactory.newEmbeddedDatabase("C:/puneetsh/neo4j-community-2.1.3/data/graph.db");
      
      ExecutionEngine execEngine = new ExecutionEngine(graphDb);
      final String query = "MATCH (n:User) RETURN n LIMIT 5 ";
      
      final String userQuery = "MATCH (user:User{userName:'ranjana.bapat'})-[:PRIMARY_ADDRESS_OF]-(pAddress:PrimaryAddress) "+
    		  					" WITH user,pAddress MATCH (user)-[:SECONDARY_ADDRESS_OF]-(sAddress:SecondaryAddress) "+
    		  					" WITH user,pAddress,sAddress MATCH (user)-[:SOCIAL_NETWORK_OF]-(sn:SocialNetwork) "+
    		  					" WITH user,pAddress,sAddress,sn MATCH (user)-[:USER_OF]-(school:School) "+
    		  					" WITH user,pAddress,sAddress,sn,school MATCH (user)-[:CONTACT_OF]-(contact:Contact) " +
    		  					"RETURN user,pAddress,sAddress,sn,school,contact";
      
//      ExecutionResult execResult = execEngine.execute("MATCH (java:JAVA) RETURN java");
      
//      ExecutionResult execResult = execEngine.execute(query);
//      String results = execResult.dumpToString();
//      System.out.println(results);
      try {
    	  ExecutionResult execResult = execEngine.execute(userQuery);    	  
//    	  for (Map<String,Object> row : execResult) {
//    	    	        	   Node x = (Node)row.get("n");
//       	   System.out.println("\n Node : "+x.getId()+" -- "+x.getId());
//       	   for (String prop : x.getPropertyKeys()) {
////       		   System.out.println("\n");
//       		   System.out.println(prop +": "+x.getProperty(prop));
//       	   }
//       	}
    	  String results = execResult.dumpToString();
    	  System.out.println(results);
    	  
	} catch (Exception e) {
		// TODO: handle exception
		 System.out.println("\n : Catch block.");
	}
     
     
//     Iterator resultITR = execResult.iterator();
     
//     while (resultITR.hasNext()) {
//		Object object = (Object) resultITR.next();
//		System.out.println("object :"+object);
//	}
    
     
     
   }
}