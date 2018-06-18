import java.util.*;

public class BibfileSort implements Comparator<BibtexRecord>{
	public int compare(BibtexRecord bib1, BibtexRecord bib2){
		return bib1.bibfileOrder - bib2.bibfileOrder;
	}
}
