package org.atm.database;

import org.atm.model.Atm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class AtmDataAccess extends DataAccess<Atm> {

  public AtmDataAccess() {
    localDatabase = new LinkedList<>();
    getDataFromStorage();
  }

  @Override
  protected boolean getDataFromStorage() {
    try (Scanner scan = new Scanner(new File(DATA_FILE_PATH.toString()))) {
      String atmLine = scan.nextLine();
      Atm atmFromStorage = new Atm(atmLine);
      localDatabase.add(atmFromStorage);
      localDatabase.get(0).setBalance(atmFromStorage.getBalance());
      return true;
    } catch (FileNotFoundException e) {
      System.out.println("Data file was not found");
      return false;
    }
  }

  @Override
  public Atm getObject() {
    Atm atm = null;
    try {
      atm = localDatabase.get(0);
    } catch (RuntimeException ex) {
      System.out.println("ATM was not found in collection/storage");
    }
    return atm;
  }

  @Override
  public boolean updateObject(Atm atm) {
    try {
      List<String> fileContent =
          new LinkedList<>(Files.readAllLines(DATA_FILE_PATH, StandardCharsets.UTF_8));
      atm.setBalance(atm.getBalance());
      fileContent.set(
          0,
          atm.getAtmId()
              + " "
              + atm.getBalance()
              + " "
              + atm.getAtmName()
              + " "
              + atm.getAddress());
      Files.write(DATA_FILE_PATH, fileContent, StandardCharsets.UTF_8);
      return true;
    } catch (IOException e) {
      System.out.println(e.getMessage());
      return false;
    }
  }
}
