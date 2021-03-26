package com.zzh.lib.core.lang;

/**
 * Created by ZZH on 3/24/21.
 *
 * @Date: 3/24/21
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description:
 */
public class CharSequenceUtils {
    private static final int NOT_FOUND = -1;


    static int indexOf(final CharSequence cs, final CharSequence searchChar, final int start) {
        if (cs instanceof String) {
            return ((String) cs).indexOf(searchChar.toString(), start);
        } else if (cs instanceof StringBuilder) {
            return ((StringBuilder) cs).indexOf(searchChar.toString(), start);
        } else if (cs instanceof StringBuffer) {
            return ((StringBuffer) cs).indexOf(searchChar.toString(), start);
        }
        return cs.toString().indexOf(searchChar.toString(), start);
    }

    static int indexOf(final CharSequence cs, final int searchChar, int start) {
        if (cs instanceof String) {
            return ((String) cs).indexOf(searchChar, start);
        }
        final int sz = cs.length();
        if (start < 0) {
            start = 0;
        }
        if (searchChar < Character.MIN_SUPPLEMENTARY_CODE_POINT) {
            for (int i = start; i < sz; i++) {
                if (cs.charAt(i) == searchChar) {
                    return i;
                }
            }
            return NOT_FOUND;
        }
        //supplementary characters (LANG1300)
        if (searchChar <= Character.MAX_CODE_POINT) {
            final char[] chars = Character.toChars(searchChar);
            for (int i = start; i < sz - 1; i++) {
                final char high = cs.charAt(i);
                final char low = cs.charAt(i + 1);
                if (high == chars[0] && low == chars[1]) {
                    return i;
                }
            }
        }
        return NOT_FOUND;
    }

    public static char[] toCharArray(final CharSequence source) {
        final int len = HStrUtils.length(source);
        if (len == 0) {
            return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        if (source instanceof String) {
            return ((String) source).toCharArray();
        }
        final char[] array = new char[len];
        for (int i = 0; i < len; i++) {
            array[i] = source.charAt(i);
        }
        return array;
    }

    static boolean regionMatches(final CharSequence cs, final boolean ignoreCase, final int thisStart,
                                 final CharSequence substring, final int start, final int length)    {
        if (cs instanceof String && substring instanceof String) {
            return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
        }
        int index1 = thisStart;
        int index2 = start;
        int tmpLen = length;

        // Extract these first so we detect NPEs the same as the java.lang.String version
        final int srcLen = cs.length() - thisStart;
        final int otherLen = substring.length() - start;

        // Check for invalid parameters
        if (thisStart < 0 || start < 0 || length < 0) {
            return false;
        }

        // Check that the regions are long enough
        if (srcLen < length || otherLen < length) {
            return false;
        }

        while (tmpLen-- > 0) {
            final char c1 = cs.charAt(index1++);
            final char c2 = substring.charAt(index2++);

            if (c1 == c2) {
                continue;
            }

            if (!ignoreCase) {
                return false;
            }

            final char u1 = Character.toUpperCase(c1);
            final char u2 = Character.toUpperCase(c2);
            if (u1 != u2 && Character.toLowerCase(u1) != Character.toLowerCase(u2)) {
                return false;
            }
        }

        return true;
    }
    static final int TO_STRING_LIMIT = 16;
    static int lastIndexOf(final CharSequence cs, final CharSequence searchChar, int start) {
        if (searchChar == null || cs == null) {
            return NOT_FOUND;
        }
        if (searchChar instanceof String) {
            if (cs instanceof String) {
                return ((String) cs).lastIndexOf((String) searchChar, start);
            } else if (cs instanceof StringBuilder) {
                return ((StringBuilder) cs).lastIndexOf((String) searchChar, start);
            } else if (cs instanceof StringBuffer) {
                return ((StringBuffer) cs).lastIndexOf((String) searchChar, start);
            }
        }

        final int len1 = cs.length();
        final int len2 = searchChar.length();

        if (start > len1) {
            start = len1;
        }

        if (start < 0 || len2 < 0 || len2 > len1) {
            return NOT_FOUND;
        }

        if (len2 == 0) {
            return start;
        }

        if (len2 <= TO_STRING_LIMIT) {
            if (cs instanceof String) {
                return ((String) cs).lastIndexOf(searchChar.toString(), start);
            } else if (cs instanceof StringBuilder) {
                return ((StringBuilder) cs).lastIndexOf(searchChar.toString(), start);
            } else if (cs instanceof StringBuffer) {
                return ((StringBuffer) cs).lastIndexOf(searchChar.toString(), start);
            }
        }

        if (start + len2 > len1) {
            start = len1 - len2;
        }

        final char char0 = searchChar.charAt(0);

        int i = start;
        while (true) {
            while (cs.charAt(i) != char0) {
                i--;
                if (i < 0) {
                    return NOT_FOUND;
                }
            }
            if (checkLaterThan1(cs, searchChar, len2, i)) {
                return i;
            }
            i--;
            if (i < 0) {
                return NOT_FOUND;
            }
        }
    }

    private static boolean checkLaterThan1(final CharSequence cs, final CharSequence searchChar, final int len2, final int start1) {
        for (int i = 1, j = len2 - 1; i <= j; i++, j--) {
            if (cs.charAt(start1 + i) != searchChar.charAt(i)
                    ||
                    cs.charAt(start1 + j) != searchChar.charAt(j)
            ) {
                return false;
            }
        }
        return true;
    }

    static int lastIndexOf(final CharSequence cs, final int searchChar, int start) {
        if (cs instanceof String) {
            return ((String) cs).lastIndexOf(searchChar, start);
        }
        final int sz = cs.length();
        if (start < 0) {
            return NOT_FOUND;
        }
        if (start >= sz) {
            start = sz - 1;
        }
        if (searchChar < Character.MIN_SUPPLEMENTARY_CODE_POINT) {
            for (int i = start; i >= 0; --i) {
                if (cs.charAt(i) == searchChar) {
                    return i;
                }
            }
            return NOT_FOUND;
        }
        //supplementary characters (LANG1300)
        //NOTE - we must do a forward traversal for this to avoid duplicating code points
        if (searchChar <= Character.MAX_CODE_POINT) {
            final char[] chars = Character.toChars(searchChar);
            //make sure it's not the last index
            if (start == sz - 1) {
                return NOT_FOUND;
            }
            for (int i = start; i >= 0; i--) {
                final char high = cs.charAt(i);
                final char low = cs.charAt(i + 1);
                if (chars[0] == high && chars[1] == low) {
                    return i;
                }
            }
        }
        return NOT_FOUND;
    }
}
