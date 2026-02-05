package com.kajal.ecommerce.User.Service;

import com.kajal.ecommerce.User.DTO.AddressDTO;
import com.kajal.ecommerce.User.DTO.UserRequest;
import com.kajal.ecommerce.User.DTO.UserResponse;
import com.kajal.ecommerce.User.ENUM.UserRole;
import com.kajal.ecommerce.User.Repository.UserRepository;
import com.kajal.ecommerce.User.entity.Address;
import com.kajal.ecommerce.User.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;

    public List<UserResponse> fetchAllUsers()
    {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toUnmodifiableList());
    }

    public void addUser(UserRequest userRequest)
    {
        User user= new User();
        updateUserFromRequest(user,userRequest);

        if(user.getRole()==null)
        {
            user.setRole(UserRole.CUSTOMER);
        }
        userRepository.save(user);
    }



    public Optional<UserResponse> fetchUserById(String id)
    {
        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }

    public boolean updateUser(String id, UserRequest updateUserRequest)
    {
        Optional<User> existingUserOptional = userRepository.findById(id);

        // If the user exists, update their information
        if(existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            // Update the fields of the existing user with the provided updateUser
//            existingUser.setFirstName(updateUserRequest.getFirstName());
//            existingUser.setLastName(updateUserRequest.getLastName());
//            existingUser.setEmail(updateUserRequest.getEmail());
//            existingUser.setPhone(updateUserRequest.getPhone());
//            existingUser.setRole(UserRole.CUSTOMER);

            updateUserFromRequest(existingUser,updateUserRequest);
            // Add more fields here as needed...

            // Save the updated user back to the repository
            userRepository.save(existingUser);
            return true;  // Return true to indicate successful update
        } else {
            // User with the given ID does not exist
            return false; // Return false if the user does not exist
        }
    }

    private UserResponse mapToUserResponse(User user)
    {
        UserResponse response=new UserResponse();

        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if(user.getAddress()!=null)
        {
            AddressDTO addressDTO=new AddressDTO();

            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            addressDTO.setStreet(user.getAddress().getStreet());
            response.setAddress(addressDTO);
        }
        return response;
    }

    private void updateUserFromRequest(User user, UserRequest userRequest)
    {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        if(userRequest.getAddress()!=null)
        {
            Address address=new Address();

            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setZipcode(userRequest.getAddress().getZipcode());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());

            user.setAddress(address);
        }

    }

}
