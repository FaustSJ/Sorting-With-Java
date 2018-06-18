// DO NOT MODIFY THIS FILE
import java.util.ArrayList;
import java.util.regex.*;
import java.io.*;
import java.util.logging.*;

public class Parse {
  private static Logger logger = Logger.getLogger("bibify");

  // Read a whole file as a string
  // http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file
  public static String slurp( String file ) throws IOException {
    BufferedReader reader = new BufferedReader( new FileReader (file));
    String         line = null;
    StringBuilder  stringBuilder = new StringBuilder();
    String         ls = System.getProperty("line.separator");

    while( ( line = reader.readLine() ) != null ) {
      stringBuilder.append( line );
      stringBuilder.append( ls );
    }
    reader.close();
    return stringBuilder.toString();
  }

  // Match a citation
  public static String citationRE = "\\\\cite\\{(\\w+)\\}";

  // Generic matcher: pull out all Strings in contents which match
  // regex re and are in match-group group
  public static ArrayList<String> getMatches(String contents, String re, int group){
    ArrayList<String> records = new ArrayList<String>();
    Matcher m = Pattern.compile(re).matcher(contents);
    while(m.find()){
      records.add(m.group(group));
    }
    return records;
  }
    
  // Matches whole bibtex record. Match groups are type, key, 
  // content1, and content
  public static String bibtexRecordRE = 
    "@(\\w+)\\s*\\{(\\w+)\\s*\\,\\s*(\\w+\\s*=\\s*\\{[^}]*\\}\\s*(\\,\\s*\\w+\\s*=\\s*\\{[^}]*\\}\\s*)*)\\s*\\}";

  public static String bibtexContentRE = 
    "(\\w+)\\s*=\\s*\\{([^}]*)\\}\\s*[,]?";

  // Divide a String up into other an array of Strings which are
  // individual bibtex entries
  public static BibtexRecord parseBibtex(String bibcontent)
    throws Exception
  {
    Matcher m = Pattern.compile(bibtexRecordRE).matcher(bibcontent);
    if(!m.find() || m.groupCount() < 2){
      throw new Exception("Something is wrong with a bibtex record string\n"+bibcontent);
    }
    BibtexRecord b = new BibtexRecord();
    b.type = m.group(1);
    b.bibkey = m.group(2);
    String contents = m.group(3);
    Matcher cm = Pattern.compile(bibtexContentRE).matcher(contents);
    while(cm.find()){
      String key = cm.group(1);
      String val = cm.group(2);
      b.put(key,val);
    }
    return b;
  }
  
  // Goes through file, parses bibtex records, returns as an array.
  // The bibfileOrder field is set as the records are read in.
  public static ArrayList<BibtexRecord> readBibtexRecords(String bibfile)
    throws IOException
  {
    // Split up the bibtex records into individual strings
    String bib = Parse.slurp(bibfile);
    ArrayList<String> stringRecords = Parse.getMatches(bib,Parse.bibtexRecordRE,0);

    // Create arraylist of bibtex records
    ArrayList<BibtexRecord> bibRecords = new ArrayList<BibtexRecord>();
    for(int i=0; i<stringRecords.size(); i++){
      String s = stringRecords.get(i);
      try{
	BibtexRecord b = Parse.parseBibtex(s);
	bibRecords.add(b);
	b.bibfileOrder = bibRecords.size();
      }
      catch(Exception e){
	logger.warning(String.format("Problem with record %d, skipping: %s",
				     i,e.getMessage()));
      }

    }
    return bibRecords;
  }

}
