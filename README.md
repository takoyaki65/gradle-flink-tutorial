# Gradle Tutorial

## 0. 前提条件

- Linux or WSL on Windows
- Ubuntu 24.04 LTS
- bash shell

## 1. 環境構築 (Linux, WSL on Windows)
### sdkmanのインストール
sdkmanというSoftware Development Kit Managerを用いる。その前に、インストールする
ために必要なcurl,zip,unzipをインストールする。
```bash
sudo apt update
sudo apt install curl zip unzip
```
[公式ドキュメント](https://sdkman.io/install/)を参照し、以下のコマンドを実行し、sdkmanをインストールする。
```bash
curl -s "https://get.sdkman.io" | bash
```
インストールが完了したら、シェルを開きなおし、以下のコマンドを実行してsdkコマンドが使える確認する。
```bash
sdk version

# SDKMAN!
# script: 5.19.0
# native: 0.7.4 (linux x86_64)

```
### Javaのインストール
以下のコマンドを実行することで、インストール可能なJavaのバージョンを確認する。
```bash
sdk list java
# ================================================================================
# Available Java Versions for Linux 64bit
# ================================================================================
#  Vendor        | Use | Version      | Dist    | Status     | Identifier
# --------------------------------------------------------------------------------
#  Corretto      |     | 24.0.1       | amzn    |            | 24.0.1-amzn         
#                |     | 24           | amzn    |            | 24-amzn             
#                |     | 23.0.2       | amzn    |            | 23.0.2-amzn         
#                |     | 21.0.7       | amzn    |            | 21.0.7-amzn         
#                |     | 21.0.6       | amzn    |            | 21.0.6-amzn         
#                |     | 17.0.15      | amzn    |            | 17.0.15-amzn        
#                |     | 17.0.14      | amzn    |            | 17.0.14-amzn        
#                |     | 11.0.27      | amzn    |            | 11.0.27-amzn        
#                |     | 11.0.26      | amzn    |            | 11.0.26-amzn        
#                |     | 8.0.452      | amzn    |            | 8.0.452-amzn        
#                |     | 8.0.442      | amzn    |            | 8.0.442-amzn        
#  Dragonwell    |     | 21.0.7       | albba   |            | 21.0.7-albba        
#                |     | 21.0.6       | albba   |            | 21.0.6-albba        
#                |     | 17.0.15      | albba   |            | 17.0.15-albba    
# ......   
```
今回は、`17.0.15-amzn`をインストールする。
```bash
sdk install java 17.0.15-amzn

# Downloading: java 17.0.15-amzn

# In progress...

# ######################################################################################################################################################################################################################################## 100.0%

# Repackaging Java 17.0.15-amzn...

# Done repackaging...

# Installing: java 17.0.15-amzn
# Done installing!


# Setting java 17.0.15-amzn as default.
```
Javaがインストールされたことを以下のコマンドで確認する。
```bash
# バージョン確認
java --version
# openjdk 17.0.15 2025-04-15 LTS
# OpenJDK Runtime Environment Corretto-17.0.15.6.1 (build 17.0.15+6-LTS)
# OpenJDK 64-Bit Server VM Corretto-17.0.15.6.1 (build 17.0.15+6-LTS, mixed mode, sharing)

# インストール先のパスの確認 (環境によって出力は異なる)
which java
# /root/.sdkman/candidates/java/current/bin/java
```
## 2. プロジェクトの作成


## 3. マルチプロジェクトの設定方法

## 4. ビルドと実行
