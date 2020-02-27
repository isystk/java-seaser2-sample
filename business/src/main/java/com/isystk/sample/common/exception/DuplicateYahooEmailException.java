package com.isystk.sample.common.exception;

import com.isystk.sample.common.exception.ApplicationException;

public class DuplicateYahooEmailException extends ApplicationException {
    private static final long serialVersionUID = 1L;

    public DuplicateYahooEmailException() {
	super("重複しています");
    }
    
}
