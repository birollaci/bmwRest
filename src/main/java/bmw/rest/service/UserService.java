package bmw.rest.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bmw.rest.domain.Address;
import bmw.rest.domain.Company;
import bmw.rest.domain.User;
import bmw.rest.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {
    Logger logger = LoggerFactory.getLogger(UserService.class);
    
    private final UserRepository userRepository;
    private final AddressService addressService;
    private final CompanyService companyService;

    @Autowired
    public UserService(UserRepository userRepository, AddressService addressService, CompanyService companyService) {
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.companyService = companyService;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(long userId){
        Optional<User> user = userRepository.findById((long) userId);
        if(!user.isPresent()){
            logger.warn(String.format("User with id %d does not exist in database", userId));
            throw new EntityNotFoundException(String.format("User with id %d does not exist in database", userId));
        }
        return user.get();
    }

    public User getUserByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent()){
            logger.warn(String.format("User with username %s does not exist in database", username));
            throw new EntityNotFoundException(String.format("User with username %s does not exist in database", username));
        }
        return user.get();
    }

    public User updateUser(User user, User newData){
        user.copyvalidValuesFrom(newData);
        if(newData.getAddress() != null){
            addressService.updateAddress(user.getAddress(), newData.getAddress());
        }
        if(newData.getCompany() != null){
            companyService.updateCompany(user.getCompany(), newData.getCompany());
        }
        return userRepository.save(user);
    }

    public User createUser(User user){
        // Check if user would be unique
        if(userRepository.existsByUsername(user.getUsername())){
            throw new EntityExistsException(String.format("User with username %s already exists", user.getUsername()));
        }
        if(userRepository.existsByEmail(user.getEmail())){
            throw new EntityExistsException(String.format("User with email %s already exists", user.getEmail()));
        }
        // set address
        Address address = addressService.createAddress(user.getAddress());
        user.setAddress(address);
        // set company
        Company company = companyService.createCompany(user.getCompany());
        user.setCompany(company);
        // save user
        return userRepository.save(user);
    }

    public void deleteUser(Long userId){
        if(!userRepository.existsById(userId)){
            throw new EntityNotFoundException(String.format("User with id %d does not exist in database", userId));
        }
        userRepository.deleteById(userId);
    }

    public void deleteUserByUsername(String username){
        if(!userRepository.existsByUsername(username)){
            throw new EntityNotFoundException(String.format("User with username %s does not exist in database", username));
        }
        userRepository.deleteByUsername(username);
    }
}
