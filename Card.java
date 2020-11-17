/*
Name: Rahul Sura
Email: sura@chapman.edu
Date: 11/17/2020
Class section: CPSC 231 - 01
Project: Modern War(fare)
File: Card.java
*/

public class Card{
  private int m_value; // J would be 11, Q would be 12, etc.
  private char m_faceValue; // 11 would be J, 12 would be Q, etc.
  private char m_suit;

  public Card(int value, char suit){
    m_value = value;
    m_suit = suit;
    setFaceValue(value);
  }

  public Card(Card card){
    if(card != null){
      m_value = card.getValue();
      m_suit = card.getSuit();
      setFaceValue(m_value);
    }
  }

  public int getValue(){
    return m_value;
  }

  public char getSuit(){
    return m_suit;
  }

  // There are no mutators here because if this was a Card in real life, once a card is made
  // with a constructor, it can't have its attributes changed otherwise it would disturb
  // the game

  private void setFaceValue(int value){
    if (value >= 2 && value <= 9){
      m_faceValue = (char)(value + 48); // uses ASCII chart to give a character of an int
    } else if (value == 10){
      m_faceValue = '0'; // no character for a double digit number like 10 so I used '0'
    } else if (value == 11){
      m_faceValue = 'J';
    } else if (value == 12){
      m_faceValue = 'Q';
    } else if (value == 13){
      m_faceValue = 'K';
    } else if (value == 14){
      m_faceValue = 'A'; // don't know if A is actually 14, but it's higher than J,Q, and K, so I made it worth 14
    }
  }

  public String toString(){
    if(m_value >= 2 && m_value <= 10){
      return ("" + m_value + m_suit); // numerical value + suit
    } else {
      return ("" + m_faceValue + m_suit); // letter + suit
    }
  }
}
