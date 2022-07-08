package bmw.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bmw.rest.domain.Address;
import bmw.rest.repository.AddressRepository;
import bmw.rest.service.AddressService;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(path = "/address")
public class AddressController {
    Logger logger = LoggerFactory.getLogger(AddressController.class);
    
    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public List<Address> getAll() {
        return addressService.getAddresses();
    }

    @GetMapping("{id}")
    public ResponseEntity<Address> getAddress(@PathVariable long id) {
        Address address = addressService.getAddress(id);
        logger.info("returned address by ID");
        return new ResponseEntity<Address>(address, HttpStatus.OK);
    }

    @PostMapping
    public Address create(@RequestBody Address address) {
        return addressService.createAddress(address);
    }

    @PutMapping
    public ResponseEntity<Address> update(@RequestBody Address address) {
        Address oldAddress = addressService.getAddress(address.getId());
        addressService.updateAddress(oldAddress, address);
        return ResponseEntity.ok(address);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Address> deleteAddress(@PathVariable long id){
        Address address = addressService.getAddress(id);
        if (address == null){
            return ResponseEntity.notFound().build();
        }
        else {
            addressService.deleteAddress(id);
            logger.info("The address deleted successfully");
            return ResponseEntity.ok().build();
        }
    }
}
