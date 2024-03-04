package com.chen.mint;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Mint {
    /**
     * @param args
     */
    public static void main(String[] args) {
        String filename = args[0];
        try {
			Scanner scanner = new Scanner(new File(filename));

			while (scanner.hasNextLine()) {
				System.out.println(scanner.nextLine());
			}

			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
}
