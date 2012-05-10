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
	 * 
	 * public static String citiesQuery =
	 * "PREFIX vcard:<http://www.w3.org/2006/vcard/ns#>" +
	 * "select distinct ?location " + "where {" + "  ?mensa vcard:adr ?adr." +
	 * "  ?adr vcard:locality ?location." + "}";
	 */

	/**
	 * query retrieving location mensa mensaname lat lon
	 */
	public static String mensaCityLatLonQuery = "PREFIX vcard:<http://www.w3.org/2006/vcard/ns#>"
			+ "PREFIX gr:<http://purl.org/goodrelations/v1#>"
			+ "select distinct ?location ?mensa ?mensaname ?lat ?lon "
			+ "where {"
			+ "   ?mensa  vcard:adr ?adr."
			+ "   ?mensa  gr:name ?mensaname."
			+ "   ?adr    vcard:locality ?location.	   "
			+ "   ?mensa  vcard:geo ?geo."
			+ "   ?geo    vcard:latitude ?lat."
			+ "   ?geo    vcard:longitude ?lon." + "}";

	/**
	 * query retrieving id, name, description, start, and end of all menus
	 */
	public static String menuQuery = "PREFIX gr:<http://purl.org/goodrelations/v1#>"
			+ "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
			+ "select distinct ?name ?description ?start ?end"
			+ " where{"
			+ "?id rdf:type gr:Offering."
			+ "?id gr:name ?name."
			+ "?id gr:description ?description."
			+ "?id gr:availabilityStarts ?start."
			+ "?id gr:availabilityEnds ?end." + "}";

	/**
	 * 
	 * @param name
	 * @param date
	 *            YYYY-MM-DD
	 * @return
	 */
	public static String getMenuQueryOfDay(String name, String date) {
		/**
		 * PREFIX gr:<http://purl.org/goodrelations/v1#> PREFIX
		 * rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX
		 * xsd:<http://www.w3.org/2001/XMLSchema#>
		 * 
		 * select ?name ?description ?start ?end where { ?mensaURI gr:name
		 * "BOKU Wien Mensa & M-Cafe Muthgasse". ?offer gr:availableAtOrFrom
		 * ?mensaURI. ?offer gr:name ?name. ?offer gr:description ?description.
		 * ?offer gr:availabilityStarts ?start. ?offer gr:availabilityEnds ?end.
		 * filter((?start >= "2012-04-23T00:00:00.0+02:00"^^xsd:dateTime &&
		 * ?start <= "2012-04-23T00:00:00.0+02:00"^^xsd:dateTime) || ( ?end >=
		 * "2012-04-23T00:00:00.0+02:00"^^xsd:dateTime && ?end <=
		 * "2012-04-23T23:59:59.0+02:00"^^xsd:dateTime) ) } order by (?start)
		 */
		String query = "PREFIX gr:<http://purl.org/goodrelations/v1#> "
				+ "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX xsd:<http://www.w3.org/2001/XMLSchema#> "
				+ "select ?name ?description ?start ?end" + " where{"
				+ " ?mensaURI gr:name \""
				+ name
				+ "\"."
				+ " ?offer gr:availableAtOrFrom ?mensaURI."
				+ " ?offer rdf:type gr:Offering."
				+ " ?offer gr:name ?name."
				+ " ?offer gr:description ?description."
				+ " ?offer gr:availabilityStarts ?start."
				+ " ?offer gr:availabilityEnds ?end."
				+ " filter("
				+ "(?start >= \""
				+ date
				+ "T00:00:00.0+02:00\"^^xsd:dateTime && "
				+ "?start <= \""
				+ date
				+ "T00:00:00.0+02:00\"^^xsd:dateTime) ||"
				+ "(?end >= \""
				+ date
				+ "T00:00:00.0+02:00\"^^xsd:dateTime && "
				+ " ?end <= \""
				+ date
				+ "T23:59:59.0+02:00\"^^xsd:dateTime) ) }"
				+ " order by (?start)";

		return query;
	}
}
