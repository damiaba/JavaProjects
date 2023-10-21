import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class Lanes extends GameParts {

    public Lanes() { //Creates the Lanes
        piles = new Stack[7];
        for (int i = 0; i < 7; i++) {
            piles[i] = new Stack<>();
        }
    }

    public void display() { //Displays the Lane columns
        ArrayList<ArrayList<String>> place = new ArrayList<>();
        int lm = 0;
        for (int i = 0; i < 7; i++) {
            ArrayList<String> ukc = new ArrayList<>();
            for (int j = 0; j < piles[i].size(); j++) {
                Card c = piles[i].get(j);
                if (c.getStatus()) {
                    ukc.add(c.toString());
                }
                else {
                    ukc.add("**");
                }
            }
            place.add(ukc);
            lm = Math.max(lm, ukc.size());
        }

        System.out.println(" 1\t 2\t 3\t 4\t 5\t 6\t 7");
        for (int i = 0; i < lm; i++) {
            for (int j = 0; j < 7; j++) {
                if (i < place.get(j).size()){
                    System.out.print(place.get(j).get(i));
                    System.out.print("\t");
                }
                else{
                    System.out.print(" \t");
                }
            }
            System.out.println();
        }
    }

    public void dealCards(Deck d) //Deals the cards in the deck to the lanes
    {
        for(int i = 0; i<7; i++)
        {
            for(int j = 0; j<=i; j++) {
                if (d.SizeOfDeck() > 0) {
                    d.showTop();

                    Card c = d.getTop();
                    if (j == i) {
                        c.faceUp();
                    }

                    piles[i].add(c);
                }
            }
        }
    }

    boolean checkNext(Card c, char num) { //Checks if the card can put in the lane with the corresponding number
        int ind = (int)num - 1 - 48;

        if(piles[ind].empty())
        {
            //If empty lane only King can be put
            return c.getRank().equalsIgnoreCase("K");
        }

        if(c.getRank().equalsIgnoreCase("K")) {
            return false;
        }
        Card current=piles[ind].peek();
        return current.isInOrderFor(c);
    }

    Stack<Card> getNCards(int l) { //Finds the cards that are faced up in the lane
        if (piles[l].empty()) {
            return null;
        }

        Stack<Card> cards = new Stack<>();
        while (!piles[l].empty()) {
            Card c = piles[l].peek();
            if(c.getStatus()) {
                cards.add(c);
                piles[l].pop();
            }
            else{
                break;
            }
        }

        return cards;
    }

    void placeNext(Card c, char num) { //Adds card to the lane
        int ind = (int)num - 1 - 48;
        piles[ind].add(c);
    }

    void turnTop() { //Turns the cards in the lanes that are at the top
        for (int i = 0; i < 7; i++) { //
            if (!piles[i].empty()) {
                piles[i].peek().faceUp();
            }
        }
    }






}
