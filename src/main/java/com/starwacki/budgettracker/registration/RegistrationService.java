package com.starwacki.budgettracker.registration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class RegistrationService {

    private final UserRepository userRepository;

    void registerUser(UserDTO userDTO) {
        if (isUserCanBeRegister(userDTO))
            registerNewUser(userDTO);
        else
            throw new UserOrEmailAlreadyExist();
    }

    private void registerNewUser(UserDTO userDTO) {
        userRepository.save(UserMapper.mapDtoToEntity(userDTO));
    }

    private boolean isUserCanBeRegister(UserDTO userDTO) {
        return !userRepository.existsByUsername(userDTO.username()) && !userRepository.existsByEmail(userDTO.email());
    }
}
