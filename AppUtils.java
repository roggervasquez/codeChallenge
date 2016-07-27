import java.io.*;
import java.util.*;
import com.eclipsesource.json.*; // minimal lib to parse Json

public class AppUtils
{
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
            String line = "{ \"product_name\":"+ "\""+ productListing.product.product_name +"\" ," + "\"listings\": [" ;
            for (int j=0; j<productListing.listings.size(); j++)
            {
               Listing listing = productListing.listings.get(j);
               JsonValue title = Json.value(listing.title);
               line = line + "{" +
                      "\"title\":" + title.toString() + ","  +
                      "\"manufacturer\": \"" + listing.manufacturer +"\"," +
                      "\"currency\": \"" + listing.currency +"\"," +
                      "\"price\": \"" +listing.price +"\"}"  ;

               if (j!=productListing.listings.size()-1)
                    line = line +",";
            }
            line = line + "] }" ; //Ends the Json array of listings and the Json object
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
 public static void mapJsonToProduct (Product p, JsonObject jsonObject)
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
 public static void mapJsonToListing (Listing l, JsonObject jsonObject)
 {
   l.title = jsonObject.get( "title" )!=null ? jsonObject.get( "title" ).asString() : "";
   l.manufacturer = jsonObject.get( "manufacturer" )!=null ? jsonObject.get( "manufacturer" ).asString() : "";
   l.currency = jsonObject.get( "currency" )!=null ? jsonObject.get( "currency" ).asString() : "";
   l.price=jsonObject.get( "price" )!=null ? jsonObject.get( "price" ).asString() : "";

 }

}
