


/**
*  A class the encapsulates unit testinng of the most important functions.
*
*/

public class TestClass
{
   public TestClass() {}
   public void runAllTests()
   {
          //  this.testSameManufacturer1();
          //  this.testSameManufacturer2();
          //  this.testSameManufacturer3();
          //   this.testSameManufacturer4();
          //     this.testSameManufacturer5();
      //     this.testDifferentManufacturer1();
          // this.testCompareStrings1();
          // this.testCompareStrings2();
          // this.testCompareStrings3();
          // this.testCompareStrings4();
          // this.testCompareStrings5();
          // this.testCompareStrings6();
          this.testIsSameModel1();
          this.testIsSameModel2();
          this.testIsSameModel3();
          this.testIsSameModel4();

        //  this.testCountOcurrences1();
        //  this.testCountOcurrences2();
        //  this.testCountOcurrences3();
        //this.testCountOcurrences4();

   }
   public static void  testIsSameModel1()
   {

     Product p = new Product();
     Listing l = new Listing();

     p.product_name="";
     p.manufacturer ="SONY";
     p.model="DSC-T99";

     l.title="Sony T Series DSC-T990 14.1 Megapixel DSC Camera with Super HAD CCD Image Sensor (Silver) ";
     l.manufacturer="";

     boolean expected =  false;

     ResultSameModel result = MatchSolver.isSameModel(p,l);
     if (result.found==expected)
     {
       System.out.println("TEST PASSED: different Model");
     }
     else
     {
         System.out.println("TEST FAILED: ");
     }

   }
   public static void  testIsSameModel2()
   {

     Product p = new Product();
     Listing l = new Listing();

     p.product_name="";
     p.manufacturer ="Canon";
     p.model="A3100 IS";

     l.title="Canon Powershot A3100IS 12.1 MP digital (Silver)";
     l.manufacturer="Canon Canada";

     boolean expected =  true;

     ResultSameModel result = MatchSolver.isSameModel(p,l);
     if (result.found==expected)
     {
       System.out.println("TEST PASSED: Same Model");
     }
     else
     {
         System.out.println("TEST FAILED: ");
     }

   }
   public static void  testIsSameModel3()
   {

     Product p = new Product();
     Listing l = new Listing();

     p.product_name="";
     p.manufacturer ="Olympus";
     p.model="mju Tough 8000";

     l.title="Olympus Stylus tough 8000 12 Megapixel Digital Camera (Waterproof)";
     l.manufacturer="OLYMPUS";

     boolean expected =  true;

     ResultSameModel result = MatchSolver.isSameModel(p,l);
     if (result.found==expected)
     {
       System.out.println("TEST PASSED: Same Model");
     }
     else
     {
         System.out.println("TEST FAILED: ");
     }

   }
   public static void  testIsSameModel4()
   {

     Product p = new Product();
     Listing l = new Listing();

     p.product_name="";
     p.manufacturer ="Canon";
     p.model="130 IS";

     l.title="Canon powershot SD1300IS 12 mp (Silver)";
     l.manufacturer="Canon";

     boolean expected =  false;

     ResultSameModel result = MatchSolver.isSameModel(p,l);
     if (result.found==expected)
     {
       System.out.println("TEST PASSED: different Model");
     }
     else
     {
         System.out.println("TEST FAILED: ");
     }

   }

   public static void testCountOcurrences1()
   {
        String[] models = new String[]{"a3100","IS","other"};
        int count = MatchSolver.countWordOcurrences( models , "SXA3100ISTEM");
        int expected = 2;
        if (count==expected)
        {
          System.out.println("TEST PASSED: Ocurrences:" + count);
        }
        else
        {
            System.out.println("TEST FAILED: Ocurrences:" + count);
        }


   }
   public static void testCountOcurrences2()
   {
        String[] models = new String[]{"130","IS"};
        int count = MatchSolver.countWordOcurrences( models ,"SX130IS");
        int expected = 2;
        if (count==expected)
        {
          System.out.println("TEST PASSED: Ocurrences:" + count);
        }
        else
        {
            System.out.println("TEST FAILED: Ocurrences:" + count);
        }

   }
   public static void testCountOcurrences3()
   {
        String[] models = new String[]{"Dt-Tough-800x","Tough", "8000"};
        int count = MatchSolver.countWordOcurrences( models,"Dt-Tough-800x");
        int expected = 1;
        if (count==expected)
        {
          System.out.println("TEST PASSED: Ocurrences:" + count);
        }
        else
        {
            System.out.println("TEST FAILED: Ocurrences:" + count);
        }

   }
   public static void testCountOcurrences4()
   {
        String[] manufacturer = new String[]{"Man","Konica", "Minolta"};
        String[] words = new String[]{"KONICA", "ACER", "MINolta"};

        int count = MatchSolver.countWordOcurrences( words,manufacturer);
        int expected = 2;
        if (count==expected)
        {
          System.out.println("TEST PASSED: Ocurrences:" + count);
        }
        else
        {
            System.out.println("TEST FAILED: Ocurrences:" + count);
        }

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

   public static void  testSameManufacturer2()
   {
      Product p = new Product();
      Listing l = new Listing();

      p.product_name="";
      p.manufacturer ="SONY";

      l.title="ManuSonyFab T Series DSC-T99 14.1 Megapixel DSC Camera with Super HAD CCD Image Sensor (Silver) ";
      l.manufacturer="";


      boolean expected =  false;
      boolean result = MatchSolver.isSameManufacturer(p,l);

      if (expected == result)
      {
          System.out.println("TEST PASSED : Is not same  manufacturer");
      }
      else
      {
           System.out.println("TEST FAILED");
      }
      System.out.println("----------------------------------------------------------------------------------------\n");
   }
   public static void  testSameManufacturer3()
   {
      Product p = new Product();
      Listing l = new Listing();

      p.product_name="";
      p.manufacturer ="Canon";

      l.title="DURAGADGET Premium wrist Camera Carrying Strap with 2 Year Warranty for Canon Ixus 1000HS, Ixus 300HS, Ixus 210, Ixus 200IS, Ixus 130, Ixus 120IS";
      l.manufacturer="DURAGADGET";


      boolean expected =  false;
      boolean result = MatchSolver.isSameManufacturer(p,l);

      if (expected == result)
      {
          System.out.println("TEST PASSED : Is not same  manufacturer");
      }
      else
      {
           System.out.println("TEST FAILED");
      }
      System.out.println("----------------------------------------------------------------------------------------\n");
   }

   public static void  testSameManufacturer4()
   {
      Product p = new Product();
      Listing l = new Listing();

      p.product_name="";
      p.manufacturer ="Kodak";

      l.title="Kodak EasyShare M893 IS Digital Camera (Blue)";
      l.manufacturer="8187049";


      boolean expected =  true;
      boolean result = MatchSolver.isSameManufacturer(p,l);

      if (expected == result)
      {
          System.out.println("TEST PASSED : Is same  manufacturer");
      }
      else
      {
           System.out.println("TEST FAILED");
      }
      System.out.println("----------------------------------------------------------------------------------------\n");
   }

   public static void  testSameManufacturer5()
   {
      Product p = new Product();
      Listing l = new Listing();

      p.product_name="";
      p.manufacturer ="Konica Minolta";

      l.title="Konica-Minolta Dimage X1 Digitalkamera (8 Megapixel) in schwarz";
      l.manufacturer="Konica Minolta Europe GmbH";


      boolean expected =  true;
      boolean result = MatchSolver.isSameManufacturer(p,l);

      if (expected == result)
      {
          System.out.println("TEST PASSED : Is same  manufacturer");
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



}
