import java.util.*;

public class SortBibkey implements Comparator<BibtexRecord>{	
	public int compare(BibtexRecord bib1, BibtexRecord bib2){
		return bib1.bibkey.compareToIgnoreCase(bib2.bibkey);
	}
}
