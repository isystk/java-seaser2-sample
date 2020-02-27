#!/bin/sh

test -r /etc/profile.d/env.sh && source /etc/profile.d/env.sh
export LANG="ja_JP.UTF=8"
umask 002

source $(dirname $0)/setenv.sh

usage(){
  echo 'Usage: batch.sh [100...XXX] [target_date(YYYYMMDD)]' 1>&2
  echo '' 1>&2
  echo '  100: Bat100Job' 1>&2
  echo '' 1>&2
}

if [ $# -gt 5 ]; then
  usage
  exit 1
fi

case $1 in
  100)
    BATCH_NAME='Bat100Job' ;;
  *)
    usage
    exit 1
    ;;
esac

if [ -n "$BATCH_NAME" ]; then
  java $JAVA_OPTS -classpath $CLASSPATH com.isystk.sample.batch.job.$BATCH_NAME $2 $3 $4 $5
fi
exit 0
