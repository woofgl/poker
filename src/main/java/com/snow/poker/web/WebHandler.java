package com.snow.poker.web;


import com.britesnow.snow.web.RequestContext;
import com.britesnow.snow.web.handler.annotation.WebModelHandler;
import com.britesnow.snow.web.param.annotation.WebParam;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.json.JsonObject;
import com.restfb.types.User;

import java.util.ArrayList;
import java.util.List;

public class WebHandler {


    /**
     * connect demo
     *
     * @param token
     * @param limit
     * @param offset
     * @param rc
     */
    @WebModelHandler(startsWith = "/friends")
    public void getFriends(@WebParam("token") String token, @WebParam("limit") Integer limit,
                           @WebParam("offset") Integer offset, RequestContext rc) {
        if (limit == null || limit <= 0) {
            limit = 10;
        }
        if (offset == null || offset < 0) offset = 0;
        FacebookClient facebookClient = new DefaultFacebookClient(token);
        //fetch friend from facebook, limit is 10
        Connection<User> myFriends = facebookClient.fetchConnection("me/friends", User.class,
                Parameter.with("limit", limit), Parameter.with("offset", offset));
        List<User> users = new ArrayList<User>();

        for (User myFriend : myFriends.getData()) {
            users.add(myFriend);
        }
        rc.getWebModel().put("_jsonData", users);
    }

    /**
     * search demo
     *
     * @param token
     * @param q
     * @param type
     * @param rc
     */
    @WebModelHandler(startsWith = "/search")
    public void search(@WebParam("token") String token, @WebParam("q") String q,
                       @WebParam("type") String type, RequestContext rc) {
        FacebookClient facebookClient = new DefaultFacebookClient(token);
        //fetch friend from facebook, limit is 10
        Connection<JsonObject> searchs = facebookClient.fetchConnection("search", JsonObject.class,
                Parameter.with("q", q), Parameter.with("type", type));
        List<String> results = new ArrayList<String>();

        for (JsonObject jsonObj : searchs.getData()) {
            results.add(jsonObj.toString());
        }
        rc.getWebModel().put("_jsonData", results);
    }
}
