
import java.util.*;

public class SearchSortComplete {
  public static boolean debug = false;
  public static Scanner sc = new Scanner (System.in);
  
  
  public static void main(String[] args) {
    
    // a value to search for, hoping for its index in the array.
    int key = 0;
    
    // a resulting index to be returned from a search.
    int result = 0;
    
    //does the user want to quit?
    boolean quit = false;
    
    //change this however you like.
    int[] x = { 3, 5, 2, 7, 4, 6, 0, 10, 3 };
    int[] copyOfX = new int[x.length];
    
    //why can't we just say "copyOfX = x;" ?
    for (int i = 0; i < x.length; i++) {
      copyOfX[i] = x[i];
    }
    
    while (!quit) {
      printMenu();
      
      //get a number from 1-9 from the user.
      int choice = getChoice(9);
      
      //just these options need an extra value, for the key.
      if (choice == 5 || choice == 6) {
        key = getChoice();
      }
      
      //dispatch the requested operation.
      switch (choice) {
        case 1:
          bubbleSort(x);
          break;
        case 2:
          insertionSort(x);
          break;
        case 3:
          mergeSort(x);
          break;
        case 4:
          quickSort(x);
          break;
        case 5:
          result = linearSearch(key, x);
          displaySearchResult(result, key);
          break;
        case 6:
          result = binarySearch(key, x);
          displaySearchResult(result, key);
          break;
        case 7:
          printArray(x);
          break;
        case 8:
          for (int i = 0; i < x.length; i++) {
              x[i] = copyOfX[i];
          }
          printArray(x);
          break;
        case 9:
          quit = true;
          break;
        default:
          System.out.println("try again!");
      }
    }
  }
  
  /*
   Sorting Methods Section.
   */
  
  public static void bubbleSort(int[] a) {
    int swaps = 0;
    int temp = -1;
    // perform one 'bubbling' pass (n-1) times.
    for (int count = 0; count < a.length-1; count++) {
      for (int i = 0; i < a.length-1; i++) {
        //if these two are out of order, swap them.
        if (a[i] > a[i+1]) {
          if (debug) {
            System.out.println("swapping "+a[i]+"@[" + i + "] and " + a[i+1]+" @[" + (i+1) + "].");
            swaps++;
          }
          //swap these locations.
          temp = a[i];
          a[i] = a[i+1];
          a[i+1] = temp;
        }
      }
    }
    if (debug)
      System.out.println("total swaps: " + swaps);
  }
  
  public static void insertionSort(int[] a) {
    int swaps = 0;
    for (int i = 1; i < a.length; i++) {
      for (int j = i; j >0; j--) {
        // if necessary, swap value at j with value at (j-1).
        if (a[j-1] > a[j]) {
          //before swapping, mention it.
          if (debug) {
            System.out.println("swapping a[" + j + "-1]=" + a[j-1] + " and a["+ j + "]=" + a[j]);
            printArray(a);
            swaps++;
          }
          int temp = a[j];
          a[j] = a[j-1];
          a[j-1] = temp;
        }
      }
    }
    if (debug)
      System.out.println("Number of swaps=" + swaps);
  }
  
  public static void mergeSort(int[] a) {
    
    //base cases
    if (a.length <= 1) {
      return;
    }
    
    int halfnum = a.length / 2;
    
    //make two arrays. secondHalf might be one-longer than firstHalf for odd-length arrays.
    int[] firstHalf = new int[halfnum];
    int[] secondHalf = new int[a.length - halfnum];
    
    //copy in the values to firstHalf and secondHalf.
    //Note the guard statement (i<halfnum), not (i<a.length).
    for (int i = 0; i < halfnum; i++) {
      firstHalf[i] = a[i];
    }
    for (int i=0; i<secondHalf.length;i++){
      secondHalf[i] = a[i + halfnum];
    }
    
    if (debug) {
      System.out.println("to merge: " + a.length);
      printArray(a);
      printArray(firstHalf);
      printArray(secondHalf);
    }
    
    //sort the two halves--Recursively!
    mergeSort(firstHalf);
    mergeSort(secondHalf);
    
    if (debug) {
      System.out.println("having merged: " + a.length);
      printArray(a);
      printArray(firstHalf);
      printArray(secondHalf);
    }
    
    /**
     * merge the two halves back together. If you think of the smallest
     * cases, this part actually does the sorting!
     */
    int ifst = 0;
    int isnd = 0;
    
    for (int i = 0; i < a.length; i++) {
      //when there are no more values in the first half.
      if (ifst >= firstHalf.length) {
        a[i] = secondHalf[isnd++];
      }
      //when there are no more values in the second half.
      else if (isnd >= secondHalf.length) {
        a[i] = firstHalf[ifst++];
      }
      //if both have values left, and the first's next one is smaller.
      else if (firstHalf[ifst] <= secondHalf[isnd]) {
        a[i] = firstHalf[ifst++];
      }
      //if both have values left, and the second's next one is smaller.
      else {
        a[i] = secondHalf[isnd++];
      }
    }
    
    if (debug) {
      System.out.println("\tending a length: " + a.length);
      printArray(a);
    }
  }
  
