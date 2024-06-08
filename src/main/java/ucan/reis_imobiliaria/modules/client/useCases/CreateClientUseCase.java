package ucan.reis_imobiliaria.modules.client.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ucan.reis_imobiliaria.exceptions.UserFoundException;
import ucan.reis_imobiliaria.modules.client.ClientEntity;
import ucan.reis_imobiliaria.modules.client.ClientRepository;


@Service
public class CreateClientUseCase {

    @Autowired
    private ClientRepository clientRepository;

    public ClientEntity execute(ClientEntity clientEntity) {
        this.clientRepository
                .findByNif(clientEntity.getNif())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });
                
        return this.clientRepository.save(clientEntity);
    }
}
