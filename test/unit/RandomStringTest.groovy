class RandomStringTest extends GroovyTestCase {

    void testGetString() {
        int len = (System.currentTimeMillis() % 32) + 1

        def s1 = RandomString.getString(len)
        assert len == s1.length()

        def s2 = RandomString.getString(len)
        assert !s1.equals(s2)

        def s3 = RandomString.getString(len, "abcdefghijklmno")
        assert s3 =~ "[P-Z0-9]"  
    }

    void testGetStringFromLong() {
        def s = RandomString.getStringFromLong()
        assert s != null
        assert s.length() > 0
    }

}