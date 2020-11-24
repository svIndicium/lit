package hu.indicium.dev.ledenadministratie.setting;

import hu.indicium.dev.ledenadministratie.infrastructure.auth.Auth0User;
import hu.indicium.dev.ledenadministratie.infrastructure.auth.AuthService;
import hu.indicium.dev.ledenadministratie.setting.dto.SettingDTO;
import hu.indicium.dev.ledenadministratie.setting.exceptions.SettingNotFoundException;
import hu.indicium.dev.ledenadministratie.setting.exceptions.SettingNotSetException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SettingServiceImpl implements SettingService {

    private final SettingRepository settingRepository;

    private final AuthService authService;

    public SettingServiceImpl(SettingRepository settingRepository, @Lazy AuthService authService) {
        this.settingRepository = settingRepository;
        this.authService = authService;
    }

    @Override
    public String getValueByKey(String key) {
        return getSettingByKey(key).getValue();
    }

    @Override
    public SettingDTO getSettingByKey(String key) {
        Setting setting = getSetting(key);
        if (setting.getValue().isBlank()) {
            throw new SettingNotSetException(key);
        }
        return SettingMapper.map(setting);
    }

    @Override
    @PostAuthorize("hasPermission('write:' + returnObject.permission)")
    public SettingDTO updateSetting(String key, String value) {
        Auth0User auth0User = authService.getCurrentUser();
        Setting setting = getSetting(key);
        setting.setValue(value);
        setting.setUpdatedBy(auth0User.getName());
        setting = settingRepository.save(setting);
        return SettingMapper.map(setting);
    }

    @Override
    @PreAuthorize("hasPermission('read:settings')")
    @PostFilter("hasPermission('read:' + filterObject.permission)")
    public List<SettingDTO> getAllSettings() {
        return settingRepository.findAll()
                .stream()
                .map(SettingMapper::map)
                .collect(Collectors.toList());
    }

    private Setting getSetting(String key) {
        return settingRepository.findByKey(key)
                .orElseThrow(() -> new SettingNotFoundException(key));
    }
}
