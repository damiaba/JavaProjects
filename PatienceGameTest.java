import java.util.*;
import java.util.Scanner;
import java.util.Stack;

public class PatienceGameTest {

    Deck deck = new Deck();
    Lanes l = new Lanes();
    SuitPiles s = new SuitPiles();
    private int score = 0; //Score count
    Stack<Card> uncoveredCard = new Stack<>(); //Cards that are uncovered

    void initialise(){ //Initialises the game
        deck.createCardDeck();
        deck.shuffleDeck();
        l.dealCards(deck);
    }
    boolean checkGameOver(){
        for(int i=0;i<7;i++)//Checks if card can move from lane to lane
        {

            Stack<Card> holder =l.getNCards(i);
            if(holder ==null)
                continue;
            if(holder.empty())
                continue;
            Card c= holder.peek();
            for(int j=0;j<7;j++)//Checks if the cards that are within the lane can be moved to the other lanes
            {
                char ln=(char)(j+1+48);
                if(i!=j)
                {
                    if(l.checkNext(c, ln))//Checker
                    {
                        while(!holder.empty())//Puts cards back to lane without leaving the method
                        {
                            Card ch= holder.peek();
                            holder.pop();
                            ln=(char)(i+1+48);
                            l.placeNext(ch,ln);
                        }
                        return false;
                    }
                }
                while(!holder.empty()) //Placing cards to the lanes
                {
                    Card ch= holder.peek();
                    holder.pop();
                    ln=(char)(i+1+48);
                    l.placeNext(ch,ln);
                }
            }
        }
        for(int i=0;i<7;i++) //Checks if cards can be moved from lanes to suit piles
        {

            Stack<Card> holder =l.getNCards(i);
            if(holder ==null) //Places card back in lane
                continue;
            if(holder.empty())
                continue;
            Card c= holder.peek();
            while(!holder.isEmpty())
            {
                c= holder.peek();
                holder.pop();
                char a=(char)(i+1+48);
                l.placeNext(c,a);
            }

            if(s.checkNext(c, c.getSuit().charAt(0)))
                return false;
        }

        ArrayList<Card> deck1=deck.returnDeck();
        if(!deck1.isEmpty())
        {
            for(Card c:deck1)//Checks if any moves are possible from the deck
            {
                for(int j=0;j<7;j++) //Check through the lanes
                {
                    char a=(char)(j+1+48);

                    if(l.checkNext(c, a))
                    {
                        return false;
                    }
                }
                if(s.checkNext(c, c.getSuit().charAt(0))) //Check through the suit piles
                    return false;
            }
        }
        return true;
    }

