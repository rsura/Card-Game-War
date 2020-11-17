/*
Name: Rahul Sura
Email: sura@chapman.edu
Date: 11/17/2020
Class section: CPSC 231 - 01
Project: Modern War(fare)
File: Game.java
*/

import java.util.LinkedList;

public class Game{
  private Deck m_deck;
  private Player m_player1;
  private Player m_player2;

  private int m_battleNumber = 0;
  private int m_numWars = 0;
  private int m_numDoubleWars = 0;
  private static int m_gameNumber = 0; // static because there will be multiple games in simulation
  private boolean m_gameIsOver = false;

  public Game(){
    m_deck = new Deck(); // new deck of cards
    m_player1 = new Player(1, m_deck); // deals random cards to player 1
    m_player2 = new Player(2, m_deck); // deals random cards to player 2
    m_gameNumber++;
    while(!(m_player1.hasWon() || m_player2.hasWon()) && !m_gameIsOver){ // continues battle until these conditions are stop being met
      play();
    }
    if(m_player1.hasWon()){
      WarLogger.getInstance().logGameOutcome(m_gameNumber, WarLogger.P1);
    } else if (m_player2.hasWon()){
      WarLogger.getInstance().logGameOutcome(m_gameNumber, WarLogger.P2);
    }
  }

  public Deck getDeck(){
    return m_deck;
  }

  public Player getPlayer1(){
    return m_player1;
  }

  public Player getPlayer2(){
    return m_player2;
  }

  public int getBattleNumber() {
    return m_battleNumber;
  }

  public int getNumWars() {
    return m_numWars;
  }

  public int getNumDoubleWars() {
    return m_numDoubleWars;
  }

  // no explicit mutators because there shouldn't be other factors affecting the performance of the game

  private Card tryDealing(Player player){
    if(player.getNumCardsInHand() > 0){ // to prevent out of bounds exception error
      return player.flip();
    }
    return null;
  }

  private int calculateMedianCard(Card one, Card two, Card three){
    if(one != null && two != null && three != null){ // gets the median of 3 cards if they all are not null objects
      int a = one.getValue();
      int b = two.getValue();
      int c = three.getValue();
      if((b < a && a < c) || (c < a && a < b)) {
        return one.getValue();
      } else if((a < b && b < c) || (c < b && b < a)) {
        return two.getValue();
      } else if((a < c && c < b) || (b < c && b < a)) {
        return three.getValue();
      } else if (a == b){
        return a;
      } else if (b == c){
        return b;
      } else if (a == c){
        return c;
      }
    }

    if (one == null) { // if card one is null
      return calculateMedianCard(two, three); // uses an overloaded method to check median card of 2 values
    } else if (two == null) { // if card two is null
      return calculateMedianCard(one, three); // uses an overloaded method to check median card of 2 values
    } else if (three == null) { // if card three is null
      return calculateMedianCard(one, two); // uses an overloaded method to check median card of 2 values
    } else {
      return -1; // if all are null for some reason, median value is negative 1 to show they lost
    }

  }

  private int calculateMedianCard(Card one, Card two){ // overloaded calculateMedianCard method
    if(one == null){
      return two.getValue();
    } else if (two == null){
      return one.getValue();
    }
    return Math.max(one.getValue(),two.getValue());
  }

