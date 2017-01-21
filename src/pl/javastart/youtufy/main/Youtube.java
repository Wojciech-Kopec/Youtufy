package pl.javastart.youtufy.main;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pl.javastart.youtufy.data.YoutubeVideo;

public class Youtube {
    private final String YOUTUBE_SEARCH_URL = "http://www.youtube.com/results";
    private final String SEARCH_PARAMETER = "search_query";
    private final String VIDEO_NODES = "div.yt-lockup-content";
    private final String VIDEO_TITLE_URL = "a.yt-uix-tile-link.yt-ui-ellipsis.yt-ui-ellipsis-2.yt-uix-sessionlink.spf-link ";
    private final String VIDEO_AUTHOR = "a.yt-uix-sessionlink.spf-link.g-hovercard";

    private ObservableList<YoutubeVideo> youtubeVideos;
    private StringProperty searchQuery;

    public ObservableList<YoutubeVideo> getYoutubeVideos() {
        return youtubeVideos;
    }

    public StringProperty getSearchQuery() {
        return searchQuery;
    }

    public Youtube() {
        youtubeVideos = FXCollections.observableArrayList();
        searchQuery = new SimpleStringProperty();
    }

    public void search() throws IOException {
        search(searchQuery.get());
    }

    private void search(String query) throws IOException {

        String pageContent = getPageSource(query);
        Document doc = Jsoup.parse(pageContent);
        Elements videosNodes = doc.select(VIDEO_NODES);

        List<YoutubeVideo> videos = new ArrayList<>();
        System.out.println("Elementów: " + videosNodes.size());
        for (Element e : videosNodes) {
            Element titleUrlElement = e.select(VIDEO_TITLE_URL).first();
            Element authorElement = e.select(VIDEO_AUTHOR).first();

            String url = titleUrlElement.attr("href").replace("/watch?v=", "");
            String title = titleUrlElement.text();
            String author = authorElement.text();
            if (url.contains("list") || url.contains("channel") || url.contains("user")) {
                System.out.println("Pominięto materiał");
            } else {
                YoutubeVideo yv = new YoutubeVideo();
                yv.setId(url);
                yv.setTitle(title);
                yv.setAuthor(author);
                videos.add(yv);
                System.out.println("Dodano materiał");
            }
        }
        youtubeVideos.clear();
        youtubeVideos.addAll(videos);
        System.out.println("Dodano do youtubeVideos");
    }

    private String getPageSource(String query) throws IOException {
        URI searchUri = null;
        try {
            searchUri = new URIBuilder(YOUTUBE_SEARCH_URL).addParameter(SEARCH_PARAMETER, query).build();
        } catch (URISyntaxException e) {
            System.err.println("Błąd przy budowaniu adresu URL");
        }

        // tworzymy obiekty do wysłania żądania i odebrania odpowiedzi od serwera
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(searchUri);
        CloseableHttpResponse response = null;
        HttpEntity httpEntity;
        String pageContent = null;

        // pobieramy treść strony WWW
        try {
            response = httpClient.execute(request);
            httpEntity = response.getEntity();
            pageContent = EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            response.close();
            httpClient.close();
        }
        return pageContent;
    }
}