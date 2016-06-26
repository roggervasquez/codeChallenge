import java.util.ArrayList;

public class Algorithms {

  /**
   * Edit Distance between 2 strings
   * @param a  First string
   * @param b  Second string
   * @return int, the cost of Edit distance
   */
    public static int editDistance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();

        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++)
        {
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++)
            {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }
    /**
     * Similarity between 2 strings based on edit distance, not too effective
     * @param s1  First string
     * @param s2  Second string
     * @return double, a value from 0 to 1, 1 means they are the same
     */

    public static double similarity(String s1, String s2)
    {
      String longer = s1, shorter = s2;
      if (s1.length() < s2.length())
      {
        longer = s2; shorter = s1;
      }
      int longerLength = longer.length();
      if (longerLength == 0) { return 1.0; /* both strings are zero length */ }
      return (longerLength - editDistance(longer, shorter)) / (double) longerLength;
    }

    /**
     * Similarity between 2 strings based on edit distance, not too effective
     * @param s1  First string
     * @param s2  Second string
     * @return double, a value from 0 to 1, 1 means they are the same
     */
    public static String longestCommonSubstring(String S1, String S2)
    {
        int Start = 0;
        int Max = 0;
        for (int i = 0; i < S1.length(); i++)
        {
            for (int j = 0; j < S2.length(); j++)
            {
                int x = 0;
                while (S1.charAt(i + x) == S2.charAt(j + x))
                {
                    x++;
                    if (((i + x) >= S1.length()) || ((j + x) >= S2.length())) break;
                }
                if (x > Max)
                {
                    Max = x;
                    Start = i;
                }
             }
        }
        return S1.substring(Start, (Start + Max));
    }
    /**
     * Similarity between 2 strings based on algorithm by Simon White
     * from   http://www.catalysoft.com/articles/StrikeAMatch.html
     * @param str1  First string
     * @param str2  Second string
     * @return double, a value from 0 to 1, 1 means they are the same
     */
   public static double compareStrings(String str1, String str2)
   {
           ArrayList pairs1 = wordLetterPairs(str1.toUpperCase());
           ArrayList pairs2 = wordLetterPairs(str2.toUpperCase());
           int intersection = 0;
           int union = pairs1.size() + pairs2.size();
           for (int i=0; i<pairs1.size(); i++)
           {
               Object pair1=pairs1.get(i);
               for(int j=0; j<pairs2.size(); j++)
               {
                   Object pair2=pairs2.get(j);
                   if (pair1.equals(pair2))
                   {
                       intersection++;
                       pairs2.remove(j);
                       break;
                   }
               }
           }
           return (2.0*intersection)/union;
     }

    /** @return an array of adjacent letter pairs contained in the input string */

       private static String[] letterPairs(String str)
       {
           int numPairs = str.length()-1;
           String[] pairs = new String[numPairs];
           for (int i=0; i<numPairs; i++)
            {
               pairs[i] = str.substring(i,i+2);
           }

           return pairs;

       }

    /** @return an ArrayList of 2-character Strings. */

    private static ArrayList wordLetterPairs(String str)
     {

          ArrayList allPairs = new ArrayList();
          // Tokenize the string and put the tokens/words into an array
          String[] words = str.split("\\s");
          // For each word
          for (int w=0; w < words.length; w++)
          {
              // Find the pairs of characters
              String[] pairsInWord = letterPairs(words[w]);
              for (int p=0; p < pairsInWord.length; p++)
              {
                  allPairs.add(pairsInWord[p]);
              }
          }
          return allPairs;
      }


}
