package org.atm.database;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

public abstract class DataAccess<T> {
  final Path DATA_FILE_PATH = Paths.get("src/main/resources/data_storage.txt");
  protected LinkedList<T> localDatabase;

  protected boolean getDataFromStorage() {
    return false;
  }

  public T getObject(String value) {
    return null;
  }

  public T getObject() {
    return null;
  }

  public boolean updateObject(T value) {
    return false;
  }
}
