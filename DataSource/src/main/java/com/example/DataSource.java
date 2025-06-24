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
   * リソースファイルから株式データを読み込む
   */
  private void loadStockData() {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("stock_data.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

      String line;
      boolean isFirstLine = true;

      while ((line = reader.readLine()) != null) {
        if (isFirstLine && skipHeader) {
          isFirstLine = false;
          continue; // ヘッダー行をスキップ
        }
        stockData.add(line);
        isFirstLine = false;
      }

      if (stockData.isEmpty()) {
        throw new RuntimeException("株式データファイルが空です");
      }

      System.out.println("株式データを読み込みました: " + stockData.size() + "行");

    } catch (IOException e) {
      throw new RuntimeException("株式データファイルの読み込みに失敗しました", e);
    }
  }

  /**
   * 次の株式データ行を取得（無限ループ）
   * 
   * @return 株式データの行
   */
  public String getNextLine() {
    if (stockData.isEmpty()) {
      throw new RuntimeException("株式データが利用できません");
    }

    String line = stockData.get(currentIndex);
    currentIndex = (currentIndex + 1) % stockData.size(); // 無限ループ

    return line;
  }

  /**
   * 現在のインデックスを取得
   * 
   * @return 現在のインデックス
   */
  public int getCurrentIndex() {
    return currentIndex;
  }

  /**
   * データの総行数を取得
   * 
   * @return データの総行数
   */
  public int getTotalLines() {
    return stockData.size();
  }
}
