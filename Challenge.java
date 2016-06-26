
/**
 * Challenge program to match listings to products
 *
 * @author  Rogger Vasquez
 * @version 1.0
 * @since 2016-06-26
 */



import com.eclipsesource.json.*; // minimal lib to parse Json
import java.io.*;
import java.util.*;

public class Challenge
{
 /**
  * The main method will receive the name of 2 input files
  * @param args 2 parameters, name of 2 input files, the products and the lisitngs
  * @return Nothing, but produces a new file results.txt and a unmatchlistings.txt
  */

public static void main(String[] args)
 {

   MatchSolver matchSolver;
   List<Product> listOfProducts=null;
   listOfProducts = loadProductsFromFile (args[0]); // First load products.txt into a List of products
   if (listOfProducts==null)
   {
       System.err.println("Error: Products file cannot be loaded, exiting ");
       return;
   }
   // Main Loop, where basically instantiates a MatchSolver passing a list of products to work with.
   // Then read line by line the listings (instead of loading them all in a collection) , so we don't waste memory for the moment
   // and based on a greedy approach, matchSolver will try to match the current listing with a product (list of products), in case it does, matchSolver stores
   // the results in a internal results structure and then we can return it once we finished processing all listings.
   // if the matchSolver determines that the listing cannot be match, it is stored in another listingsNotAssigned .. so we can also take a look of them
   // to generate a separate file of this listings that were not matched with any product (for debuggin purposes)
    try
    {
           matchSolver =new MatchSolver(listOfProducts); // Instatiate the matchSolver passing the list of products
           FileInputStream fstream = new FileInputStream(args[1]);  // Try to read the listings.txt file
           BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
           String strLine;
           JsonObject jsonObject;
           while ((strLine = br.readLine()) != null)
           {
               jsonObject = JsonObject.readFrom( strLine );  // Parse the Json Object
               Listing listing = new Listing();
               mapJsonToListing(listing,jsonObject );
/* ===> */     matchSolver.matchListing (listing);  // Try to match the listing just read from the file, to a product, matchsolver stores results inside
           }
           br.close();

           // if we get to this point, we have processed all listings and try to match them all, so let's get the results
           MatchResults  results = matchSolver.getMatchResults();
           outPutResultsToFiles(results,"results.txt", "unmatchlistings.txt");

   }catch (Exception e)
      {
          System.err.println("Something went wrong: " + e.getMessage());
          return ;
      }

} // end of main

  /**
   * This method  outputs the results of the matching into 2 files, the listings matched and the unmatch listings
   * one in the resultsFileName and the other in the unMatchListingsFileName
   */
  public static void outPutResultsToFiles (MatchResults results, String resultsFileName, String unMatchListingsFileName)
  {

  }

 /**
  * This method loads the txt file in json format to a list of products
  * @param fileName, name of the file
  * @return List<Product>, List of products from file
  */
 public static List<Product> loadProductsFromFile (String fileName)
 {
   List<Product> products = new ArrayList<Product> ();
   try
   {
       // Open the file products.txt
        FileInputStream fstream = new FileInputStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;
        JsonObject jsonObject;
        while ((strLine = br.readLine()) != null)
        {
            jsonObject = JsonObject.readFrom( strLine );  // Parse the Json Object
            Product p = new Product();
            mapJsonToProduct (p,jsonObject); // Found that some products did not had family so threw a exception, so I created a mapping function
            products.add(p); //add to list
       }
        br.close();   //Close the input stream
    }catch (Exception e)
       {
           System.err.println("Error Loading Products file: " + e.getMessage());
           return null;
       }

   return products;
 }
 /**
  * This method maps a jsonobject from products file to the products class, checks if fields exists and not is null
  */
 private static void mapJsonToProduct (Product p, JsonObject jsonObject)
 {
   p.product_name = jsonObject.get( "product_name" )!=null ? jsonObject.get( "product_name" ).asString() : "";
   p.manufacturer = jsonObject.get( "manufacturer" )!=null ? jsonObject.get( "manufacturer" ).asString() : "";
   p.model= jsonObject.get( "model" )!=null ? jsonObject.get( "model" ).asString() : "";
   p.announced_date=jsonObject.get( "announced-date" )!=null ? jsonObject.get( "announced-date" ).asString() : "";
   p.family = jsonObject.get( "family" )!=null ? jsonObject.get( "family" ).asString() : "";

 }
 /**
  * This method maps a jsonobject from listings file to the listing class, checks if fields exists and not is null
  */
 private static void mapJsonToListing (Listing l, JsonObject jsonObject)
 {
   l.title = jsonObject.get( "title" )!=null ? jsonObject.get( "title" ).asString() : "";
   l.manufacturer = jsonObject.get( "manufacturer" )!=null ? jsonObject.get( "manufacturer" ).asString() : "";
   l.currency = jsonObject.get( "currency" )!=null ? jsonObject.get( "currency" ).asString() : "";
   l.price=jsonObject.get( "price" )!=null ? jsonObject.get( "price" ).asString() : "";

 }


 public static void testing()
 {
   String s1, s2,s3;    // The strings to find the edit distance between


   s1 = "Fuji_PinePix_T205" ;
   s2 = "Fuji_T205_PinePix" ;
   s3 = "Fuji" ;


   int d = Algorithms.editDistance(s1,s2);

   double db = Algorithms.similarity(s1,s3);
   double other = Algorithms.compareStrings(s1,s3);
     System.out.println("Distance:" + d);
     System.out.println("Common xx:" + Algorithms.longestCommonSubstring(s1,s2));
     System.out.println("similarity:" + db);
        System.out.println("Compare:" + other);


 }



}
