package com.luispiquinrey.user.Service;

import org.springframework.data.repository.CrudRepository;

import com.luispiquinrey.Service.CrudService;
import com.luispiquinrey.user.Entities.Address;
import com.luispiquinrey.user.Repository.AddressRepository;

public class AddressService extends CrudService<Address, String>{

    public AddressService(AddressRepository addressRepository, Class<Address> entityClass) {
        super(addressRepository, entityClass);
    }
}
