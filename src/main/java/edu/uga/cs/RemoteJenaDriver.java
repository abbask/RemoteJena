package edu.uga.cs;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionRemote;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;
import org.apache.jena.util.FileUtils;
import org.topbraid.spin.arq.ARQ2SPIN;
import org.topbraid.spin.arq.ARQFactory;
import org.topbraid.spin.model.Select;
import org.topbraid.spin.system.SPINModuleRegistry;

/**
 * https://jena.apache.org/download/maven.html
 * https://jena.apache.org/tutorials/rdf_api.html
 * https://jena.apache.org/tutorials/rdf_api.html#ch-Writing%20RDF
 * https://jena.apache.org/documentation/rdfconnection/
 * https://jena.apache.org/tutorials/.
 * 
 * Some samples:
 * https://www.programcreek.com/java-api-examples/?api=org.apache.jena.rdf.model.Property
 * https://networkedplanet.com/blog/2015/10/16/sparql-construct-101.html
 * 
 * OWL:
 * https://jena.apache.org/documentation/ontology/index.html
 * https://jena.apache.org/documentation/ontology/index.html#ontology-properties
 * 
 * SPARQL;
 * https://www.w3.org/Submission/SPARQL-Update/#sec_examples
 * 
 * SPIN:
 * https://github.com/spinrdf/spinrdf/tree/master/src-examples/org/topbraid/spin/examples
 * https://github.com/spinrdf/spinrdf
 * 
 * @author abbas
 *
 */
public class RemoteJenaDriver {

	final static String ENDPOINT = "http://localhost:8890/sparql";
	//final static String GRAPH = "http://prokino.uga.edu";
	final static String oGRAPH = "http://www.semanticweb.org/abbas/ontologies/2015/2/oscar";
	
	//final static String NS = "http://www.semanticweb.org/abbas/ontologies/2015/2/oscar";
	
	public static void main(String[] args) {
		/*
		String constructQuery  = "prefix : <http://www.semanticweb.org/abbas/ontologies/2015/2/oscar#>"
        		+ "CONSTRUCT { ?p :hasSecret ?m . } FROM <" + oGRAPH + "> "
        		+ "WHERE {?p a :Producer. ?m a :Movie. ?p :featuredAs ?c. ?c :hasProduced ?m}";
                       
       construct(constructQuery);
       */
		/*
		String selectQuery = "prefix : <http://www.semanticweb.org/abbas/ontologies/2015/2/oscar#>"
        		+ " select ?s ?o FROM <" + oGRAPH + "> where {?s :hasSecret ?o} LIMIT 100";
        
		String selectQuery2 = "prefix : <http://www.semanticweb.org/abbas/ontologies/2015/2/oscar#>"
        		+ " select ?s ?m FROM <" + oGRAPH + "> where {?actrs a :Actress . ?actrs :actIn ?m} ";
		
		String selectQuery3 = "prefix : <http://www.semanticweb.org/abbas/ontologies/2015/2/oscar#>"
        		+ " select ?p ?m FROM <" + oGRAPH + "> where {?p a :Producer . ?p :hasProduced ?m.} LIMIT 100";
		*/
		String selectQuery4 = "prefix : <http://www.semanticweb.org/abbas/ontologies/2015/2/oscar#>"
        		+ " select ?p ?o FROM <" + oGRAPH + "> where {:cast074 ?p ?o} LIMIT 100";
		/*
		String selectQuery5 = "prefix : <http://www.semanticweb.org/abbas/ontologies/2015/2/oscar#>"
        		+ " select ?p ?c ?m FROM <" + oGRAPH + "> where {?p :featuredAs ?c. ?c :hasProduced ?m} LIMIT 100";
        */
		String allObjectPrp = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX owl: <http://www.w3.org/2002/07/owl#>"
				+ " SELECT DISTINCT ?domain ?name FROM <" + oGRAPH + "> WHERE { ?name a owl:ObjectProperty}";
        
		//selectQuery(selectQuery4); 
		selectQuery2(selectQuery4);
		
		//addObjectProperty();
        
    }
	
