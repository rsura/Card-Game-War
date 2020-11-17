/*
Name: Rahul Sura
Email: sura@chapman.edu
Date: 11/17/2020
Class section: CPSC 231 - 01
Project: Modern War(fare)
File: Deck.java
*/

import java.util.LinkedList;

public class Deck{
  private LinkedList<Card> m_deckOfCards = new LinkedList<Card>();

  public Deck(){
    //makes a standard deck of cards
    // to copy and paste for suit char: ♠ ♥ ♦ ♣
    for (int i = 2; i <= 14; i++) {
      m_deckOfCards.add(new Card(i,'♠'));
      m_deckOfCards.add(new Card(i,'♥'));
      m_deckOfCards.add(new Card(i,'♦'));
      m_deckOfCards.add(new Card(i,'♣'));
    }
  }

  public LinkedList<Card> getDeckOfCards(){
    return m_deckOfCards;
  }

  // There are no generic mutators here: a Deck is made with the constructor. The only
  // mutator that should be used here is the deal() method below for the purpose of
  // any card game

  public Card deal(){
    // deals a random card from the stack when distributing to each player
    int randomNum = (int)(Math.random()*m_deckOfCards.size());
    Card randomCard = new Card(m_deckOfCards.get(randomNum));
    m_deckOfCards.remove(randomNum);
    return randomCard;
  }
}
