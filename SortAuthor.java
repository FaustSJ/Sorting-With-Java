import java.util.*;

public class SortAuthor implements Comparator<BibtexRecord>{
	
   public int compare(BibtexRecord bib1, BibtexRecord bib2){
   	   String b1 = bib1.firstAuthorLastName();
   	   String b2 = bib2.firstAuthorLastName();
   	   return b1.compareToIgnoreCase(b2);
   }
}
