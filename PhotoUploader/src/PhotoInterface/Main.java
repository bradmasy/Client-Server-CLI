package PhotoInterface;

import PhotoInterface.Exceptions.WrongInputException;

import java.io.IOException;

public class Main {

    public static void main(final String[] args){
        PhotoUploader photoUploader = new PhotoUploader();

        try
        {
            photoUploader.displayMenu();
        }
        catch(IOException|WrongInputException e)
        {
            System.out.println(e.getMessage());
        }

    }
}
