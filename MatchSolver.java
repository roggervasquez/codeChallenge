
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

      if (wordsFound!=0 && wordsFound>=modelParts.length) // we found all words from the model in the listing
      {

          result.found= true;
          return result; // Is the same model
      }

      // If we got here, we have to consider another possibility is that the compound model can be in just a whole word
      // Example:  model : A3100 IS     ... can appear in a listing as   A3100IS, but just if the model has more than one word
      // But model : 130 IS  , Could also be found in SD1300IS by mistake, it has to deal with this cases too
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
      return result;
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
       countInManufacturers = countWordOcurrences(productManufacturerParts, listingManufacturerParts, 1.0);
       countInListings = countWordOcurrences(listingManufacturerParts, listingParts, 0.9);
     }
     countProductsInListings = countWordOcurrences(productManufacturerParts,listingParts, 1.0);

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
  /**
  * Checks in a word ,  how many of the strings in modelWords exists, but IN ORDER and side by side, consecutive
  * Looking for  130 IS    in  SA130AFIS   will return 1  SA  130  AF IS   , because 130 and IS are not consecutive, will only find 130
  * Looking for  130 IS    in  SD130IS    will return 2  SD  130  IS
  * Looking for 130 IS     in SD1300IS    will return 0 ,   SD 1300 IS  .. because it will not found the first token (130)
  */
  public static  int countWordOcurrences(String [] listOfWords,String word)
  {
     int count=0;
     String temp = word.toLowerCase();
     String [] tokens =  temp.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
     int nextIndex=-1;
    // First check if we can find the first word in tokens
     for (int j=0; j< tokens.length; j++)
     {
        if (listOfWords[0].toLowerCase().equals(tokens[j].toLowerCase()))
        {
          nextIndex = j+1;
          count=1;
          break;
        }
     }
     // If we did not found the first word, then return count or zero, no need to continue
     if (count==0)  return 0;

     // Search for the other words in listOfWords but in order in the tokens array
     for(int i=1; i<listOfWords.length; i++)
     {
        // rest of words has to start in nextIndex
        if (listOfWords[i].toLowerCase().equals(tokens[nextIndex].toLowerCase()))
        {
          count++;
          nextIndex = nextIndex + 1;
        }
        else
        {
          break; // They are not consecutive , just return what ever it count consecutive
        }
      }
     return count;
  }

  /**
  * Checks in a word ,  how many of the strings in modelWords exists
  */
  // public static  int countWordOcurrences(String [] listOfWords,String word)
  // {
  //    int count=0;
  //    String temp = word.toLowerCase();
  //
  //    int indexFound = temp.indexOf(listOfWords[0].toLowerCase()); // First word has to be in the string, if not return 0
  //    if (indexFound==-1)
  //       return 0;
  //    else
  //    {
  //       count=1;
  //       temp = temp.substring(indexFound + listOfWords[0].length()); // Chop the word without the first word found
  //    }
  //    // Start searching from the seconud word forward, all inner ocurrences need to happen at position ZERO
  //    for(int i=1; i<listOfWords.length; i++)
  //    {
  //    // The first word can be found anywhere in the string but the next ones, has to be continuous
  //    // Example : 130 IS =>  SD1300IS   :  will return 1, instead of 2 ;   130IS will work
  //       indexFound = temp.indexOf(listOfWords[i].toLowerCase());
  //      if (indexFound==0)
  //      {
  //         count++;
  //         temp = temp.substring(indexFound + listOfWords[i].length());
  //      }
  //      else
  //      {
  //        break; // No need to look forward
  //      }
  //    }
  //    return count;
  // }
  /**
  * Checks if the words in wordsToFind appear in the array whereToFind
  * But in order. When a word is found the next one starts looking where the other was found +1
  */
  public static  int countWordOcurrences(String [] wordsToFind, String [] whereToFind, double similarity)
  {
     int count=0;
     int currentIndex=0;
     for (int i=0; i<wordsToFind.length; i++)
     {
         for(int j=currentIndex; j < whereToFind.length;j++)
         {
           if (wordsToFind[i].toLowerCase().equals(whereToFind[j].toLowerCase())
              || Algorithms.similarity(wordsToFind[i].toLowerCase(), whereToFind[j].toLowerCase())>=similarity )
           {
             count++;
             currentIndex = j+1;;
             break;
           }
         }
     }
     return count;
  }


}
