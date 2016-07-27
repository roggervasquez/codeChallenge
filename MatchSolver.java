
import java.util.*;
import java.lang.Math;

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
 * Similarity is used only for detecting if sameManufacturer, in case of misspelled manufacturer in the listing title, it has to
 * very similar >=0.9
 */
  public void matchListing(Listing listing,double similarity)
  {
      int bestIndex =-1;  // keeps track which index of the productsListing is the best match so far.
      for (ProductListing pl : this.matchResults.productsListings)
      {
         bestIndex = bestIndex + 1;
         ResultSameManufacturer res = MatchSolver.isSameManufacturer(pl.product,listing,similarity);
         if (res.found==true)  // Is the same manufacturer then is a candidate, to look more into it ..
         {
            ResultSameModel result = MatchSolver.isSameModel(pl.product,listing);
            if (result.found)
            {
              listing._checkPrice = res.checkPrice; // Flag to later check this listing by it's price if true
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

  private double getAverageByCurrency (ArrayList <Listing> listings, String currency)
  {
    double valueAccumulator = 0.0;
    int count =0;
    for (Listing listing : listings)
    {
       if (listing.currency.toLowerCase().equals(currency.toLowerCase()) && listing._checkPrice==false  )
       {
            valueAccumulator = valueAccumulator + Double.parseDouble(listing.price);
            count++;
       }
    }
    if (count > 0 )
          return (valueAccumulator / count);
    else
          return 1.0;
  }
  /**
  * REmoves all listings that has the checkPrice flag = true if they price is far away the average of prices of those listings with specific currency
  */
  public void checkListingsbyPrice(double minValue, double maxValue)
  {
    ArrayList<ProductListing> productsListings = this.matchResults.productsListings;

    for (int i = 0; i < productsListings.size(); i++)
    {
      ProductListing productListing = productsListings.get(i);
      if (productListing.listings.size()>0)   // The product has listings.
      {
        for (int j=productListing.listings.size()-1; j>=0; j--) // Check each listing for the _checkPrice flag but in reverse so no issues when removing items
        {
           Listing listing = productListing.listings.get(j);
           // Check if listing needs to be check by price
           if (listing._checkPrice)
           {
               double averagePrice = this.getAverageByCurrency(productListing.listings, listing.currency);
               double currentListingPrice = Double.parseDouble(listing.price);
               // Calculate the difference of the current listing price vs the Average price of those listings with same currency
               double rangeValue = Math.abs(averagePrice - currentListingPrice) / averagePrice;
               if (!(rangeValue>=minValue && rangeValue <=maxValue)) // if the range value is out of limits, then remove it is false positive big difference in price
                {
                     productListing.listings.remove(j);
                }
           }
        }
      }
    }
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
            if(countFound==modelParts.length) // We found all words of the model
            {
              result.found= true;
              return result; // Is the same model; Found the model inside a compound  word in the listing
            }
          }
      }
      result.found= false;
      return result;
   }

  /**
  * Checks if a listing is from the same manufacturer as the product , based on different criteria
  */
  public static  ResultSameManufacturer isSameManufacturer(Product product, Listing listing, double similarity)
  {
     ResultSameManufacturer result = new ResultSameManufacturer();

     String[] listingParts = listing.title.split("\\b+");
     String[] productManufacturerParts = product.manufacturer.split("\\s+");
     String[] listingManufacturerParts = listing.manufacturer.split("\\s+");

     int countInManufacturers=0;
     int countInListings =0;
     int countProductsInListings =0;

     if (!listing.manufacturer.isEmpty())
     {
       countInManufacturers = countWordOcurrences(productManufacturerParts, listingManufacturerParts, 1.0); // similarity of 1 means EQUAL
       countInListings = countWordOcurrences(listingManufacturerParts, listingParts, similarity); // pass the similarity defined outside1
     }
     countProductsInListings = countWordOcurrences(productManufacturerParts,listingParts, 1.0); //similarity of 1 means EQUAL

     if (countInManufacturers == productManufacturerParts.length) { result.found = true; return result; }
     if(listing.manufacturer.isEmpty())
     {
       if (countProductsInListings== productManufacturerParts.length) { result.found = true; return result; }
     }

     if (countInListings==listingManufacturerParts.length) { result.found = false; return result; }// By transitive propoerty cannot be true because of previous conditions

     if (countInListings==0) // the manufacturer in listing does not appear in title
     {
        // It means that the manufacturer probably is not set correctly in the listing. So by heuristic we will search in the title
        // But maybe it is a accesory so will checkPRice = true to check later
      if (countProductsInListings== productManufacturerParts.length) { result.found = true; result.checkPrice=true; return result; }
     }

    result.found = false; return result;  // If you get here, there are not from the same manufacturer
  }

  /**
  * Checks in a word ,  how many of the strings in modelWords exists
  * Looking for  130 IS    in  SA130AFIS   will return 1
  * Looking for  130 IS    in  SD130IS    will return 2
  * Looking for 130 IS     in SD1300IS    will return 0 ,
  */
  public static  int countWordOcurrences(String [] listOfWords,String word)
  {
     int count=0;
     String temp = word.toLowerCase();

     int indexFound = temp.indexOf(listOfWords[0].toLowerCase()); // First word has to be in the string, if not return 0
     if (indexFound==-1)
        return 0;
     else
     {
        count=1;
        temp = temp.substring(indexFound + listOfWords[0].length()); // Chop the word without the first word found
     }
     // Start searching from the seconud word forward, all inner ocurrences need to happen at position ZERO
     for(int i=1; i<listOfWords.length; i++)
     {
     // The first word can be found anywhere in the string but the next ones, has to be continuous
     // Example : 130 IS =>  SD1300IS   :  will return 1, instead of 2 ;   130IS will work
        indexFound = temp.indexOf(listOfWords[i].toLowerCase());
       if (indexFound==0)
       {
          count++;
          temp = temp.substring(indexFound + listOfWords[i].length());
       }
       else
       {
         break; // No need to look forward
       }
     }
     return count;
  }
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
