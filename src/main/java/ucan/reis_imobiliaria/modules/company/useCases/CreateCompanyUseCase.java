package ucan.reis_imobiliaria.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ucan.reis_imobiliaria.exceptions.UserFoundException;
import ucan.reis_imobiliaria.modules.company.CompanyEntity;
import ucan.reis_imobiliaria.modules.company.CompanyRepository;


@Service
public class CreateCompanyUseCase {
    @Autowired //Instanciar
    private CompanyRepository companyRepository;

    public CompanyEntity execute(CompanyEntity companyEntity) {
        this.companyRepository
            .findByNif(companyEntity.getNif())
            .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        return this.companyRepository.save(companyEntity);
    }
}
