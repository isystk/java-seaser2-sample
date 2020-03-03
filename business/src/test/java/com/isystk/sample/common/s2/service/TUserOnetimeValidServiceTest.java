package com.isystk.sample.common.s2.service;

import javax.annotation.Generated;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;

import com.isystk.sample.common.s2.service.TUserOnetimeValidService;

import static org.junit.Assert.*;

/**
 * {@link TUserOnetimeValidService}のテストクラスです。
 * 
 */
@RunWith(Seasar2.class)
@Generated(value = { "S2JDBC-Gen 2.4.44", "org.seasar.extension.jdbc.gen.internal.model.ServiceTestModelFactoryImpl" })
public class TUserOnetimeValidServiceTest {

	private TUserOnetimeValidService tUserOnetimeValidService;

	/**
	 * {@link #tUserOnetimeValidService}が利用可能であることをテストします。
	 * 
	 * @throws Exception
	 */
	public void testAvailable() throws Exception {
		assertNotNull(tUserOnetimeValidService);
	}
}