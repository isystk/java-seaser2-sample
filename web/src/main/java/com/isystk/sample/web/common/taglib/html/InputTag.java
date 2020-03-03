package com.isystk.sample.web.common.taglib.html;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.BaseFieldTag;

/**
 * Custom tag for input fields of type "date".
 * 
 */

public class InputTag extends BaseFieldTag {

	private static final long serialVersionUID = 954084234766181508L;

	protected String type = null;

	protected String placeholder = null;

	protected String dataOptions = null;

	protected String dataRole = null;

	protected String pattern = null;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public String getDataOptions() {
		return dataOptions;
	}

	public void setDataOptions(String dataOptions) {
		this.dataOptions = dataOptions;
	}

	public String getDataRole() {
		return dataRole;
	}

	public void setDataRole(String dataRole) {
		this.dataRole = dataRole;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	/**
	 * Construct a new instance of this tag.
	 */
	public InputTag() {
		super();
		doReadonly = true;
	}

	@Override
	protected String renderInputElement() throws JspException {
		StringBuffer results = new StringBuffer("<input");

		prepareAttribute(results, "type", getType());
		prepareAttribute(results, "name", prepareName());
		prepareAttribute(results, "accesskey", getAccesskey());
		prepareAttribute(results, "accept", getAccept());
		prepareAttribute(results, "maxlength", getMaxlength());
		prepareAttribute(results, "size", getCols());
		prepareAttribute(results, "tabindex", getTabindex());
		prepareAttribute(results, "placeholder", getPlaceholder());
		prepareAttribute(results, "data-role", getDataRole());
		prepareAttribute(results, "pattern", getPattern());
		prepareAttributeApostrophe(results, "data-options", getDataOptions());
		prepareValue(results);
		results.append(this.prepareEventHandlers());
		results.append(this.prepareStyles());
		prepareOtherAttributes(results);
		results.append(this.getElementClose());
		return results.toString();
	}

	protected void prepareAttributeApostrophe(StringBuffer handlers, String name, Object value) {
		if (value != null) {
			handlers.append(" ");
			handlers.append(name);
			handlers.append("='");
			handlers.append(value);
			handlers.append("'");
		}
	}

}
