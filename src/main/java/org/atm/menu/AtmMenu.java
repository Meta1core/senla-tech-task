package org.atm.menu;

import org.atm.service.AtmService;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AtmMenu {
  private final AtmService currentAtmService;
  private final Scanner scanner;
  private final String CARD_FORMAT = "####-####-####-####";
  private final String PIN_CODE_FORMAT = "####";

  public AtmMenu(AtmService atmService) {
    currentAtmService = atmService;
    this.scanner = new Scanner(System.in);
  }

  public void getUserCredentials() {
    ShowAtmInfo();
    System.out.println("Please, enter card number (format: '####-####-####-####')");
    String cardNumber = inputCardNumber();
    System.out.println("Please, enter pin code (format: '####')");
    int pinCode = inputPinCode();
    sendLoginRequest(cardNumber, pinCode);
  }

  private void sendLoginRequest(String cardNumber, int pinCode) {
    switch (currentAtmService.loginUser(cardNumber, pinCode)) {
      case LOGGED_IN:
        System.out.println("You are successfully logged in");
        showAtmMenu();
        break;
      case NOT_LOGGED_IN:
        System.out.println("Pin-code or card number is not valid");
        System.out.println("---------------------------------------------------");
        System.out.println("Please, re-enter card number (format: '####-####-####-####')");
        String newCardNumber = inputCardNumber();
        System.out.println("Please, re-enter pin code (format: '####')");
        int newPinCode = inputPinCode();
        sendLoginRequest(newCardNumber, newPinCode);
        break;
      case BLOCKED:
        System.out.println("Your card was blocked");
        System.out.println(
            "Card will be automatically unblocked twenty-four hours later since the blocking");
        System.out.println(
            "Please contact to "
                + currentAtmService.getConnectedAtm().getBank().getBankName()
                + "\n Phone: "
                + currentAtmService.getConnectedAtm().getBank().getPhone());
    }
  }

  private void ShowAtmInfo() {
    System.out.println("---------------------------------------------------");
    System.out.println(
        "Welcome to " + currentAtmService.getConnectedAtm().getBank().getBankName() + "!");
    System.out.println("Current ATM: " + currentAtmService.getConnectedAtm().getAtmName());
    System.out.println(
        "Main bank address: " + currentAtmService.getConnectedAtm().getBank().getAddress());
    System.out.println("ATM Address: " + currentAtmService.getConnectedAtm().getAddress());
    System.out.println("---------------------------------------------------");
  }

  private int inputPinCode() {
    try {
      MaskFormatter maskFormatter = new MaskFormatter(PIN_CODE_FORMAT);
      maskFormatter.setAllowsInvalid(false);
      System.out.println("---------------------------------------------------");
      String pinCode = scanner.nextLine();
      if (pinCode.length() != 4) throw new ParseException("", 0);
      return Integer.parseInt(maskFormatter.valueToString(pinCode));
    } catch (ParseException e) {
      System.out.println("You need to enter a valid format(format: '####', example: 1234)");
    }
    return inputPinCode();
  }

  private String inputCardNumber() {
    try {
      MaskFormatter maskFormatter = new MaskFormatter(CARD_FORMAT);
      maskFormatter.setPlaceholderCharacter('_');
      maskFormatter.setAllowsInvalid(false);
      String cardNumber = scanner.nextLine();
      if (cardNumber.length() != 19) throw new ParseException("", 0);
      return cardNumber;
    } catch (ParseException e) {
      System.out.println(
          "You need to enter a valid format (format: '####-####-####-####', example: 1111-2222-3333-4444)");
    }
    return inputCardNumber();
  }

  private void showAtmMenu() {
    try {
      while (true) {
        System.out.println("---------------------------------------------------");
        System.out.println("What will you do?");
        System.out.println("1. Check the balance");
        System.out.println("2. Withdraw money");
        System.out.println("3. Top up the balance");
        System.out.println("---------------------------------------------------");
        int input = scanner.nextInt();
        switch (input) {
          case 1:
            System.out.println("Checking...");
            System.out.println("Your balance is " + currentAtmService.checkBalance());
            break;
          case 2:
            System.out.println("Please, enter an amount to withdraw money");
            input = scanner.nextInt();
            currentAtmService.cashOut(input);
            break;
          case 3:
            System.out.println("Please, enter an amount to top your balance!");
            input = scanner.nextInt();
            currentAtmService.cashIn(input);
            break;
          default:
            System.out.println("Please, enter a number from 1 to 3!");
            break;
        }
      }
    } catch (InputMismatchException ex) {
      System.out.println("Please, enter a number [1-3]!");
    } catch (NoSuchElementException ex) {
      System.out.println(ex.getMessage());
    } finally {
      scanner.next();
      showAtmMenu();
    }
  }
}
