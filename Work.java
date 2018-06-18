import java.util.*;
import java.util.logging.*;

public class Work{
  // Good for warnings
  	private static Logger logger = Logger.getLogger("bibify");

////////////////////////////////////////////////////////////////////////////////
  	//sorts the records 
  	public static void sortBibtexRecords(ArrayList<BibtexRecord> bibs,
				       String bibstyle)throws Exception
	{
	   try{
		if(bibstyle.equals("author"))
		{
		//	sorts by last name of author
			Comparator<BibtexRecord> au = new SortAuthor();
			Collections.sort(bibs, au);
			//sets each record's sortedOrder
			int order = 1;
			for (BibtexRecord br : bibs){
				br.sortedOrder = order;
				order = order+1;
			}
		}
		else{
		   if(bibstyle.equals("bibkey"))
		   {
		   //	sorts by bibkey
			Comparator<BibtexRecord> bik = new SortBibkey();
			Collections.sort(bibs, bik);
			//sets each record's sortedOrder
			int order2 = 1;
			for (BibtexRecord br : bibs){
				br.sortedOrder = order2;
				order2 = order2+1;
			}
			
		   }
		   else{
			if(bibstyle.equals("text"))
			{
			// sorts by textOrder
			   Comparator<BibtexRecord> txt = new TextSort();
			   Collections.sort(bibs, txt);
			   //sets each record's sortedOrder
			   int order3 = 1;
			   for (BibtexRecord br : bibs){
				br.sortedOrder = order3;
				order3 = order3+1;
			   }
			}
			else{
			   if(bibstyle.equals("bibfile"))
			   {
			   //	sorts according to location in bib file
			   	Comparator<BibtexRecord> bif = new BibfileSort();
			   	Collections.sort(bibs, bif);
			   	//sets each record's sortedOrder
			   	int order4 = 1;
			   	for (BibtexRecord br : bibs){
				br.sortedOrder = order4;
				order4 = order4+1;
			}
			   }
			   else{throw new Exception();}
	   }	}  }	}
	   catch(Exception e){System.out.println("Problem: "+e);}
    		return;
    	}

////////////////////////////////////////////////////////////////////////////////
    	//gets the citations and returns a proper list of them
 	 public static ArrayList<BibtexRecord> 
 	 	extractCited(ArrayList<BibtexRecord> bibRecords,
		ArrayList<String> allKeys)throws Exception
		//bibRecords = bibliography
		//allKeys = citations
	{	//set bibRecords.textOrder = allKeys location
		
		//sorts the records according to bibkey
		Comparator<BibtexRecord> bik = new SortBibkey();
		Collections.sort(bibRecords, bik);
		
		//sets up a list of just bibkeys
		ArrayList<String> bKeys = new ArrayList<String>();
		for (BibtexRecord bib : bibRecords){
			if (bKeys.size()==0){
				bKeys.add(bib.bibkey);
			}
			else{
				//avoids any repeats
				int found = 0;
				for(String ss : bKeys){
					if(ss.equals(bib.bibkey)){
						found = 1;
					}
				}
				if (found==0){
					bKeys.add(bib.bibkey);
				}
			}
		}
		
		//citeList is the list of found citations
		ArrayList<BibtexRecord> citeList = new ArrayList<BibtexRecord>();
		for (String s : allKeys){
			int location = Collections.binarySearch(bKeys, s);
			if (location<0){
				System.out.println("Citation wasn't found");
			}
			else{
				
				for (BibtexRecord bib : bibRecords){
					//avoids repeats
					int find = 0;
					for(BibtexRecord c : citeList){
						if(bib.bibkey.equals(c.bibkey)){
							find = 1;
						}
					}
					if((bib.bibkey.equals(s))&&(find==0)){
						bib.textOrder = location+1;
						citeList.add(bib);
					}
				}
			}
		}
		
		
    		return citeList; //return arraylist of 
    	}

////////////////////////////////////////////////////////////////////////////////
    	//creats and object that properly formats each citation
  	public static CitationFormatter getCitationFormatter(String citestyle)
  	throws Exception
  	{
  	   try{
  	   	   if (citestyle.equals("number")){
  	   	   	         //cite as (#)
  	   	   	   	CitationFormatter cForm = new NumberStyle();
  	   	   	   	return cForm;
  	   	   }	   	   	      
  	   	   else{
  	   	   	         
  	   	   	   if (citestyle.equals("author")){
  	   	   	            //cite as (name, #) 
  				    CitationFormatter cForm = new AuthorStyle();
  				    return cForm;
  			   } 
  			   else{throw new Exception();}
  		   }
  	   }	      
  	   catch(Exception e){System.out.println("Problem: "+e);}
  	   CitationFormatter cForm = new AuthorStyle();
  	   return cForm;
    	}

////////////////////////////////////////////////////////////////////////////////
    	//puts the now formatted citations back into the original text
  	public static String insertCitations(String text, 
			 ArrayList<BibtexRecord> cited, 
			 CitationFormatter citeFormat)
	{
		String bk = "";
		String rep = "";
		String temp = "";
		for(BibtexRecord bi1 : cited){
			bk = bi1.bibkey;
			rep = citeFormat.formatCitation(bi1);
			temp = text.replace("\\cite{"+bk+"}", rep);
			text = temp;
		}
		
    		return text;
    	}
  
////////////////////////////////////////////////////////////////////////////////
	//creats a bibliography
  	public static String createBibliography(ArrayList<BibtexRecord> cited, 
			    CitationFormatter citeFormat)
	{
		//bibString is the string that hold the bibliography
		String bibString = "";
		String temp = "";
		String ciStr = "";
		String tempForm = "";
		for(BibtexRecord bi2 : cited){
		//citations are formatted and added
			ciStr = citeFormat.formatCitation(bi2);
			temp = bibString.concat(ciStr);
			bibString = temp.concat(" ");
		//the bibliography part is added
			tempForm = BibtexFormatter.formatItem(bi2);
			temp = bibString.concat(tempForm);
			bibString = temp.concat("\n");
		}
    		return bibString;
    	}
}
