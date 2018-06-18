import java.util.*;

public class AuthorStyle implements CitationFormatter{
	public String formatCitation(BibtexRecord b){
		String name = b.firstAuthorLastName();
  	   	String year = b.year();
  	   	String citeS = "("+name+", "+year+")";
  	   	//String citeS = "("+b.sortedOrder+")";
  	   	//System.out.println("here");
  	   	//System.out.println(b.sortedOrder);
  	   	return citeS;
	}
}
