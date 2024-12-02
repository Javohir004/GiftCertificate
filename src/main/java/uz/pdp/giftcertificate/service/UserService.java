package uz.pdp.giftcertificate.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.giftcertificate.exception.BaseException;
import uz.pdp.giftcertificate.domain.dto.request.LoginDTO;
import uz.pdp.giftcertificate.domain.dto.request.UserCreateDTO;
import uz.pdp.giftcertificate.domain.dto.response.UserResponse;
import uz.pdp.giftcertificate.domain.entity.UserEntity;
import uz.pdp.giftcertificate.domain.views.UserView;
import uz.pdp.giftcertificate.repository.UserRepository;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserService implements BaseService<UserEntity, UserResponse, UserCreateDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityTokenService tokenService;

    @Transactional
    public UserResponse save(UserCreateDTO userCreateDTO) {
        return mapEntityToResponse(userRepository.save(
                mapRequestToEntity(userCreateDTO)
        ));
    }

    public UserResponse userInfo(String token) {
        return mapEntityToResponse(tokenService.decode(token));
    }

    public String login(LoginDTO loginDTO) {
        UserEntity user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new BaseException("wrong password or username"));
        if (Objects.equals(user.getPassword(), loginDTO.getPassword())) {
            return tokenService.generateToken(user);
        }
        throw new BaseException("wrong password or username");
    }

    public UserView findByIdView(UUID id) {
        return userRepository.findUserEntityById(id).orElseThrow(
                () -> new BaseException("user not found"));
    }

    @Override
    public UserEntity mapRequestToEntity(UserCreateDTO userCreateDTO) {
        return modelMapper.map(userCreateDTO, UserEntity.class);
    }

    @Override
    public UserResponse mapEntityToResponse(UserEntity entity) {
        return modelMapper.map(entity, UserResponse.class);
    }
}
