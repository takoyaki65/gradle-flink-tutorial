package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataSource {
  private List<String> stockData;
  private int currentIndex;
  private boolean skipHeader;

  public DataSource() {
    this.stockData = new ArrayList<>();
    this.currentIndex = 0;
    this.skipHeader = true;
    loadStockData();
  }

  /**
   * read stock data from resource file
   */
  private void loadStockData() {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("stock_data.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

      String line;
      boolean isFirstLine = true;

      while ((line = reader.readLine()) != null) {
        if (isFirstLine && skipHeader) {
          isFirstLine = false;
          continue; // skip header line
        }
        stockData.add(line);
        isFirstLine = false;
      }

      if (stockData.isEmpty()) {
        throw new RuntimeException("stock data file is empty");
      }

      System.out.println("stock data loaded: " + stockData.size() + " lines");

    } catch (IOException e) {
      throw new RuntimeException("failed to read stock data file", e);
    }
  }

  /**
   * get next stock data line (infinite loop)
   * 
   * @return stock data line
   */
  public String getNextLine() {
    if (stockData.isEmpty()) {
      throw new RuntimeException("stock data is not available");
    }

    String line = stockData.get(currentIndex);
    currentIndex = (currentIndex + 1) % stockData.size(); // infinite loop

    return line;
  }

  /**
   * get current index
   * 
   * @return current index
   */
  public int getCurrentIndex() {
    return currentIndex;
  }

  /**
   * get total lines of data
   * 
   * @return total lines of data
   */
  public int getTotalLines() {
    return stockData.size();
  }
}
