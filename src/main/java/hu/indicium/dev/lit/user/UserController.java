package hu.indicium.dev.lit.user;

import hu.indicium.dev.lit.userdata.UserData;
import hu.indicium.dev.lit.userdata.dto.UserDataDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    private final UserServiceInterface userService;

    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserServiceInterface userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/whoami")
    Object user() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Map<String, Object> res = new HashMap<>();
        res.put("principal", auth.getPrincipal());
        res.put("authorities", auth.getAuthorities());
        res.put("credentials", auth.getCredentials());
        res.put("details", auth.getDetails());
        return res;
    }

    private UserDataDTO convertToDTO(UserData userData) {
        return modelMapper.map(userData, UserDataDTO.class);
    }
}
