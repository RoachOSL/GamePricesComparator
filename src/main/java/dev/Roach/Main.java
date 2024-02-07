package dev.Roach;

import dev.Roach.fetchers.DealsFetcher;
import dev.Roach.fetchers.GamesFetcher;
import dev.Roach.fetchers.StoresFetcher;

public class Main {
    public static void main(String[] args) {

        DealsFetcher dealsFetcher = new DealsFetcher();

        GamesFetcher gamesFetcher = new GamesFetcher();

        StoresFetcher storesFetcher = new StoresFetcher();

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
//
//        storesFetcher.getAllShops();
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
//
//        GameLookup gameLookup = new GameLookup();
////        String gameTitleForLookup = "legobatman";
////        System.out.println(gameLookup.giveTitleToGetListOFDealsWithStores(gameTitleForLookup));
//
//        String wrongGameTitleForLookup = "wrong";
//        System.out.println(gameLookup.giveTitleToGetListOFDealsWithStores(wrongGameTitleForLookup));


        //Alerts

        AlertService alertService = new AlertService();

//        System.out.println(alertService.createOrUpdateAlert("os1996@o2.pl",288, 18.00));
//
//        System.out.println(alertService.createOrUpdateAlert("os1996@o2.pl",61222222, 115.00));
//
//        System.out.println(alertService.deleteAlert("os1996@o2.pl",288));
//
//        System.out.println(alertService.getAlertsForEmail("os1996@o2.pl"));
//
//        System.out.println(alertService.createOrUpdateAlertWithGameTitle(null,"os1996@o2.pl", 5.00));

    }

}
