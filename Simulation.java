/*
Name: Rahul Sura
Email: sura@chapman.edu
Date: 11/17/2020
Class section: CPSC 231 - 01
Project: Modern War(fare)
File: Simulation.java
*/


public class Simulation {
  private int[] m_battlesPerGame; // battles for each game
  private int[] m_warsPerGame; // wars for each game
  private int[] m_doubleWarsPerGame; // double wars for each game

  // averages initialized to zero which calculate() will take care of
  private double m_avgBattlesPerGame = 0.0;
  private double m_avgWarsPerGame = 0.0;
  private double m_avgDoubleWarsPerGame = 0.0;


  // initializing mins and maxes with extreme values which will definitely be overwritten
  private int m_maxBattlesInGame = Integer.MIN_VALUE; // max battles in game will always be greater than Integer.MIN_VALUE
  private int m_minBattlesInGame = Integer.MAX_VALUE; // min battles in game will always be less than Integer.MAX_VALUE
  private int m_maxWarsInGame = Integer.MIN_VALUE; // max battles in game will always be greater than Integer.MIN_VALUE
  private int m_minWarsInGame = Integer.MAX_VALUE; // min battles in game will always be less than Integer.MAX_VALUE

  public Simulation(int n){ // new simulation with 'n' games
    // initializes all arrays to that length
    m_battlesPerGame = new int[n];
    m_warsPerGame = new int[n];
    m_doubleWarsPerGame = new int[n];
    simulate(n); // simulates the n number of games
  }

  public void simulate(int n){
    for (int i = 0; i < n; i++) {
      Game game = new Game();

      // after the whole game is over...
      m_battlesPerGame[i] = game.getBattleNumber();
      m_warsPerGame[i] = game.getNumWars();
      m_doubleWarsPerGame[i] = game.getNumDoubleWars();
    }
    calculate(); // called to calculate all stats
    report(); // reports them in the end to print all the stats out
  }

  public void calculate(){ // calculates all stats
    for (int i : m_battlesPerGame) { // calculates m_minBattlesInGame, m_maxBattlesInGame, and m_avgBattlesPerGame
      m_avgBattlesPerGame += i;
      if (i > m_maxBattlesInGame) {
        m_maxBattlesInGame = i;
      }
      if (i < m_minBattlesInGame) {
        m_minBattlesInGame = i;
      }
    }
    m_avgBattlesPerGame /= m_battlesPerGame.length; // divides sum of all battles across all games by number of games

    for (int i : m_warsPerGame) { // calculates m_minWarsInGame, m_maxWarsInGame, and m_avgWarsPerGame
      m_avgWarsPerGame += i;
      if (i > m_maxWarsInGame) {
        m_maxWarsInGame = i;
      }
      if (i < m_minWarsInGame) {
        m_minWarsInGame = i;
      }
    }
    m_avgWarsPerGame /= m_warsPerGame.length; // divides sum of all wars across all games by number of games

    for (int i : m_doubleWarsPerGame) { // calculates m_avgDoubleWarsPerGame
      m_avgDoubleWarsPerGame += i;
    }
    m_avgDoubleWarsPerGame /= m_doubleWarsPerGame.length; // divides sum of all double wars across all games by number of games

    // following 3 lines makes the averages rounded to 2 decimal places
    m_avgBattlesPerGame = ((int)(m_avgBattlesPerGame * 100))/100.0;
    m_avgWarsPerGame = ((int)(m_avgWarsPerGame * 100))/100.0;
    m_avgDoubleWarsPerGame = ((int)(m_avgDoubleWarsPerGame * 100))/100.0;
  }

  public void report(){ // neatly prints out stats of the game with colors
    System.out.println("---------------------------------------------------------------");
    System.out.println("There were a total of \u001B[32m" + m_battlesPerGame.length + "\u001B[0m game(s) played. Here are the stats:");
    System.out.println("---------------------------------------------------------------");
    System.out.println("\u001B[33mAverage \u001B[0mnumber of battles per game: " + m_avgBattlesPerGame);
    System.out.println("\u001B[33mAverage \u001B[0mnumber of wars per game: " + m_avgWarsPerGame);
    System.out.println("\u001B[33mAverage \u001B[0mnumber of double wars per game: " + m_avgDoubleWarsPerGame + "\n");
    System.out.println("\u001B[32mMax \u001B[0mnumber of battles in a game: " + m_maxBattlesInGame);
    System.out.println("\u001B[31mMin \u001B[0mnumber of battles in a game: " + m_minBattlesInGame + "\n");
    System.out.println("\u001B[32mMax \u001B[0mnumber of wars in a game: " + m_maxWarsInGame);
    System.out.println("\u001B[31mMin \u001B[0mnumber of wars in a game: " + m_minWarsInGame);
    System.out.println("---------------------------------------------------------------");
    System.out.println("\u001B[32m" + "** Remember to check WarLogger.txt for full details of the game **" + "\u001B[0m");
  }

  public static void main(String[] args) {
    // In case arguments aren't passed correctly or at all when running Simulation, the try-catch block handles it
    // the new Simulation isn't put in a variable because we have no use for it after it runs.
    try{
      // takes the absolute value of the integer for number of games and makes a simulation from that.
      // if anything but an integer, it will go to the catch block and make a simulation of 3 games
      new Simulation(Math.abs(Integer.parseInt(args[0])));
    } catch (Exception e){
      new Simulation(3); //default number of games is 3.
    }
    WarLogger.getInstance().release(); // ends WarLogger
  }
}
