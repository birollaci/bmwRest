package bmw.rest.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import bmw.rest.domain.Address;
import bmw.rest.domain.Company;
import bmw.rest.domain.Geo;
import bmw.rest.domain.User;
import bmw.rest.service.UserService;
import bmw.rest.validate.Validator;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

@Configuration
public class UserConfig {
    
    Logger logger = LoggerFactory.getLogger(UserConfig.class);
    
    @Bean
    CommandLineRunner userConfigCommandLineRunner(UserService userService) {
        
        return args -> {

            HttpResponse<JsonNode> res = Unirest.get("https://jsonplaceholder.typicode.com/users").header("accept", "application/json").asJson();
		    JSONArray data = res.getBody().getArray();

            Validator.validateStatus(logger, res.getStatus());
            if(!Validator.validateNumberOfUsers(data.length())){
                logger.warn("Number of users is greater than limit");
            }

            User user;

            for (int jsonIter = 0; jsonIter < data.length(); jsonIter++) {
                JSONObject userObject = data.getJSONObject(jsonIter);
                JSONObject addressObject = userObject.getJSONObject("address");
                JSONObject geoObject = addressObject.getJSONObject("geo");
                JSONObject companyObject = userObject.getJSONObject("company");

                if(!Validator.validateEmail(userObject.getString("email"))) {
                    logger.warn("Email of " + userObject.getString("username") + " is invalid!");
                    continue;
                }

                user = new User(
                    userObject.getInt("id"),
                    userObject.getString("name"),
                    userObject.getString("username"),
                    userObject.getString("email"),
                    new Address(
                        addressObject.getString("street"),
                        addressObject.getString("suite"),
                        addressObject.getString("city"),
                        addressObject.getString("zipcode"),
                        new Geo(
                            geoObject.getString("lat"),
                            geoObject.getString("lng")
                        )
                    ),
                    userObject.getString("phone"),
                    userObject.getString("website"),
                    new Company(
                        companyObject.getString("name"),
                        companyObject.getString("catchPhrase"),
                        companyObject.getString("bs")
                    )
                );

                userService.createUser(user);
                if(jsonIter >= data.length()-1) {
                    logger.info("Emails of users are valid!");
                    logger.info("New users have been inserted.");
                }
            }

            

            User admin = new User(
                11,
                "admin",
                "admin",
                "admin@bmw.ro",
                new Address(
                    "Utca", 
                    "120", 
                    "M-Ciuc", 
                    "537203", 
                    new Geo(
                        "46.360957",
                        "25.805639"
                    )),
                "0749109407",
                "https://github.com/birollaci/bmwRest.git",
                new Company(
                    "Magic Solutions srl", 
                    "IT BANK Streaming", 
                    "harness real-time e-markets")
            );

            userService.createUser(admin);
        };
    }
}
