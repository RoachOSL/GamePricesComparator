package dev.Roach;

import dev.Roach.fetchers.DealsFetcher;
import dev.Roach.fetchers.GamesFetcher;
import dev.Roach.fetchers.StoresFetcher;

import java.net.http.HttpClient;

public class Main {
    public static void main(String[] args) {

        var client = HttpClient.newBuilder().build();

        DealsFetcher dealsFetcher = new DealsFetcher(client);

        GamesFetcher gamesFetcher = new GamesFetcher(client);

        StoresFetcher storesFetcher = new StoresFetcher(client);

        JSONMapper jsonMapper = new JSONMapper();

        //Downloading data

//        dealsFetcher.getAllDeals();
//        storesFetcher.getAllShops();
//
        String dealID = "0f%2B4gT2VVUn4UcmFzPxXnuqoXKAOYoJ5mpFZRWNyohc%3D";
//        dealsFetcher.getDealUsingID(dealID);
        System.out.println(dealsFetcher.getDealUsingID(dealID));
//
//        int gameID = 612;
//        gamesFetcher.getGameUsingID(gameID);
//
//        String keyword = "batman";
//        gamesFetcher.getGameContainingKeyword(keyword);

        //Deals

//        ArrayList<DealAllListPojo> dealAllListPojos = dealsFetcher.getAllDeals();
//
//        ArrayList<DealAllListPojo> dealAllListPojosFromTheFile = dealsFetcher.readAllDealsFromFile();
//
//        System.out.println(dealAllListPojosFromTheFile);

    }
}
