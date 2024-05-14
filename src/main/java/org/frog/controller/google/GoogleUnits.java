package org.frog.controller.google;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.frog.model.GoogleUser;

public class GoogleUnits{

    public static String getToken(final String code) throws ClientProtocolException, IOException {
        String response = Request.Post(Container.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form().add("client_id", Container.GOOGLE_CLIENT_ID)
                        .add("client_secret", Container.GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", Container.GOOGLE_REDIRECT_URI).add("code", code)
                        .add("grant_type", Container.GOOGLE_GRANT_TYPE).build())
                .execute().returnContent().toString();
        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        return jobj.get("access_token").toString().replaceAll("\"", "");
    }

    public static GoogleUser getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = Container.GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        GoogleUser googleUser = new Gson().fromJson(response, GoogleUser.class);
        System.out.println(googleUser);
        return googleUser;
    }
}
