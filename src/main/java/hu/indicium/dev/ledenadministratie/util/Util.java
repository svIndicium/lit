package hu.indicium.dev.ledenadministratie.util;

public class Util {
    public static String getFullLastName(String middleName, String lastName) {
        if (middleName == null) {
            return lastName;
        }
        return middleName + ' ' + lastName;
    }
}