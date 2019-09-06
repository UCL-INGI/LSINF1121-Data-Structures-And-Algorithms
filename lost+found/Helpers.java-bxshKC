package tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Helpers {
    public static String[] readfile(String filename, int size) {

        String res[] = new String[size];

        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String st;
            int i=0;
            while ((st = br.readLine()) != null && i<size) {
                res[i] = st;
                i++;
            }

            return res;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
