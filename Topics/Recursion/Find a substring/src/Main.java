class Util {
    public static int indexOf(String src, String tgt) {

        if (src.equals("")) {
            return -1;
        } else if (src.startsWith(tgt)) {
            return 0;
        } else {
            int subIndex = indexOf(src.substring(1), tgt);
            return subIndex == -1 ? -1 : subIndex + 1;
        }

    }

    public static void main(String[] args) {
        int result = indexOf("Javadr", "J");
        System.out.println(result);
    }
}