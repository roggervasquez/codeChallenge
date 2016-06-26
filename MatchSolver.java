
import java.util.*;

/**
 * Class that has all logic for solving the matching problem
 */

public class MatchSolver
{

  private MatchResults  matchResults;

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

  public void matchListing(Listing listing)
  {

  }
  public MatchResults getMatchResults()
  {
      return matchResults;
  }
}
