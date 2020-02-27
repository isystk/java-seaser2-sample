/*
 * Copyright 1999-2005 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.log4j;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.log4j.helpers.CountingQuietWriter;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * <code>RollingFileAppender</code> と <code>DailyRollingFileAppender</code> の
 * 機能を組み合わせたクラスです。
 * 
 * <p>
 * ログファイルのサイズが <b>MaxFileSize</b> を超えた時、及び <b>DatePattern</b>
 * で示される日付が切り替わる時にローテーションが行われます。
 * </p>
 * 
 * <p>
 * ローテーションが行われる際、バックアップログファイル数 (<b>File</b> で示されるファイルを除いたログファイル数。例えば sample.log,
 * sample.log.1, sample.log.2 の3つのログファイルがある場合は、バックアップログファイル数は2)が
 * <b>MaxBackupIndex</b> 以上であれば、最も古いログファイルが削除されます。
 * 最も古いログファイルはログファイルのタイムスタンプから判断されます。 そのため、ログファイルのタイムスタンプを不用意に変更しないで下さい。
 * </p>
 * 
 * <p>
 * ログファイル出力ディレクトリにバックアップログファイルが n 個存在する状態で <b>MaxBackupIndex</b> を n
 * より小さく設定しても、<b>MaxBackupIndex</b> が n の
 * 場合と同じ動作をします。これは上記のように削除するログファイルをタイムスタンプで 判断しているからです。<br>
 * ログファイル出力ディレクトリ内のバックアップログファイル数を <b>MaxBackupIndex</b> 以下にすれば、期待通りの動作をします。
 * </p>
 * 
 * <p>
 * 本クラスで追加されたプロパティの種類とデフォルト値は以下の通りです。
 * </p>
 * 
 * <table border="1">
 * <tr>
 * <th><b>プロパティ名</b></th>
 * <th><b>説明</b></th>
 * <th><b>デフォルト値</b></th>
 * </tr>
 * <tr>
 * <td nowrap="true"><b>MaxFileSize</b></td>
 * <td>最大ファイルサイズ</td>
 * <td nowrap="true">java.lang.Long#MAX_VALUE</td>
 * </tr>
 * <tr>
 * <td nowrap="true"><b>MaxBackupIndex</b></td>
 * <td>最大バックアップ数</td>
 * <td nowrap="true">1</td>
 * </tr>
 * <tr>
 * <td nowrap="true"><b>DatePattern</b></td>
 * <td>日付パターン</td>
 * <td nowrap="true">'.'yyyy-MM-dd</td>
 * </tr>
 * </table>
 * 
 * <p>
 * サイズと日付でローテーションさせたい場合は、上記3つのプロパティを設定ファイル等で
 * 設定します。日付でのみローテーションさせたい場合は、<b>MaxFileSize</b> プロパティを
 * 省略して下さい。サイズでのみローテーションする必要がある場合は本クラスの代わりに <code>RollingFileAppender</code>
 * を使用してください。<br>
 * 尚、<b>MaxBackupIndex</b> を0にした場合はローテーションが実行されません。
 * </p>
 * 
 * <h4>ローテーションの例(<b>datePattern</b>は'.'yyyy-MM-dd)</h4>
 * <ul>
 * <li><b>(by size)</b> sample.log → sample.log(new file), sample.log.1
 * <li><b>(by date)</b> sample.log → sample.log(new file), sample.log.2006-07-20
 * <li><b>(by size)</b> sample.log, sample.log.1, sample.log.2006-07-20,
 * sample.log.2006-07-20.1<br>
 * → sample.log(new file), sample.log.1, sample.log.2, sample.log.2006-07-20,
 * sample.log.2006-07-20.1
 * <li><b>(by date)</b> sample.log, sample.log.1, sample.log.2006-07-20,
 * sample.log.2006-07-20.1<br>
 * → sample.log(new file), sample.log.2006-07-21, sample.log.2006-07-21.1,
 * sample.log.2006-07-20, sample.log.2006-07-20.1
 * </ul>
 * 
 * <h4>設定ファイル例</h4>
 * 
 * <pre>
 *   &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot; ?&gt;
 *   &lt;!DOCTYPE log4j:configuration SYSTEM &quot;log4j.dtd&quot;&gt;
 *   
 *   &lt;log4j:configuration xmlns:log4j=&quot;http://jakarta.apache.org/log4j/&quot;&gt;
 *    &lt;appender name=&quot;FILE&quot; class=&quot;org.apache.log4j.CompositeRollingFileAppender&quot;&gt;
 *      &lt;param name=&quot;File&quot;   value=&quot;log/sample.log&quot; /&gt;
 *      &lt;param name=&quot;MaxFileSize&quot; value=&quot;10MB&quot; /&gt;
 *      &lt;param name=&quot;MaxBackupIndex&quot; value=&quot;10&quot; /&gt;    
 *      &lt;param name=&quot;datePattern&quot; value=&quot;'.'yyyy-MM-dd&quot; /&gt; 
 *      
 *      &lt;layout class=&quot;org.apache.log4j.PatternLayout&quot;&gt;
 *        &lt;param name=&quot;ConversionPattern&quot; value=&quot;%x %d %t %-5p %c{2} - %m%n&quot;/&gt;
 *      &lt;/layout&gt;
 *    &lt;/appender&gt;
 *  
 *    &lt;logger name=&quot;log4j.sample&quot;&gt;
 *      &lt;level value=&quot;debug&quot; /&gt;
 *      &lt;appender-ref ref=&quot;FILE&quot; /&gt;
 *    &lt;/logger&gt;
 *   &lt;/log4j:configuration&gt;
 * </pre>
 * 
 * @author Hiroshi Ogawa
 * @author Heinz Richter
 * @author Eirik Lygre
 * @author Ceki G&uuml;lc&uuml;
 */
