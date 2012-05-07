package at.sti2.mensaapp;

/**
 * Unites all SparqlQueries that are needed in the project
 * 
 * @author michael
 *
 */
public class SparqlQueries {
	public static String scheme = "http";
	public static String host = "rdf.sti2.at";
	public static int port = 8080;
	public static String path= "/openrdf-sesame/repositories/lom4";
	

	/**
	 * all cities where mensas are located
	 */
	public static String citiesQuery = 
		"PREFIX vcard:<http://www.w3.org/2006/vcard/ns#>" +
		"select distinct ?location " +
		"where {" +
		"  ?mensa vcard:adr ?adr." +
		"  ?adr vcard:locality ?location." +
		"}";
	
	/**
	 * query retrieving location mensa mensaname lat lon
	 */
	public static String mensaCityLatLonQuery = 
		"PREFIX vcard:<http://www.w3.org/2006/vcard/ns#>" +
		"PREFIX gr:<http://purl.org/goodrelations/v1#>" +
		"select distinct ?location ?mensa ?mensaname ?lat ?lon " +
		"where {" +
		"   ?mensa  vcard:adr ?adr." +
		"   ?mensa  gr:name ?mensaname." +
		"   ?adr    vcard:locality ?location.	   " +
		"   ?mensa  vcard:geo ?geo." +
		"   ?geo    vcard:latitude ?lat." +
		"   ?geo    vcard:longitude ?lon." +
		"}";
}
