package com.example.application;


import com.example.application.community.dto.CommunityDto;
import com.example.application.community.mapper.CommunityWriteMapper;
import com.example.application.community.service.CommunityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@SpringBootTest
public class TestClass  {

    @Autowired
    CommunityWriteMapper communityWriteMapper;

    @Test
    public void testCreateData() {
        AtomicInteger count = new AtomicInteger(0);
        IntStream.rangeClosed(1, 1000000)
                .parallel()
                .forEach(i -> {
                    // 데이터 생성 로직
                    // 여기에 데이터 생성 코드를 작성합니다.
                    // 예: communityService.saveCommunity(new CommunityNewReqDto(...), accountId);
                    communityWriteMapper.insertCommunity(CommunityDto.CommunityNewDto.builder().content("FFFFFFF").title("FFFFFFFFF").accountId(3L).build());
                    count.incrementAndGet();
                });

        // 생성된 데이터의 개수를 출력합니다.
        System.out.println("Created " + count.get() + " data.");
    }
}