package dev.Roach;

import dev.Roach.fetchers.GamesFetcher;
import dev.Roach.fetchers.StoresFetcher;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu(new Scanner(System.in), new StoresFetcher(), new GameLookup(),
                new GamesFetcher(), new AlertService());
        menu.startTheProgram();
    }
}
