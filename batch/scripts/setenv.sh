#!/bin/sh

JAVA_OPTS="$JAVA_OPTS -Djava.util.Arrays.useLegacyMergeSort=true -server -Xmx4096m -Xms512m -verbose:gc -Xloggc:/var/log/app/batch/gc/gc.log.$(date +%Y%m%d%H%M) -XX:+PrintGCTimeStamps -XX:-PrintGCDetails -XX:+PrintClassHistogram"

LIB_HOME=/var/app/version/current/batch/bin
CLASSPATH=/var/app/version/current/batch/bin
for f in `ls $LIB_HOME`; do
  CLASSPATH=$CLASSPATH:$LIB_HOME/$f
done
