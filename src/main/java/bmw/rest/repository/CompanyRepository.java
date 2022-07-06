package bmw.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bmw.rest.domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}