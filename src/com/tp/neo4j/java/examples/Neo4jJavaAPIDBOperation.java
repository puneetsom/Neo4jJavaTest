package com.tp.neo4j.java.examples;

import static org.neo4j.io.fs.FileUtils.deleteRecursively;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.helpers.collection.IteratorUtil;

public class Neo4jJavaAPIDBOperation {

//	private static final String DB_PATH = "target/graph.db";
	private static final String DB_PATH = "C:/puneetsh/neo4j-community-2.1.3/data/graph.db";
	String resultString;
	String columnsString;
	String nodeResult;
	String rows = "";

	public static void main(String[] args) {
		JavaQuery javaQuery = new JavaQuery();
		javaQuery.run();
	}

	void run() {
		clearDbPath();

		 final String userQuery = "MATCH (user:User{userName:'ranjana.bapat'})-[:PRIMARY_ADDRESS_OF]-(pAddress:PrimaryAddress) "+
					" WITH user,pAddress MATCH (user)-[:SECONDARY_ADDRESS_OF]-(sAddress:SecondaryAddress) "+
					" WITH user,pAddress,sAddress MATCH (user)-[:SOCIAL_NETWORK_OF]-(sn:SocialNetwork) "+
					" WITH user,pAddress,sAddress,sn MATCH (user)-[:USER_OF]-(school:School) "+
					" WITH user,pAddress,sAddress,sn,school MATCH (user)-[:CONTACT_OF]-(contact:Contact) " +
					"RETURN user,pAddress,sAddress,sn,school,contact";
		// START SNIPPET: addData
		GraphDatabaseService db = new GraphDatabaseFactory()
				.newEmbeddedDatabase(DB_PATH);

//		try (Transaction tx = db.beginTx()) {
//			Node myNode = db.createNode();
//			myNode.setProperty("name", "my node");
//			tx.success();
//		}
		// END SNIPPET: addData

		// START SNIPPET: execute
		try (Transaction ignored = db.beginTx();
				Result result = db
						.execute(userQuery)) {
			while (result.hasNext()) {
				Map<String, Object> row = result.next();
				for (Entry<String, Object> column : row.entrySet()) {
					rows += column.getKey() + ": " + column.getValue() + "; ";
				}
				rows += "\n";
			}
			System.out.println("rows --> "+rows.toString());
		}
		// END SNIPPET: execute
		// the result is now empty, get a new one
		try (Transaction ignored = db.beginTx();
				Result result = db
						.execute(userQuery)) {
			// START SNIPPET: items
			Iterator<Node> n_column = result.columnAs("n");
			for (Node node : IteratorUtil.asIterable(n_column)) {
				nodeResult = node + ": " + node.getProperty("name");
			}
			
			System.out.println("nodeResult --> "+nodeResult);
			// END SNIPPET: items

			// START SNIPPET: columns
			List<String> columns = result.columns();
			// END SNIPPET: columns
			columnsString = columns.toString();
			resultString = db.execute(userQuery)
					.resultAsString();
			System.out.println(resultString);
		}

//		db.shutdown();
	}

	private void clearDbPath() {
		try {
			deleteRecursively(new File(DB_PATH));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}