java-seaser2-sample
====

## Description

Seaser2を利用したサンプルです。

### ディレクトリ構造
```
.
├── batch (バッチ)
├── business (共通ロジック)
├── web (Web共通ロジック)
├── web-solr (Solr)
└── front (フロントAPP)
```

## Demo

## VS. 

## Requirement

## Usage

### DockerWindows(WSL)を利用している場合は以下の設定が必要です。
$ vi ~/.bashrc
``` 
export DOCKER_HOST=tcp://localhost:2375
```

$ sudo vi /etc/wsl.conf
``` 
[automount]
root = /
options = "metadata"
```

### Eclipseは以下のURLから、「利用しているOSの Java-FullEdition」をダウンロードしてtoolsディレクトリ以下に配置してください。
https://mergedoc.osdn.jp/

### 使い方
$ dc.sh -h
```
Usage:  dc.sh [command] [<options>]

Options:
  stats|st                 Dockerコンテナの状態を表示します。
  init                     Dockerコンテナ・イメージ・生成ファイルの状態を初期化します。
  start                    すべてのDaemonを起動します。
  stop                     すべてのDaemonを停止します。
  mysql login              MySQLデータベースにログインします。
  --version, -v     バージョンを表示します。
  --help, -h        ヘルプを表示します。
```

## Install

```
./dc.sh start
open http://localhost/
```

## Contribution

## Licence

[MIT](https://github.com/isystk/docker-java/LICENCE)

## Author

[isystk](https://github.com/isystk)


