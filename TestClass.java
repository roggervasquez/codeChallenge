


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
          this.testCompareStrings1();
          this.testCompareStrings2();
          this.testCompareStrings3();
          this.testCompareStrings4();
          this.testCompareStrings5();
          this.testCompareStrings6();
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
      System.out.println("----------------------------------------------------------------------------------------\n");
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
       System.out.println("----------------------------------------------------------------------------------------\n");
   }
   public static void  testCompareStrings1()
   {

      String title = "Sony T Series DSC-T99 14.1 Megapixel DSC Camera with Super HAD CCD Image Sensor (Silver)";
      String productName = "Sony Cyber-shot DSC-T99";
      System.out.println("Comparing:" + title + " **VS** " + productName);
      double result = Algorithms.compareStrings(title,productName);
      System.out.println("REsult:" + result);
      String lcs= Algorithms.longestCommonSubstring(title,productName);
      System.out.println("LCS:" + lcs);
      System.out.println("Edit Distance:" + Algorithms.editDistance(title,productName));
      System.out.println("Similarity:" + Algorithms.similarity(title,productName));
      System.out.println("----------------------------------------------------------------------------------------\n");



   }
   public static void  testCompareStrings2()
   {

      String model1 = "DSC-T99";
      String model2 = "DSC T99";
      System.out.println("Comparing:" + model1 + " **VS** " + model2);
      double result = Algorithms.compareStrings(model1,model2);
      System.out.println("REsult:" + result);
      String lcs= Algorithms.longestCommonSubstring(model1,model2);
      System.out.println("LCS:" + lcs);
      System.out.println("Edit Distance:" + Algorithms.editDistance(model1,model2));
      System.out.println("Similarity:" + Algorithms.similarity(model1,model2));
      System.out.println("----------------------------------------------------------------------------------------\n");


   }
   public static void  testCompareStrings3()
   {

      String model1 = "DSC-T99";
      String model2 = "DSCT99";
      System.out.println("Comparing:" + model1 + " **VS** " + model2);
      double result = Algorithms.compareStrings(model1,model2);
      System.out.println("REsult:" + result);
      String lcs= Algorithms.longestCommonSubstring(model1,model2);
      System.out.println("LCS:" + lcs);
      System.out.println("Edit Distance:" + Algorithms.editDistance(model1,model2));
      System.out.println("Similarity:" + Algorithms.similarity(model1,model2));
      System.out.println("----------------------------------------------------------------------------------------\n");
   }


   public static void  testCompareStrings4()
   {

      String model1 = "PDR-M70";
      String model2 = "PDR-M700";
      System.out.println("Comparing:" + model1 + " **VS** " + model2);
      double result = Algorithms.compareStrings(model1,model2);
      System.out.println("REsult:" + result);
      String lcs= Algorithms.longestCommonSubstring(model1,model2);
      System.out.println("LCS:" + lcs);
      System.out.println("Edit Distance:" + Algorithms.editDistance(model1,model2));
      System.out.println("Similarity:" + Algorithms.similarity(model1,model2));
      System.out.println("----------------------------------------------------------------------------------------\n");
   }

   public static void  testCompareStrings5()
   {

      String model1 = "Tough 8000";
      String model2 = "mju Tough 8000";
      System.out.println("Comparing:" + model1 + " **VS** " + model2);
      double result = Algorithms.compareStrings(model1,model2);
      System.out.println("REsult:" + result);
      String lcs= Algorithms.longestCommonSubstring(model1,model2);
      System.out.println("LCS:" + lcs);
      System.out.println("Edit Distance:" + Algorithms.editDistance(model1,model2));
      System.out.println("Similarity:" + Algorithms.similarity(model1,model2));
      System.out.println("----------------------------------------------------------------------------------------\n");
   }
   public static void  testCompareStrings6()
   {

      String model1 = "A3100IS";
      String model2 = "A3100 IS";
      System.out.println("Comparing:" + model1 + " **VS** " + model2);
      double result = Algorithms.compareStrings(model1,model2);
      System.out.println("REsult:" + result);
      String lcs= Algorithms.longestCommonSubstring(model1,model2);
      System.out.println("LCS:" + lcs);
      System.out.println("Edit Distance:" + Algorithms.editDistance(model1,model2));
      System.out.println("Similarity:" + Algorithms.similarity(model1,model2));
      System.out.println("----------------------------------------------------------------------------------------\n");
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