  public static void quickSort(int[] a) {
    quickSort(a, 0, a.length - 1);
  }
  
  public static void quickSort(int[] a, int leftIndex, int rightIndex) {
    if (debug)
      System.out.println("quicksorting from indexes " + leftIndex + " to " + rightIndex);
    if (leftIndex == rightIndex) {
      return;
    }
    // choose a pivot. we are arbitrarily choosing the middle value,
    // which isn't that better than any other value. But if the data
    // happen to be mostly sorted, this will tend to do all right.
    int pivotIndex = (leftIndex + rightIndex) / 2;
    if (debug) System.out.println("\t with pivot " + pivotIndex);
    
    //swap pivot value to far left of region.
    int temp = a[leftIndex];
    a[leftIndex] = a[pivotIndex];
    a[pivotIndex] = temp;
    //realign pivot index.
    pivotIndex = leftIndex;
    
    //partition values around the pivot.
    //i walks right, and pivot swaps smaller values to the left side of it.
    for (int i = leftIndex + 1; i <= rightIndex; i++) {
      if (a[pivotIndex] > a[i]) {
        if (debug)
          System.out.println("\t[swapping " + i + " " + pivotIndex + " " + (pivotIndex + 1));
        //three-way swap. This places the smaller-than-pivot value from a[i]
        // at the old pivot's location, moves the pivot value to the right one,
        // and moves this value displaced by the pivot to where the smaller one 
        // happened to be (i.e., anywhere to the right of the pivot).
        temp = a[pivotIndex];
        a[pivotIndex] = a[i];
        a[i] = a[pivotIndex + 1];
        a[pivotIndex + 1] = temp;
        
        //realign pivot index.
        pivotIndex++;
      }
    }
    
    //print out our partially sorted array.
    if (debug) {
      System.out.println("pivot now =" + pivotIndex);
      printArray(a);
    }
    
    //quickSort the two partitions.
    if (pivotIndex > leftIndex) {
      if (debug)
        System.out.println("running left...\n");
      quickSort(a, leftIndex, pivotIndex - 1);
    }
    if (pivotIndex < rightIndex) {
      if (debug)
        System.out.println("running right...\n");
      quickSort(a, pivotIndex + 1, rightIndex);
    }
  }
  
  /*
   Searching Methods Section.
   */
  
  public static int linearSearch(int k, int[] a) {
    for (int i = 0; i < a.length; i++) {
      if (a[i] == k) {
        return i;
      }
    }
    return -1;
  }
  
  public static int binarySearch(int k, int[] a) {
    return binarySearch(k, a, 0, a.length - 1);
  }
  
  private static int binarySearch(int k, int[] a, int left, int right) {
    
    int middle = (left + right) / 2;
    if (a[middle] == k) {
      return middle;
    }
    if (left == right) {
      return -1;
    } else if (a[middle] > k) {
      return binarySearch(k, a, left, middle - 1);
    } else {
      return binarySearch(k, a, middle + 1, right);
    }
  }
  
  /**
   * Helpful Functions Section. These all make the main method more
   * "streamlined."
   */
  
  public static void printArray(int[] a) {
    //a slightly smart printing function.
    String vals = "values:";
    String indices = "index: ";
    for (int i = 0; i < a.length; i++) {
      if (a[i] < 10)
        vals += " ";
      if (a[i] < 100)
        vals += " ";
      if (a[i] >= 0)
        vals += " ";
      vals += a[i];
      
      if (i < 10)
        indices += " ";
      if (i < 100)
        indices += " ";
      indices += " " + i;
    }
    System.out.println(vals + "\n" + indices);
  }
  
  public static int getChoice(int num) {
    int x = -1;
    while (x < 1 || x > num) {
      try {
        x = sc.nextInt();
      } catch (Exception e) {
        System.out
          .println("Whoops!  try again--enter a menu option from 1 to "
                     + num + ":");
        sc.nextLine();
      }
    }
    return x;
  }
  
  public static int getChoice() {
    System.out.println("please enter a number:");
    while (true) {
      try {
        return sc.nextInt();
      } catch (Exception e) {
        System.out.println("Whoops!  try again--enter a number:");
        sc.nextLine();
      }
    }
  }
  
  public static void printMenu() {
    System.out.println("Please choose:");
    System.out.println("[1]Bubble Sort");
    System.out.println("[2]Insertion Sort");
    System.out.println("[3]Merge Sort");
    System.out.println("[4]Quick Sort");
    System.out.println("[5]Linear Search");
    System.out.println("[6]Binary Search");
    System.out.println("[7]Print the Array");
    System.out.println("[8]Reset the Array");
    System.out.println("[9]Quit!");
  }
  
  public static void displaySearchResult(int r, int k) {
    //display results for
    if (r != -1) {
      System.out.println("The index for key=" + k + " is " + r);
    } else {
      System.out.println("The key=" + k + " was not found in the array.");
    }
    
  }
  
}