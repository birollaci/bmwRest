package bmw.rest.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String street;

    @NotBlank
    private String suite;

    @NotBlank
    private String city;

    @NotBlank
    private String zipcode;

	@OneToOne
    private Geo geo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public Address() {
    }

    public Address(String street, String suite, String city, String zipcode, Geo geo) {
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipcode = zipcode;
        this.geo = geo;
    }

    public Address copyValidValuesFrom(Address address) {
        if (address.street != null)
            this.street = address.street;
        if (address.suite != null)
            this.suite = address.suite;
        if (address.city != null)
            this.city = address.city;
        if (address.zipcode != null)
            this.zipcode = address.zipcode;
        if (address.geo != null)
            this.geo = address.geo;
        return this;
    }

    @Override
    public String toString() {
        return "Address [id=" + id + 
        ", street=" + street + 
        ", suite=" + suite + 
        ", city=" + city + 
        ", zipcode=" + zipcode + "]";
    }
}

// test:
// {
//     "street": "Kulas Light",
//     "suite": "Apt. 556",
//     "city": "Gwenborough",
//     "zipcode": "92998-3874",
//     "geo": {
//       "lat": "-37.3159",
//       "lng": "81.1496"
//     }
// }