public class CompositeRollingFileAppender extends FileAppender {

    // The code assumes that the following constants are in a increasing
    // sequence.
    private static final int TOP_OF_TROUBLE = -1;

    private static final int TOP_OF_MINUTE = 0;

    private static final int TOP_OF_HOUR = 1;

    private static final int HALF_DAY = 2;

    private static final int TOP_OF_DAY = 3;

    private static final int TOP_OF_WEEK = 4;

    private static final int TOP_OF_MONTH = 5;

    // The gmtTimeZone is used only in computeCheckPeriod() method.
    private static final TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");

    /** サイズベースのローテーション */
    private static final int BY_SIZE = 0;

    /** 日付ベースのローテーション */
    private static final int BY_DATE = 1;

    /**
     * The default maximum file size is java.lang.Long#MAX_VALUE
     */
    protected long maxFileSize = Long.MAX_VALUE;

    /**
     * There is one backup file by default.
     */
    protected int maxBackupIndex = 1;

    /**
     * The date pattern. By default, the pattern is set to "'.'yyyy-MM-dd"
     * meaning daily rollover.
     */
    protected String datePattern = "'.'yyyy-MM-dd";

    /**
     * The log file will be renamed to the value of the scheduledFilename
     * variable when the next interval is entered. For example, if the rollover
     * period is one hour, the log file will be renamed to the value of
     * "scheduledFilename" at the beginning of the next hour.
     * 
     * The precise time when a rollover occurs depends on logging activity.
     */
    protected String scheduledFilename;

    /**
     * The next time we estimate a rollover should occur.
     */
    protected long nextCheck = System.currentTimeMillis() - 1;

    private Date now = new Date();

    private SimpleDateFormat sdf;

    private RollingCalendar rc = new RollingCalendar();

    /*----------------------------------------------------------------------------*/

    /**
     * The default constructor simply calls its FileAppender#FileAppender
     * parents constructor.
     */
    public CompositeRollingFileAppender() {
        super();
    }

    /**
     * Instantiate a RollingFileAppender and open the file designated by
     * <code>filename</code>. The opened filename will become the ouput
     * destination for this appender.
     * 
     * <p>
     * If the <code>append</code> parameter is true, the file will be appended
     * to. Otherwise, the file desginated by <code>filename</code> will be
     * truncated before being opened.
     */
    public CompositeRollingFileAppender(Layout layout, String filename, boolean append)
            throws IOException {
        super(layout, filename, append);
    }

    /**
     * Instantiate a FileAppender and open the file designated by
     * <code>filename</code>. The opened filename will become the output
     * destination for this appender.
     * 
     * <p>
     * The file will be appended to.
     */
    public CompositeRollingFileAppender(Layout layout, String filename) throws IOException {
        super(layout, filename);
    }

    /**
     * Instantiate a <code>DailyRollingFileAppender</code> and open the file
     * designated by <code>filename</code>. The opened filename will become
     * the ouput destination for this appender.
     * 
     */
    public CompositeRollingFileAppender(Layout layout, String filename, String datePattern)
            throws IOException {
        super(layout, filename, true);
        this.datePattern = datePattern;
        activateOptions();
    }

    public CompositeRollingFileAppender(Layout layout, String filename, String datePattern,
            boolean append) throws IOException {
        super(layout, filename, append);
        this.datePattern = datePattern;
        activateOptions();
    }

