package PhotoInterface;

import PhotoInterface.Exceptions.WrongInputException;

public class Validations {

    public static boolean properInteger(int n) throws WrongInputException {
        boolean valid = true;
        if(n <= 0 || n > 3)
        {
           throw new WrongInputException("Option \"" + n + "\" is not a correct option. Please try again...\n");
        }
        return valid;
    }
}
