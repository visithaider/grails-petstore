class RandomString {

    final static Random rd = new Random()

    String getString(int count) {
        getString(count, null)
    }

    String getString(int count, String exclude) {
        if (exclude == null) {
            exclude = ""
        }
        char start = ' '
        char end = 'z'
        char num = end - start
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

    String getStringFromLong() {
        Long.toString(Math.abs(rd.nextLong()), 36)
    }

}