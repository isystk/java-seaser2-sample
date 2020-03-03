package com.isystk.sample.common.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * 先輩カップルタグNGワード管理
 * 
 * @author iseyoshitaka
 */
public final class PeopleTagNgWord {

	private static final Logger LOGGER = LoggerFactory.getLogger(PeopleTagNgWord.class);

	private static List<String> peopleTagNgWordList;

	static {

		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("people-tag-ng-word.txt");

		peopleTagNgWordList = Lists.newArrayList();
		if (in == null) {
			LOGGER.warn("not exists `classpath:people-tag-ng-word.txt`");
		} else {
			try {

				BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
				String line;
				while ((line = br.readLine()) != null) {
					peopleTagNgWordList.add(line);
				}

			} catch (IOException e) {
				LOGGER.error("read failed `classpath:people-tag-ng-word.txt`", e);
			}
		}
	}

	public static List<String> getAllList() {
		return peopleTagNgWordList;
	}

}
