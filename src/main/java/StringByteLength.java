import java.io.UnsupportedEncodingException;

/**
 * @Author yangwen-bo
 * @Date 2019/11/22.
 * @Version 1.0
 * 字符串可能有中文 计算其字节长度的方法
 */
public class StringByteLength {
    /*由于Java是基于Unicode编码的，因此，一个汉字的长度为1，而不是2。
         * 但有时需要以字节单位获得字符串的长度。例如，“123abc长城”按字节长度计算是10，而按Unicode计算长度是8。
         * 为了获得10，需要从头扫描根据字符的Ascii来获得具体的长度。如果是标准的字符，Ascii的范围是0至255，如果是汉字或其他全角字符，Ascii会大于255。
         * 因此，可以编写如下的方法来获得以字节为单位的字符串长度。*/
    public static int getWordCount(String s)
    {
        int length = 0;
        for(int i = 0; i < s.length(); i++)
        {
            int ascii = Character.codePointAt(s, i);
            if(ascii >= 0 && ascii <=255)
                length++;
            else
                length += 2;

        }
        return length;

    }

    /*基本原理是将字符串中所有的非标准字符（双字节字符）替换成两个标准字符（**，或其他的也可以）。这样就可以直接例用length方法获得字符串的字节长度了*/
    public static  int getWordCountRegex(String s)
    {

        s = s.replaceAll("[^\\x00-\\xff]", "**");
        int length = s.length();
        return length;
    }

    /*按特定的编码格式获取长度*/
    public static int getWordCountCode(String str, String code) throws UnsupportedEncodingException {
         return str.getBytes(code).length;

    }

    public static void main(String[] args) {
            String str="*12345678";
//        System.out.println(str.replaceFirst( ".","(除外)" ));
        String strs="12345678";
        System.out.println(strs.substring( 2,4 ));
        System.out.println(strs.substring( 4,5 ));
        System.out.println(strs.substring( 0,1 ));
    }
}