    void StartGame() {
        Scanner in = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Deck  \tP \t  \tD \tH \tC \tS ");
                if (deck.SizeOfDeck() != 0 || !deck.checkDeck())
                    System.out.print("|**|\t");
                else
                    System.out.print("[||]\t");
                if (!uncoveredCard.empty()) {
                    System.out.print(uncoveredCard.peek() + "\t  \t");
                } else
                    System.out.print("[]\t  \t");
                s.display();//Print the suites
                System.out.println();
                l.display();//Print the lanes
                System.out.println();
                boolean full = s.check();//Checks if all cards have been placed in the suits

                if (full) { //Checks if the game has been won
                    System.out.println("Game over. You won!");
                    return;
                }
                if (checkGameOver())//Checks if the game has ended and is over
                {
                    System.out.println("Game Over!You lost! Better luck next time :(");
                    return;
                }

                if(deck.SizeOfDeck() == 0){ //If the deck is empty and all cards have been distributed then move on
                System.out.println("Deck is now empty complete the game!");
                }
                else if (deck.check())//Resets card deck if all cards have been uncovered.
                {
                    Card c = uncoveredCard.peek();
                    uncoveredCard = new Stack<>();
                    uncoveredCard.add(c);
                }

                System.out.println("Player Options:\n-To draw a new card from the deck please type 'D'\n-To make a move type the location followed by the destination using the columns.(e.g - 2D, P2, 74)\n-To quite type 'Q'\n ");
                System.out.println("Score : " + score);
                System.out.print("Player's input : ");
                String userinput = in.nextLine().trim().toUpperCase();
                int len = userinput.length();

                if (len == 1) {
                    if (userinput.equals("Q")) {
                        System.out.println("Game ended by the user");
                        System.out.println("You lost! Hard Luck!");
                        return;
                    }
                    else if (userinput.equals("D")) {
                        if (deck.SizeOfDeck() == 0){
                            System.out.println("Didn't I just tell you the deck is empty??? Finish the game already!!");
                        }
                        else if(deck.SizeOfDeck() > 0)
                        uncoveredCard.add(deck.drawCard());
                    }
                    else {
                        throw new InvalidMove();
                    }
                } else if (len == 2) {
                    char c1 = userinput.charAt(0);
                    char c2 = userinput.charAt(1);
                    //The following code checks if the string entered (source and destination) is a valid move.
                    if ((c1 > 48 && c1 < 56) || c1 == 'P') { //48 and 56 is 0-7 as Unicode values
                    }
                    else {
                        throw new InvalidMove();

                    }
                    if ((c2 > 48 && c2 < 56) || c2 == 'D' || c2 == 'H' || c2 == 'C' || c2 == 'S') {
                    }
                    else {
                        throw new InvalidMove();

                    }
                    Stack<Card> cSource = new Stack<>();

                    if (c1 > 48 && c1 < 56) {
                        cSource = l.getNCards((int)(c1) - 49);
                        if (cSource == null || cSource.empty()) { //If the piles don't have any cards throw exception
                            throw new InvalidPlay();
                        }
                    } else if (c1 == 'P') {
                        if (uncoveredCard.empty()) { //If there is no card uncovered throw exception
                            throw new InvalidMove();
                        }
                        cSource.add(uncoveredCard.peek());
                        uncoveredCard.pop();
                    } else {
                        throw new InvalidMove();
                    }
                    if (c2 > 48 && c2 < 56) {
                        Card c = cSource.peek();
                        if (!l.checkNext(c, c2))//If the cards from lanes that were input cannot be transferred to other lanes
                        {
                            while (!cSource.empty())//Putting cards back in their original places
                            {
                                if (c1 == 'P')
                                    uncoveredCard.add(cSource.peek());
                                else
                                    l.placeNext(cSource.peek(), c1);
                                cSource.pop();
                            }
                            throw new InvalidPlay();

                        }
                        else {
                            while (!cSource.empty()) {
                                if (c1 != 'P')
                                    score += 5;
                                l.placeNext(cSource.peek(), c2);//Adding to new lane
                                cSource.pop();
                            }
                        }
                    }
                    else {//Adding to one of the piles

                        Card c = cSource.peek();

                        while (cSource.size() > 1)//Putting all cards back except the top
                        {
                            c = cSource.peek();
                            cSource.pop();
                            l.placeNext(c, c1);
                        }
                        c = cSource.peek();
                        if (s.checkNext(c, c2)) {
                            s.placeNext(c, c2);
                            if (c1 == 'P') //If card is from pile adds 10 points to score
                                score += 10;
                            else //If card is from lane adds 20 points to score
                                score += 20;
                        }
                        else {
                            if (c1 == 'P')
                                uncoveredCard.add(c);
                            else
                                l.placeNext(c, c1);
                            throw new InvalidPlay();

                        }
                    }
                    l.turnTop();
                    if (c1 == 'P') {
                        deck.removeCard(); //Takes card away from the deck.
                    }
                }
                    else {
                        throw new InvalidMove();
                    }
                }

            catch (InvalidPlay | InvalidMove e) { //Prints error messages
                System.out.println(e.getMessage());
            }

        }
    }

    public static void main(String[] args){ //Starts game.
        PatienceGameTest g = new PatienceGameTest();
        g.initialise();
        g.StartGame();

    }

}