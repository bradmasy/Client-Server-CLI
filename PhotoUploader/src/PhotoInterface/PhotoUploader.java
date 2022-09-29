package PhotoInterface;

import PhotoInterface.Exceptions.WrongInputException;
import netscape.javascript.JSObject;

import javax.sound.sampled.Port;

import org.json.simple.JSONObject;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Scanner;


public class PhotoUploader {

    Socket connection;
    final private static int UPLOAD;
    final private static String HOST;
    final private static String GOOGLE;
    final private static int EXIT;
    final private static int READ_DATABASE;

    static {
        UPLOAD = 1;
        READ_DATABASE = 2;
        EXIT = 3;
        HOST = "http://localhost:8081/upload";
        GOOGLE = "https://www.google.com/";

    }


    PhotoUploader() {


    }

    public void makeConnectionToServer() throws IOException {

    }


    public void printFileNames(String[] fileNames) {
        int index = 0;
        boolean first = true;
        for (String file : fileNames) {
            if (first) {
                first = false;
            } else {
                System.out.println("Picture[" + index + "]: " + file);
                index++;
            }
        }
    }

    /**
     * @return
     */
    public boolean uploadPhoto(Scanner userInput) throws IOException, InterruptedException {


        System.out.println("Below is a list of all the files in your directory:\n");

        URL obj = new URL("http://localhost:8081/upload/images");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Prints the result
            String responseString = response.toString();
            String[] responseSplit = responseString.split(",");
            printFileNames(responseSplit);
            System.out.println();
        } else {
            System.out.println("GET request not worked");
        }

        return true;
    }


    private boolean readFromDatabase() {


        return true;
    }

    /**
     * @param userInput
     * @return
     */
    public boolean chooseOption(final int userInputInt, Scanner userInput) throws IOException, InterruptedException {
        boolean continueProgram = true;

        try {
            if(Validations.properInteger(userInputInt)) {
                if (userInputInt == UPLOAD) {
                    continueProgram = uploadPhoto(userInput);
                } else if (userInputInt == READ_DATABASE) {
                    continueProgram = readFromDatabase();
                } else if (userInputInt == EXIT) {
                    continueProgram = false; // will trigger an exit event.
                }
            }
        } catch (WrongInputException e) {
            System.out.println(e.getMessage());
        }
        return continueProgram;
    }

    public void displayMenu() throws WrongInputException, IOException {
        boolean continueProgram = true;
        int userDecision;
        Scanner userScanner = new Scanner(System.in);

        makeConnectionToServer(); // connect to the server.

        while (continueProgram) {

            initiateInterface();

            try {
                if (userScanner.hasNextInt()) {

                    userDecision = userScanner.nextInt();
                    continueProgram = chooseOption(userDecision, userScanner);

                } else {
                    throw new WrongInputException("\nIncorrect Input. Please Try Again\n");
                }
            } catch (WrongInputException | IOException | InterruptedException e) {
                System.out.println(e.getMessage());
                userScanner.next();
            }
        }

        System.out.println("Exiting The Program...");
    }

    public void initiateInterface() {
        System.out.println("Welcome To The Photo Uploader!");
        System.out.println("------------------------------");
        System.out.println("Please Choose an Option:");
        System.out.println("1. Upload Photo");
        System.out.println("2. Read Photos");
        System.out.println("3. Exit");
    }


}
