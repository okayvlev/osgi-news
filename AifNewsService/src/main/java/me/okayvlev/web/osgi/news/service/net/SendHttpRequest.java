package me.okayvlev.web.osgi.news.service.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class SendHttpRequest {
    private final URL url;
    private int timeout = 5000;

    public SendHttpRequest(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public SendHttpRequest withTimeout(int timeout, TimeUnit unit) {
        this.timeout = (int) unit.toMillis(timeout);
        return this;
    }

    private String readResponse(InputStream is) throws IOException {
        String response;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            StringBuilder content = new StringBuilder();
            while ((response = in.readLine()) != null) {
                content.append(response);
            }
            response = content.toString();
        }
        return response;
    }

    public String execute() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);

        try {
            connection.connect();
            int code = connection.getResponseCode();
            if (code / 100 > 2) {
                throw new IOException(code + " " + connection.getResponseMessage());
            } else {
                return readResponse(connection.getInputStream());
            }
        } finally {
            connection.disconnect();
        }
    }
}
