package rnk.bb.utils.security;

public class Hasher {
    public static void main(String[] args){
        String xword=args[0];
        HashUtils hu=new HashUtils();
        byte[] bsalt=hu.salt(10);
        String salt=hu.toBase64(bsalt);
        byte[] bpasswd=hu.hash_strong(xword,bsalt);
        String passwd=salt+hu.toBase64(bpasswd);


        System.out.print("xword:\t"+xword+"\n");
        System.out.print("salt:\t"+salt+"\n");
        System.out.print("pswd:\t"+passwd+"\n");
        System.out.print("reverse\n");

        salt=passwd.split("==")[0];
        bsalt=hu.fromBase64(salt+"==");
        bpasswd=hu.hash_strong(xword,bsalt);
        passwd=salt+"=="+hu.toBase64(bpasswd);
        System.out.print("paswd:\t"+passwd+"\n");
    }
}
