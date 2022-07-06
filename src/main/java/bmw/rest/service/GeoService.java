package bmw.rest.service;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bmw.rest.domain.Geo;
import bmw.rest.repository.GeoRepository;

@Service
public class GeoService {
    Logger logger = LoggerFactory.getLogger(GeoService.class);
    
    private final GeoRepository geoRepository;

    @Autowired
    public GeoService(GeoRepository geoRepository) {
        this.geoRepository = geoRepository;
    }

    public List<Geo> getCompanies() {
        logger.info("Visszateritett cimek");
        return geoRepository.findAll();
    }

    public Optional<Geo> getGeo(Long geoId) {
        return geoRepository.findById(geoId);
    }
    
    
    public Geo getGeoById(Long geoId) {
        Optional<Geo> geo = geoRepository.findById(geoId);
        if(!geo.isPresent()){
            throw new EntityNotFoundException(String.format("Geo with id %d does not exist in database", geoId));
        }
        return geo.get();
    }
    public Geo createGeo(Geo geo){
        return geoRepository.save(geo);
    }

    public Geo updateGeo(Geo geo, Geo newData){
        geo.copyValidValuesFrom(newData);
        return geoRepository.save(geo);
    }

    public void deleteGeo(Long geoId){
        geoRepository.deleteById(geoId);
    }
}
