package hu.indicium.dev.ledenadministratie.setting;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.indicium.dev.ledenadministratie.setting.dto.SettingDTO;
import hu.indicium.dev.ledenadministratie.setting.requests.UpdateSettingRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SettingController.class)
@DisplayName("Setting Controller")
@Tag("Controller")
class SettingControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private SettingService settingService;

    @Autowired
    private MockMvc mvc;

    private SettingDTO settingDTO;

    @BeforeEach
    void setup() {
        settingDTO = new SettingDTO();
        settingDTO.setKey("testSetting");
        settingDTO.setValue("testValue");
        settingDTO.setTitle("Title");
        settingDTO.setDescription("Description");
        settingDTO.setPermission("test_settings");
        settingDTO.setUpdatedBy("John Doe");
        settingDTO.setUpdatedAt(new Date());
    }

    @Test
    @DisplayName("Get all settings")
    void shouldReturnJson_whenGetAllSettings() throws Exception {
        given(settingService.getAllSettings()).willReturn(Collections.singletonList(settingDTO));

        mvc.perform(get("/settings/lit")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].key", is(settingDTO.getKey())))
                .andExpect(jsonPath("$[0].value", is(settingDTO.getValue())))
                .andExpect(jsonPath("$[0].title", is(settingDTO.getTitle())))
                .andExpect(jsonPath("$[0].description", is(settingDTO.getDescription())))
                .andExpect(jsonPath("$[0].permission", is(settingDTO.getPermission())))
                .andExpect(jsonPath("$[0].updatedBy", is(settingDTO.getUpdatedBy())));
    }

    @Test
    @DisplayName("Update setting")
    void shouldReturnJson_whenUpdateSetting() throws Exception {
        String newValue = "newValue";

        settingDTO.setValue(newValue);

        given(settingService.updateSetting(eq(settingDTO.getKey()), eq(newValue))).willReturn(settingDTO);

        UpdateSettingRequest updateSettingRequest = new UpdateSettingRequest();
        updateSettingRequest.setValue(newValue);

        mvc.perform(put("/settings/lit/" + settingDTO.getKey())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(updateSettingRequest)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.key", is(settingDTO.getKey())))
                .andExpect(jsonPath("$.value", is(settingDTO.getValue())))
                .andExpect(jsonPath("$.title", is(settingDTO.getTitle())))
                .andExpect(jsonPath("$.description", is(settingDTO.getDescription())))
                .andExpect(jsonPath("$.permission", is(settingDTO.getPermission())))
                .andExpect(jsonPath("$.updatedBy", is(settingDTO.getUpdatedBy())));
    }

    @Test
    @DisplayName("Update setting")
    void shouldThrowException_whenUpdateValueWithBlankValue() throws Exception {
        String newValue = "";

        UpdateSettingRequest updateSettingRequest = new UpdateSettingRequest();
        updateSettingRequest.setValue(newValue);

        mvc.perform(put("/settings/lit/" + settingDTO.getKey())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(updateSettingRequest)))
                .andExpect(status().isBadRequest());
    }


}