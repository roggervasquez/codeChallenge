
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
 * The most important method, is the one that decides if a listing matches to a product, and if matches to several of them
 * it decides based on a bestScore which one of the products is the most likely to match with the listing. If a match is found
 * bestIndex will provide the location of that product in the productListings list.
 * but it also decides that the listing does not match with any of the products, it is discarded and added to a list of not assigned listings.
 */
  public void matchListing(Listing listing)
  {
      int bestIndex =-1;  // keeps track which index of the productsListing is the best match so far.
      double bestScore=0.0;  // Keeps the information of the best score found at the moment of a matching

      for (ProductListing pl : this.matchResults.productsListings)
      {
         if (MatchSolver.isSameManufacturer(pl.product,listing))  // Is the same manufacturer then is a candidate, to look more into it .. bestScore will decide the best match
         {

         }
         //else  don't even mind, is not from the same manufacturer, no matther how similar the product is, so skip to the next product.
         //Example: CyberShot Camera from sony is not the same as CyberShot camera from Nikon, no matter that in the lising Cysbershot Camera is a common string
      }
      // If we found a match, is the best we found based on the bestScore. In this case a listing at MOST will be match just to only one product or none at all.
      if(bestIndex!=-1)
      {
         //the bestIndex tells us the position of the best product it matches. SO need to add the listing to it
         this.matchResults.productsListings.get(bestIndex).listings.add(listing);
      }
      else   // if we got to this point and bestindex = -1, then we do not find any product to match the listing pass by parameter
      {
        this.matchResults.listingsNotAssigned.add(listing);
      }
  }








  /**
  * Checks if a listing is from the same manufacturer as the product , based on different criteria
  */
  public static  boolean isSameManufacturer(Product product, Listing listing)
  {


     int checkListingManufacturer = listing.manufacturer.toLowerCase().indexOf(product.manufacturer.toLowerCase());
     int checkListingTitle = listing.title.toLowerCase().indexOf(product.manufacturer.toLowerCase());
     // We have to look the product.manufacturer in the listing manufacturer or in the title, in case the other is empty
     if (checkListingTitle >=0 || checkListingManufacturer>=0)
     {

       return true;
     }
    return false;
  }

}
