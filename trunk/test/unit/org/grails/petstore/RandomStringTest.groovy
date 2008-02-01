package org.grails.petstore

class RandomStringTest extends GroovyTestCase {

    void testGetString() {
        int len = (System.currentTimeMillis() % 32) + 1

        def rs = new RandomString()
        def s1 = rs.getString(len)
        assert len == s1.length()

        def s2 = rs.getString(len)
        assert !s1.equals(s2)

        def s3 = rs.getString(len, "abcdefghijklmno")
        assert s3 =~ "[P-Z0-9]"  
    }

    void testGetStringFromLong() {
        def rs = new RandomString()
        def s = rs.getStringFromLong()
        assert s != null
        assert s.length() > 0
    }

}