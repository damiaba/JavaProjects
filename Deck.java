import java.util.List;
import java.util.*;
import java.util.ArrayList;

class Deck {
    ArrayList<Card> deck = new ArrayList<Card>(); //creates an array list that will take in the cards.

    void createCardDeck() { //Creates a deck of cards from the for loop.
        String[] suits = {"D", "H", "C", "S"};
        String[] order = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        for (String suit : suits) {
            for (String s : order) {
                Card c = new Card(s, suit);
                deck.add(c);
            }
        }
    }

    void shuffleDeck() { //shuffles the deck
        Collections.shuffle(deck);
    }

    int SizeOfDeck() { //returns the size of deck
        return deck.size();
    }

    ArrayList<Card> returnDeck() { //returns the deck
        return deck;
    }

    int point = 0;

    boolean checkDeck() { //checks if all cards have been removed from the deck to the pile
        return point == deck.size();
    }

    Card top = null;

    Card getTop() { //gets the card at the top
        Card rt = top;
        top = null;
        return rt;
    }

    void showTop() { //shows the card that is at the top
        int TopLocation = deck.size() - 1;
        Card d = deck.get(TopLocation);
        deck.remove(TopLocation);
        top = d;
    }

    boolean check() { //Checks if the deck is empty and all cards have been used
        return point == 1;
    }

    Card drawCard(){ //draws cards
        if(point == deck.size())
        {
            point = 0;
        }
        Card c = deck.get(point);
        point++;
        return c;
    }

    void removeCard(){ //removes card from the point in the array
        point--;
        deck.remove(point);
        if(point == 0){
            point = deck.size();
        }
    }

    //void printDeck(){ //This function was just used in the testing period
    //                   //To ensure that the deck was coming out correctly.
    //     for(Card c:deck){
    //         System.out.println(c.getRank() + " of " + c.getSuit());
    //     }
    // }

}