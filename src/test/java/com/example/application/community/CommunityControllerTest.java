package com.example.application.community;

import com.example.application.WithAccount;
import com.example.application.account.mapper.AccountReadMapper;
import com.example.application.community.dto.CommunityDto;
import com.example.application.community.mapper.CommunityReadMapper;
import com.example.application.community.mapper.CommunityWriteMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class CommunityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommunityWriteMapper communityWriteMapper;

    @Autowired
    private AccountReadMapper accountReadMapper;

    @Autowired
    private CommunityReadMapper communityReadMapper;





    @WithAccount("ddddd")
    @DisplayName("게시글 글쓰기 폼")
    @Test
    void writeCommunityForm() throws Exception {
        mockMvc.perform(get("/community/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("community/new"))
                .andExpect(authenticated().withUsername("ddddd"))
                .andExpect(model().attributeExists("whitelist"));
    }

    @WithAccount("fffff")
    @DisplayName("게시글 글쓰기 - 성공")
    @Test
    void writeCommunity_success() throws Exception {

        mockMvc.perform(post("/community/new")
                .param("title", "ddddd")
                .param("tag", "ggggg")
                .param("content", "ggggggg")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(authenticated().withUsername("fffff"))
                .andExpect(redirectedUrl("/community"));

    }

    @WithAccount("fffffff")
    @DisplayName("게시글 글쓰기 - 실패 - 입력값 오류")
    @Test
    void writeCommunity_fail() throws Exception {

        mockMvc.perform(post("/community/new")
                        .param("title", "")
                        .param("tag", "")
                        .param("content", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(authenticated().withUsername("fffffff"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("titleError"))
                .andExpect(model().attributeExists("contentError"))
                .andExpect(model().attributeExists("communityNewReqDto"))
                .andExpect(view().name("community/new"));

    }

    @WithAccount("qqqqq")
    @DisplayName("게시글 수정 폼")
    @Test
    void updateCommunityForm() throws Exception {

        Long accountId = accountReadMapper.selectAccountIdByNickname("qqqqq");

        communityWriteMapper.insertCommunity(
                CommunityDto.CommunityNewDto.builder()
                        .content("제목")
                        .title("내용")
                        .accountId(accountId)
                        .build()
        );

       Long communityId = communityReadMapper.selectCommunityIdByAccountId(accountId);

        mockMvc.perform(get("/articles/{communityId}/modify", communityId))
                .andExpect(status().isOk())
                .andExpect(view().name("community/articleEdit"))
                .andExpect(authenticated().withUsername("qqqqq"))
                .andExpect(model().attributeExists("articleModificationFormDto"))
                .andExpect(model().attributeExists("whitelist"));
    }

    @WithAccount("ddddddd")
    @DisplayName("게시글 수정하기 - 입력값 정상")
    @Test
    void updateCommunity_success() throws Exception {

        Long accountId = accountReadMapper.selectAccountIdByNickname("ddddddd");

        communityWriteMapper.insertCommunity(
                CommunityDto.CommunityNewDto.builder()
                        .content("제목")
                        .title("내용")
                        .accountId(accountId)
                        .build()
        );

        Long communityId = communityReadMapper.selectCommunityIdByAccountId(accountId);

        mockMvc.perform(post("/articles/{communityId}/modify", communityId)
                .param("communityId", communityId.toString())
                .param("title", "제목 변경")
                .param("content", "내용 변경")
                .param("accountId", accountId.toString())
                .param("tag", "태그")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(authenticated().withUsername("ddddddd"))
                .andExpect(redirectedUrl("/community"));

    }


    @WithAccount("dddddddd")
    @DisplayName("게시글 수정하기 - 입력값 실패")
    @Test
    void updateCommunity_fail() throws Exception {

        Long accountId = accountReadMapper.selectAccountIdByNickname("dddddddd");

        communityWriteMapper.insertCommunity(
                CommunityDto.CommunityNewDto.builder()
                        .content("제목")
                        .title("내용")
                        .accountId(accountId)
                        .build()
        );

        Long communityId = communityReadMapper.selectCommunityIdByAccountId(accountId);

        mockMvc.perform(post("/articles/{communityId}/modify", communityId)
                        .param("communityId", communityId.toString())
                        .param("title", "")
                        .param("content", "")
                        .param("accountId", accountId.toString())
                        .param("tag", "태그")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("articleModificationFormDto"))
                .andExpect(model().attributeExists("whitelist"))
                .andExpect(authenticated().withUsername("dddddddd"))
                .andExpect(view().name("community/articleEdit"));

    }


    @WithAccount("sssss")
    @DisplayName("게시글 삭제하기")
    @Test
    void deleteCommunity_success() throws Exception {

        Long accountId = accountReadMapper.selectAccountIdByNickname("sssss");

        communityWriteMapper.insertCommunity(
                CommunityDto.CommunityNewDto.builder()
                        .content("제목")
                        .title("내용")
                        .accountId(accountId)
                        .build()
        );

        Long communityId = communityReadMapper.selectCommunityIdByAccountId(accountId);

        mockMvc.perform(post("/articles/{communityId}/delete", communityId)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(authenticated().withUsername("sssss"))
                .andExpect(flash().attributeExists("msg"))
                .andExpect(redirectedUrl("/community"));

    }

    @WithAccount("ffffff")
    @DisplayName("특정 유저의 썼던 글 목록으로 이동")
    @Test
    void selectUserActivity() throws Exception {
        Long accountId = accountReadMapper.selectAccountIdByNickname("ffffff");

        communityWriteMapper.insertCommunity(
                CommunityDto.CommunityNewDto.builder()
                        .content("제목")
                        .title("내용")
                        .accountId(accountId)
                        .build()
        );

        mockMvc.perform(get("/user/{nickname}/activity", "ffffff"))
                .andExpect(status().isOk())
                .andExpect(view().name("community/activity"))
                .andExpect(model().attributeExists("nickname"));
    }


    @WithAccount("sdsdsd")
    @DisplayName("특정 유저의 썼던 글을 커서 페이징으로 불러옴")
    @Test
    void selectUserActivityData() throws Exception {
        Long accountId = accountReadMapper.selectAccountIdByNickname("sdsdsd");

        communityWriteMapper.insertCommunity(
                CommunityDto.CommunityNewDto.builder()
                        .content("제목")
                        .title("내용")
                        .accountId(accountId)
                        .build()
        );

        mockMvc.perform(get("/user/{nickname}/activityData", "sdsdsd")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

}
