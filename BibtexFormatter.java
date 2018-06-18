// DO NOT MODIFY THIS CLASS
public class BibtexFormatter{
  // Format a BibtexRecord for display.  Currently simply appends
  // authors, title, journal, and year as in
  // 
  // Chris Kauffman, George Karypis. LIBRUS: combined machine learning and homology information for sequence-based ligand-binding residue prediction. Bioinformatics. 2009.
  // 
  public static String formatItem(BibtexRecord b){
    StringBuilder bibString = new StringBuilder();
    bibString.append(b.author());
    bibString.append(". ");
    bibString.append(b.title());
    bibString.append(". ");
    bibString.append(b.journal());
    bibString.append(". ");
    bibString.append(b.year());
    bibString.append(".");
    return bibString.toString();
  }
}
