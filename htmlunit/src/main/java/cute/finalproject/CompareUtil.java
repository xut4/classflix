package cute.finalproject;
import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.Objects;

/** * 比對工具（支援漢字比對） * * @author JZY */
public class CompareUtil {
   

    /** * 判斷單個字元是否為漢字 * * @param c 字元 * @return 是否為漢字 */
    public static boolean isHanZi(char c) {
   
        return String.valueOf(c).matches("[\u4e00-\u9fa5]");
    }

    /** * 比對兩個字元（可含漢字）大小 * * @param c1 字元1 * @param c2 字元2 * @return 兩個字元相差的大小（返回結果大於0表示第一個字元大，返回結果小於0表示第二個字元大，返回結果等於0表示兩個字元一樣大） * （針對兩個讀音和音調相同的中文/漢字字元，再次進行常規的編碼大小比對） */
    public static int compareCharWithHanZi(char c1, char c2) {
   
        boolean b1 = isHanZi(c1);
        boolean b2 = isHanZi(c2);
        if (b1 && b2) {
   
            int result = Objects.requireNonNull(getFirstStringFromHanyuPinyinStringArray(c1)).compareTo(Objects.requireNonNull(getFirstStringFromHanyuPinyinStringArray(c2)));
            return result != 0 ? result : Character.compare(c1, c2);
        } else {
   
            return Character.compare(c1, c2);
        }
    }

    /** * 判斷兩個字串（可含漢字）大小 * * @param string1 字串1 * @param string2 字串2 * @return 兩個字串大小的比對結果（返回1表示第一個字串大，返回-1表示第二個字串大，返回0表示兩個字串一樣大） */
    public static boolean compareStringWithHanZi(String string1, String string2) {
        char[] charArray1 = string1.toCharArray();
        char[] charArray2 = string2.toCharArray();
        int length1 = charArray1.length;
        int length2 = charArray2.length;
        int result=3;
        int count=0;
        for(int j=0; j < length1; j++)
        {
            for (int i = 0; i < length2; i++) {
                result = compareCharWithHanZi(charArray1[j], charArray2[i]);
                if (result==0) {
                    count++;
                    break;
                }
            }   
        }
        if(count==length1) {count=0; return true;}
        else {count=0; return false;}
    }
    /** * 獲取單個字元的第一個漢語拼音 * * @param c 字元 * @return 漢字字元的第一個漢語拼音 */
    public static String getFirstStringFromHanyuPinyinStringArray(char c) {
   
        String[] strings = PinyinHelper.toHanyuPinyinStringArray(c);
        if (strings != null) {
   
            return strings[0];
        } else {
   
            return null;
        }
    }
}