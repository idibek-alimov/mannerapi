package shopapi.shopapi.service.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopapi.shopapi.dto.address.AddressDto;
import shopapi.shopapi.models.user.Address;
import shopapi.shopapi.models.user.User;
import shopapi.shopapi.repository.user.AddressRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {
    private final AddressRepository addressRepository;

    public List<Address> getByUserId(Long id){
        return addressRepository.findByUserId(id);
    }
    public Address findByUserIdAndAddressId(Long userId,Long addressId){
        return addressRepository.findByUserIdAndAddressId(userId,addressId);
    }
    public Address findOrCreateAddress(AddressDto addressDto, User user){
        Address address = findByUserIdAndAddressId(user.getId(),addressDto.getId());
        if(address != null)
            return address;
        return addressRepository.save(Address.builder()
                        .addressLine(addressDto.getAddressLine())
                        .latitude(addressDto.getLatitude())
                        .longitude(addressDto.getLongitude())
                        .user(user)
                .build());
    }
    public Address createAddress(Address address){
        return addressRepository.save(address);
    }
}
