import java.util.Random;
import java.util.Scanner;

public class GuessTheNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int maxRounds = 3; // Number of rounds
        int maxAttempts = 5; // Number of attempts per round
        int score = 0; // Total score

        System.out.println("Welcome to the Guess the Number Game!");

        for (int round = 1; round <= maxRounds; round++) {
            int numberToGuess = random.nextInt(100) + 1;
            int attempts = 0;
            boolean guessed = false;

            System.out.println("\nRound " + round + ":");
            System.out.println("I have selected a number between 1 and 100. Can you guess it?");

            while (attempts < maxAttempts && !guessed) {
                System.out.print("Enter your guess: ");
                int userGuess = scanner.nextInt();
                attempts++;

                if (userGuess < numberToGuess) {
                    System.out.println("The number is higher. Try again.");
                } else if (userGuess > numberToGuess) {
                    System.out.println("The number is lower. Try again.");
                } else {
                    System.out.println("Congratulations! You guessed the number.");
                    guessed = true;
                    score += (maxAttempts - attempts + 1) * 10; // Points based on remaining attempts
                }
            }

            if (!guessed) {
                System.out.println("Sorry, you've used all attempts. The number was " + numberToGuess);
            }

            System.out.println("Score after round " + round + ": " + score);
        }

        System.out.println("\nGame over! Your final score is: " + score);
        scanner.close();
    }
}
