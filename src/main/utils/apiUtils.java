package main.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import main.deserialisationobjects.getContestResponse;
import main.restassured.BaseApiClass;
import main.serialisationobjects.bearerToken;
import main.serialisationobjects.GenerateOTP;
import main.serialisationobjects.innings;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class apiUtils extends BaseApiClass {

    public void generateOtp(String phoneNumber) throws MalformedURLException {
        GenerateOTP otp = new GenerateOTP();
        otp.setPhoneNumber(phoneNumber);
        Map<String,String> headers = new HashMap<String,String>();
        headers.put("X-DREAMAUTH-AUTHORIZATION","hP9ecqPd5g_KCLhNglbrGIB1VMWZcYxQrhSyqtiZX6c");
        headers.put("Content-Type","application/json");
        headers.put("x-auth-connection","sms");
        post(headers,"https://guardian-nv.d11platform.com/auth/passwordless/init",otp);
    }

    public String generateBearerToken(String phoneNumber) throws MalformedURLException {
        bearerToken token = new bearerToken();
        token.setUsername(phoneNumber);
        Map<String,String> headers = new HashMap<String,String>();
        headers.put("X-DREAMAUTH-AUTHORIZATION","hP9ecqPd5g_KCLhNglbrGIB1VMWZcYxQrhSyqtiZX6c");
        headers.put("Content-Type","application/json");
        headers.put("x-auth-connection","sms");
        Response response = post(headers,"https://guardian-nv.d11platform.com/auth/passwordless/complete",token);
        return response.body().jsonPath().getString("accessToken");
    }

    public void getContest(String bearer) throws MalformedURLException {
        Response response = get(Map.of("Authorization","Bearer "+bearer),"https://api-cric.staging.rario.com/goofy/games/contests?status=open");
        List<getContestResponse> contestData = response.body().jsonPath().getList("data.contests", getContestResponse.class);
        System.out.println(contestData.get(0).getMatch().getName());
    }

    public void submitInnings(int matchId,String bearer) throws MalformedURLException {
        List<innings.contestInningTeam> inningTeams = new ArrayList<>();
        innings innings = new innings();
        main.serialisationobjects.innings.contestInningTeam inningsObj = new innings.contestInningTeam(10.0,20.0,30.0,40.0,5.0);
        inningTeams.add(inningsObj);
        innings.setContestInningTeam(inningTeams);

        Response response = post(Map.of("Authorization","Bearer "+bearer)
                ,"https://api-cric.staging.rario.com/goofy/games/contests/"+matchId+"/innings/team"
                ,innings);
        List<getContestResponse> contestData = response.body().jsonPath().getList("data.contests", getContestResponse.class);
        System.out.println(contestData.get(0).getMatch().getName());
    }

    public HashMap<String, String> fetchWalletBalance(String phoneNUmber, String bearer){
        HashMap<String,String> map = new HashMap<>();
        Map<String,String> headers = new HashMap<String,String>();
        headers.put("authorization","Bearer "+bearer);
        headers.put("Content-Type","application/json");
        Response res = RestAssured.given()
                .headers(headers)
                .baseUri("https://api-cric.staging.rario.com/wallet/balance/v2")
                .log().all()
                .get();
        map.put("userId",res.body().jsonPath().getString("data.bling.userId"));
        map.put("blingBalance",res.body().jsonPath().getString("data.bling.availableBalance"));
        map.put("boltBalance",res.body().jsonPath().getString("data.bolt.availableBalance"));
        map.put("phoneNumber",phoneNUmber);
        map.put("bearerToken",bearer);
        return map;
    }

}
