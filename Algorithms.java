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
  

}
