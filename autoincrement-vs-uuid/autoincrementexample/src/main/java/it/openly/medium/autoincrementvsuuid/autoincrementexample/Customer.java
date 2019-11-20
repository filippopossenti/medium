package it.openly.medium.autoincrementvsuuid.autoincrementexample;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
class Customer {
    private Long id;
    private String name;
    private List<PhoneNumber> phoneNumbers;
}
