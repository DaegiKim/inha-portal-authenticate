package kim.daegi;


import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Authenticator {
    private static final String REQUEST_FORM_DATA = "dest=http%3A%2F%2Fplaza.inha.ac.kr/plaza.asp?con_chk=no&uid={uid}&pwd={pwd}";
    private static final String LOGIN_URL = "https://plaza.inha.ac.kr/common/asp/login/loginProcess.asp";

    public boolean doAuthenticate(String uid, String pwd) {
        String _uid = new String(Base64.encodeBase64(uid.getBytes()));
        String _pwd = new String(Base64.encodeBase64(pwd.getBytes()));
        return getHtml(_uid, _pwd);
    }

    private boolean getHtml(String uid, String pwd) {
        boolean result = false;

        HttpURLConnection hConnection = null;
        PrintStream ps = null;
        InputStream is = null;
        BufferedReader in = null;

        try{
            URL url = new URL(LOGIN_URL);
            hConnection = (HttpURLConnection)url.openConnection();
            hConnection.setInstanceFollowRedirects(false);
            hConnection.setDoOutput(true);
            hConnection.setRequestMethod("POST");
            ps = new PrintStream(hConnection.getOutputStream());

            ps.print(REQUEST_FORM_DATA.replace("{uid}", uid).replace("{pwd}", pwd));

            hConnection.connect();
            int responseCode = hConnection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            hConnection.disconnect();
            ps.close();
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
