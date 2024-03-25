package dev.Roach;

import dev.Roach.fetchers.GamesFetcher;
import dev.Roach.fetchers.StoresFetcher;
import dev.Roach.services.AlertService;
import dev.Roach.services.GameLookUpService;
import dev.Roach.ui.Menu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Menu menu = new Menu(new Scanner(System.in), new StoresFetcher(), new GameLookUpService(),
                new GamesFetcher(), new AlertService());
        menu.startTheProgram();

    }
}
