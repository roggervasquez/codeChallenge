
import java.util.*;

/**
 * Class that has all logic for solving the matching problem
 */

public class MatchSolver
{

  private MatchResults  matchResults;
  public MatchResults getMatchResults()
  {
      return matchResults;
  }

 // Constructor
  public MatchSolver(List<Product> products)
  {
    //Fill the matchResults with the whole list of products but with emppty listings for each one, matching has not started.
    matchResults = new MatchResults ();
    for (Product p : products)
    {
      ProductListing pl = new ProductListing(p); // Creates a bucket with a product but with a empty list of listings ..
      matchResults.addProductListing(pl); // Add the new bucket to the internal list of Match results.
    }
  }


 /**
 * The most important method, is the one that decides if a listing matches to a product, and if matches in manufacturer
 * and model it decides that is the best matching
 */
  public void matchListing(Listing listing)
  {
      int bestIndex =-1;  // keeps track which index of the productsListing is the best match so far.
      for (ProductListing pl : this.matchResults.productsListings)
      {
         bestIndex = bestIndex + 1;
         if (MatchSolver.isSameManufacturer(pl.product,listing))  // Is the same manufacturer then is a candidate, to look more into it ..
         {
            boolean samemodel = MatchSolver.isSameModel(pl.product,listing);
            if (samemodel)
            {
              this.matchResults.productsListings.get(bestIndex).listings.add(listing);
              return; // No need to look further because of the unique property base on Manufacturer+model (so it will not be a better matching ahead)
            }
            else
            {
              // we could try to see if is a match by the family, but ...
            }
         }
         //else  don't even mind, is not from the same manufacturer, no matther how similar the product is, so skip to the next product.
         //Example: CyberShot Camera from sony is not the same as CyberShot camera from Nikon, no matter that in the listing Cysbershot Camera is a common string
      }
     // if reaches this point there is not match so add to no matching list
      this.matchResults.listingsNotAssigned.add(listing);

  }

 /**
 * Checks if the listing is the same model as a product, pre-condition, asumming we already check that there are from the same manufacturer
 */
  public static  boolean isSameModel(Product product, Listing listing)
  {

    String[] modelParts = product.model.split("\\s+");
    String[] listingParts = listing.title.split("\\s+");
    int wordsFound =0;
    for (int i=0; i<modelParts.length;i++)
     {  // Try to find the model in the array of listing parts ...
       // for example:  mju Tough 8000 , lenght is 3, but it will be enough finding 2 words for example tough 8000
         for (int j=1; j<listingParts.length; j++)
         {
           if (listingParts[j].toLowerCase().equals(modelParts[i].toLowerCase()))
            {
              wordsFound++;
              break; // found once no need to keep searching
            }
          }
      } // End of modelParts

      if (wordsFound!=0 && wordsFound==modelParts.length) // we found at least all words -1
      {
          return true; // Is the same model
      }

      // If we got here, we have to consider another possibility is that the compound model can be in just a whole word
      // Example:  model : A3100 IS     ... can appear in a listing as   A3100IS, but just if the model has more than one word
      if (modelParts.length>1)
      {
        for (int j=1; j<listingParts.length; j++)
          {
            int countFound = MatchSolver.countModelOcurrences(listingParts[j].toLowerCase(),modelParts);
            if(countFound==modelParts.length)
            {
              return true;  // Found the model inside a compound  word in the listing
            }
          }
      }
      return false;
   }

   /**
   * Checks in a word ,  how many of the strings in modelWords exists
   */
   public static  int countModelOcurrences(String word, String [] modelWords)
   {
      int count=0;
      String temp = word.toLowerCase();
      for(int i=0; i<modelWords.length; i++)
      {
        //System.out.println(temp + ":" + modelWords[i]);
        int indexFound = temp.indexOf(modelWords[i].toLowerCase());
        if (indexFound!=-1)
        {
           count++;
           temp = temp.substring(indexFound + modelWords[i].length());
        }
      }
      return count;
   }



  /**
  * Checks if a listing is from the same manufacturer as the product , based on different criteria
  */
  public static  boolean isSameManufacturer(Product product, Listing listing)
  {
      //If the listing manufacturer is not empty we could check that , but normally the listing has the manufacturer toLowerCase

     // Split the listing title in whole words to test if the manufacturer exists in those words
     // Indexof could not work in case a manufacturer name is a substring of another manufacturer name
     String[] listingParts = listing.title.split("\\b+");
     for(int i=0; i<listingParts.length;i++)
     {
       if(listingParts[i].toLowerCase().equals(product.manufacturer.toLowerCase()))
       {
         return true;
       }
     }
     return false;
  }

}
