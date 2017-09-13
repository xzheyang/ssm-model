package shiro.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

public class MD5Util {

    private static String salt="hyjava123456";

    public static String getPassword(String password){
        return new Md5Hash(password, salt).toString();
    }

}