    /*----------------------------------------------------------------------------*/

    public synchronized void setFile(String fileName, boolean append, boolean bufferedIO,
            int bufferSize) throws IOException {
        super.setFile(fileName, append, this.bufferedIO, this.bufferSize);
        if (append) {
            File f = new File(fileName);
            ((CountingQuietWriter) qw).setCount(f.length());
        }
    }

    public void activateOptions() {
        super.activateOptions();
        if (datePattern != null && fileName != null) {
            now.setTime(System.currentTimeMillis());
            sdf = new SimpleDateFormat(datePattern);
            int type = computeCheckPeriod();
            printPeriodicity(type);
            rc.setType(type);
            File file = new File(fileName);
            scheduledFilename = fileName + sdf.format(new Date(file.lastModified()));
        } else {
            LogLog.error("Either File or DatePattern options are not set for appender [" + name
                    + "].");
        }
    }

    void printPeriodicity(int type) {
        switch (type) {
        case TOP_OF_MINUTE:
            LogLog.debug("Appender [" + name + "] to be rolled every minute.");
            break;
        case TOP_OF_HOUR:
            LogLog.debug("Appender [" + name + "] to be rolled on top of every hour.");
            break;
        case HALF_DAY:
            LogLog.debug("Appender [" + name + "] to be rolled at midday and midnight.");
            break;
        case TOP_OF_DAY:
            LogLog.debug("Appender [" + name + "] to be rolled at midnight.");
            break;
        case TOP_OF_WEEK:
            LogLog.debug("Appender [" + name + "] to be rolled at start of week.");
            break;
        case TOP_OF_MONTH:
            LogLog.debug("Appender [" + name + "] to be rolled at start of every month.");
            break;
        default:
            LogLog.warn("Unknown periodicity for appender [" + name + "].");
        }
    }

    // This method computes the roll over period by looping over the
    // periods, starting with the shortest, and stopping when the r0 is
    // different from from r1, where r0 is the epoch formatted according
    // the datePattern (supplied by the user) and r1 is the
    // epoch+nextMillis(i) formatted according to datePattern. All date
    // formatting is done in GMT and not local format because the test
    // logic is based on comparisons relative to 1970-01-01 00:00:00
    // GMT (the epoch).

