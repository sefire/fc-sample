package bel.tnp.fb;

import java.net.URLEncoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.codec.binary.Base64;

import org.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;

public class Controller extends AbstractController {

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView mav = new ModelAndView("appView");

        //Facebook App info
        String fbSecretKey = "abcdefghijklmno";
        String fbAppId = "1234567890";
        String fbCanvasPage = "http://apps.facebook.com/example-app/";
        String fbCanvasUrl = "http://example.com/";

        //parse signed_request
        if(request.getParameter("signed_request") != null) {

            //it is important to enable url-safe mode for Base64 encoder
            Base64 base64 = new Base64(true);

            //split request into signature and data
            String[] signedRequest = request.getParameter("signed_request").split("\\.", 2);

            //parse signature
            String sig = new String(base64.decode(signedRequest[0].getBytes("UTF-8")));

            //parse data and convert to json object
            JSONObject data = new JSONObject(new String(base64.decode(signedRequest[1].getBytes("UTF-8"))));

            //check signature algorithm
            if(!data.getString("algorithm").equals("HMAC-SHA256")) {
                //unknown algorithm is used
                return null;
            }

            //check if data is signed correctly
            if(!hmacSHA256(signedRequest[1], fbSecretKey).equals(sig)) {
                //signature is not correct, possibly the data was tampered with
                return null;
            }

            //check if user authorized the app
            if(!data.has("user_id") || !data.has("oauth_token")) {
                //this is guest, create authorization url that will be passed to javascript
                //note that redirect_uri (page the user will be forwarded to after authorization) is set to fbCanvasUrl
                mav.addObject("redirectUrl", "https://www.facebook.com/dialog/oauth?client_id=" + fbAppId +
                        "&redirect_uri=" + URLEncoder.encode(fbCanvasUrl, "UTF-8") +
                        "&scope=publish_stream,offline_access,email");
            } else {
                //this is authorized user, get their info from Graph API using received access token
                String accessToken = data.getString("oauth_token");
                FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
                User user = facebookClient.fetchObject("me", User.class);
                mav.addObject("user", user);
            }

        } else {
            //this page was opened not inside facebook iframe,
            //possibly as a post-authorization redirect.
            //do server side forward to facebook app
            return new ModelAndView(new RedirectView(fbCanvasPage, true));
        }

        return mav;

    }

    //HmacSHA256 implementation
    private String hmacSHA256(String data, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKey);
        byte[] hmacData = mac.doFinal(data.getBytes("UTF-8"));
        return new String(hmacData);
    }

}
