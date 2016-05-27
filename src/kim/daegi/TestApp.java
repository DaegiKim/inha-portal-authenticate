package kim.daegi;

public class TestApp {
    public static void main(String[] args) {
        String uid = "";
        String pwd = "";

        Authenticator authenticator = new Authenticator();
        if(authenticator.doAuthenticate(uid, pwd)) {
            System.out.println("인하대학교 포털 인증 성공");
        } else {
            System.out.println("인하대학교 포털 인증 실패");
        }
    }
}
