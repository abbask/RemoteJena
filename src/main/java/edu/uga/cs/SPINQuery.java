package edu.uga.cs;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.jena.query.Query;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileUtils;
import org.topbraid.spin.arq.ARQ2SPIN;
import org.topbraid.spin.arq.ARQFactory;
import org.topbraid.spin.model.Select;

public class SPINQuery {
	
	public static void main(String[] args) {
		
		
		String query = "select distinct ?Concept where {[] a ?Concept} LIMIT 100";
		
		Model model = ModelFactory.createDefaultModel();
		Query arqQuery = ARQFactory.get().createQuery(model, query);
		ARQ2SPIN arq2SPIN = new ARQ2SPIN( model );
		Select sparqlQuery = (Select) arq2SPIN.createQuery( arqQuery, null );
		
//		System.out.println( "SPARQL Query:\n" + sparqlQuery );
//		
//		System.out.println( "\nSPIN Representation:" );
//		model.write( System.out, FileUtils.langTurtle );
//		
//		System.out.println( "\nSPIN Representation:" );
//		model.write( System.out, FileUtils.langXMLAbbrev );
		
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
		
		
		
		
		
	}

}
