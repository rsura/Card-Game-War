/*
Name: Rahul Sura
Email: sura@chapman.edu
Date: 11/17/2020
Class section: CPSC 231 - 01
Project: Modern War(fare)
File: Player.java
*/

import java.util.LinkedList;

public class Player{
  private int m_playerNum;
  private LinkedList<Card> m_ownedCards = new LinkedList<Card>(); // player's own set of cards

  public Player(int playerNum, Deck deck){
    m_playerNum = playerNum;
    for(int i = 0; i < 26; i++){
      m_ownedCards.add(deck.deal()); // gets 26 random cards from the deck
    }
  }

  public int getPlayerNum(){
    return m_playerNum;
  }

  public void setPlayerNum(int n){
    m_playerNum = n;
  }

  // an accessor of m_ownedCards
  public int getNumCardsInHand(){
    return m_ownedCards.size();
  }

  // an accessor/mutator of m_ownedCards
  public Card deal(){ // same functionality as the deal method in the Deck class
    int randomNum = (int)(Math.random()*m_ownedCards.size());
    Card randomCard = new Card(m_ownedCards.get(randomNum));
    m_ownedCards.remove(randomNum);
    return randomCard;
  }

  // an accessor/mutator of m_ownedCards
  public Card flip(){ // opens the top card
    if(!m_ownedCards.isEmpty()){
      Card topCard = new Card(m_ownedCards.get(0));
      m_ownedCards.remove(0);
      return topCard;
    }
    return null;
  }

  // a mutator of m_ownedCards
  // collects both players' hands to the bottom of this player's owned cards
  public void collect(LinkedList<Card> playedCards){
    for (Card c: playedCards) {
      if(c != null){
        m_ownedCards.add(c);
      }
    }
  }

  // checks to see if a player won by seeing if they have 52 cards.
  // it will never go over 52 because when collecting both players hands in the
  // collect() method, it will make sure the card isn't null
  public boolean hasWon(){
    return getNumCardsInHand() == 52;
  }
}
