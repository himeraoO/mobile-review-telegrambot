package com.github.himeraoO.mrtb.mrtbclient;


import com.github.himeraoO.mrtb.mrtbclient.dto.PostInfo;

import java.util.List;

/**
 * Client for mobile-review.com for posts.
 */
public interface MRPostClient {

    /**
     * Find new posts since lastArticleId in provided group.
     *
     * @param groupId provided group ID.
     * @param lastArticleId provided last post ID.
     * @return the collection of the new {@link PostInfo}.
     */
    List<PostInfo> findNewPosts(Integer groupId, Integer lastArticleId);
}