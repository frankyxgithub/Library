package com.example.Library.Controller;

import com.example.Library.Model.Users;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class LogFile {
    public static void writeFile(String text) throws IOException {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/user/Desktop/Projects/Library /src/main/java/com/example/Library/Controller/log.txt", true))) {
            bw.write(String.valueOf(text));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
