


/**
*  A class the encapsulates unit testinng of the most important functions.
*
*/

public class TestClass
{
   public TestClass() {}
   public void runAllTests()
   {
          this.testSameManufacturer1();
          this.testDifferentManufacturer1();

   }


   public static void  testSameManufacturer1()
   {
      Product p = new Product();
      Listing l = new Listing();

      p.product_name="";
      p.manufacturer ="SONY";

      l.title="Sony T Series DSC-T99 14.1 Megapixel DSC Camera with Super HAD CCD Image Sensor (Silver) ";
      l.manufacturer="";


      boolean expected =  true;
      boolean result = MatchSolver.isSameManufacturer(p,l);

      if (expected == result)
      {
          System.out.println("TEST PASSED : Is same manufacturer");
      }
      else
      {
           System.out.println("TEST FAILED");
      }

   }

   public static void  testDifferentManufacturer1()
   {
      Product p = new Product();
      Listing l = new Listing();

      p.product_name="";
      p.manufacturer ="SONY";


      l.title="Soniy T Series DSC-T99 14.1 Megapixel DSC Camera with Super HAD CCD Image Sensor (Silver) ";
      l.manufacturer="";


      boolean expected =  false;
      boolean result = MatchSolver.isSameManufacturer(p,l);

      if (expected == result)
      {
          System.out.println("TEST PASSED : Is different");
      }
      else
      {
           System.out.println("TEST FAILED");
      }

   }

   public  void testAlgotithms()
    {
      String s1, s2,s3;    // The strings to find the edit distance between


      s1 = "Fuji_PinePix_T205" ;
      s2 = "Fuji_T205_PinePix" ;
      s3 = "Fuji" ;


      int d = Algorithms.editDistance(s1,s2);

      double db = Algorithms.similarity(s1,s3);
      double other = Algorithms.compareStrings(s1,s3);
        System.out.println("Distance:" + d);
        System.out.println("Common xx:" + Algorithms.longestCommonSubstring(s1,s2));
        System.out.println("similarity:" + db);
           System.out.println("Compare:" + other);


    }


}
