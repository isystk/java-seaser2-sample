package com.isystk.sample.common.s2.solr;

/**
 * @author iseyoshitaka
 */
public class RuntimeSolrServerException extends RuntimeException {

	private static final long serialVersionUID = 9179241536269449473L;

	/**
	 * Default contructor.
	 */
	public RuntimeSolrServerException() {
		super();
	}

	/**
	 * Contructor.
	 * 
	 * @param message Error message.
	 * @param cause cause.
	 */
	public RuntimeSolrServerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Contructor.
	 * 
	 * @param message Error message.
	 */
	public RuntimeSolrServerException(String message) {
		super(message);
	}

	/**
	 * Contructor.
	 * 
	 * @param cause cause.
	 */
	public RuntimeSolrServerException(Throwable cause) {
		super(cause);
	}
}
