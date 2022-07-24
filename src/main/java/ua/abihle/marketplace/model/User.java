package ua.abihle.marketplace.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "firstName is mandatory for User")
    private String firstName;
     @NotBlank(message = "lastName is mandatory for User")
    private String lastName;
    @NotNull(message = "amountOfMoney is mandatory for User")
    @Min(value = 0, message = "Cannot be less then 0")
    private BigDecimal amountOfMoney;

    @ManyToMany
    @JsonIgnore
    private Set<Product> products;

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
    public Set<Product> getProducts() {
        return products;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(BigDecimal amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

}
