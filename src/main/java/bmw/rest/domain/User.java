package bmw.rest.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "users", 
uniqueConstraints = { 
    @UniqueConstraint(columnNames = "username"),
    @UniqueConstraint(columnNames = "email") 
})
public class User {

	@Id
	private int id;

	@NotBlank
	private String name;

	@NotBlank
	private String username;

	@NotBlank
	@Email
	private String email;

    @NotNull
    @OneToOne
    private Address address;

	@NotBlank
    private String phone;

	@NotBlank
    private String website;

	@NotNull
	@OneToOne
	private Company company;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public User() {
	}

	public User(int id, String name, String username, String email, Address address, String phone, String website, Company company) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.website = website;
		this.company = company;
	}

	

	public User copyvalidValuesFrom(User user){
		if(user.id > 0)
			this.id = user.id;
		if(user.name != null)
			this.name = user.name;
		if(user.username != null)
			this.username = user.username;
		if(user.email != null)
			this.email = user.email;
		if(user.address != null)
			this.address.copyValidValuesFrom(user.address);
		if(user.phone != null)
			this.phone = user.phone;
		if(user.website != null)
			this.website = user.website;
		if(user.company != null)
			this.company.copyValidValuesFrom(user.company);

		return this;
	}
}

// { 
	// "id": 1,
    // "name": "Leanne Graham",
    // "username": "Bret",
    // "email": "Sincere@april.biz",
    // "address": {
    //   "street": "Kulas Light",
    //   "suite": "Apt. 556",
    //   "city": "Gwenborough",
    //   "zipcode": "92998-3874",
    //   "geo": {
    //     "lat": "-37.3159",
    //     "lng": "81.1496"
    //   }
    // },
    // "phone": "1-770-736-8031 x56442",
    // "website": "hildegard.org",
    // "company": {
    //   "name": "Romaguera-Crona",
    //   "catchPhrase": "Multi-layered client-server neural-net",
    //   "bs": "harness real-time e-markets"
    // }
// }

// login
// {
//     "username": "admin",
//     "password": "admin"
// }