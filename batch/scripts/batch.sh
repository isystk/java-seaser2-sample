#!/bin/sh

test -r /etc/profile.d/env.sh && source /etc/profile.d/env.sh
export LANG="ja_JP.UTF=8"
umask 002

source $(dirname $0)/setenv.sh

usage(){
  echo 'Usage: batch.sh [100...900] [target_date(YYYYMMDD)]' 1>&2
  echo '' 1>&2
  echo '  100: Bat100Job' 1>&2
  echo '  101: Bat101Job' 1>&2
  echo '  102: Bat102Job' 1>&2
  echo '  104: Bat104Job' 1>&2
  echo '  105: Bat105Job' 1>&2
  echo '  106: Bat106Job' 1>&2
  echo '  107: Bat107Job' 1>&2
  echo '  108: Bat108Job' 1>&2
  echo '  109: Bat109Job' 1>&2
  echo '  110: Bat110Job' 1>&2
  echo '  111: Bat111Job' 1>&2
  echo '  112: Bat112Job' 1>&2
  echo '  113: Bat113Job' 1>&2
  echo '  114: Bat114Job' 1>&2
  echo '  120: Bat120Job' 1>&2
  echo '  122: Bat122Job' 1>&2
  echo '  200: Bat200Job' 1>&2
  echo '  201: Bat201Job' 1>&2
  echo '  202: Bat202Job' 1>&2
  echo '  203: Bat203Job' 1>&2
  echo '  204: Bat204Job' 1>&2
  echo '  205: Bat205Job' 1>&2
  echo '  206: Bat206Job' 1>&2
  echo '  207: Bat207Job' 1>&2
  echo '  210: Bat210Job' 1>&2
  echo '  211: Bat211Job' 1>&2
  echo '  212: Bat212Job' 1>&2
  echo '  213: Bat213Job' 1>&2
  echo '  301: Bat301Job' 1>&2
  echo '  302: Bat302Job' 1>&2
  echo '  303: Bat303Job' 1>&2
  echo '  304: Bat304Job' 1>&2
  echo '  305: Bat305Job' 1>&2
  echo '  306: Bat306Job' 1>&2
  echo '  307: Bat307Job' 1>&2
  echo '  308: Bat308Job' 1>&2
  echo '  400: Bat400Job' 1>&2
  echo '  401: Bat401Job' 1>&2
  echo '  410: Bat410Job' 1>&2
  echo '  411: Bat411Job' 1>&2
  echo '  412: Bat412Job' 1>&2
  echo '  413: Bat413Job' 1>&2
  echo '  501: Bat501Job' 1>&2
  echo '  502: Bat502Job' 1>&2
  echo '  503: Bat503Job' 1>&2
  echo '  504: Bat504Job' 1>&2
  echo '  601: Bat601Job' 1>&2
  echo '  602: Bat602Job' 1>&2
  echo '  603: Bat603Job' 1>&2
  echo '  711: Bat711Job' 1>&2
  echo '  712: Bat712Job' 1>&2
  echo '  713: Bat713Job' 1>&2
  echo '  714: Bat714Job' 1>&2
  echo '  715: Bat715Job' 1>&2
  echo '  716: Bat716Job' 1>&2
  echo '  801: Bat801Job' 1>&2
  echo '' 1>&2
}

if [ $# -gt 5 ]; then
  usage
  exit 1
fi

case $1 in
  100)
    BATCH_NAME='Bat100Job' ;;
  101)
    BATCH_NAME='Bat101Job' ;;
  102)
    BATCH_NAME='Bat102Job' ;;
  104)
    BATCH_NAME='Bat104Job' ;;
  105)
    BATCH_NAME='Bat105Job' ;;
  106)
    BATCH_NAME='Bat106Job' ;;
  107)
    BATCH_NAME='Bat107Job' ;;
  108)
    BATCH_NAME='Bat108Job' ;;
  109)
    BATCH_NAME='Bat109Job' ;;
  110)
    BATCH_NAME='Bat110Job' ;;
  111)
    BATCH_NAME='Bat111Job' ;;
  112)
    BATCH_NAME='Bat112Job' ;;
  113)
    BATCH_NAME='Bat113Job' ;;
  114)
    BATCH_NAME='Bat114Job' ;;
  120)
    BATCH_NAME='Bat120Job' ;;
  122)
    BATCH_NAME='Bat122Job' ;;
  200)
    BATCH_NAME='Bat200Job' ;;
  201)
    BATCH_NAME='Bat201Job' ;;
  202)
    BATCH_NAME='Bat202Job' ;;
  203)
    BATCH_NAME='Bat203Job' ;;
  204)
    BATCH_NAME='Bat204Job' ;;
  205)
    BATCH_NAME='Bat205Job' ;;
  206)
    BATCH_NAME='Bat206Job' ;;
  207)
    BATCH_NAME='Bat207Job' ;;
  210)
    BATCH_NAME='Bat210Job' ;;
  211)
    BATCH_NAME='Bat211Job' ;;
  212)
    BATCH_NAME='Bat212Job' ;;
  213)
    BATCH_NAME='Bat213Job' ;;
  301)
    BATCH_NAME='Bat301Job' ;;
  302)
    BATCH_NAME='Bat302Job' ;;
  303)
    BATCH_NAME='Bat303Job' ;;
  304)
    BATCH_NAME='Bat304Job' ;;
  305)
    BATCH_NAME='Bat305Job' ;;
  306)
    BATCH_NAME='Bat306Job' ;;
  307)
    BATCH_NAME='Bat307Job' ;;
  308)
    BATCH_NAME='Bat308Job' ;;
  400)
    BATCH_NAME='Bat400Job' ;;
  401)
    BATCH_NAME='Bat401Job' ;;
  407)
    BATCH_NAME='Bat407Job' ;;
  410)
    BATCH_NAME='Bat410Job' ;;
  411)
    BATCH_NAME='Bat411Job' ;;
  412)
    BATCH_NAME='Bat412Job' ;;
  413)
    BATCH_NAME='Bat413Job' ;;
  501)
  	BATCH_NAME='Bat501Job' ;;
  502)
  	BATCH_NAME='Bat502Job' ;;
  503)
  	BATCH_NAME='Bat503Job' ;;
  504)
  	BATCH_NAME='Bat504Job' ;;
  601)
  	BATCH_NAME='Bat601Job' ;;
  602)
  	BATCH_NAME='Bat602Job' ;;
  603)
  	BATCH_NAME='Bat603Job' ;;
  711)
  	BATCH_NAME='Bat711Job' ;;
  712)
  	BATCH_NAME='Bat712Job' ;;
  713)
  	BATCH_NAME='Bat713Job' ;;
  714)
  	BATCH_NAME='Bat714Job' ;;
  715)
  	BATCH_NAME='Bat715Job' ;;
  716)
  	BATCH_NAME='Bat716Job' ;;
  801)
  	BATCH_NAME='Bat801Job' ;;
  *)
    usage
    exit 1
    ;;
esac

if [ -n "$BATCH_NAME" ]; then
  java $JAVA_OPTS -classpath $CLASSPATH com.isystk.sample.batch.job.$BATCH_NAME $2 $3 $4 $5
fi
exit 0
