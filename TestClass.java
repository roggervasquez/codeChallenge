


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
           this.testSameManufacturer2();
           this.testSameManufacturer3();
           this.testSameManufacturer4();
           this.testSameManufacturer5();
           this.testSameManufacturer6();

           this.testIsSameModel1();
           this.testIsSameModel2();
           this.testIsSameModel3();
           this.testIsSameModel4();

           this.testCompareStrings1();
           this.testCompareStrings2();
           this.testCompareStrings3();
           this.testCompareStrings4();
           this.testCompareStrings5();
           this.testCompareStrings6();

           this.testSimilarity1();

           this.testCoutOcurrences1();
           this.testCoutOcurrences2();
           this.testCoutOcurrences3();
           this.testCoutOcurrences4();
           this.testCoutOcurrences5();


   }
  public  void testCoutOcurrences1()
  {
     String s1 ="130 IS";
     String s2 ="SD1300IS";
     String[] modelParts = s1.split("\\s+");
     int expected = 1;
     int value = MatchSolver.countWordOcurrences(modelParts,s2);
     if (value==expected)
     {
       System.out.println("TEST PASSED: Counted correctly");
     }
     else
     {
         System.out.println("TEST FAILED: Counted Incorrectly ");
     }
       System.out.println("----------------------------------------------------------------------------------------\n");

  }
  public  void testCoutOcurrences2()
  {
     String s1 ="130 IS";
     String s2 ="SD130AF09IS";
     String[] modelParts = s1.split("\\s+");
     int expected = 1;
     int value = MatchSolver.countWordOcurrences(modelParts,s2);
     if (value==expected)
     {
       System.out.println("TEST PASSED: Counted correctly");
     }
     else
     {
         System.out.println("TEST FAILED: Counted Incorrectly ");
     }
       System.out.println("----------------------------------------------------------------------------------------\n");

  }
  public  void testCoutOcurrences3()
  {
     String s1 ="130 IS";
     String s2 ="SD130IS90";
     String[] modelParts = s1.split("\\s+");
     int expected = 2;
     int value = MatchSolver.countWordOcurrences(modelParts,s2);
     if (value==expected)
     {
       System.out.println("TEST PASSED: Counted correctly");
     }
     else
     {
         System.out.println("TEST FAILED: Counted Incorrectly ");
     }
       System.out.println("----------------------------------------------------------------------------------------\n");

  }
  public  void testCoutOcurrences4()
  {
     String s1 ="130 IS 900";
     String s2 ="SD130IS9000";
     String[] modelParts = s1.split("\\s+");
     int expected = 3;
     int value = MatchSolver.countWordOcurrences(modelParts,s2);
     if (value==expected)
     {
       System.out.println("TEST PASSED: Counted correctly");
     }
     else
     {
         System.out.println("TEST FAILED: Counted Incorrectly ");
     }
       System.out.println("----------------------------------------------------------------------------------------\n");

  }
  public  void testCoutOcurrences5()
  {
     String s1 ="130 IS-AF 900";
     String s2 ="SD130IS-AF900XD";
     String[] modelParts = s1.split("\\s+");
     int expected = 3;
     int value = MatchSolver.countWordOcurrences(modelParts,s2);
     if (value==expected)
     {
       System.out.println("TEST PASSED: Counted correctly");
     }
     else
     {
         System.out.println("TEST FAILED: Counted Incorrectly ");
     }
       System.out.println("----------------------------------------------------------------------------------------\n");

  }
   public  void testSimilarity1()
   {
     String s1= "DURAGADGET";
     String s2= "DURAGAGDGET";
     double value = Algorithms.similarity( s1,  s2);
     System.out.println("Value:" +value);

     int distance = Algorithms.editDistance(s1,s2);
     System.out.println("Value:" +distance);


   }

   public  void  testIsSameModel1()
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
       System.out.println("----------------------------------------------------------------------------------------\n");
   }
   public  void  testIsSameModel2()
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
      System.out.println("----------------------------------------------------------------------------------------\n");
   }
   public  void  testIsSameModel3()
   {

     Product p = new Product();
     Listing l = new Listing();

     p.product_name="";
     p.manufacturer ="Olympus";
     p.model="mju Tough 8000";

     l.title="Olympus Stylus tough 8000 12 Megapixel Digital Camera (Waterproof)";
     l.manufacturer="OLYMPUS";

     boolean expected =  false;

     ResultSameModel result = MatchSolver.isSameModel(p,l);
     if (result.found==expected)
     {
       System.out.println("TEST PASSED: NOT Same Model");
     }
     else
     {
         System.out.println("TEST FAILED: ");
     }
    System.out.println("----------------------------------------------------------------------------------------\n");
   }
   public  void  testIsSameModel4()
   {

     Product p = new Product();
     Listing l = new Listing();

     p.product_name="";
     p.manufacturer ="Canon";
     p.model="130 IS";

     l.title="Canon powershot SD1300IS 12 mp (Silver)";
     //l.title="SD1300IS";
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
      System.out.println("----------------------------------------------------------------------------------------\n");
   }



   public  void  testSameManufacturer1()
   {
      Product p = new Product();
      Listing l = new Listing();

      p.product_name="";
      p.manufacturer ="SONY";

      l.title="Sony T Series DSC-T99 14.1 Megapixel DSC Camera with Super HAD CCD Image Sensor (Silver) ";
      l.manufacturer="";


      boolean expected =  true;
      ResultSameManufacturer result = MatchSolver.isSameManufacturer(p,l,0.9);

      if (expected == result.found)
      {
          System.out.println("TEST PASSED : Is same manufacturer");
      }
      else
      {
           System.out.println("TEST FAILED");
      }
      System.out.println("----------------------------------------------------------------------------------------\n");
   }

   public  void  testSameManufacturer2()
   {
      Product p = new Product();
      Listing l = new Listing();

      p.product_name="";
      p.manufacturer ="SONY";

      l.title="ManuSonyFab T Series DSC-T99 14.1 Megapixel DSC Camera with Super HAD CCD Image Sensor (Silver) ";
      l.manufacturer="";


      boolean expected =  false;
      ResultSameManufacturer result = MatchSolver.isSameManufacturer(p,l,0.9);

      if (expected == result.found)
      {
          System.out.println("TEST PASSED : Is not same  manufacturer");
      }
      else
      {
           System.out.println("TEST FAILED");
      }
      System.out.println("----------------------------------------------------------------------------------------\n");
   }
   public  void  testSameManufacturer3()
   {
      Product p = new Product();
      Listing l = new Listing();

      p.product_name="";
      p.manufacturer ="Canon";

      l.title="DURAGADGET Premium wrist Camera Carrying Strap with 2 Year Warranty for Canon Ixus 1000HS, Ixus 300HS, Ixus 210, Ixus 200IS, Ixus 130, Ixus 120IS";
      l.manufacturer="DURAGADGET";


      boolean expected =  false;
      ResultSameManufacturer result = MatchSolver.isSameManufacturer(p,l,0.9);

      if (expected == result.found)
      {
          System.out.println("TEST PASSED : Is not same  manufacturer");
      }
      else
      {
           System.out.println("TEST FAILED");
      }
      System.out.println("----------------------------------------------------------------------------------------\n");
   }

   public  void  testSameManufacturer4()
   {
      Product p = new Product();
      Listing l = new Listing();

      p.product_name="";
      p.manufacturer ="Kodak";

      l.title="Kodak EasyShare M893 IS Digital Camera (Blue)";
      l.manufacturer="8187049";


      boolean expected =  true;
      ResultSameManufacturer result = MatchSolver.isSameManufacturer(p,l,0.9);

      if (expected == result.found)
      {
          System.out.println("TEST PASSED : Is same  manufacturer");
      }
      else
      {
           System.out.println("TEST FAILED");
      }
      System.out.println("----------------------------------------------------------------------------------------\n");
   }

   public  void  testSameManufacturer5()
   {
      Product p = new Product();
      Listing l = new Listing();

      p.product_name="";
      p.manufacturer ="Konica Minolta";

      l.title="Konica-Minolta Dimage X1 Digitalkamera (8 Megapixel) in schwarz";
      l.manufacturer="Konica Minolta Europe GmbH";


      boolean expected =  true;
      ResultSameManufacturer result = MatchSolver.isSameManufacturer(p,l,0.9);

      if (expected == result.found)
      {
          System.out.println("TEST PASSED : Is same  manufacturer");
      }
      else
      {
           System.out.println("TEST FAILED");
      }
      System.out.println("----------------------------------------------------------------------------------------\n");
   }
   public  void  testSameManufacturer6()
   {
      Product p = new Product();
      Listing l = new Listing();

      p.product_name="";
      p.manufacturer ="Samsung";

      l.title="DURAGAGDGET Deluxe Black Carry Case / Bag / Pouch with Belt loop for digital camera + belt clip for Samsung WB210";
      l.manufacturer="DURAGADGET";


      boolean expected =  false;
      ResultSameManufacturer result = MatchSolver.isSameManufacturer(p,l,0.9);

      if (expected == result.found)
      {
          System.out.println("TEST PASSED : Is NOT same  manufacturer");
      }
      else
      {
           System.out.println("TEST FAILED");
      }
      System.out.println("----------------------------------------------------------------------------------------\n");
   }



   public  void  testCompareStrings1()
   {

      String title = "Sony T Series DSC-T99 14.1 Megapixel DSC Camera with Super HAD CCD Image Sensor (Silver)";
      String productName = "Sony Cyber-shot DSC-T99";
      System.out.println("Comparing:" + title + " **VS** " + productName);

      String lcs= Algorithms.longestCommonSubstring(title,productName);
      System.out.println("LCS:" + lcs);
      System.out.println("Edit Distance:" + Algorithms.editDistance(title,productName));
      System.out.println("Similarity:" + Algorithms.similarity(title,productName));
      System.out.println("----------------------------------------------------------------------------------------\n");



   }
   public  void  testCompareStrings2()
   {

      String model1 = "DSC-T99";
      String model2 = "DSC T99";
      System.out.println("Comparing:" + model1 + " **VS** " + model2);

      String lcs= Algorithms.longestCommonSubstring(model1,model2);
      System.out.println("LCS:" + lcs);
      System.out.println("Edit Distance:" + Algorithms.editDistance(model1,model2));
      System.out.println("Similarity:" + Algorithms.similarity(model1,model2));
      System.out.println("----------------------------------------------------------------------------------------\n");


   }
   public  void  testCompareStrings3()
   {

      String model1 = "DSC-T99";
      String model2 = "DSCT99";
      System.out.println("Comparing:" + model1 + " **VS** " + model2);

      String lcs= Algorithms.longestCommonSubstring(model1,model2);
      System.out.println("LCS:" + lcs);
      System.out.println("Edit Distance:" + Algorithms.editDistance(model1,model2));
      System.out.println("Similarity:" + Algorithms.similarity(model1,model2));
      System.out.println("----------------------------------------------------------------------------------------\n");
   }


   public  void  testCompareStrings4()
   {

      String model1 = "PDR-M70";
      String model2 = "PDR-M700";
      System.out.println("Comparing:" + model1 + " **VS** " + model2);

      String lcs= Algorithms.longestCommonSubstring(model1,model2);
      System.out.println("LCS:" + lcs);
      System.out.println("Edit Distance:" + Algorithms.editDistance(model1,model2));
      System.out.println("Similarity:" + Algorithms.similarity(model1,model2));
      System.out.println("----------------------------------------------------------------------------------------\n");
   }

   public  void  testCompareStrings5()
   {

      String model1 = "Tough 8000";
      String model2 = "mju Tough 8000";
      System.out.println("Comparing:" + model1 + " **VS** " + model2);

      String lcs= Algorithms.longestCommonSubstring(model1,model2);
      System.out.println("LCS:" + lcs);
      System.out.println("Edit Distance:" + Algorithms.editDistance(model1,model2));
      System.out.println("Similarity:" + Algorithms.similarity(model1,model2));
      System.out.println("----------------------------------------------------------------------------------------\n");
   }
   public  void  testCompareStrings6()
   {

      String model1 = "A3100IS";
      String model2 = "A3100 IS";
      System.out.println("Comparing:" + model1 + " **VS** " + model2);

      String lcs= Algorithms.longestCommonSubstring(model1,model2);
      System.out.println("LCS:" + lcs);
      System.out.println("Edit Distance:" + Algorithms.editDistance(model1,model2));
      System.out.println("Similarity:" + Algorithms.similarity(model1,model2));
      System.out.println("----------------------------------------------------------------------------------------\n");
   }



}
