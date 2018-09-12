package model.validation;

import model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {

    private final Client client;

    private static final long NUMBER_OF_DIGITS = 10000000000000l;
    private static final String PATTERN_CARD_NUMBER = "[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9]";

    public List<String> errors;

    public ClientValidator(Client client) {
        this.client = client;
        errors = new ArrayList<>();
    }

    public boolean validateClient(){
        validateCardNumber();
        validateCNP();
        return errors.isEmpty();
    }

    public void validateCardNumber(){
        if (!client.getIdCardNumber().matches(PATTERN_CARD_NUMBER)){
            errors.add("Id Card Number is wrong!");
        }

    }

    public void validateCNP(){
        long cnp = client.getCNP();
        if (cnp / NUMBER_OF_DIGITS != 0){
            errors.add("The number of digits of cnp is worng!");
        }

    }





}
