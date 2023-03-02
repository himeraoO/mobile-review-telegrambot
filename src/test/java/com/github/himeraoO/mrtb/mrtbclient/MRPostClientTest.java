package com.github.himeraoO.mrtb.mrtbclient;

import com.github.himeraoO.mrtb.mrtbclient.dto.PostInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("Integration-level testing for MRPostClient")
class MRPostClientTest {

    public static final String MR_PATH = "https://mobile-review.com/";

    private final MRPostClient postClient = new MRPostClientImpl(new MRGroupClientImpl(MR_PATH));

    @Test
    public void shouldProperlyGetNew32Posts() {
        //when
        List<PostInfo> newPosts = postClient.findNewPosts(1, 0);

        //then
        Assertions.assertEquals(32, newPosts.size());

        //when
        List<PostInfo> articlePosts = postClient.findNewPosts(2, 0);

        //then
        Assertions.assertEquals(32, articlePosts.size());

        //when
        List<PostInfo> reviewPosts = postClient.findNewPosts(3, 0);

        //then
        Assertions.assertEquals(32, reviewPosts.size());
    }
}