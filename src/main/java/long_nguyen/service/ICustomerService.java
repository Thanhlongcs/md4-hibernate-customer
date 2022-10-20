package long_nguyen.service;

import long_nguyen.model.Customer;

import java.util.List;

public interface ICustomerService {
    List<Customer> findAll();
    void save(Customer customer);
    Customer findById(Long id);
    void deleteById(Long id);

}
