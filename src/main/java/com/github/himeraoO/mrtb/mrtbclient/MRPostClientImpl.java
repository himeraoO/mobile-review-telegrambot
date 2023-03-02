package com.github.himeraoO.mrtb.mrtbclient;

import com.github.himeraoO.mrtb.mrtbclient.dto.PostInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link MRPostClient} interface.
 */
@Component
public class MRPostClientImpl implements MRPostClient {

    private final MRGroupClient mrGroupClient;

    @Autowired
    public MRPostClientImpl(MRGroupClient mrGroupClient) {
        this.mrGroupClient = mrGroupClient;
    }

    @Override
    public List<PostInfo> findNewPosts(Integer groupId, Integer lastPostId) {
        List<PostInfo> listPosts = findPosts(groupId);
        List<PostInfo> newPosts = new ArrayList<>();

        for (PostInfo post : listPosts) {
            if (lastPostId.equals(post.getId())) {
                return newPosts;
            }
            newPosts.add(post);
        }
        return newPosts;
    }

    private List<PostInfo> findPosts(Integer groupId) {
        Document doc = null;
        try {
            doc = Jsoup.connect(mrGroupClient.getGroupById(groupId).getUrl())
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();
        } catch (IOException e) {
            //TODO add exception handling
            e.printStackTrace();
        }

        List<PostInfo> listNews = new ArrayList<>();
        if (doc != null) {

            Elements newsElements = doc.getElementsByClass("article-name");
            for (Element e : newsElements) {
                PostInfo postInfo = new PostInfo();

                postInfo.setTitle(e.child(0).text());
                postInfo.setDescription(e.child(1).text());

                String link = e.attr("href");
                postInfo.setLink(link);

                String id = "";
                Document refDoc = null;
                try {
                    refDoc = Jsoup.connect(link)
                            .userAgent("Chrome/4.0.249.0 Safari/532.5")
                            .referrer("http://www.google.com")
                            .get();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (refDoc != null) {
                    Elements subElements = refDoc.select("body");
                    for (Element subElement : subElements) {
                        id = subElement.className().split(" ")[3].split("-")[1];
                    }

                    postInfo.setId(Integer.valueOf(id));
                }

                listNews.add(postInfo);
            }
        }
        return listNews;
    }


}
