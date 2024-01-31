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

       // //Deals

// //       ArrayList<DealAllListPojo> dealAllListPojosFetch = dealsFetcher.getAllDeals();
//
//        ArrayList<DealAllListPojo> dealAllListPojosFromTheFile = dealsFetcher.readAllDealsFromFile();
//
//        ArrayList<Deal> deals = new ArrayList<>();
//
//        for (DealAllListPojo dealAllListPojo : dealAllListPojosFromTheFile) {
//            ArrayList<DealAllPojo> dealAllPojos = dealAllListPojo.getDeals();
//            for (DealAllPojo dealPojo : dealAllPojos) {
//                Deal deal = new Deal(dealPojo.getStoreID(), dealPojo.getDealID(), dealPojo.getPrice(),
//                        dealPojo.getRetailPrice(), dealPojo.getSavings());
//                deals.add(deal);
//            }
//        }
//
//        deals.forEach(System.out::println);

//        //Game

//        String keyword = "call of duty";
//        ArrayList<GamePojo> gamePojos = gamesFetcher.getGameContainingKeyword(keyword);
//        ArrayList<Game> games = new ArrayList<>();
//
//        for (GamePojo gamePojo : gamePojos) {
//            Game game = new Game(gamePojo.getTitle(), gamePojo.getSteamID(), gamePojo.getCheapestPrice(),
//                    gamePojo.getGameID());
//            games.add(game);
//        }
//
//        games.forEach(System.out::println);

        //Store

//   //     storesFetcher.getAllShops();

//        ArrayList<StoreAllPojo> storeAllPojos = storesFetcher.readAllShopsFromFile();
//
//        ArrayList<Store> stores = new ArrayList<>();
//
//        for (StoreAllPojo storeAllPojo : storeAllPojos) {
//            Store store = new Store(storeAllPojo.getId(), storeAllPojo.getName(), storeAllPojo.isActive());
//            stores.add(store);
//        }
//
//        stores.forEach(System.out::println);

        // //GameLookup

//        GameLookup gameLookup = new GameLookup();
//        String gameTitleForLookup = "lego batman";
//        gameLookup.giveTitleToGetListOFDealsWithStores(gameTitleForLookup);

    }

}
