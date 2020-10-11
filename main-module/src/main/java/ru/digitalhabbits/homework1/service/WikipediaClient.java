package ru.digitalhabbits.homework1.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WikipediaClient {
    public static final String WIKIPEDIA_SEARCH_URL = "https://en.wikipedia.org/w/api.php";

    @Nonnull
    public String search(@Nonnull String searchString) {
        final URI uri = prepareSearchUrl(searchString);
        String result = "";

        try (final CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final Gson gson = new Gson();
            final HttpGet request = new HttpGet(uri);


            final CloseableHttpResponse response = httpClient.execute(request);
            final String entity = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            final JsonObject json = gson.fromJson(entity, JsonObject.class)
                    .getAsJsonObject("query")
                    .getAsJsonObject("pages");
            final List<String> keySet = new ArrayList<>(json.keySet());
            //System.out.println(json.getAsJsonObject(keySet.get(0))
//                    .getAsJsonPrimitive("extract")
//                    .getAsString());
            return json.getAsJsonObject(keySet.get(0))
                    .getAsJsonPrimitive("extract")
                    .getAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Nonnull
    private URI prepareSearchUrl(@Nonnull String searchString) {
        try {
            return new URIBuilder(WIKIPEDIA_SEARCH_URL)
                    .addParameter("action", "query")
                    .addParameter("format", "json")
                    .addParameter("titles", searchString)
                    .addParameter("prop", "extracts")
                    .addParameter("explaintext", "")
                    .build();
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
    }
}
