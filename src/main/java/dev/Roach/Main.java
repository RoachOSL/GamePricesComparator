package dev.Roach;

import dev.Roach.fetcher.DealsFetcher;
import dev.Roach.fetcher.GamesFetcher;
import dev.Roach.fetcher.StoresFetcher;

import java.net.http.HttpClient;

public class Main {
    public static void main(String[] args) {

        var client = HttpClient.newBuilder().build();

        DealsFetcher dealsFetcher = new DealsFetcher(client);

        GamesFetcher gamesFetcher = new GamesFetcher(client);

        StoresFetcher storesFetcher = new StoresFetcher(client);

//        System.out.println(dealsFetcher.getAllDeals());
//        System.out.println(dealsFetcher.getDealUsingID("X8sebHhbc1Ga0dTkgg59WgyM506af9oNZZJLU9uSrX8%3D"));
//
//        System.out.println(gamesFetcher.getGameContainingKeyword(null));
//        System.out.println(gamesFetcher.getGameUsingID(243801));

//        System.out.println(storesFetcher.getAllShops());

        JSONMapper jsonMapper = new JSONMapper();

        System.out.println(dealsFetcher.getDealUsingID("X8sebHhbc1Ga0dTkgg59WgyM506af9oNZZJLU9uSrX8%3D"));

//        DealByIDPojo dealByIDPojo = jsonMapper.mapToJava(dealsFetcher.getDealUsingID("X8sebHhbc1Ga0dTkgg59WgyM506af9oNZZJLU9uSrX8%3D"), DealByIDPojo.class);
//
//        System.out.println(dealByIDPojo);
//

//        DealAllList dealAllList = new DealAllList(jsonMapper.mapArrayToJava(dealsFetcher.readAllDealsFromFile(), DealAllPojo.class));

//        Deal deal = Deal.createProperDealObject(dealByIDPojo, dealAllList);
//
//        System.out.println(deal);


    }
}