	public static void addObjectProperty() {
		//createOntologyModel
		RDFConnectionRemoteBuilder builder = RDFConnectionRemote.create()
                .destination(ENDPOINT)
                .queryEndpoint("sparql")            
                .updateEndpoint(null)
                .gspEndpoint(null);
		
		OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
		
		
//		System.out.println("Query Cosntruct: \n" + query.toString());
		
		//builder.bu
		
		
		
		
//		OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
//		OntClass programme = m.createClass( NS + "Programme" );
//		OntClass orgEvent = m.createClass( NS + "OrganizedEvent" );
//
//		ObjectProperty hasProgramme = m.createObjectProperty( NS + "hasProgramme" );


	}
	
	public static void construct(String queryString) {
		RDFConnectionRemoteBuilder builder = RDFConnectionRemote.create()
                .destination(ENDPOINT)
                // Query only.
                .queryEndpoint("sparql")            
                .updateEndpoint("sparql")
                .gspEndpoint(null);
		
		Query query = QueryFactory.create(queryString) ;
		
		//System.out.println("Query Cosntruct: \n" + query.toString());
		

		try ( RDFConnection conn = builder.build() ) { 
            Model resultingModel = conn.queryConstruct(query);
        }
		
		
		
	}
	
	public static void selectQuery2(String queryString) {
		QueryExecution qexec = QueryExecutionFactory.sparqlService(ENDPOINT, queryString);
		ResultSet rs = qexec.execSelect();
		while(rs.hasNext()) {
			QuerySolution sol = rs.nextSolution();
			System.out.println( sol.getResource("p").toString() + " :: " + sol.getResource("o").toString() );
			
		}
		
	}
	
	public static void selectQuery(String queryString) {
		RDFConnectionRemoteBuilder builder = RDFConnectionRemote.create()
                .destination(ENDPOINT)
                // Query only.
                .queryEndpoint("sparql")            
                .updateEndpoint(null)
                .gspEndpoint(null);
        
//        String queryString = "select ?s ?p ?o FROM <" + oGSP + "> where {?s ?p ?o} LIMIT 100";
        
		
        Query query = QueryFactory.create(queryString);
        //System.out.println("Query : \n" + query.toString());
        
        // Whether the connection can be reused depends on the details of the implementation.
        // See example 5.
        try ( RDFConnection conn = builder.build() ) { 
            conn.queryResultSet(query, ResultSetFormatter::toList);
        }
        System.out.println("Done.");

	}
	
	public static void SPINOperation() {
		// Register system functions
		SPINModuleRegistry.get().init();
		
		// Create an empty OntModel
		final Model model = ModelFactory.createDefaultModel();
		
		String query = "select distinct ?Concept where {[] a ?Concept} LIMIT 100";
		//String query = "PREFIX ta:      <https://tutorial-academy.com/2015/spin#> PREFIX sp:      <http://spinrdf.org/sp#>  PREFIX rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#> SELECT ( ( AVG ( ?value ) ) AS ?average ) ?sensor WHERE { 	?measurement 	rdf:type 		ta:Measurement .     ?measurement 	ta:timestamp 	?timestamp .     ?measurement ta:value 		?value .     ?measurement 	ta:sensor 		?sensor . } GROUP BY ( ?sensor ) ORDER BY DESC ( ?average ) ";
					
		Query arqQuery = ARQFactory.get().createQuery( model, query );
		ARQ2SPIN arq2SPIN = new ARQ2SPIN( model );
		
		Select sparqlQuery = (Select) arq2SPIN.createQuery( arqQuery, null );
		
		System.out.println( "SPARQL Query:\n" + sparqlQuery );
		
		System.out.println( "\nSPIN Representation:" );
		model.write( System.out, FileUtils.langTurtle );
		
		System.out.println( "\nSPIN Representation:" );
		model.write( System.out, FileUtils.langXMLAbbrev );
		
		//String fileNameRDF = "result-simple.rdf";
		String fileNameOWL = "result-simple.owl";
		//FileWriter outRDF ;
		FileWriter outOWL ;
		try {
			//outRDF = new FileWriter( fileNameRDF );
			//model.write(outRDF, "RDF/XML");
			
			outOWL = new FileWriter( fileNameOWL );
			model.write(outOWL, "RDF/XML-ABBREV");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//				
//	}
	}

}
