package it.openly.medium.autoincrementvsuuid.autoincrementexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController("customers")
public class CustomerController {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @RequestMapping("/")
    public String index() {
        return "Up and running!";
    }

    @PostMapping("create")
    public Customer createCustomer(@RequestBody Customer customer) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", customer.getName());
        KeyHolder customerKeyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update("insert into customers(name) values (:name)", params, customerKeyHolder, new String[] { "id" });
        customer.setId(Objects.requireNonNull(customerKeyHolder.getKey()).longValue());
        for(PhoneNumber number : customer.getPhoneNumbers()) {
            addPhoneNumber(number, customer);
        }
        return customer;
    }

    private void addPhoneNumber(PhoneNumber phoneNumber, Customer customer) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("number", phoneNumber.getNumber());
        params.addValue("customer_id", customer.getId());
        KeyHolder phoneNumberKeyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update("insert into phone_numbers(customer_id, number) values (:customer_id, :number)", params, phoneNumberKeyHolder, new String[] { "id" });
        phoneNumber.setId(Objects.requireNonNull(phoneNumberKeyHolder.getKey()).longValue());
    }
}
