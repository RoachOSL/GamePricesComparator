package dev.Roach;

import dev.Roach.datamodel.game.Game;
import dev.Roach.datamodel.game.GamePojo;
import dev.Roach.fetchers.GamesFetcher;
import dev.Roach.fetchers.StoresFetcher;
import lombok.Setter;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

@Setter
public class Menu {

    private static boolean programControlFlag = false;

    public void startTheProgram() {

        StoresFetcher storesFetcher = new StoresFetcher();
        GameLookup gameLookup = new GameLookup();
        GamesFetcher gamesFetcher = new GamesFetcher();
        AlertService alertService = new AlertService();
        Scanner scanner = new Scanner(System.in);

        while (!programControlFlag) {
            storesFetcher.getAllShops();
            System.out.println("Welcome to the Deal Finder!");
            System.out.print("""
                    Menu (please select an option by entering the corresponding number):
                    1. Search for deals using an exact game title.
                    2. Search for deals using a keyword (limit to 25 results).
                    3. Set up a price alert for a game.
                    4. Manage your alerts via email link.
                    5. Exit the program.
                    """);

            try {

                int choice = 0;

                try {
                    System.out.println("Please select an option:");
                    choice = Integer.parseInt(scanner.nextLine());

                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }

                switch (choice) {
                    case 1 -> searchForDealsByTitle(scanner, gameLookup);
                    case 2 -> searchForDealsByKeyword(scanner, gamesFetcher);
                    case 3 -> setUpPriceAlert(scanner, alertService);
                    case 4 -> manageAlertsByEmail(scanner, alertService);
                    case 5 -> {
                        programControlFlag = true;
                        System.out.println("Exiting the program. Goodbye!");
                    }
                    default -> System.out.println("Invalid selection, please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    private void searchForDealsByTitle(Scanner scanner, GameLookup gameLookup) {
        System.out.println("Enter the exact title of the game to find deals:");
        String title = scanner.nextLine();

        try {
            System.out.println(gameLookup.giveTitleToGetListOFDealsWithStores(title));
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again with a different title.");
        }

        promptToReturn(scanner);
    }

    private void searchForDealsByKeyword(Scanner scanner, GamesFetcher gamesFetcher) {
        System.out.println("Enter a keyword to search for game deals:");
        String keyword = scanner.nextLine();

        List<GamePojo> gamePojos = gamesFetcher.getGameContainingKeyword(keyword);
        gamePojos.stream()
                .map(pojo -> new Game(pojo.getTitle(), pojo.getSteamID(), pojo.getCheapestPrice(), pojo.getGameID()))
                .limit(25)
                .forEach(System.out::println);

        if (gamePojos.isEmpty()) {
            System.out.println("No game deals found for the keyword: " + keyword);
        }

        promptToReturn(scanner);
    }

    private void setUpPriceAlert(Scanner scanner, AlertService alertService) {
        String title = "";
        while (title.isEmpty()) {
            System.out.println("Enter the game title for the price alert:");
            title = scanner.nextLine().strip();
            if (title.isEmpty()) {
                System.out.println("Game title cannot be empty.");
            }
        }

        double price = -1;
        while (price < 0) {
            System.out.println("Enter the target price for the alert (alerts trigger below this price):");
            String priceInput = scanner.nextLine().strip();
            try {
                price = Double.parseDouble(priceInput);
                if (price < 0) {
                    System.out.println("Price cannot be negative.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number for the price.");
            }
        }

        String email = "";
        while (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")) {
            System.out.println("Enter your email to receive alert notifications:");
            email = scanner.nextLine().strip();
            if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")) {
                System.out.println("Invalid email format.");
            }
        }

        boolean success = alertService.createOrUpdateAlertWithGameTitle(title, email, price);
        System.out.println(success ? "Alert created successfully for " + email : "Failed to create alert for " + email);

        promptToReturn(scanner);
    }

    private void manageAlertsByEmail(Scanner scanner, AlertService alertService) {

        String email = "";
        while (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")) {
            System.out.println("Enter your email to receive alert notifications:");
            email = scanner.nextLine().strip();
            if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")) {
                System.out.println("Invalid email format.");
            }
        }

        System.out.println(alertService.getAlertsForEmail(email));

        promptToReturn(scanner);
    }

    private boolean promptToReturn(Scanner scanner) {

        while (true) {
            System.out.println("Press 'M/m' to return to the menu, or 'E/e' to exit.");
            String decision = scanner.nextLine().strip().toUpperCase();

            if ("M".equals(decision)) {
                return true;
            } else if ("E".equals(decision)) {
                programControlFlag = true;
                return false;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

}
