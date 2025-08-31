package MoveTest;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;

public class SafeScore {

    FileWriter writer;{
    try {
      writer = new FileWriter("C:/Users/Daniel Metzner/SpaceEaters/highscore.txt");
      String c = "HighScore = 1999901loool";
      writer.write(c);
      writer.close();
    } catch (IOException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    }
        FileReader fr;{
		try {
			fr = new FileReader("C:/Users/Daniel Metzner/SpaceEaters/highscore.txt");
			  int ch;
			  do {
				  ch = fr.read();
				  if (ch != -1)
					  System.out.print((char) ch);
			  } while (ch != -1);
			  fr.close();
			
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e3) {
			e3.printStackTrace();
		}
      
        }
}

