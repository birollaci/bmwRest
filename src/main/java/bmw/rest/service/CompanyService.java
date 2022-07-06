package bmw.rest.service;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bmw.rest.domain.Company;
import bmw.rest.repository.CompanyRepository;

@Service
public class CompanyService {
    Logger logger = LoggerFactory.getLogger(CompanyService.class);
    
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getCompanies() {
        logger.info("Visszateritett cimek");
        return companyRepository.findAll();
    }

    public Optional<Company> getCompany(Long companyId) {
        return companyRepository.findById(companyId);
    }
    
    
    public Company getCompanyById(Long companyId) {
        Optional<Company> company = companyRepository.findById(companyId);
        if(!company.isPresent()){
            throw new EntityNotFoundException(String.format("Company with id %d does not exist in database", companyId));
        }
        return company.get();
    }
    public Company createCompany(Company company){
        return companyRepository.save(company);
    }

    public Company updateCompany(Company company, Company newData){
        company.copyValidValuesFrom(newData);
        return companyRepository.save(company);
    }

    public void deleteCompany(Long companyId){
        companyRepository.deleteById(companyId);
    }
}
