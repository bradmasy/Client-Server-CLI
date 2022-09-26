package PhotoInterface;

import PhotoInterface.Exceptions.WrongInputException;

import javax.sound.sampled.Port;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class PhotoUploader {

    Socket connection;
    String host = "http://localhost:8081/";


    PhotoUploader() {


    }

    public void makeConnectionToServer() throws IOException
    {
        System.out.println("Attempting Connection...");
        URL url = new URL(host);
        InetAddress IP = InetAddress.getByName(url.getHost());
        System.out.println(IP);
        String Address = IP.getHostAddress();
        System.out.println(Address);
        // need to connect with a socket...
        // need IP address of server, the port number,
        int port = url.getPort();
        System.out.println("port number is: " + port);
        connection = new Socket(IP,port);
        System.out.println("Connection made to: " + connection.getInetAddress().getHostName());


    }

    /**
     *
     * @return
     */
    public boolean uploadPhoto() throws IOException {

        makeConnectionToServer();


        return true;
    }

    /**
     *
     * @param userInput
     * @return
     */
    public boolean chooseOption(final int userInput) throws IOException {
        boolean continueProgram = true;

        switch (userInput){
            case 1:
                continueProgram = uploadPhoto();
            case 2:
                continueProgram = false; // will trigger an exit event.
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
                    continueProgram = chooseOption(userDecision);
                }
                else {
                    throw new WrongInputException("\nIncorrect Input. Please Try Again\n");
                }
            } catch (WrongInputException | IOException e) {
                System.out.println(e.getMessage());
                userScanner.next();
            }
        }

        System.out.println("Exiting The Program...2");
    }

    public void initiateInterface() {
        System.out.println("Welcome To The Photo Uploader!");
        System.out.println("------------------------------");
        System.out.println("Please Choose an Option:");
        System.out.println("1. Upload Photo");
        System.out.println("2. Exit");
    }


}
