package com.example.application.util;


import com.example.application.account.dto.Account;
import com.example.application.security.UserAccount;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;

import java.util.ArrayList;
import java.util.List;

public class SessionUtils {
    public static void expireSessionsByNickname(String nickname, SessionRegistry sessionRegistry) {
        List<Object> principals = new ArrayList<>(sessionRegistry.getAllPrincipals());

        for (Object principal : principals) {
            if (principal instanceof UserAccount) {
                UserAccount userAccount = (UserAccount) principal;
                Account account = userAccount.getAccount();

                if (account.getNickname().equals(nickname)) {
                    List<SessionInformation> sessions = sessionRegistry.getAllSessions(principal, false);
                    for (SessionInformation session : sessions) {
                        session.expireNow();
                    }
                }
            }
        }
    }
}
