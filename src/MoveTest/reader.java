package MoveTest;

import java.io.File;
import java.io.FileReader;


/**

 *
 *  @author <a href="mailto:jvanzyl@apache.org">Jason van Zyl</a>
 *  @author <a href="mailto:dlr@finemaltcoding.com">Daniel Rall</a>
 *  @version $Id: StringUtils.java 685685 2008-08-13 21:43:27Z nbubna $
 */
public class reader {


  /**
   * Read the contents of a file and place them in
   * a string object.
   *
   * @param file path to file.
   * @return String contents of the file.
   */
  public static String fileContentsToString(String file)
  {
      String contents = "";

      File f = null;
      try
      {
          f = new File(file);

          if (f.exists())
          {
              FileReader fr = null;
              try
              {
                  fr = new FileReader(f);
                  char[] template = new char[(int) f.length()];
                  fr.read(template);
                  contents = new String(template);
              }
              catch (Exception e)
              {
                  e.printStackTrace();
              }
              finally
              {
                  if (fr != null)
                  {
                      fr.close();
                  }
              }
          }
      }
      catch (Exception e)
      {
          e.printStackTrace();
      }
      return contents;
  }
}


