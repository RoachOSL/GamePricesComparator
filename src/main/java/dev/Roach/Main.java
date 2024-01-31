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

        //CheckForLimit
//
//        System.out.println(gamesFetcher.getGameUsingID(612));

        //Deals

//   //     List<DealAllListPojo> dealAllListPojosFetch = dealsFetcher.getAllDeals();

//        List<DealAllListPojo> dealAllListPojosFromTheFile = dealsFetcher.readAllDealsFromFile();
//
//        ArrayList<Deal> deals = new ArrayList<>();
//
//        for (DealAllListPojo dealAllListPojo : dealAllListPojosFromTheFile) {
//            List<DealAllPojo> dealAllPojos = dealAllListPojo.getDeals();
//            for (DealAllPojo dealPojo : dealAllPojos) {
//                Deal deal = new Deal(dealPojo.getStoreID(), dealPojo.getDealID(), dealPojo.getPrice(),
//                        dealPojo.getRetailPrice(), dealPojo.getSavings());
//                deals.add(deal);
//            }
//        }
//
//        deals.forEach(System.out::println);

//        Game
//
//        String keyword = "diablo 4";
//        List<GamePojo> gamePojos = gamesFetcher.getGameContainingKeyword(keyword);
//        ArrayList<Game> games = new ArrayList<>();
//
//        for (GamePojo gamePojo : gamePojos) {
//            Game game = new Game(gamePojo.getTitle(), gamePojo.getSteamID(), gamePojo.getCheapestPrice(),
//                    gamePojo.getGameID());
//            games.add(game);
//        }
//
//        games.forEach(System.out::println);

//        Stores

//        storesFetcher.getAllShops();
//
//        List<StoreAllPojo> storeAllPojos = storesFetcher.readAllShopsFromFile();
//
//        ArrayList<Store> stores = new ArrayList<>();
//
//        for (StoreAllPojo storeAllPojo : storeAllPojos) {
//            Store store = new Store(storeAllPojo.getId(), storeAllPojo.getName(), storeAllPojo.isActive());
//            stores.add(store);
//        }
//
//        stores.forEach(System.out::println);

//        //GameLookup

        GameLookup gameLookup = new GameLookup();
//        String gameTitleWrong = "gagaga";
//        gameLookup.giveTitleToGetListOFDealsWithStores(gameTitleWrong);

//        Proper title:
        String gameTitleProper = "legobatman";
        System.out.println(gameLookup.giveTitleToGetListOFDealsWithStores(gameTitleProper));

        System.out.println("test");

    }

}
