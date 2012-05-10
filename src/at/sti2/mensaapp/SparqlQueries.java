package at.sti2.mensaapp;

/**
 * Unites all SparqlQueries that are needed in the project
 * 
 * @author michael
 * 
 */
/**
 * @author lancelot
 *
 */
public class SparqlQueries {
	public static String scheme = "http";
	public static String host = "rdf.sti2.at";
	public static int port = 8080;
	public static String path = "/openrdf-sesame/repositories/lom4";

	// michael: http://85.127.38.54:8080/openrdf-sesame/repositories/lom
	// public static String scheme = "http";
	// public static String host = "85.127.38.54";
	// public static int port = 8080;
	// public static String path = "/openrdf-sesame/repositories/lom";

	// public static String scheme = "http";
	// public static String host = "127.0.0.1";
	// public static int port = 8080;
	// public static String path = "/openrdf-sesame/repositories/mensa";

	/**
	 * all cities where mensas are located
	 */
	public static String citiesQuery = "PREFIX vcard:<http://www.w3.org/2006/vcard/ns#>"
			+ "select distinct ?location " + "where {" + "  ?mensa vcard:adr ?adr."
			+ "  ?adr vcard:locality ?location." + "}";

	/**
	 * query retrieving location mensa mensaname lat lon
	 */
	public static String mensaCityLatLonQuery = "PREFIX vcard:<http://www.w3.org/2006/vcard/ns#>"
			+ "PREFIX gr:<http://purl.org/goodrelations/v1#>"
			+ "select distinct ?location ?mensa ?mensaname ?lat ?lon " + "where {"
			+ "   ?mensa  vcard:adr ?adr." + "   ?mensa  gr:name ?mensaname."
			+ "   ?adr    vcard:locality ?location.	   " + "   ?mensa  vcard:geo ?geo."
			+ "   ?geo    vcard:latitude ?lat." + "   ?geo    vcard:longitude ?lon." + "}";

	
	/**
	 * query retrieving id, name, description, start, and end of all menus 
	 */
	public static String menuQuery = "PREFIX gr:<http://purl.org/goodrelations/v1#>"
			+ "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
			+ "select distinct ?name ?description ?start ?end" + "where{"
			+ "?id rdf:type gr:Offering." + "?id gr:name ?name."
			+ "?id gr:description ?description." + "?id gr:availabilityStarts ?start."
			+ "?id gr:availabilityEnds ?end." + "}";

}
