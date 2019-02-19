package edu.uga.cs;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionRemote;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;

/**
 * https://jena.apache.org/download/maven.html
 * https://jena.apache.org/tutorials/rdf_api.html
 * https://jena.apache.org/tutorials/rdf_api.html#ch-Writing%20RDF
 * https://jena.apache.org/documentation/rdfconnection/
 * https://jena.apache.org/tutorials/
 * @author abbas
 *
 */
public class RemoteJenaDriver {

	final static String ENDPOINT = "http://localhost:8890/sparql";
	final static String GSP = "http://prokino.uga.edu";
	final static String oGSP = "http://www.semanticweb.org/abbas/ontologies/2015/2/oscar";
	
	public static void main(String[] args) {
        RDFConnectionRemoteBuilder builder = RDFConnectionRemote.create()
                .destination(ENDPOINT)
                // Query only.
                .queryEndpoint("sparql")
                .gspEndpoint(oGSP)
                .updateEndpoint(null)
                .gspEndpoint(null);
        
        String queryString = "select ?s ?p ?o FROM <" + oGSP + "> where {?s ?p ?o} LIMIT 100";
            
        Query query = QueryFactory.create(queryString);

        // Whether the connection can be reused depends on the details of the implementation.
        // See example 5.
        try ( RDFConnection conn = builder.build() ) { 
            conn.queryResultSet(query, ResultSetFormatter::out);
        }
    }

}
