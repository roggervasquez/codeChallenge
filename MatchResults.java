/**
* Class that represents the results of the matching solver
*/

import java.util.*;


public class MatchResults
{
   public ArrayList<ProductListing> productsListings;
   public ArrayList<Listing>  listingsNotAssigned;


   public MatchResults()
   {
     productsListings = new ArrayList<ProductListing> ();
     listingsNotAssigned = new ArrayList<Listing> ();
   }
   public void addProductListing(ProductListing pl)
   {
     this.productsListings.add(pl);

   }
   public void addListing(Listing l)
   {
     this.listingsNotAssigned.add(l);
   }
   public ArrayList<ProductListing> getProductsListings()
   {
     return this.productsListings;
   }
   public ArrayList<Listing> getListingsNotAssigned()
   {
     return this.listingsNotAssigned;
   }

}
