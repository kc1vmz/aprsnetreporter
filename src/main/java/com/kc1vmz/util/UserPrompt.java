package com.kc1vmz.util;

import java.io.Console;
import java.util.Arrays;

public class UserPrompt {
    /**
     * get string from user
     *
     * @param prompt user prompt
     * @return user input
     */
    public static String promptUser(String prompt, boolean isPassword) {
        System.out.print(prompt);
        Console console = System.console();
        if (isPassword && console != null) {
            char[] passwordChars = console.readPassword();
            String password = new String(passwordChars);
            // Clear the password from memory
            Arrays.fill(passwordChars, '0');
            return password;
        } else {
            return console.readLine();
        }
    }

}
