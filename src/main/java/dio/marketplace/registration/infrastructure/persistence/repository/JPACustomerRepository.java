package dio.marketplace.registration.infrastructure.persistence.repository;

import dio.marketplace.registration.domain.CustomerId;
import dio.marketplace.registration.domain.CustomerRepository;
import dio.marketplace.registration.infrastructure.persistence.entity.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Repository
public class JPACustomerRepository implements CustomerRepository {
    private final CustomerEntityRepository customerEntityRepository;

    public JPACustomerRepository(CustomerEntityRepository customerEntityRepository) {
        this.customerEntityRepository = customerEntityRepository;
    }

    @Override
    public dio.marketplace.registration.domain.Customer save(dio.marketplace.registration.domain.Customer customer) {
        var customerEntity = toEntity(customer);
        customerEntityRepository.save(customerEntity);
        return customer;
    }

    @Override
    public List<dio.marketplace.registration.domain.Customer> findAll() {
        var iterable = customerEntityRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .map(JPACustomerRepository::toDomain)
                .toList();
    }

    private static Customer toEntity(dio.marketplace.registration.domain.Customer customer) {
        var entity = new Customer();
        entity.setId(customer.getId().id());
        entity.setFirstName(customer.getName());
        entity.setEmailAddress(customer.getEmail());
        return entity;
    }

    private static dio.marketplace.registration.domain.Customer toDomain(Customer customer) {
        String fullName = Optional.ofNullable(customer.getLastName())
                .map(lastName -> customer.getFirstName() + " " + lastName)
                .orElseGet(customer::getFirstName);
        return new dio.marketplace.registration.domain.Customer(new CustomerId(customer.getId()), fullName, customer.getEmailAddress());
    }
}
