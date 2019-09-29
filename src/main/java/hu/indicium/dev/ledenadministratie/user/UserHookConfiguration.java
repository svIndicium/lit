package hu.indicium.dev.ledenadministratie.user;

import hu.indicium.dev.ledenadministratie.hooks.CreationHook;
import hu.indicium.dev.ledenadministratie.hooks.HookGroup;
import hu.indicium.dev.ledenadministratie.hooks.UpdateHook;
import hu.indicium.dev.ledenadministratie.mail.MailListService;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import hu.indicium.dev.ledenadministratie.user.hooks.MailListUserCreationHook;
import hu.indicium.dev.ledenadministratie.user.hooks.MailListUserUpdateHook;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class UserHookConfiguration {

    private final MailListService mailListService;

    public UserHookConfiguration(MailListService mailListService) {
        this.mailListService = mailListService;
    }

    @Bean
    CreationHook<UserDTO> userCreationHook() {
        return new HookGroup<>(Collections.singletonList(new MailListUserCreationHook(mailListService)));
    }

    @Bean
    UpdateHook<UserDTO> userUpdateHook() {
        return new HookGroup<>(Collections.singletonList(new MailListUserUpdateHook(mailListService)));
    }
}