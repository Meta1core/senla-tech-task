package org.atm.service.impl;

import org.atm.database.BankDataAccess;
import org.atm.database.DataAccess;
import org.atm.model.Bank;
import org.atm.model.Card;
import org.atm.model.LoginState;
import org.atm.service.BankService;

public class BankServiceImpl implements BankService {
  public static final int BLOCK_TIME_IN_MILLIS = 86400000;
  private final DataAccess<Card> bankDatabase;
  private Card currentCard;
  private final Bank currentBank;

  public BankServiceImpl(Bank bank) {
    bankDatabase = new BankDataAccess();
    currentBank = bank;
  }

  @Override
  public boolean withdrawFunds(int withdrawalAmount) {
    if (withdrawalAmount <= currentCard.getBalance()) {
      currentCard.setBalance(currentCard.getBalance() - withdrawalAmount);
      return bankDatabase.updateObject(currentCard);
    } else {
      System.out.println("You have no money in your account");
      return false;
    }
  }

  @Override
  public boolean topUpBalance(int topUpAmount) {
    if (topUpAmount < 1000000) {
      currentCard.setBalance(currentCard.getBalance() + topUpAmount);
      bankDatabase.updateObject(currentCard);
      return true;
    } else {
      System.out.println("The maximum amount of top up is 999 999");
      return false;
    }
  }

  @Override
  public int checkBalance() {
    return bankDatabase.getObject(currentCard.getCardNumber()).getBalance();
  }

  @Override
  public LoginState login(String providedCardNumber, int providedPinCode) {
    Card card = bankDatabase.getObject(providedCardNumber);
    if (card == null) {
      return LoginState.NOT_LOGGED_IN;
    }
    if (isCardBlocked(card, providedCardNumber, providedPinCode)) {
      return LoginState.BLOCKED;
    }
    if (isCredentialsNotValid(card, providedCardNumber, providedPinCode)) {
      return LoginState.NOT_LOGGED_IN;
    }
    currentCard = card;
    return LoginState.LOGGED_IN;
  }

  private boolean isCardBlocked(Card card, String providedCardNumber, int providedPinCode) {
    if (card.getBlockDate() == 0) {
      return false;
    }
    if (card.getBlockDate() + BLOCK_TIME_IN_MILLIS >= System.currentTimeMillis()) {
      return true;
    } else {
      if (card.getPinCode() == providedPinCode && card.getCardNumber().equals(providedCardNumber)) {
        card.setBlockDate(0);
        card.setInvalidInputAttempt(0);
        bankDatabase.updateObject(card);
      }
      return false;
    }
  }

  private boolean isCredentialsNotValid(Card card, String providedCardNumber, int providedPinCode) {
    if (card.getPinCode() != providedPinCode) {
      setInvalidAttempt(providedCardNumber);
      return true;
    }
    if (!card.getCardNumber().equals(providedCardNumber)) {
      return true;
    }
    card.setInvalidInputAttempt(0);
    bankDatabase.updateObject(card);
    return false;
  }

  @Override
  public void setInvalidAttempt(String cardNumber) {
    Card card = bankDatabase.getObject(cardNumber);
    card.setInvalidInputAttempt(card.getInvalidInputAttempt() + 1);
    if (card.getInvalidInputAttempt() == 3) {
      card.setBlockDate(System.currentTimeMillis());
    }
    bankDatabase.updateObject(card);
  }
}
