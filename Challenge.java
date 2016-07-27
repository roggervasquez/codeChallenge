
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
  * @param args 3 parameters, name of 2 input files, the products and the lisitngs and a option to see if running some tests
  * @return Nothing, but produces a new file results.txt and a unmatchlistings.txt
  */
  private static final int DOTINTERVAL=100;
public static void main(String[] args)
 {
   if (args.length<2)
   { System.err.println("At least 2 parameters are needed, products file name, listings filename");
     return;
   }
   else
   { // has a 3 parameter, can be the option to run the tests
     if (args.length==3)
          runTests(args[2]) ; // If appropiate command line parameter 'runtests', then will run some tests.
   }
  //---------------------------------------------------------------------------------------------------------------------------
   MatchSolver matchSolver;
   List<Product> listOfProducts=null;
   System.out.println("Starting : Loading products file ...");
   listOfProducts = AppUtils.loadProductsFromFile (args[0]); // First load products.txt into a List of products
   if (listOfProducts==null)
   {
       System.err.println("Error: Products file cannot be loaded, exiting ");
       return;
   }
   System.out.println("Finished : Loading products file !!!");
   // Main Loop, where basically instantiates a MatchSolver passing a list of products to work with.
   // Then read line by line the listings (instead of loading them all in a collection) , so we don't waste memory for the moment
   // and based on a greedy approach, matchSolver will try to match the current listing with a product (list of products), in case it does, matchSolver stores
   // the results in a internal results structure and then we can return it once we finished processing all listings.
   // if the matchSolver determines that the listing cannot be match, it is stored in another listingsNotAssigned .. so we can also take a look of them
   // to generate a separate file of this listings that were not matched with any product (for debugging purposes)
    try
    {
           System.out.println("Starting : Initializing MatchSolver with the List of Products ...");
           matchSolver =new MatchSolver(listOfProducts); // Instatiate the matchSolver passing the list of products
           System.out.println("Finished : MatchSolver Initialized with the List of Products !!!");
           FileInputStream fstream = new FileInputStream(args[1]);  // Try to read the listings.txt file
           BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
           String strLine;
           JsonObject jsonObject;
           System.out.println("Starting : Read the Listing file line by line and process each listing ...");
           System.out.println("Note: Every DOT represents " +  DOTINTERVAL + " listings processed :)");
           int count =0;
           while ((strLine = br.readLine()) != null) // Read line by line every listing
           {
               jsonObject = JsonObject.readFrom( strLine );  // Parse the Json Object
               Listing listing = new Listing();
               AppUtils.mapJsonToListing(listing,jsonObject );
/* ===> */     matchSolver.matchListing (listing,0.9);  // Try to match the listing just read from the file, to a product, matchsolver stores results inside
               count++;
               if (count%DOTINTERVAL==0)
                  System.out.print(".");
           }
           br.close();
           System.out.println("\nFinished : Listings file processed !!!");
           // Lets try to remove some false positives by it's price, all listings with checkPRice=true we will need to check them
            System.out.println("Starting: Check false positives by Price Range ...");
           matchSolver.checkListingsbyPrice(0.0,0.20); // Acceptable range of deviation of price
            System.out.println("Finished: Checked false positives by Price Range !!!");
           // if we get to this point, we have processed all listings and try to match them all, so let's get the results
           MatchResults  results = matchSolver.getMatchResults();

           System.out.println("Starting: Generating results.txt file ...");
           AppUtils.outPutResultsToFile(results.productsListings,"results.txt");
           System.out.println("Finished: Generating results.txt file !!!");

           AppUtils.outPutUnMatchListingsToFile(results.listingsNotAssigned,"unmatchlistings.txt");

   }catch (Exception e)
      {
          System.err.println("Something went wrong: " + e.getMessage());
          return ;
      }

} // end of main


 public static void runTests(String s)
 {
   if (s!=null && s.equals("runtests") ) // Just to run some tests ...
   {
      TestClass test = new TestClass();
      test.runAllTests();

    }
 }

}
