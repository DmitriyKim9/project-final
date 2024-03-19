package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.common.util.JsonUtil;
import com.javarush.jira.profile.internal.ProfileMapper;
import com.javarush.jira.profile.internal.ProfileRepository;
import com.javarush.jira.profile.internal.model.Profile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.login.internal.web.UserTestData.*;
import static com.javarush.jira.profile.internal.web.ProfileRestController.REST_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    private ProfileRepository repository;
    @Autowired
    ProfileMapper mapper;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAuthorizedUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getUnauthorizedUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNew() throws Exception {

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(ProfileTestData.getNewTo())))
                .andExpect(status().isNoContent());

        Profile profileToActual = repository.getExisted(USER_ID);
        Profile profileToExpected = ProfileTestData.getNew(USER_ID);

        ProfileTestData.PROFILE_MATCHER.assertMatch(profileToActual, profileToExpected);

    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getUpdated() throws Exception {

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(ProfileTestData.getUpdatedTo())))
                .andExpect(status().isNoContent());

        Profile profileToActual = repository.getExisted(USER_ID);
        Profile profileToExpected = ProfileTestData.getUpdated(USER_ID);

        ProfileTestData.PROFILE_MATCHER.assertMatch(profileToActual, profileToExpected);

    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getInvalidTo() throws Exception {

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(ProfileTestData.getInvalidTo())))
                .andExpect(status().is4xxClientError());

    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getWithUnknownNotificationTo() throws Exception {

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(ProfileTestData.getWithUnknownNotificationTo())))
                .andExpect(status().is4xxClientError());

    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getWithUnknownContactTo() throws Exception {

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(ProfileTestData.getWithUnknownContactTo())))
                .andExpect(status().is4xxClientError());

    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getWithContactHtmlUnsafeTo() throws Exception {

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(ProfileTestData.getWithContactHtmlUnsafeTo())))
                .andExpect(status().is4xxClientError());

    }

}