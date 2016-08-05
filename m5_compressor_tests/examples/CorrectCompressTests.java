import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;

public class CompressTests {

    @Test
    public void firstTest() {
        try {
            String str = "abcdefg";
            assertEquals(compressDecompress(str), str);

            StringBuilder builder = new StringBuilder();
            for (int i = 0;i < 100;i++) {
                builder.append("abracadabra");
            }
            str = builder.toString();

            assertTrue(this.getSizeCompressed(str) < str.length());
            assertEquals(this.compressDecompress(str), str);
        } catch (Exception e) {
            fail("Exception occured : " + e);
        }
    }

    public long getSizeCompressed(String content) throws IOException {

        PrintWriter writer = new PrintWriter("./input.txt");
        writer.println(content);
        writer.close();
        Compress.main(new String[]{"./input.txt", "./compressed.txt"});

        File file = new File("./compressed.txt");
        return file.length();

    }

    public String compressDecompress(String content) throws IOException {
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
