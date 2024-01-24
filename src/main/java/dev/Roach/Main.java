package dev.Roach;

import com.fasterxml.jackson.core.type.TypeReference;
import dev.Roach.fetcher.DealsFetcher;
import dev.Roach.fetcher.GamesFetcher;
import dev.Roach.fetcher.StoresFetcher;
import dev.Roach.pojo.deal.Deal;
import dev.Roach.pojo.deal.DealAllList;
import dev.Roach.pojo.deal.DealAllPojo;
import dev.Roach.pojo.deal.DealByIDPojo;

import java.net.http.HttpClient;
import java.util.List;

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

//        System.out.println(dealsFetcher.getDealUsingID("X8sebHhbc1Ga0dTkgg59WgyM506af9oNZZJLU9uSrX8%3D"));

//        dealsFetcher.getAllDeals();


        DealByIDPojo dealByIDPojo = jsonMapper.mapToJava(dealsFetcher.readDealUsingIDFromFile(), DealByIDPojo.class);
        String allDealsJson = dealsFetcher.readAllDealsFromFile();

        TypeReference<List<List<DealAllPojo>>> typeRef = new TypeReference<List<List<DealAllPojo>>>() {};
        List<List<DealAllPojo>> dealAllList = jsonMapper.mapDoubleArrayToJava(allDealsJson, typeRef);

        DealAllList dealAllListObj = new DealAllList();
        dealAllListObj.setDeals(dealAllList);

        Deal deal = Deal.createProperDealObject(dealByIDPojo, dealAllListObj);
        System.out.println(deal);

    }
}
