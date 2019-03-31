package domain.validators;

import domain.Client;

import java.util.Arrays;

public class ClientValidator implements Validator<Client>{


    @Override
    public void validate(Client client) throws ValidatorException {

        Arrays.stream(ValidatorsClient.values()).filter(v -> v.predicate.test(client)).
                findFirst().ifPresent(s->{throw new ValidatorException("Invalid input!");});


    }
}
