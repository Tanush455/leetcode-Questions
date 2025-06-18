class Solution {
    public int maxDiff(int num) {
        String s = String.valueOf(num);

        char[] maxChars = s.toCharArray();
        char digitToReplaceForMax = ' ';
        for (char c : maxChars) {
            if (c != '9') {
                digitToReplaceForMax = c;
                break;
            }
        }
        if (digitToReplaceForMax != ' ') {
            for (int i = 0; i < maxChars.length; i++) {
                if (maxChars[i] == digitToReplaceForMax) {
                    maxChars[i] = '9';
                }
            }
        }
        int maxNum = Integer.parseInt(new String(maxChars));

        char[] minChars = s.toCharArray();
        char digitToReplaceForMin = ' ';

        if (minChars[0] != '1') {
            digitToReplaceForMin = minChars[0];
            for (int i = 0; i < minChars.length; i++) {
                if (minChars[i] == digitToReplaceForMin) {
                    minChars[i] = '1';
                }
            }
        } else {
            for (int i = 0; i < minChars.length; i++) {
                if (minChars[i] != '0' && minChars[i] != '1') {
                    digitToReplaceForMin = minChars[i];
                    break;
                }
            }
            if (digitToReplaceForMin != ' ') {
                for (int i = 0; i < minChars.length; i++) {
                    if (minChars[i] == digitToReplaceForMin) {
                        minChars[i] = '0';
                    }
                }
            }
        }
        int minNum = Integer.parseInt(new String(minChars));

        return maxNum - minNum;
    }
}