package com.isystk.sample.web.common.deploy;

public class Profiler {

	private final String strTitle;
	private final long lStart = System.nanoTime();

	public Profiler(String strTitle) {
		this.strTitle = strTitle;
	}

	public String start(String strDescription) {
		return String.format("### %s %s start...", this.strTitle, strDescription);
	}

	public String end() {
		return String.format("### %s end. (%f[s])", this.strTitle, (System.nanoTime() - this.lStart) / 1000000000.0);
	}
}
