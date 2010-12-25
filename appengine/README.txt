動かし方を簡単に説明します

* 必要なもの *
Maven2系
  sudo port install mvn とかで入るはず(Mac)
Eclipse GoogleAppEngineプラグイン
  ぐぐれ
Google Chrome
  Command+Option+I でデバッグコンソールとか出てくる
  ResourcesのXHRを見ると、どういうデータを投げるとどういうデータが返ってくるか観察できる

* 動かし方 *
このREADMEと同じ場所で mvn clean eclipse:eclipse を実行
そーするとEclipseからプロジェクトとしてImport出来るようになるので取り込む
プロジェクトを右クリックする→Debug As→Web Application
ブラウザで http://localhost:8888/BugReport.html にアクセス
Androidからのデータの投げ方は適当に分かってください

* AndroidからローカルサーバへPostする *
同一LAN内で適当に叩けばOKです
ただ、Eclipseで動かしているサーバは自分からのアクセスしか受け付けません
AndroidZaurus Android GAE あたりでぐぐると解決方法が載ってます

* Googleのインフラ上で動かす(deploy) *
ぐぐれ
わかめにやれ！って言うとやったりします。RemoteProcedureCallです。(VVakame-RPC)
