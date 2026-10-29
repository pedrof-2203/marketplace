package dio.marketplace.registration.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

@Getter
@Setter
public class Customer {
    private CustomerId id;
    private String name;
    private String email;

    public Customer(CustomerId id, String name, String email) {
        Assert.notNull(name, "Customer's name must not be null.");
        Assert.notNull(email, "Customer's e-mail must not be null.");

        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Customer(String name, String email) {
        this(new CustomerId(), name, email);
    }
}
