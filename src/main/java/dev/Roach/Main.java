package dev.Roach;

import com.fasterxml.jackson.core.type.TypeReference;
import dev.Roach.fetchers.DealsFetcher;
import dev.Roach.fetchers.GamesFetcher;
import dev.Roach.fetchers.StoresFetcher;
import dev.Roach.datamodel.deal.Deal;
import dev.Roach.datamodel.deal.DealAllListPojo;
import dev.Roach.datamodel.deal.DealAllPojo;
import dev.Roach.datamodel.deal.DealByIDPojo;
import dev.Roach.datamodel.game.Game;
import dev.Roach.datamodel.game.GameByID;
import dev.Roach.datamodel.game.GamePojo;
import dev.Roach.datamodel.store.Store;
import dev.Roach.datamodel.store.StoreAllPojo;

import java.net.http.HttpClient;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        var client = HttpClient.newBuilder().build();

        DealsFetcher dealsFetcher = new DealsFetcher(client);

        GamesFetcher gamesFetcher = new GamesFetcher(client);

        StoresFetcher storesFetcher = new StoresFetcher(client);

        JSONMapper jsonMapper = new JSONMapper();

        //Downloading data

        dealsFetcher.getAllDeals();
        storesFetcher.getAllShops();

        String dealID = "0f%2B4gT2VVUn4UcmFzPxXnuqoXKAOYoJ5mpFZRWNyohc%3D";
        dealsFetcher.getDealUsingID(dealID);

        int gameID = 612;
        gamesFetcher.getGameUsingID(gameID);

        String keyword = "batman";
        gamesFetcher.getGameContainingKeyword(keyword);

        //Creating a Deal object with provided values

        DealByIDPojo dealByIDPojo = jsonMapper.mapToJava(dealsFetcher.readDealUsingIDFromFile(), DealByIDPojo.class);
        String allDealsJson = dealsFetcher.readAllDealsFromFile();

        TypeReference<List<List<DealAllPojo>>> typeRef = new TypeReference<List<List<DealAllPojo>>>() {};
        List<List<DealAllPojo>> dealAllList = jsonMapper.mapDoubleArrayToJava(allDealsJson, typeRef);

        DealAllListPojo dealAllListPojoObj = new DealAllListPojo();
        dealAllListPojoObj.setDeals(dealAllList);

        Deal deal = Deal.createProperDealObject(dealByIDPojo, dealAllListPojoObj);
        System.out.println(deal);

        //Creating a Store object with provided values

        List<StoreAllPojo> storeAllPojo = jsonMapper.mapArrayToJava(storesFetcher.readAllShopsFromFile(), StoreAllPojo[].class);
        Store store = Store.createProperStoreObjectByStoreID(storeAllPojo, "1");
        System.out.println(store);

        //Creating a Game object with provided values

        List<GamePojo> gamePojos = jsonMapper.mapArrayToJava(gamesFetcher.readGameContainingKeywordFromFile(), GamePojo[].class);
        Game gameKeyword = Game.createProperGameObjectByContaingKeyword(gamePojos, "612");
        System.out.println(gameKeyword);

        //Returns list of deals by game ID.

        GameByID gamePojoByID = jsonMapper.mapToJava(gamesFetcher.readGameUsingIDFromFile(), GameByID.class);
        System.out.println(gamePojoByID);

    }
}
