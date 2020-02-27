package com.isystk.sample.common.s2.service;

import javax.annotation.Generated;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;

import com.isystk.sample.common.s2.service.TUserOnetimePassService;

import static org.junit.Assert.*;

/**
 * {@link TUserOnetimePassService}のテストクラスです。
 * 
 */
@RunWith(Seasar2.class)
@Generated(value = {"S2JDBC-Gen 2.4.44", "org.seasar.extension.jdbc.gen.internal.model.ServiceTestModelFactoryImpl"})
public class TUserOnetimePassServiceTest {

    private TUserOnetimePassService tUserOnetimePassService;

    /**
     * {@link #tUserOnetimePassService}が利用可能であることをテストします。
     * 
     * @throws Exception
     */
    public void testAvailable() throws Exception {
        assertNotNull(tUserOnetimePassService);
    }
}