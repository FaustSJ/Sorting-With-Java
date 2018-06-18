// DO NOT MODIFY THIS FILE
import java.util.*;
import java.util.logging.*;

public class BibtexRecord{
  // Create - feel free to set public fields
  BibtexRecord(){
    this.keyVals = new HashMap<String,String>();
  }

  public String bibkey;		// Key like Kauffman2009
  public String type;		// Type of record: @article, @book, etc
  Integer textOrder;		// Order that the bib appeared in the text
  Integer bibfileOrder;		// Order that the bib appeared in the bibliography file
  Integer sortedOrder;		// Final sorted order of the bibliography

  // Put a new bit of content in the record like "journal","Proteins"
  public void put(String key, String value){
    this.keyVals.put(key,value);
  }

  // Return the title of the record
  public String title(){
    return this.get("title");
  }
  // Return the year of publication (as a string)
  public String year(){
    return this.get("year");
  }
  // Return the journal of the record
  public String journal(){
    return this.get("journal");
  }
  // Return just the last name of the first author
  public String firstAuthorLastName(){
    String fl[][] = new String[2][0];
    this.firstLastAuthorNames(fl);
    return fl[1][0];
  }

  // Return a formatted string of all authors separeted by commas, as
  // in
  // 
  // Chris Kauffman, Huzefa Rangwala, George Karyps
  public String author(){
    String fl[][] = new String[2][0];
    this.firstLastAuthorNames(fl);
    String firsts[] = fl[0];
    String lasts[] = fl[1];
    StringBuilder author = new StringBuilder();
    int n = firsts.length;
    for(int i=0; i<n-1; i++){
      author.append(firsts[i]);
      author.append(" ");
      author.append(lasts[i]);
      author.append(", ");
    }
    author.append(firsts[n-1]);
    author.append(" ");
    author.append(lasts[n-1]);
    return author.toString();
  }

  // Map field for all contents like journal = ...
  public Map<String,String> keyVals; 

  // Internal function to be used to get contents like the journal,
  // author, etc.  Not for public use.  Warns if the content can't be
  // found
  protected String get(String key){
    String result = this.keyVals.get(key);
    if(result == null){
      logger.warning(String.format("No %s found for %s",
				   key, this.bibkey));
      return "";
    }
    else{
      return result;
    }
  }

  // Internal function used to parse out first and last names of the
  // authors in the author bibtex field.  Fills in argument fl with
  // two equal sized arrays of 
  // fl[0] = first names
  // fl[1] = last names
  protected void firstLastAuthorNames(String fl[][]){
    String allAuth = this.get("author");
    if(allAuth == null){
      fl[0] = null;
      fl[1] = null;
    }
    String[] authors = allAuth.split("\\s+(and|;)\\s+");
    String[] firsts = new String[authors.length];
    String[] lasts = new String[authors.length];
    for(int i=0; i<authors.length; i++){
      String auth = authors[i];
      if(auth.contains(",")){
	String names[] = auth.split("\\s*,\\s*");
	firsts[i] = names[1];
	lasts[i] = names[0];
      }
      else{
	String names[] = auth.split("\\s+");
	for(int j=0; j<names.length-1; j++){
	  firsts[i] += names[j];
	}
	lasts[i] = names[names.length-1];
      }
    }
    fl[0] = firsts;
    fl[1] = lasts;
  }

  // For logging errors
  private static Logger logger = Logger.getLogger("bibify");
}

