/*
Name: Rahul Sura
Email: sura@chapman.edu
Date: 11/17/2020
Class section: CPSC 231 - 01
Project: Modern War(fare)
File: WarLogger.java
*/

import java.io.FileWriter;
import java.io.PrintWriter;

public class WarLogger{

    private static WarLogger m_instance;
    private PrintWriter m_pw;

    public static final String P1 = "Player 1";
    public static final String P2 = "Player 2";
    public static final String WAR = "WAR!!!";

    private WarLogger(){
        try{
            m_pw = new PrintWriter(new FileWriter("WarLogger.txt"));

        } catch(Exception e){
            System.err.println("WarLogger is unable to create log file!");
            m_pw = null;
        }
    }

    public static synchronized WarLogger getInstance(){
        if(m_instance == null){
            m_instance = new WarLogger();
        }
        return m_instance;
    }

    public void logBattle(int battleNum, String player, Card[] hand){
        try{
            m_pw.println("Battle Number: " + battleNum);
            m_pw.println("Player Number: " + player);
            m_pw.print("Player Hand: ");
            if(hand == null || hand.length < 1){
                m_pw.println("Null or Empty Hand");

            } else{
                for(Card c: hand){
                    m_pw.print(c);
                    m_pw.print(" ");
                }
                m_pw.print("\n");
            }
        }catch(Exception e){
            System.err.println("WarLogger error while printing");
        }
    }

    public void logBattleOutcome(int battleNum, String result){
        try{
            m_pw.println("The outcome of battle " + battleNum + " is " + result);
        } catch(Exception e){
            System.err.println("WarLogger error while printing");
        }
    }

    public void logWarOutcome(int warNum, String result){
        try{
            m_pw.println("The outcome of war " + warNum + " is " + result);
        } catch(Exception e){
            System.err.println("WarLogger error while printing");
        }
    }

    public void logGameOutcome(int gameNum, String playerNum){
        try{
            m_pw.println("The winner of game " + gameNum + " is " + playerNum);
        } catch(Exception e){
            System.err.println("WarLogger error while printing");
        }
    }

    public void release(){
        try{
            m_pw.close();
        } catch(Throwable t){
            System.err.println("WarLogger unable to close correctly");
        }
    }

}
