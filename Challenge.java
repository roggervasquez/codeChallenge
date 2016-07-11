
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

public static void main(String[] args)
 {
   if (args.length<2)
   { System.err.println("At least 2 parameters are needed, products file name, listings filename");
     return;
   }
   else{ // has a 3 parameter, can be the option to run the tests
     if (args.length==3)  runTests(args[2]) ; // If appropiate command line parameter 'runtests', then will run some tests.
   }

  //---------------------------------------------------------------------------------------------------------------------------
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
           outPutResultsToFile(results.productsListings,"results.txt");
           outPutUnMatchListingsToFile(results.listingsNotAssigned,"unmatchlistings.txt");

   }catch (Exception e)
      {
          System.err.println("Something went wrong: " + e.getMessage());
          return ;
      }

} // end of main

  /**
   * This method  outputs the results of the matching into 1 file, the listings matched
   */
  public static void outPutResultsToFile (ArrayList<ProductListing> productsListings, String resultsFileName)
  {
    try
    {
        File fileOutput = new File(resultsFileName);
      	FileOutputStream fileOutPutStream = new FileOutputStream(fileOutput);

      	BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(fileOutPutStream));

      	for (int i = 0; i < productsListings.size(); i++)
        {
          ProductListing productListing = productsListings.get(i);
          if (productListing.listings.size()>0)
          {
            // Use a string builder instead. and need to write the listings.
            String line = "{\"product_name\":"+ "\""+ productListing.product.product_name +"\"" + "}";
            bufferWriter.write(line);
            bufferWriter.newLine();
          }
      	}
      	bufferWriter.close();

    } catch (Exception e)
       {
           System.err.println("Error Generating  file: " + e.getMessage());
       }
  }
  /**
   * This method  outputs the results of the matching into 1 file, the listings matched
   */
  public static void outPutUnMatchListingsToFile (ArrayList<Listing> listingsNotAssigned, String unmatchFileName)
  {
    try
    {
        File fileOutput = new File(unmatchFileName);
        FileOutputStream fileOutPutStream = new FileOutputStream(fileOutput);

        BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(fileOutPutStream));

        for (int i = 0; i < listingsNotAssigned.size(); i++)
        {
          Listing listing = listingsNotAssigned.get(i);

            String line = listing.title + "," + listing.manufacturer + "," + listing.currency + "," + listing.price;
            bufferWriter.write(line);
            bufferWriter.newLine();
        }
        bufferWriter.close();

    } catch (Exception e)
       {
           System.err.println("Error Generating  file:  " + e.getMessage());
       }
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
 public static void runTests(String s)
 {
   if (s!=null && s.equals("runtests") ) // Just to run some tests ...
   {
      TestClass test = new TestClass();
      test.runAllTests();
      return;
    }



 }


}
