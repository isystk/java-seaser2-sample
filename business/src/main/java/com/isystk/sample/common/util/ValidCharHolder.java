/**
 * Copyright(c) team-lab</br>
 */
package com.isystk.sample.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用可能文字を保持するクラス.<br>
 * 
 * @author nkawamata
 */
public final class ValidCharHolder implements Set<Character> {

    /** logger */
    private Logger logger = LoggerFactory.getLogger(getClass());

    /** 唯一のインスタンス */
    private static final ValidCharHolder INSTANCE = new ValidCharHolder();

    /**
     * インスタンスを取得します.
     * 
     * @return
     */
    public static ValidCharHolder getInstance() {
	return INSTANCE;
    }

    /** */
    private final Set<Character> validchars;

    /**
     * デフォルトコンストラクタ
     */
    private ValidCharHolder() {
	Set<Character> validchars = new HashSet<Character>();
	BufferedReader reader = null;
	try {
	    InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("validator/validchars.dat");
	    if (in != null) {
		reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		while (reader.ready()) {
		    String line = reader.readLine();
		    if (line == null) {
			continue;
		    }
		    for (int i = 0; i < line.length(); i++) {
			validchars.add(Character.valueOf(line.charAt(i)));
		    }
		}
	    }
	} catch (IOException e) {
	    validchars.clear();
	    logger.error(e.getMessage(), e);
	} finally {
	    if (reader != null) {
		try {
		    reader.close();
		} catch (IOException e) {
		    logger.warn(e.getMessage(), e);
		} finally {
		    reader = null;
		}
	    }
	}
	this.validchars = Collections.unmodifiableSet(validchars);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Set#add(java.lang.Object)
     */
    public boolean add(Character o) {
	return validchars.add(o);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Set#addAll(java.util.Collection)
     */
    public boolean addAll(Collection<? extends Character> c) {
	return validchars.addAll(c);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Set#clear()
     */
    public void clear() {
	validchars.clear();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Set#contains(java.lang.Object)
     */
    public boolean contains(Object o) {
	return validchars.contains(o);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Set#containsAll(java.util.Collection)
     */
    public boolean containsAll(Collection<?> c) {
	return validchars.containsAll(c);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Set#isEmpty()
     */
    public boolean isEmpty() {
	return validchars.isEmpty();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Set#iterator()
     */
    public Iterator<Character> iterator() {
	return validchars.iterator();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Set#remove(java.lang.Object)
     */
    public boolean remove(Object o) {
	return validchars.remove(o);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Set#removeAll(java.util.Collection)
     */
    public boolean removeAll(Collection<?> c) {
	return validchars.removeAll(c);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Set#retainAll(java.util.Collection)
     */
    public boolean retainAll(Collection<?> c) {
	return validchars.retainAll(c);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Set#size()
     */
    public int size() {
	return validchars.size();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Set#toArray()
     */
    public Object[] toArray() {
	return validchars.toArray();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Set#toArray(T[])
     */
    public <T> T[] toArray(T[] a) {
	return validchars.toArray(a);
    }

}