    int computeCheckPeriod() {
        RollingCalendar rollingCalendar = new RollingCalendar(gmtTimeZone, Locale.ENGLISH);
        // set sate to 1970-01-01 00:00:00 GMT
        Date epoch = new Date(0);
        if (datePattern != null) {
            for (int i = TOP_OF_MINUTE; i <= TOP_OF_MONTH; i++) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
                simpleDateFormat.setTimeZone(gmtTimeZone); // do all date
                // formatting in GMT
                String r0 = simpleDateFormat.format(epoch);
                rollingCalendar.setType(i);
                Date next = new Date(rollingCalendar.getNextCheckMillis(epoch));
                String r1 = simpleDateFormat.format(next);
                // System.out.println("Type = "+i+", r0 = "+r0+", r1 = "+r1);
                if (r0 != null && r1 != null && !r0.equals(r1)) {
                    return i;
                }
            }
        }
        return TOP_OF_TROUBLE; // Deliberately head for trouble...
    }

    /**
     * Implements the usual roll over behaviour.
     * 
     * <p>
     * If <code>MaxBackupIndex</code> is positive, then files {<code>File.1</code>,
     * ..., <code>File.MaxBackupIndex -1</code>} are renamed to {<code>File.2</code>,
     * ..., <code>File.MaxBackupIndex</code>}. Moreover, <code>File</code>
     * is renamed <code>File.1</code> and closed. A new <code>File</code> is
     * created to receive further log output.
     * 
     * <p>
     * If <code>MaxBackupIndex</code> is equal to zero, then the
     * <code>File</code> is truncated with no backup files created.
     * 
     */
    public// synchronization not necessary since doAppend is alreasy synched
    void rollOverSize() {
        
        if (qw != null) {
            LogLog.debug("rolling over count=" + ((CountingQuietWriter) qw).getCount());
        }
        LogLog.debug("maxBackupIndex=" + maxBackupIndex);

        if (maxBackupIndex > 0) {
            rotateLogFilesBy(BY_SIZE);
        }

        try {
            // This will also close the file. This is OK since multiple
            // close operations are safe.
            this.setFile(fileName, false, bufferedIO, bufferSize);
        } catch (IOException e) {
            LogLog.error("setFile(" + fileName + ", false) call failed.", e);
        }
    }

    /**
     * Rollover the current file to a new file.
     */
    public void rollOverTime() throws IOException {

        /* Compute filename, but only if datePattern is specified */
        if (datePattern == null) {
            errorHandler.error("Missing DatePattern option in rollOver().");
            return;
        }

        String datedFilename = fileName + sdf.format(now);
        // It is too early to roll over because we are still within the
        // bounds of the current interval. Rollover will occur once the
        // next interval is reached.
        if (scheduledFilename.equals(datedFilename)) {
            return;
        }

        rotateLogFilesBy(BY_DATE);

        try {
            // This will also close the file. This is OK since multiple
            // close operations are safe.
            this.setFile(fileName, false, this.bufferedIO, this.bufferSize);
        } catch (IOException e) {
            errorHandler.error("setFile(" + fileName + ", false) call failed.");
        }
        scheduledFilename = datedFilename;
    }

    /**
     * This method differentiates DailyRollingFileAppender from its super class.
     * 
     * <p>
     * Before actually logging, this method will check whether it is time to do
     * a rollover. If it is, it will schedule the next rollover time and then
     * rollover.
     */
    protected void subAppend(LoggingEvent event) {
	// 日付ベースのローテーションが発生するかどうかチェックし、
	// 発生する場合はローテーションを行う。
        long n = System.currentTimeMillis();
        if (n >= nextCheck) {
            now.setTime(n);
            nextCheck = rc.getNextCheckMillis(now);
            try {
                rollOverTime();
            } catch (IOException ioe) {
                LogLog.error("rollOver() failed.", ioe);
            }
        }

	// サイズベースのローテーションが発生するかどうかチェックし、
	// 発生する場合はローテーションを行う
        if ((fileName != null) && ((CountingQuietWriter) qw).getCount() >= maxFileSize) {
            rollOverSize();
        }

	// 日付ベースのローテーションが発生した場合は、ローテーション後に
	// ログを書き込む必要があるため、ここでsuper.subAppend()を呼び出す
        super.subAppend(event);
    }

    /**
     * 現在のバックアップログファイル数を返す。
     */
    private int getBackupLogFileNum() {
        File[] logs = getAllLogFiles();
        return logs.length - 1;
    }

    private File[] getAllLogFiles() {
        File outputPath = new File(fileName);
        String name = outputPath.getName();
        File logDir = outputPath.getParentFile();
        if (logDir == null) {
            logDir = new File(".");
        }
        return logDir.listFiles(new LogFileNameFilter(name));
    }

    /** 最も古いログファイルを削除する */
    private void deleteOldestFile() {
        File[] logs = getAllLogFiles();
        File oldest = null;
        long maxLastModified = 0;

	// 最も古いログファイルをタイムスタンプから判断する。
	// システム起動中にログファイルを修正しないこと！
        for (int i = 0; i < logs.length; i++) {
            long lastModified = logs[i].lastModified();
            if (oldest == null) {
                oldest = logs[i];
                maxLastModified = lastModified;
            } else {
                if (maxLastModified > lastModified) {
                    oldest = logs[i];
                    maxLastModified = lastModified;
                }
            }
        }

	// 最も古いログファイルを削除する。
        // deleteFile(oldest.getAbsolutePath());
        if (oldest != null && oldest.exists()) {
            oldest.delete();
        }
    }

    /**
     * サイズまたは日付でローテーションを行う
     * 
     * @param mode {@link #BY_SIZE} または {@link #BY_DATE}
     */
    private void rotateLogFilesBy(int mode) {

	// 日付が付けられていないログファイルを検索する
        List notDailyLogs = new ArrayList();
        File[] allLogs = getAllLogFiles();
        for (int i = 0; i < allLogs.length; i++) {
            if (!isDailyRotatedLog(allLogs[i])) {
                notDailyLogs.add(allLogs[i]);
            }
        }
        int notDailyLogNum = notDailyLogs.size();

	// モードに従ってローテーションを行う
        if (mode == BY_SIZE) {
            File file = null;
            File target = null;

	    // 最も古いログファイルを削除する
            if (getBackupLogFileNum() >= maxBackupIndex) {
                deleteOldestFile();
            }

            // Map {(maxBackupIndex - 1), ..., 2, 1} to {maxBackupIndex,
            // ..., 3,
            // 2}
            for (int i = notDailyLogNum - 1; i >= 1; i--) {
                file = new File(fileName + "." + i);
                if (file.exists()) {
                    target = new File(fileName + '.' + (i + 1));
                    LogLog.debug("Renaming file " + file + " to " + target);
                    file.renameTo(target);
                }
            }

            // Rename fileName to fileName.1
            target = new File(fileName + "." + 1);

            this.closeFile(); // keep windows happy.

            file = new File(fileName);
            LogLog.debug("Renaming file " + file + " to " + target);
            file.renameTo(target);
        } else if (mode == BY_DATE) {
            // close current file, and rename it to datedFilename
            this.closeFile();

	    // 最も古いログファイルを削除する
            if (getBackupLogFileNum() >= maxBackupIndex) {
                deleteOldestFile();
            }

	    // ログファイル名に日付を付与する
            for (int i = 1; i <= notDailyLogNum - 1; i++) {
                String from = fileName + '.' + i;
                String to = scheduledFilename + '.' + i;
                renameFile(from, to);
            }

            renameFile(fileName, scheduledFilename);
        } else {
            LogLog.warn("invalid mode:" + mode);
        }
    }

    /**
     * 引数で示されるファイルが日付でローテーションされたファイルかどうか を返す。
     * 
     * @return 日付でローテーションされたならばtrue, そうでなければfalse
     */
    private boolean isDailyRotatedLog(File deletedFile) {
        String deletedName = deletedFile.getName();
        File outputPath = new File(fileName);
        String name = outputPath.getName();

        if (deletedName.equals(name)) {
            return false;
        } else {
	    // hogehoge.log.1やhogehoge.log.2006-07-13からhogehoge.log.を取り除く
            String ext = deletedName.substring(name.length() + 1);
            try {
		// 日付でローテーションされたログファイルならparseIntに失敗する
                Integer.parseInt(ext);
            } catch (NumberFormatException ex) {
                return true;
            }
            return false;
        }
    }

    /**
     * from から to へファイル名を変更する。 to で示されるファイルが既に存在する場合は削除された後に変更される。
     */
    private void renameFile(String from, String to) {
        File toFile = new File(to);
        if (toFile.exists()) {
            toFile.delete();
        }

        File fromFile = new File(from);
        fromFile.renameTo(toFile);
    }

    /*----------------------------------------------------------------------------*/

    /**
     * Returns the value of the <b>MaxBackupIndex</b> option.
     */
    public int getMaxBackupIndex() {
        return maxBackupIndex;
    }

    /**
     * Get the maximum size that the output file is allowed to reach before
     * being rolled over to backup files.
     * 
     * @since 1.1
     */
    public long getMaximumFileSize() {
        return maxFileSize;
    }

    /**
     * Set the maximum number of backup files to keep around.
     * 
     * <p>
     * The <b>MaxBackupIndex</b> option determines how many backup files are
     * kept before the oldest is erased. This option takes a positive integer
     * value. If set to zero, then there will be no backup files and the log
     * file will be truncated when it reaches <code>MaxFileSize</code>.
     */
    public void setMaxBackupIndex(int maxBackups) {
        this.maxBackupIndex = maxBackups;
    }

    /**
     * Set the maximum size that the output file is allowed to reach before
     * being rolled over to backup files.
     * 
     * <p>
     * This method is equivalent to #setMaxFileSize except that it is required
     * for differentiating the setter taking a <code>long</code> argument from
     * the setter taking a <code>String</code> argument by the JavaBeans
     * java.beans.Introspector Introspector.
     * 
     * @see #setMaxFileSize(String)
     */
    public void setMaximumFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    /**
     * Set the maximum size that the output file is allowed to reach before
     * being rolled over to backup files.
     * 
     * <p>
     * In configuration files, the <b>MaxFileSize</b> option takes an long
     * integer in the range 0 - 2^63. You can specify the value with the
     * suffixes "KB", "MB" or "GB" so that the integer is interpreted being
     * expressed respectively in kilobytes, megabytes or gigabytes. For example,
     * the value "10KB" will be interpreted as 10240.
     */
    public void setMaxFileSize(String value) {
        maxFileSize = OptionConverter.toFileSize(value, maxFileSize + 1);
    }

    protected void setQWForFiles(Writer writer) {
        this.qw = new CountingQuietWriter(writer, errorHandler);
    }

    /**
     * The <b>DatePattern</b> takes a string in the same format as expected by
     * {@link SimpleDateFormat}. This options determines the rollover schedule.
     */
    public void setDatePattern(String pattern) {
        datePattern = pattern;
    }

    /** Returns the value of the <b>DatePattern</b> option. */
    public String getDatePattern() {
        return datePattern;
    }

    /*----------------------------------------------------------------------------*/

    static class LogFileNameFilter implements FilenameFilter {
        String fileName;

        LogFileNameFilter(String name) {
            this.fileName = name;
	}

        public boolean accept(File dir, String name) {
            return name.startsWith(fileName);
        }
    }

}
