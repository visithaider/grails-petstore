class RandomString {

    final static Random rd = new Random()

    static String getString(int count) {
        getString(count, null)
    }

    static String getString(int count, String exclude) {
        exclude = exclude ?: ""
        char start = ' ', end = 'z', num = end - start
        def buf = new StringBuilder()
        while (count-- != 0) {
            char c = (char) rd.nextInt((int) num)
            if (Character.isLetterOrDigit(c) && exclude.indexOf((int) c) < 0) {
                buf.append(c)
            } else {
                count++
            }
        }
        return buf.toString()
    }

    static String getStringFromLong() {
        Long.toString(Math.abs(rd.nextLong()), 36)
    }

}