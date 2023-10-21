class Card {

    private String suit; //holds suit of card
    private String rank; //holds rank of card
    private boolean status; //Shows if card is faced up or down

    public Card(String r, String s) { //Card constructor class
        rank = r;
        suit = s;
        status = false;
    }

    public String getSuit() { //gets suit of card
        return suit;
    }

    public String getRank() { //gets rank of card
        return rank;
    }

    public boolean getStatus() { //gets the status of card if it is faced up or down
        return status;
    }

    String[] order = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"}; //Creates the order of card which will be in the rank values of each card

    public boolean BlackCard() { //checks if card is Black or Red (when its false)
        return (suit.equalsIgnoreCase("C") || suit.equalsIgnoreCase("S"));
    }

    public void faceUp() { //turns card face up card is down when it is false
        status = true;
    }

    //Checkers
    Boolean isInOrderFor(Card a) { //Checks if the card uis in the right order within the lanes and suit piles
        if (this.rank.equals("A")) {
            return false;
        }
        if (this.BlackCard() && !a.BlackCard()) {
            for (int i = 0; i < order.length; i++) {
                if (order[i].equals(a.getRank())) {
                    return (order[i + 1].equals(this.rank));
                }
            }
        } else if (!this.BlackCard() && a.BlackCard()) {
            for (int i = 0; i < order.length; i++) {
                if (order[i].equals(a.getRank())) {
                    return (order[i + 1].equals(this.rank));
                }
            }
        }
        return false;
    }

    Boolean isNext(Card a) { //Checks the card rank and sees if it is next or if its of the same suit
        if(!suit.equals(a.getSuit())) {
            return false;
        }
        if(rank.equals("K")){
            return false;
        }
        for(int i = 0;i<order.length;i++){
            if(order[i].equals(rank)){
                return(order[i+1].equals(a.getRank()));
            }
        }
        return false;
    }

    @Override //Overrides original string to function.
    public String toString(){
        String s = rank + suit;
        return s;
    }
}

