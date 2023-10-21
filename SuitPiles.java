import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class SuitPiles extends GameParts {

    public SuitPiles() { //Creates array list that will contain the suit piles
        piles = new Stack[4];
        for (int i = 0; i < piles.length; i++) {
            piles[i] = new Stack<>();
        }
    }

    public void display() { //Displays the suit piles
        for (int i = 0;i<4;i++) {
            if (piles[i].empty()) {
                System.out.print("[]\t");
            } else {
                Card c=piles[i].peek();
                System.out.print(c+"\t");
            }
        }
        System.out.println();
    }


    boolean check() { //Checks if all the piles have been filled, if true game is won
        for (Stack<Card> pile : piles) {
            if (pile.size() < 13) {
                return false;
            }
        }
        return true;
    }

    boolean checkNext(Card c, char num) { //Checks if it is possible to add a card to the suit
        String suit = c.getSuit().toLowerCase();
        String upperCaseSuit = suit.toUpperCase();

        if (suit.charAt(0) != num && upperCaseSuit.charAt(0) != num) {
            return false;
        }

        int pileIndex = getSuitIndex(c.getSuit());

        if (piles[pileIndex].empty() && c.toString().equalsIgnoreCase("A" + c.getSuit())) {
            return true;
        } else if (!piles[pileIndex].empty()) {
            Card topCard = piles[pileIndex].peek();
            return topCard.isNext(c);
        }

        return false;
    }

    int getSuitIndex(String suit) { //gets the index of the suit for comparison in above method
        return switch (suit.toLowerCase()) {
            case "d" -> 0;
            case "h" -> 1;
            case "c" -> 2;
            case "s" -> 3;
            default -> -1; // Handle unknown suit (if needed)
        };
    }

    void placeNext(Card c, char num) { //Place the card in the suit pile if it's the right card to go next
        int pileIndex = getSuitIndex(c.getSuit());
        if (pileIndex != -1) {
            piles[pileIndex].add(c);
        }
    }


}



