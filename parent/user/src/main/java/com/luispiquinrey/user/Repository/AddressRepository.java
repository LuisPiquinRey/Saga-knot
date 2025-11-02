package com.luispiquinrey.user.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luispiquinrey.user.Entities.Address;
@Repository
public interface AddressRepository extends JpaRepository<Address,String>{
    
}
