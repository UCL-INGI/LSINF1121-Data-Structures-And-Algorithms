import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;

/* Template for the unit tests of misson 5
 * To success on INGInious, need to check for :
 - An empty file
 - A long file (> 100 characters)
 - Size of compressed file (at least not larger than input file) */

/**
 * @author Simon HARDY
 */
public class CompressTests {

    @Test
    public void firstTest() {
    	try {
    	    String str = "abcdefg";
    	    assertEquals(compress_decompress(str), str);
    	} catch (Exception e) { // this is important to catch exception, for example on empty file
    	    fail("Exception occured : " + e);
    	}
    }

    public String compress_decompress(String content) throws IOException {
    	PrintWriter writer = new PrintWriter("./input.txt");
    	writer.println(content);
    	writer.close();
    	Compress.main(new String[]{"./input.txt", "./compressed.txt"});
    	Decompress.main(new String[]{"./compressed.txt", "./output.txt"});
    	Scanner scanner = new Scanner(new File("./output.txt"));
    	String str = scanner.useDelimiter("\\Z").next();
    	scanner.close();
    	return str;
    }
}
