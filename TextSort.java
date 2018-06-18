import java.util.*;

public class TextSort implements Comparator<BibtexRecord>{
	public int compare(BibtexRecord bib1, BibtexRecord bib2){
		if (bib1.textOrder>bib2.textOrder){return -1;}
		if (bib1.textOrder==bib2.textOrder){return 0;}
		if (bib1.textOrder<bib2.textOrder){return 1;}
		return bib1.textOrder - bib2.textOrder;
	}
}
