import com.reversi.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static void processGame(Game game, Player greenPlayer, Player whitePlayer) {
        while (greenPlayer.isCanMove() || whitePlayer.isCanMove()) {
            if (game.isGreenTurn()) {
                processMove(game.getReversiBoard(), greenPlayer,
                        greenPlayer.getFriendlyColor(), whitePlayer.getFriendlyColor());
                game.finishMove();
            } else {
                processMove(game.getReversiBoard(), whitePlayer,
                        whitePlayer.getFriendlyColor(), greenPlayer.getFriendlyColor());
                game.finishMove();
            }
        }
        System.out.println("\nResults:");
        System.out.println(game.getReversiBoard());
        printGameResults(greenPlayer, whitePlayer);
        if (game instanceof GameWithPlayer) {
            GameWithPlayer.setBestGreenScore(greenPlayer.getCurrentScore());
            GameWithPlayer.setBestWhiteScore(whitePlayer.getCurrentScore());
            System.out.println("Score of green player: " + greenPlayer.getCurrentScore());
            System.out.println("Score of white player: " + whitePlayer.getCurrentScore());
        } else if (game instanceof GameWithBot) {
            GameWithBot.setBestScore(greenPlayer.getCurrentScore());
            System.out.println("Score of player: " + greenPlayer.getCurrentScore());
        }
    }

    private static void processMove(ReversiBoard board, Player player,
                                    PieceColor friendlyColor, PieceColor opponentsColor) {
        Map<Coordinate, List<Square>> availableMoves =
                GameWithPlayer.getAvailableMoves(board, friendlyColor,
                        opponentsColor);
        if (!availableMoves.isEmpty()) {
            player.setCanMove(true);
            System.out.println("\n" + player + " player moves now!");
            Game.visualizeAvailableMoves(board, availableMoves);
            System.out.println(board);
            System.out.println("Valid coordinates:");
            for (Coordinate coordinate : availableMoves.keySet()) {
                System.out.println(coordinate);
            }
            if (player instanceof Bot) {
                Coordinate coordinate = ((Bot) player).findBestMove(availableMoves);
                System.out.println("Bot choose " + coordinate);
                player.move(board, coordinate, availableMoves);
            } else {
                Scanner input = new Scanner(System.in);
                System.out.print("\nEnter coordinate: ");
                Coordinate moveCoordinate = null;
                String stringCoordinate = input.nextLine();
                while (moveCoordinate == null) {
                    if (stringCoordinate.length() == 2) {
                        moveCoordinate = new Coordinate(stringCoordinate);
                    }
                    if (stringCoordinate.length() != 2 || !availableMoves.containsKey(moveCoordinate)) {
                        System.out.println("\nWrong coordinate!");
                        System.out.print("Enter coordinate: ");
                        stringCoordinate = input.nextLine();
                        moveCoordinate = null;
                    }
                }
                player.move(board, moveCoordinate, availableMoves);
            }
        } else {
            player.setCanMove(false);
            player.setCurrentScore(board, friendlyColor);
            System.out.println("\nThere is no moves for " + player + " player");
        }
    }

    private static void printGameResults(Player greenPlayer, Player whitePlayer) {
        if (greenPlayer.getCurrentScore() > whitePlayer.getCurrentScore()) {
            System.out.println(greenPlayer + " wins");
        } else if (greenPlayer.getCurrentScore() < whitePlayer.getCurrentScore()) {
            System.out.println(whitePlayer + " wins");
        } else {
            System.out.println("Tie!");
        }
    }

    private static void printInstructions() {
        System.out.println("""
                Here some tips for playing.
                                
                You have two game-mods
                Type "pvp" (any case, without quotes) to play with real player
                Type "pve" (any case, without quotes) to play with bot (only easy-mode)
                                
                Also you need to enter coordinates
                Valid representation: in entered string first letter from A to H, the second letter from 1 to 8. Example: H4
                                
                Type "records" (any case, without quotes) to get best records.
                Type "end" (any case, without quotes) to exit
                """);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        printInstructions();
        System.out.print("Choose game-mod or check records or type \"end\" to exit: ");
        String command = input.nextLine().toLowerCase();
        while (!"end".equals(command)) {
            switch (command) {
                case "pvp" -> {
                    Game gameWithPlayer = new GameWithPlayer();
                    Player greenPlayer = new Player(PieceColor.GREEN);
                    Player whitePlayer = new Bot(PieceColor.WHITE);
                    processGame(gameWithPlayer, greenPlayer, whitePlayer);
                }
                case "pve" -> {
                    Game gameWithPlayer = new GameWithBot();
                    Player greenPlayer = new Player(PieceColor.GREEN);
                    Player whitePlayer = new Bot(PieceColor.WHITE);
                    processGame(gameWithPlayer, greenPlayer, whitePlayer);
                }
                case "records" -> {
                    System.out.println("\nWith bot: " + GameWithBot.getBestScore());
                    System.out.println("\nTwo players:\nGreen: " + GameWithPlayer.getBestGreenScore() + "\nWhite: " +
                            GameWithPlayer.getBestWhiteScore());
                }
                default -> System.out.println("\nWrong command!");
            }
            System.out.print("Choose game-mod or check records or type \"end\" to exit: ");
            command = input.nextLine().toLowerCase();
        }
        System.out.println("\nThanks for playing!");
    }
}
