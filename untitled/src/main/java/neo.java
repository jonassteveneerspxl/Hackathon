import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class neo {

    public static void main(String[] args) {
        // Server response containing ANSI escape codes
        // Read server response from file
        String serverResponse = readFromFile("src/main/server_response.txt");
        String flag = extractFlag(serverResponse);
        System.out.println("Flag: " + flag);
    }

    private static String readFromFile(String filename) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private static String removeAnsiEscapeCodes(String input) {
        // Regular expression to match ANSI escape codes
        String regex = "(\\u001B\\[)([^\\u001B]*)([A-Za-z])";

        // Replace ANSI escape codes with an empty string
        return input.replaceAll(regex, "");
    }


    private static String extractFlag(String input) {
        // Regular expression to match flag pattern
        String regex = "CSC\\{[^{}]+\\}";

        // Remove ANSI escape codes from the input string
        String cleanInput = removeAnsiEscapeCodes(input);

        // Pattern object
        Pattern pattern = Pattern.compile(regex);

        // Matcher object
        Matcher matcher = pattern.matcher(cleanInput);

        // Find flag in the input string
        if (matcher.find()) {
            // Extract the flag
            return matcher.group();
        } else {
            // Flag not found
            return "Flag not found";
        }
    }
}
