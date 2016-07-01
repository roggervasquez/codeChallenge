/**
* Class that represents a product
*/
public class  Product
{

   public String product_name;  // A unique id for the product
   public String manufacturer;
   public String family; // optional grouping of products
   public String model;
   public String announced_date; // ISO-8601 formatted date string, e.g. 2011-04-28T19:00:00.000-05:00

   public Product()
   {
     product_name="";
     manufacturer ="";
     family="";
     model="";
     announced_date="";

   }

}
