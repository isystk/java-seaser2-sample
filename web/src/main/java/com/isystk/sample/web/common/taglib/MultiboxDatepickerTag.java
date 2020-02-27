package com.isystk.sample.web.common.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.struts.taglib.html.MultiboxTag;

import com.isystk.sample.common.util.DateUtils;
import com.isystk.sample.common.util.HolidayUtil;
import com.isystk.sample.common.util.RokuyouUtil;

public class MultiboxDatepickerTag extends SimpleTagSupport {
    private int year;
    private int month;
    private String property;
    private String styleClass;

    public void setYear(String year) {
	this.year = Integer.valueOf(year);
    }

    public void setMonth(String month) {
	this.month = Integer.valueOf(month);
    }

    public void setProperty(String property) {
	this.property = property;
    }

    public void setStyleClass(String styleClass) {
	this.styleClass = styleClass;
    }

    public void doTag() throws JspException, IOException {
	super.doTag();

	List<CalendarDateModel> calenderDateModelList = createCalendarModel(year, month);

	JspWriter out = this.getJspContext().getOut();

	out.println("<table class=\"calendarSmall multiboxdatepicker\">");
	out.println("<tr>");
	out.println("<th class=\"monthregion\" colspan=\"7\"><input type=\"checkbox\" id=\"multiboxdatepicker"
	    + year
	    + "-"
	    + month
	    + "\" /><label for=\"multiboxdatepicker"
	    + year
	    + "-"
	    + month
	    + "\">"
	    + year
	    + "年 "
	    + month
	    + "月</label></th>");
	out.println("</tr>");
	out.println("<tr class=\"week\">");
	out.println("<td class=\"weekregion mon\"><input class=\"mon\" type=\"checkbox\" /></td>");
	out.println("<td class=\"weekregion tue\"><input class=\"tue\" type=\"checkbox\" /></td>");
	out.println("<td class=\"weekregion wed\"><input class=\"wed\" type=\"checkbox\" /></td>");
	out.println("<td class=\"weekregion thu\"><input class=\"thu\" type=\"checkbox\" /></td>");
	out.println("<td class=\"weekregion fri\"><input class=\"fri\" type=\"checkbox\" /></td>");
	out.println("<td class=\"weekregion sat\"><input class=\"sat\" type=\"checkbox\" /></td>");
	out.println("<td class=\"weekregion sun\"><input class=\"sun\" type=\"checkbox\" /></td>");
	out.println("</tr>");
	out.println("<tr class=\"week\">");
	out.println("<td>月</td>");
	out.println("<td>火</td>");
	out.println("<td>水</td>");
	out.println("<td>木</td>");
	out.println("<td>金</td>");
	out.println("<td class=\"sat\">土</td>");
	out.println("<td class=\"holiday\">日</td>");
	out.println("</tr>");

	for (int i = 0; i < calenderDateModelList.size(); ++i) {
	    CalendarDateModel current = calenderDateModelList.get(i);

	    int lineIndex = i % 7;

	    if (lineIndex == 0) {
		out.println("<tr>");
	    }

	    out.println("<td class=\"" + current.getStyleClass() + " dateregion\" >");
	    String content = "";
	    if (current.dateType == DateType.VOID) {
		out.println("&nbsp;");
	    } else if (current.dateType == DateType.HASCONTENT) {
		if (current.hasPastday) {
		    out.println(current.day);
		} else {
		    String value = String.format("%04d/%02d/%02d", year, month, current.day);

		    MultiboxTag multiboxTag = new MultiboxTag();
		    multiboxTag.setPageContext((PageContext) getJspContext());
		    multiboxTag.setProperty(property);
		    multiboxTag.setValue(value);
		    multiboxTag.setStyleClass(styleClass);
		    multiboxTag.doEndTag(); // これでチェックボックスが出力される

		    out.println("<br />" + current.day);
		}
	    }

	    out.println("</td>");

	    if (lineIndex == 6) {
		out.println("</tr>");
	    }
	}

	out.println("</table>");

    }

