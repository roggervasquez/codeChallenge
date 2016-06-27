
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
 * it decides based on a score which one of the products is the most likely to match with the listing.
 * but it also decides that the listing does not match with any of the products, it is discarded and added to a list of not assigned listings.
 */
  public void matchListing(Listing listing)
  {
      int bestIndex =-1;  // keeps track which index of the productsListing is the best match so far.
      double bestScore=0.0;  // Keeps the information of the best score found at the moment of a matching
      // iterate the list of productListings
      for (ProductListing pl : this.matchResults.productsListings)
      {
         if (this.isSameManufacturer(pl.product,listing))
         {  // Is the same manufacturer then is a candidate, to look more into it .. bestScore will decide the best match

         }
         //else  don't even mind, is not from the same manufacturer, no matther how similar the product is
         //CyberShot Camera from sony is not the same as CyberShot camera from Nikon, no matter that in the lising Cysbershot Camera is a common string
      }

      if(bestIndex!=-1)
      {
         // we got a match, and the Index tells us the position of the best product it matches. SO need to add the listing to it
         this.matchResults.productsListings.get(bestIndex).listings.add(listing); // add the listing to that product in it's list of listings.
      }
      else   // if we got to this point and bestindex = -1, then we do not find any product to match the listing pass by parameter, so we need to add it to the not Assigned listings
      {
        this.matchResults.listingsNotAssigned.add(listing);
      }
  }

  /**
  * Checks if a listing is from the same manufacturer as the product , based on different criteria
  */
  private boolean isSameManufacturer(Product product, Listing listing)
  {
     // Basic comparison, has to be improved.

     if (product.manufacturer.equals(listing.manufacturer))
     {
       System.out.println(product.product_name + "==>" + listing.manufacturer);
       return true;
     }
    return false;
  }

}
