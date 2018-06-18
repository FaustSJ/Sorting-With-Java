import java.util.*;

public class NumberStyle implements CitationFormatter{
	public String formatCitation(BibtexRecord b){
  	   	String citeS = "("+b.sortedOrder+")";
  	   	return citeS;
	}
}
