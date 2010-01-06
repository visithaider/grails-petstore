import static java.lang.Character.*;

class RandomString {

    static final def random = new Random()
    static final def span = valueOf('z' as char) - valueOf(' ' as char)

    static String getString(int count) {
        getString(count, null)
    }

    static String getString(int count, String exclude) {
        def buf = new StringBuilder()
        while (buf.length() < count) {
            def r = random.nextInt(span)
            if (isLetterOrDigit(r) && exclude?.indexOf(r) < 0) {
                buf << (r as char)
            }
        }
        buf.toString()
    }

    static String getStringFromLong() {
        Long.toString(Math.abs(random.nextLong()), 36)
    }

}