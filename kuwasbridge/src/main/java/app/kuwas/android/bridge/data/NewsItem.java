/*
 * Copyright (C) 2019 Brook Mezgebu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.kuwas.android.bridge.data;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by BrookMG on 4/24/2019 in io.brookmg.soccerethiopiaapi.data
 * inside the project SoccerEthiopia .
 */
public class NewsItem implements Serializable {

    private int newsId;
    private String newsImage;
    private String newsTitle;
    private String newsAuthorLink;
    private String newsAuthorName;
    private ArrayList<String> newsTags;
    private Date newsPublishedOn;
    private String newsContent;

    public NewsItem(int newsId, String newsImage, String newsTitle, String newsAuthorLink, String newsAuthorName, ArrayList<String> newsTags, Date newsPublishedOn) {
        this(newsId , newsImage, newsTitle, newsAuthorLink, newsAuthorName, newsTags, newsPublishedOn, null);
    }

    public NewsItem(int newsId, String newsImage, String newsTitle, String newsAuthorLink, String newsAuthorName, ArrayList<String> newsTags, Date newsPublishedOn, String newsContent) {
        this.newsId = newsId;
        this.newsImage = newsImage;
        this.newsTitle = newsTitle;
        this.newsAuthorLink = newsAuthorLink;
        this.newsAuthorName = newsAuthorName;
        this.newsTags = newsTags;
        this.newsPublishedOn = newsPublishedOn;
        this.newsContent = newsContent;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsAuthorLink() {
        return newsAuthorLink;
    }

    public void setNewsAuthorLink(String newsAuthorLink) {
        this.newsAuthorLink = newsAuthorLink;
    }

    public ArrayList<String> getNewsTags() {
        return newsTags;
    }

    public void setNewsTags(ArrayList<String> newsTags) {
        this.newsTags = newsTags;
    }

    public Date getNewsPublishedOn() {
        return newsPublishedOn;
    }

    public void setNewsPublishedOn(Date newsPublishedOn) {
        this.newsPublishedOn = newsPublishedOn;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getNewsAuthorName() {
        return newsAuthorName;
    }

    public void setNewsAuthorName(String newsAuthorName) {
        this.newsAuthorName = newsAuthorName;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }

    @NonNull
    @Override
    public String toString() {
        return "[ID: " + newsId + ", Image: " + newsImage + ", Title: " + newsTitle +
                ", Author: " + newsAuthorName + ", Published: " +
                newsPublishedOn + ", Content: " + newsContent +
                ", Tags: " + Arrays.toString(newsTags.toArray()) + "]";
    }
}
