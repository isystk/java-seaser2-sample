package com.isystk.sample.common.constants.key;

import java.io.Serializable;

/**
 * クライアント管理者コードを示す
 * @author nkawamata
 */
public class ClientLoginId implements Serializable, Comparable<ClientLoginId> {

    /**
     * <code>serialVersionUID</code> のコメント
     */
    private static final long serialVersionUID = -2147338046688567736L;

    /**
     * @param clientAccountId クライアント管理者ID
     * @return UserCdオブジェクト
     */
    public static ClientLoginId valueOf(Integer clientAccountId) {

        if (clientAccountId == null) {
            throw new NullPointerException();
        } else if (clientAccountId <= 0) {
            throw new IllegalArgumentException();
        }

        String code = String.format("%010d", clientAccountId);
        char[] chars = new char[12];
        chars[0] = code.charAt(0);
        chars[1] = code.charAt(3);
        chars[2] = code.charAt(6);
        chars[3] = code.charAt(2);
        chars[4] = code.charAt(6);
        chars[5] = code.charAt(4);
        chars[6] = code.charAt(1);
        chars[7] = code.charAt(5);
        chars[8] = code.charAt(8);
        chars[9] = code.charAt(3);
        chars[10] = code.charAt(9);
        chars[11] = code.charAt(7);

        String s1 = RandomSeed.getSeed(Integer.valueOf(new String(chars, 0, 4)));
        chars[0] = s1.charAt(0);
        chars[1] = s1.charAt(1);
        chars[2] = s1.charAt(2);
        chars[3] = s1.charAt(3);

        String s2 = RandomSeed.getSeed(Integer.valueOf(new String(chars, 4, 4)));
        chars[4] = s2.charAt(0);
        chars[5] = s2.charAt(1);
        chars[6] = s2.charAt(2);
        chars[7] = s2.charAt(3);

        String s3 = RandomSeed.getSeed(Integer.valueOf(new String(chars, 8, 4)));
        chars[8] = s3.charAt(0);
        chars[9] = s3.charAt(1);
        chars[10] = s3.charAt(2);
        chars[11] = s3.charAt(3);

        return new ClientLoginId(new String(chars));
    }

    /**
     * @param clientAccountCd クライアント管理者コード
     * @return UserCdオブジェクト
     */
    public static ClientLoginId valueOf(String clientAccountCd) {
        if (clientAccountCd == null) {
            return null;
        }
        return new ClientLoginId(clientAccountCd);
    }

    /** クライアント管理者コード */
    private final String clientAccountCd;

    /**
     * @param clientAccountCd クライアント管理者コード
     */
    public ClientLoginId(String clientAccountCd) {
        super();
        if (clientAccountCd == null) {
            throw new NullPointerException();
        } else if (clientAccountCd.length() != 12) {
            throw new IllegalArgumentException();
        }
        this.clientAccountCd = clientAccountCd;
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#clone()
     * @author nkawamata
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new ClientLoginId(clientAccountCd);
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     * @author nkawamata
     */
    public int compareTo(ClientLoginId anotherString) {
        return clientAccountCd.compareTo(anotherString.clientAccountCd);
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#equals(java.lang.Object)
     * @author nkawamata
     */
    @Override
    public boolean equals(Object anObject) {
        return clientAccountCd.equals(anObject);
    }

    /**
     * clientAccountCdを取得する.
     * @return clientAccountCd を返します.
     */
    public String getClientAccountCd() {
        return clientAccountCd;
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#hashCode()
     * @author nkawamata
     */
    @Override
    public int hashCode() {
        return clientAccountCd.hashCode();
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#toString()
     * @author nkawamata
     */
    @Override
    public String toString() {
        return clientAccountCd.toString();
    }

}
