// DO NOT MODIFY THIS FILE
import java.util.ArrayList;

public class Bibify{
  public static void main(String args[]) throws Exception {
    if(args.length < 4){
      System.out.println("usage: java blah textfile bibfile citstyle bibstyle");
      System.exit(-1);
    }
    String textfile = args[0];
    String bibfile = args[1];
    String citestyle = args[2];
    String bibstyle = args[3];
    
    // Create arraylist of bibtex records
    ArrayList<BibtexRecord> bibRecords = Parse.readBibtexRecords(bibfile);

    // Grab all citation keys
    String text = Parse.slurp(textfile);
    ArrayList<String> allKeys = Parse.getMatches(text,Parse.citationRE,1);

    // Identify all the bibtex records that were cited in the text
    ArrayList<BibtexRecord> cited = Work.extractCited(bibRecords, allKeys);
    
    // Determine the sorted ordering of the bib records
    Work.sortBibtexRecords(cited, bibstyle);

    // Determine citation formatter
    CitationFormatter citeFormat = Work.getCitationFormatter(citestyle);

    // Modify text
    String newText = Work.insertCitations(text, cited, citeFormat);

    // Create bibliography
    String bibliography = Work.createBibliography(cited, citeFormat);

    // Print the final result
    System.out.println(newText);
    System.out.println(bibliography);
  }
}