  private void play(){
    m_battleNumber++; // adds to battle number

    //makes 3 cards for player 1
    Card playerOneCurrCard1 = tryDealing(m_player1);
    Card playerOneCurrCard2 = tryDealing(m_player1);
    Card playerOneCurrCard3 = tryDealing(m_player1);

    // puts them in an array for WarLogger
    Card[] playerOneCards = {playerOneCurrCard1, playerOneCurrCard2, playerOneCurrCard3};
    WarLogger.getInstance().logBattle(m_battleNumber,WarLogger.P1,playerOneCards);

    //makes 3 cards for player 2
    Card playerTwoCurrCard1 = tryDealing(m_player2);
    Card playerTwoCurrCard2 = tryDealing(m_player2);
    Card playerTwoCurrCard3 = tryDealing(m_player2);

    // puts them in an array for WarLogger
    Card[] playerTwoCards = {playerTwoCurrCard1, playerTwoCurrCard2, playerTwoCurrCard3};
    WarLogger.getInstance().logBattle(m_battleNumber,WarLogger.P2,playerTwoCards);

    LinkedList<Card> playedCards = new LinkedList<Card>(); // puts all cards in a pool to be collected later on
    playedCards.add(playerOneCurrCard1);
    playedCards.add(playerOneCurrCard2);
    playedCards.add(playerOneCurrCard3);

    playedCards.add(playerTwoCurrCard1);
    playedCards.add(playerTwoCurrCard2);
    playedCards.add(playerTwoCurrCard3);

    // finds which value of each player's middle card
    int playerOneMedianCardVal = calculateMedianCard(playerOneCurrCard1,playerOneCurrCard2,playerOneCurrCard3);
    int playerTwoMedianCardVal = calculateMedianCard(playerTwoCurrCard1,playerTwoCurrCard2,playerTwoCurrCard3);

    if (playerOneMedianCardVal > playerTwoMedianCardVal){ // player 1 wins battle
      WarLogger.getInstance().logBattleOutcome(m_battleNumber,WarLogger.P1);
      m_player1.collect(playedCards); // player 1 collects cards
      playedCards.clear();
    } else if (playerOneMedianCardVal < playerTwoMedianCardVal){ // player 2 wins battle
      WarLogger.getInstance().logBattleOutcome(m_battleNumber,WarLogger.P2);
      m_player2.collect(playedCards); // player 2 collects cards
      playedCards.clear();
    } else if (playerOneMedianCardVal == playerTwoMedianCardVal){ // else it's a  war
      WarLogger.getInstance().logBattleOutcome(m_battleNumber,WarLogger.WAR);
      war(playedCards); // calls the war
    }

  }

  private void war(LinkedList<Card> playedCards){
    m_numWars++; // increments number of wars
    Card playerOneWarCard = tryDealing(m_player1);
    Card playerTwoWarCard = tryDealing(m_player2);

    // adds both war cards to the pool of cards in middle
    playedCards.add(playerOneWarCard);
    playedCards.add(playerTwoWarCard);
    if(playerOneWarCard != null && playerTwoWarCard != null){
      if(playerOneWarCard.getValue() > playerTwoWarCard.getValue()){ // player 1 wins battle
        m_player1.collect(playedCards); // player 1 collects cards
        playedCards.clear();
        WarLogger.getInstance().logWarOutcome(m_numWars,WarLogger.P1);
      } else if(playerOneWarCard.getValue() < playerTwoWarCard.getValue()){ // player 2 wins battle
        m_player2.collect(playedCards); // player 2 collects cards
        playedCards.clear();
        WarLogger.getInstance().logWarOutcome(m_numWars,WarLogger.P2);
      } else {
        m_numDoubleWars++; // it's a double war then, and needs to be handled a little different

        // m_numWars gets 2 wars subtracted because when the war method is called recursively,
        // The m_numWars is in this battle incremented by 2. Instead, we don't want them to
        // count as 2 individual wars and instead as a double war.
        if (m_numWars > 1){
          m_numWars = m_numWars - 2;
        } else {
          m_numWars = 0;
        }

        WarLogger.getInstance().logBattleOutcome(m_battleNumber,WarLogger.WAR);
        war(playedCards); // recursive call
      }
    } else if (playerOneWarCard == null){ // means player 1 has no more cards to play so player 2 wins game
      m_player2.collect(playedCards); // player 2 collects the cards
      playedCards.clear();
      m_gameIsOver = true; // notifies the while loop to end
    }  else if (playerTwoWarCard == null){ // means player 2 has no more cards to play so player 1 wins game
      m_player1.collect(playedCards); // player 1 collects the cards
      playedCards.clear();
      m_gameIsOver = true; // notifies the while loop to end
    }
  }
}
