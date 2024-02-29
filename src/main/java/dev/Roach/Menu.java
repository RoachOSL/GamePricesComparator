package dev.Roach;

import dev.Roach.datamodel.game.Game;
import dev.Roach.datamodel.game.GamePojo;
import dev.Roach.datamodel.gameLookup.GameDealResponse;
import dev.Roach.datamodel.store.StoreAllPojo;
import dev.Roach.fetchers.GamesFetcher;
import dev.Roach.fetchers.StoresFetcher;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner;
    private final StoresFetcher storesFetcher;
    private final GameLookup gameLookup;
    private final GamesFetcher gamesFetcher;
    private final AlertService alertService;
    private boolean isProgramRunning = true;

    public Menu(Scanner scanner, StoresFetcher storesFetcher, GameLookup gameLookup, GamesFetcher gamesFetcher, AlertService alertService) {
        this.scanner = scanner;
        this.storesFetcher = storesFetcher;
        this.gameLookup = gameLookup;
        this.gamesFetcher = gamesFetcher;
        this.alertService = alertService;
    }

    public void startTheProgram() {
        while (isProgramRunning) {

            validateStoreList();
            displayMenu();

            try {
                int option = selectOption();
                handleMenuOption(option);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    private void validateStoreList() {
        List<StoreAllPojo> currentShops = storesFetcher.readAllShopsFromFile();
        if (currentShops.isEmpty()) {
            storesFetcher.getAllShops();
        }
    }

    private String getUserInput() {
        return scanner.nextLine().strip();
    }

    private void displayMenu() {
        System.out.println("""
                Welcome to the Deal Finder!
                Menu (please select an option by entering the corresponding number):
                1. Search for deals using an exact game title.
                2. Search for deals using a keyword (limit to 25 results).
                3. Set up a price alert for a game.
                4. Manage your alerts via email link.
                5. Exit the program.
                """);
    }

    private void handleMenuOption(int option) {
        switch (option) {
            case 1 -> searchForDealsByTitle(gameLookup);
            case 2 -> searchForDealsByKeyword(gamesFetcher);
            case 3 -> setUpPriceAlert(alertService);
            case 4 -> manageAlertsByEmail(alertService);
            case 5 -> {
                isProgramRunning = false;
                System.out.println("Exiting the program. Goodbye!");
            }
            default -> System.out.println("Invalid selection, please try again.");
        }
    }

    private int selectOption() {
        int choice = 0;

        try {
            System.out.println("Please select an option:");
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }

        return choice;
    }

    private void searchForDealsByTitle(GameLookup gameLookup) {
        System.out.println("Enter the exact title of the game to find deals:");
        String title = getUserInput();

        try {
            System.out.println(gameLookup.giveTitleToGetListOFDealsWithStores(title));
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again with a different title.");
        }

        promptToReturn();
    }

    private void searchForDealsByKeyword(GamesFetcher gamesFetcher) {
        System.out.println("Enter a keyword to search for game deals:");
        String keyword = getUserInput();

        List<GamePojo> gamePojos = gamesFetcher.getGameContainingKeyword(keyword);
        gamePojos.stream().map(pojo -> new Game(pojo.getTitle(), pojo.getSteamID(), pojo.getCheapestPrice(), pojo.getGameID())).limit(25).forEach(System.out::println);

        if (gamePojos.isEmpty()) {
            System.out.println("No game deals found for the keyword: " + keyword);
        }

        promptToReturn();
    }

    private String validateEmailInput() {
        String email = "";
        while (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")) {
            email = getUserInput();
            if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")) {
                System.out.println("Invalid email format. Try again.");
            }
        }
        return email;
    }

    private void setUpPriceAlert(AlertService alertService) {
        String title = "";
        boolean titleIsValid = false;

        while (!titleIsValid) {
            System.out.println("Enter the game title for the price alert (or type 'menu' to back to the menu and check" + " \"Search for deals using a keyword\" to get exact title):");
            title = getUserInput();
            if ("menu".equalsIgnoreCase(title)) {
                System.out.println("Exiting setup.");
                return;
            } else if (title.isEmpty()) {
                System.out.println("Game title cannot be empty.");
            } else if (!isTitleValid(title)) {
                System.out.println("Game title is incorrect. Please enter a correct title.");
            } else {
                titleIsValid = true;
            }
        }

        double price = -1;

        while (price < 0 || price > 1000) {
            System.out.println("Enter the target price for the alert in dollars (alerts trigger below this price):");
            String priceInput = getUserInput();
            try {
                price = Double.parseDouble(priceInput);
                if (price < 0) {
                    System.out.println("Price cannot be negative.");
                } else if (price > 1000) {
                    System.out.println("This price is too high for alert.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number for the price.");
                price = -1;
            }
        }

        System.out.println("Enter your email to receive alert notifications:");
        String email = validateEmailInput();

        boolean success = alertService.createOrUpdateAlertWithGameTitle(title, email, price);
        System.out.println(success ? "Alert created successfully for " + email : "Failed to create alert for " + email);

        promptToReturn();
    }

    private boolean isTitleValid(String title) {
        try {
            GameDealResponse response = gameLookup.giveTitleToGetListOFDealsWithStores(title);

            if (response == null) {
                System.out.println("No response received for the title: " + title);
                return false;
            }

            if (!response.isEmpty()) {
                return true;
            } else {
                System.out.println("Game deal is empty for the title: " + title);
                return false;
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void manageAlertsByEmail(AlertService alertService) {
        System.out.println("Enter your email to receive alert notifications:");

        String email = validateEmailInput();

        System.out.println(alertService.getAlertsForEmail(email));

        promptToReturn();
    }

    private void promptToReturn() {
        boolean shouldReturn = false;

        while (!shouldReturn) {
            System.out.println("Press 'M/m' to return to the menu, or 'E/e' to exit.");
            String decision = getUserInput().toUpperCase();

            if ("M".equals(decision)) {
                shouldReturn = true;
            } else if ("E".equals(decision)) {
                shouldReturn = true;
                isProgramRunning = false;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public boolean isRunning() {
        return isProgramRunning;
    }

}
