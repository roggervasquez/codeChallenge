
import java.util.*;

/**
 This class associates to a product a list of listings.
*/
public class ProductListing
{
      public Product product;
      public ArrayList <Listing> listings; // Listings associated to a specific product

      public ProductListing()
      {
        this.product = null;
        this.listings = null;
      }
      public ProductListing(Product p)
      {
        this.setProduct(p);
        this.listings = new ArrayList<Listing>();
      }
      public void setProduct(Product p)
      {
        this.product = p;
      }
    

}
