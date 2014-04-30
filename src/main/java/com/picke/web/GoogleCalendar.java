package com.picke.web;


import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Scanner;

@Service
public class GoogleCalendar {

    public static final String REDIRECT_URL = "http://localhost:8080/sign_in";
    public static final String CLIENT_ID = "656041725911-dru2sqb7qi82ggr4cm89rl5d03rk6mqs.apps.googleusercontent.com";
    public static final String CLIENT_SECRET = "boZEpWsGom14LwRus2zc9JgP";

    public static String CODE = null;

    public static String getAutorizationUrl() throws IOException {

        HttpTransport httpTransport = new NetHttpTransport();
        JacksonFactory jsonFactory = new JacksonFactory();

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET,
                Arrays.asList(CalendarScopes.CALENDAR))
                .setAccessType("online")
                .setApprovalPrompt("auto")
                .build();

        String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URL).build();

        System.out.println(url);

        return url;
    }

    public static Calendar getCalendarService(String code) throws IOException, GeneralSecurityException {

        HttpTransport httpTransport = new NetHttpTransport();
        JacksonFactory jsonFactory = new JacksonFactory();

        Calendar service = new Calendar(httpTransport, jsonFactory, setupCalendar(code));

        return service;
    }

    private static GoogleCredential setupCalendar(String code) throws IOException {

        HttpTransport httpTransport = new NetHttpTransport();
        JacksonFactory jsonFactory = new JacksonFactory();

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET,
                Arrays.asList(CalendarScopes.CALENDAR))
//                .setAccessType("online")
//                .setApprovalPrompt("auto")
                .build();

        GoogleTokenResponse response = flow.newTokenRequest(code)
                .setRedirectUri(REDIRECT_URL).execute();
        GoogleCredential credential = new GoogleCredential()
                .setFromTokenResponse(response);
        return credential;
    }

    public static void main(String args[]) throws IOException, GeneralSecurityException {
        String url = getAutorizationUrl();
        CODE = new Scanner(System.in).nextLine();
        Calendar calendar = getCalendarService(CODE);
//        GoogleCredential googleCredential = setupCalendar(CODE);
        Calendar.CalendarList.List listRequest = calendar.calendarList().list();
        CalendarList feed = listRequest.execute();
        for(CalendarListEntry entry:feed.getItems()){
            System.out.println("ID: " + entry.getId());
            System.out.println("Summary: " + entry.getSummary());
        }
        calendar.calendarList().insert(new CalendarListEntry());
    }
}
