package com.isystk.sample.web.common.taglib;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.isystk.sample.web.common.taglib.MultiboxDatepickerTag.CalendarDateModel;
import com.isystk.sample.web.common.taglib.MultiboxDatepickerTag.DateType;

public class CalendarTag extends TagSupport {

    private static final long serialVersionUID = -3165727294564364159L;

    private int year;
    private int month;
    private String dateLabelVar;
    private String dateValueVar;
    private String dateRokuyouVar;
    private String styleClass = "";

    public void setYear(String year) {
	this.year = Integer.valueOf(year);
    }

    public void setMonth(String month) {
	this.month = Integer.valueOf(month);
    }

    public void setDateLabelVar(String dateLabelVar) {
	this.dateLabelVar = dateLabelVar;
    }

    public void setDateValueVar(String dateValueVar) {
	this.dateValueVar = dateValueVar;
    }

    public void setDateRokuyouVar(String dateRokuyouVar) {
	this.dateRokuyouVar = dateRokuyouVar;
    }

    public void setStyleClass(String styleClass) {
	this.styleClass = styleClass;
    }

    private List<CalendarDateModel> nodes;
    private int nodesIndex = 0;

    public int doStartTag() throws JspException {
	JspWriter out = this.pageContext.getOut();

	nodes = MultiboxDatepickerTag.createCalendarModel(year, month);

	try {
	    out.println("<table class=\"" + styleClass + "\">");
	    createTableHeader(out);

	    while (nodes.get(nodesIndex).dateType == DateType.VOID) {
		startTd(out);
		out.println("&nbsp;");
		endTd(out);
	    }

	    startTd(out);

	} catch (IOException e) {
	    // void
	}

	return EVAL_BODY_INCLUDE;
    }

    public int doAfterBody() throws JspException {
	JspWriter out = this.pageContext.getOut();

	try {
	    endTd(out);

	    if (nodesIndex < nodes.size() && nodes.get(nodesIndex).dateType == DateType.HASCONTENT) {
		startTd(out);
		return EVAL_BODY_AGAIN;
	    } else {
		while (nodesIndex < nodes.size()) {
		    startTd(out);
		    out.println("&nbsp;");
		    endTd(out);
		}

		return SKIP_BODY;
	    }

	} catch (IOException e) {
	    throw new RuntimeException(e);
	}

    }

    @Override
    public int doEndTag() throws JspException {
	JspWriter out = this.pageContext.getOut();
	try {
	    out.println("</tbody>");
	    out.println("</table>");
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}

	release();

	return super.doEndTag();
    }

    protected void createTableHeader(JspWriter out) throws IOException {

	out.println("<thead>");
	out.println("<tr>");
	out.println("<th colspan=\"7\">" + year + "年 " + month + "月</th>");
	out.println("</tr>");
	out.println("</thead>");

	out.println("<tbody>");
	out.println("<tr class=\"week\">");
	out.println("<td>月</td>");
	out.println("<td>火</td>");
	out.println("<td>水</td>");
	out.println("<td>木</td>");
	out.println("<td>金</td>");
	out.println("<td class=\"sat\">土</td>");
	out.println("<td class=\"holiday\">日</td>");
	out.println("</tr>");
    }

    private void endTd(JspWriter out) throws IOException {
	out.println("</td>");

	if (nodesIndex % 7 == 6) {
	    out.println("</tr>");
	}

	++nodesIndex;
    }

    private void startTd(JspWriter out) throws IOException {
	CalendarDateModel current = nodes.get(nodesIndex);

	int lineIndex = nodesIndex % 7;

	if (lineIndex == 0) {
	    out.println("<tr>");
	}

	out.println("<td class=\"" + current.getStyleClass() + "\" >");

	if (dateValueVar != null) {
	    String value = String.format("%04d/%02d/%02d", year, month, nodes.get(nodesIndex).day);
	    pageContext.setAttribute(dateValueVar, value);
	}
	if (dateLabelVar != null) {
	    String label = String.valueOf(nodes.get(nodesIndex).day);
	    pageContext.setAttribute(dateLabelVar, label);
	}
	if (dateRokuyouVar != null) {
	    String rokuyou = nodes.get(nodesIndex).rokuyou;
	    pageContext.setAttribute(dateRokuyouVar, rokuyou);
	}
    }

    protected void prepare() throws JspTagException {
    }

    public void release() {
	init();
	super.release();
    }

    private void init() {
	year = 0;
	month = 0;
	nodes = null;
	nodesIndex = 0;
	dateLabelVar = null;
	dateValueVar = null;
	styleClass = "";
    }

}