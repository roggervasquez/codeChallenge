/**
* Class that represents a Listing
*/
public class Listing
{
  public String title; // description of product for sale
  public String manufacturer; // who manufactures the product for sale
  public String currency; // currency code, e.g. USD, CAD, GBP, etc.
  public String price;// price, e.g. 19.99, 100.00
  public boolean _checkPrice;

  public Listing()
  {
    title="";
    manufacturer="";
    currency="";
    price="";
    _checkPrice = false; // Not to be stored in the result, is for internal use only

  }
}
