
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
      double bestScore =0.0;
      for (ProductListing pl : this.matchResults.productsListings)
      {
         bestIndex = bestIndex + 1;
         if (MatchSolver.isSameManufacturer(pl.product,listing))  // Is the same manufacturer then is a candidate, to look more into it ..
         {
            ResultSameModel result = MatchSolver.isSameModel(pl.product,listing);
            if (result.found)
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
  public static  ResultSameModel isSameModel(Product product, Listing listing)
  {
    ResultSameModel result = new ResultSameModel();

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
          result.found= true;
          return result; // Is the same model
      }

      // If we got here, we have to consider another possibility is that the compound model can be in just a whole word
      // Example:  model : A3100 IS     ... can appear in a listing as   A3100IS, but just if the model has more than one word
      if (modelParts.length>1)
      {
        for (int j=1; j<listingParts.length; j++)
          {
            int countFound = MatchSolver.countWordOcurrences(modelParts,listingParts[j].toLowerCase());
            if(countFound==modelParts.length)
            {
              result.found= true;
              return result; // Is the same model;  // Found the model inside a compound  word in the listing
            }
          }
      }
      result.found= false;
      return result; // Is the same model
   }

   /**
   * Checks in a word ,  how many of the strings in modelWords exists
   */
   public static  int countWordOcurrences(String [] listOfWords,String word)
   {
      int count=0;
      String temp = word.toLowerCase();
      for(int i=0; i<listOfWords.length; i++)
      {

        int indexFound = temp.indexOf(listOfWords[i].toLowerCase());
        if (indexFound!=-1)
        {
           count++;
           temp = temp.substring(indexFound + listOfWords[i].length());
        }
      }
      return count;
   }
   /**
   * Checks if the words in wordsToFind appear in the array whereToFind
   * But in order. When a word is found the next one starts looking where the other was found +1
   */
   public static  int countWordOcurrences(String [] wordsToFind, String [] whereToFind)
   {
      int count=0;
      int currentIndex=0;
      for (int i=0; i<wordsToFind.length; i++)
      {
          for(int j=currentIndex; j < whereToFind.length;j++)
          {
            if (wordsToFind[i].toLowerCase().equals(whereToFind[j].toLowerCase()))
            {
              count++;
              currentIndex = j+1;;
              break;
            }
          }
      }
      return count;
   }


  /**
  * Checks if a listing is from the same manufacturer as the product , based on different criteria
  */
  public static  boolean isSameManufacturer(Product product, Listing listing)
  {
       // Change logic, to first check the current manufacturer of listing exists in the title is that the case
       // then search product.manufacturer only in the listing manufacturer, if not, search it in the title
     String[] listingParts = listing.title.split("\\b+");
     String[] productManufacturerParts = product.manufacturer.split("\\s+");
     String[] listingManufacturerParts = listing.manufacturer.split("\\s+");

     int countInManufacturers=0;
     int countInListings =0;
     int countProductsInListings =0;

     if (!listing.manufacturer.isEmpty())
     {
       countInManufacturers = countWordOcurrences(productManufacturerParts, listingManufacturerParts);
       countInListings = countWordOcurrences(listingManufacturerParts, listingParts);
     }
     countProductsInListings = countWordOcurrences(productManufacturerParts,listingParts);

     if (countInManufacturers == productManufacturerParts.length) return true;
     if(listing.manufacturer.isEmpty())
     {
       if (countProductsInListings== productManufacturerParts.length) return true;
     }

     if (countInListings==listingManufacturerParts.length)  return false ; // By transitive propoerty cannot be true because of previous conditions

     if (countInListings==0) // the manufacturer in listing does not appear in title
     {
        // It means that the manufacturer probably is not set correctly in the listing. So by heuristic we will search in the title
      if (countProductsInListings== productManufacturerParts.length) return true;
     }

    return false; // If you get here, there are not from the same manufacturer
  }

}
