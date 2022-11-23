package org.atm.database;

import org.atm.model.Card;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BankDataAccess extends DataAccess<Card> {
  public BankDataAccess() {
    localDatabase = new LinkedList<>();
    getDataFromStorage();
  }

  @Override
  protected boolean getDataFromStorage() {
    LinkedList<String> arrFromStr = new LinkedList<>();
    try (Scanner scan = new Scanner(new File(DATA_FILE_PATH.toString()))) {
      while (scan.hasNextLine()) {
        arrFromStr.add(scan.nextLine());
      }
      for (int i = 1; i < arrFromStr.size(); i++) {
        if (!arrFromStr.get(i).equals("")) {
          Card cardFromDb = new Card(arrFromStr.get(i));
          localDatabase.add(cardFromDb);
        }
      }
      return true;
    } catch (FileNotFoundException e) {
      System.out.println("Data file was not found");
      return false;
    } catch (NoSuchElementException e1) {
      System.out.println("Problem with cards in storage");
      return false;
    }
  }

  @Override
  public Card getObject(String cardNumber) {
    Card card = null;
    try {
      card =
          localDatabase.stream()
              .filter(card1 -> cardNumber.equals(card1.getCardNumber()))
              .findAny()
              .orElse(null);
    } catch (RuntimeException ex) {
      System.out.println("Card was not found in collection/storage");
    }
    return card;
  }

  @Override
  public boolean updateObject(Card providedCard) {
    int rowIndex;
    try {
      for (int i = 0; i < localDatabase.size(); i++) {
        if (localDatabase.get(i).getCardNumber().equals(providedCard.getCardNumber())) {
          localDatabase.get(i).setBlockDate(providedCard.getBlockDate());
          localDatabase.get(i).setInvalidInputAttempt(providedCard.getInvalidInputAttempt());
          localDatabase.get(i).setBalance(providedCard.getBalance());
          rowIndex = i + 1; // ATM info is on the first line
          List<String> fileContent =
              new LinkedList<>(Files.readAllLines(DATA_FILE_PATH, StandardCharsets.UTF_8));
          for (int j = 0; j < fileContent.size(); j++) {
            if (j == rowIndex) {
              fileContent.set(
                  j,
                  providedCard.getCardNumber()
                      + " "
                      + providedCard.getPinCode()
                      + " "
                      + providedCard.getBalance()
                      + " "
                      + providedCard.getInvalidInputAttempt()
                      + " "
                      + providedCard.getBlockDate());
              break;
            }
          }
          Files.write(DATA_FILE_PATH, fileContent, StandardCharsets.UTF_8);
        }
      }
      return true;
    } catch (FileNotFoundException e) {
      System.out.println("Data file was not found");
      return false;
    } catch (IOException e) {
      System.out.println(e.getMessage());
      return false;
    }
  }
}
