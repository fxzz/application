package com.example.application.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoOAuth2Response {
    private Long id;
    private LocalDateTime connectedAt;
    private Map<String, Object> properties;
    private KakaoAccount kakaoAccount;


    public static KakaoOAuth2Response from(Map<String, Object> attributes) {
        return new KakaoOAuth2Response(
                Long.valueOf(String.valueOf(attributes.get("id"))),
                LocalDateTime.parse(
                        String.valueOf(attributes.get("connected_at")),
                        DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault())
                ),
                (Map<String, Object>) attributes.get("properties"),
                KakaoAccount.from((Map<String, Object>) attributes.get("kakao_account"))
        );
    }

    public String email() {
        return this.kakaoAccount.getEmail();
    }

    public String nickname() {
        return this.kakaoAccount.profile.getNickname();
    }



    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class KakaoAccount {
        private Boolean profileNicknameNeedsAgreement;
        private Profile profile;
        private Boolean hasEmail;
        private Boolean emailNeedsAgreement;
        private Boolean isEmailValid;
        private Boolean isEmailVerified;
        private String email;

        public static KakaoAccount from(Map<String, Object> attributes) {
            return new KakaoAccount(
                    Boolean.valueOf(String.valueOf(attributes.get("profile_nickname_needs_agreement"))),
                    Profile.from((Map<String, Object>) attributes.get("profile")),
                    Boolean.valueOf(String.valueOf(attributes.get("has_email"))),
                    Boolean.valueOf(String.valueOf(attributes.get("email_needs_agreement"))),
                    Boolean.valueOf(String.valueOf(attributes.get("is_email_valid"))),
                    Boolean.valueOf(String.valueOf(attributes.get("is_email_verified"))),
                    String.valueOf(attributes.get("email"))
            );
        }

        public String nickname() {
            return profile.getNickname();
        }
    }

    @Getter
    @Setter
    public static class Profile {
        private String nickname;

        public Profile(String nickname) {
            this.nickname = nickname;
        }
        public static Profile from(Map<String, Object> attributes) {
            return new Profile(String.valueOf(attributes.get("nickname")));
        }
    }

}