    public static List<CalendarDateModel> createCalendarModel(int year, int month) {

	Calendar tempTodayCal = Calendar.getInstance();
	tempTodayCal.setTime(DateUtils.getNow());

	Calendar todayCal = Calendar.getInstance();
	todayCal.clear();
	todayCal.set(tempTodayCal.get(Calendar.YEAR), tempTodayCal.get(Calendar.MONTH), tempTodayCal.get(Calendar.DATE));

	List<CalendarDateModel> result = new ArrayList<CalendarDateModel>();

	Calendar currentCal = Calendar.getInstance();
	currentCal.clear();
	currentCal.set(year, month - 1, 1);

	int startPosition = (currentCal.get(Calendar.DAY_OF_WEEK) + 5) % 7;

	// 前月までの日付を埋める
	for (int i = 0; i < startPosition; ++i) {
	    result.add(new CalendarDateModel(DateType.VOID, 0, 0, false, false, false, ""));
	}

	// 今月の日付を埋める
	for (int i = 0; i < currentCal.getActualMaximum(Calendar.DAY_OF_MONTH); ++i) {
	    int currentDate = i + 1;
	    currentCal.set(Calendar.DATE, currentDate);
	    int currentWeek = currentCal.get(Calendar.DAY_OF_WEEK);

	    boolean isToday = (todayCal.getTime().getTime() == currentCal.getTime().getTime());

	    boolean hasPastday = todayCal.getTime().getTime() > currentCal.getTime().getTime();

	    boolean isHoliday = HolidayUtil.getHolidaySet().contains(currentCal.getTime());

	    String rokuyou = RokuyouUtil.getRokuyou(currentCal.getTime());

	    result.add(new CalendarDateModel(DateType.HASCONTENT, currentDate, currentWeek, isToday, isHoliday, hasPastday, rokuyou));
	}

	currentCal.set(Calendar.DATE, currentCal.getActualMaximum(Calendar.DAY_OF_MONTH));
	int fillDaysCount = ((7 + 1) - currentCal.get(Calendar.DAY_OF_WEEK)) % 7;

	// 残りの日付を埋めておく
	for (int i = 0; i < fillDaysCount; ++i) {
	    result.add(new CalendarDateModel(DateType.VOID, 0, 0, false, false, false, ""));
	}

	return result;
    }

    public static enum DateType {
	VOID, HASCONTENT;
    }

    public static class CalendarDateModel {
	public DateType dateType;
	public int day;
	public int week;
	public boolean isToday;
	public boolean isHoliday;
	public boolean hasPastday;
	public String rokuyou;

	public CalendarDateModel(DateType dateType, int day, int week, boolean isToday, boolean isHoliday, boolean hasPastday, String rokuyou) {
	    this.dateType = dateType;
	    this.day = day;
	    this.week = week;
	    this.isToday = isToday;
	    this.isHoliday = isHoliday;
	    this.hasPastday = hasPastday;
	    this.rokuyou = rokuyou;
	}

	public String getStyleClass() {
	    String result = "";
	    switch (week) {
	    case Calendar.SUNDAY:
		result = "sun";
		break;
	    case Calendar.MONDAY:
		result = "mon";
		break;
	    case Calendar.TUESDAY:
		result = "tue";
		break;
	    case Calendar.WEDNESDAY:
		result = "wed";
		break;
	    case Calendar.THURSDAY:
		result = "thu";
		break;
	    case Calendar.FRIDAY:
		result = "fri";
		break;
	    case Calendar.SATURDAY:
		result = "sat";
		break;
	    default:
		break;
	    }

	    if (week != 0) {
		// 祝日および日曜日をholidayとする
		result = result + (isHoliday || week == Calendar.SUNDAY ? " holiday" : "");
	    }

	    if (week != 0) {
		// 土曜、日曜日以外でかつ祝日でない日をweekdayとする
		result = result + (isHoliday == false && week != Calendar.SATURDAY && week != Calendar.SUNDAY ? " weekday" : "");
	    }

	    return result + (isToday ? " today" : "") + (hasPastday ? " past" : "");
	}
    }

}