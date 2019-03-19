package hu.indicium.dev.lit.userdata;

import java.util.Date;

public interface UserDataBuilderInterface {
    UserDataBuilderInterface setFirstName(String firstName);

    UserDataBuilderInterface setLastName(String lastName);

    UserDataBuilderInterface setGender(Gender gender);

    UserDataBuilderInterface setDateOfBirth(Date dateOfBirth);

    UserDataBuilderInterface setStreet(String street);

    UserDataBuilderInterface setHouseNumber(String houseNumber);

    UserDataBuilderInterface setZipCode(String zipCode);

    UserDataBuilderInterface setCity(String city);

    UserDataBuilderInterface setCountry(String country);

    UserDataBuilderInterface setDateOfRegister(Date dateOfRegister);

    UserDataBuilderInterface setEmail(String email);

    UserDataBuilderInterface setPhoneNumber(String phoneNumber);

    UserDataBuilderInterface setStudentId(int studentId);

    UserData build();
}
