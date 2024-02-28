package dev.Roach;

import dev.Roach.fetchers.GamesFetcher;
import dev.Roach.fetchers.StoresFetcher;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        StoresFetcher storesFetcher = new StoresFetcher();
        GameLookup gameLookup = new GameLookup();
        GamesFetcher gamesFetcher = new GamesFetcher();
        AlertService alertService = new AlertService();

        Menu menu = new Menu(scanner, storesFetcher, gameLookup, gamesFetcher, alertService);
        menu.startTheProgram();





    }

}
