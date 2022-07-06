package bmw.rest.service;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bmw.rest.domain.Address;
import bmw.rest.domain.Geo;
import bmw.rest.repository.AddressRepository;

@Service
public class AddressService {
    Logger logger = LoggerFactory.getLogger(AddressService.class);
    
    private final AddressRepository addressRepository;
    private final GeoService geoService;

    @Autowired
    public AddressService(AddressRepository addressRepository, GeoService geoService) {
        this.addressRepository = addressRepository;
        this.geoService = geoService;
    }

    public List<Address> getAddresses() {
        logger.info("Visszateritett cimek");
        return addressRepository.findAll();
    }

    public Optional<Address> getAddress(Long addressId) {
        return addressRepository.findById(addressId);
    }
    
    
    public Address getAddressById(Long addressId) {

    Optional<Address> address = addressRepository.findById(addressId);
        if(!address.isPresent()){
            throw new EntityNotFoundException(String.format("Address with id %d does not exist in database", addressId));
        }
        return address.get();
    }
    public Address createAddress(Address address){
        Geo geo = geoService.createGeo(address.getGeo());
        address.setGeo(geo);
        return addressRepository.save(address);
    }

    public Address updateAddress(Address address, Address newData){
        address.copyValidValuesFrom(newData);
        if(newData.getGeo() != null){
            geoService.updateGeo(address.getGeo(), newData.getGeo());
        }
        return addressRepository.save(address);
    }

    public void deleteAddress(Long addressId){
        addressRepository.deleteById(addressId);
    }
}
