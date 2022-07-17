package com.github.himeraoO.mrtb.mrtbclient;

import com.github.himeraoO.mrtb.mrtbclient.dto.GroupInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link MRGroupClient} interface.
 */
@Component
public class MRGroupClientImpl implements MRGroupClient{

    private final List<GroupInfo> groupInfos = new ArrayList<>();

    public MRGroupClientImpl(@Value("${mrtb.url}") String url) {
        String urlForNews = url + "all/news/";
        String urlForArticle = url + "all/articles/";
        String urlForReview = url + "all/reviews/";
        init(urlForNews, urlForArticle, urlForReview);
    }

    private void init(String urlForNews, String urlForArticle, String urlForReview) {
        GroupInfo news = new GroupInfo();
        news.setId(1);
        news.setTitle("News");
        news.setUrl(urlForNews);
        GroupInfo article = new GroupInfo();
        article.setId(2);
        article.setTitle("Article");
        article.setUrl(urlForArticle);
        GroupInfo review = new GroupInfo();
        review.setId(3);
        review.setTitle("Review");
        review.setUrl(urlForReview);
        groupInfos.add(news);
        groupInfos.add(article);
        groupInfos.add(review);
    }

    @Override
    public List<GroupInfo> getGroupList() {
        return groupInfos;
    }

    @Override
    public Integer getGroupCount() {
        return groupInfos.size();
    }

    @Override
    public GroupInfo getGroupById(Integer id) {
        return groupInfos.stream().filter(groupInfo1 ->
            groupInfo1.getId().equals(id)).findFirst().orElseThrow(NotFoundException::new);
    }

}
