package bmw.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bmw.rest.domain.Geo;

@Repository
public interface GeoRepository extends JpaRepository<Geo, Long> {
}