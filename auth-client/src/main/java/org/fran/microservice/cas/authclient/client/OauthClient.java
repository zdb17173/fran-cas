package org.fran.microservice.cas.authclient.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.http.entity.ContentType;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fran
 * @Description
 * @Date 2019/4/30 17:07
 */
public class OauthClient extends AbstractHttpClientService {

    String clientId;
    String clientSecurity;
    String oauthServiceUrl;
    String authorization;
    String authServerUrl;
    String resourceServerUrl;

    public void init(){
        byte[] basic = (clientId + ":" + clientSecurity).getBytes();
        String b64 = new String(Base64.getEncoder().encode(basic));
        authorization = b64;
        super.init(oauthServiceUrl, 10000, 10000, 10000);
    }

    //使用用户username password获取token  可以获取登陆用户信息
    public OAuth2TokenResult loginByPwd(String username, String password){
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "basic " + authorization);
        try {
            OAuth2TokenResult res = doPost(authServerUrl + "/auth/oauth/token", new OAuth2PasswordRequest(username, password), header, ContentType.APPLICATION_FORM_URLENCODED, OAuth2TokenResult.class);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //使用客户端clientId & clientSecurity获取token  无法获取登陆用户信息
    public OAuth2TokenResult clientCredentials(){
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "basic " + authorization);
        try {
            OAuth2TokenResult r = doPost(authServerUrl + "/auth/oauth/token",
                    new OAuth2ClientCredentialsRequest(
                            clientId,
                            clientSecurity,
                            null,
                            "all",
                            null,
                            "12345"),
                    header,
                    ContentType.APPLICATION_FORM_URLENCODED,
                    OAuth2TokenResult.class
            );
            return r; //96c4d013-5d7e-45eb-a462-a0698cea5721
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //访问认证服务登陆：http://localhost:8081/auth/oauth/authorize?client_id=client1&redirect_uri=http://localhost:8082/ui/login&response_type=code
    //回调：http://localhost:8082/ui/login?code=2LsxAa 使用回调的code调用authorizationCode
    //使用clientId & clientSecurity & redirectUri回调获取code 通过code获取token 可以登陆用户信息
    public String authorizationCode(String code){
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "basic " + authorization);
        try {
            OAuth2TokenResult r = doPost(authServerUrl + "/auth/oauth/token",
                    new OAuth2ClientCredentialsRequest("authorization_code",
                            clientId,
                            clientSecurity,
                            code,
                            "all",
                            "http://localhost:8082/ui/login",
                            "12345"),
                    header,
                    ContentType.APPLICATION_FORM_URLENCODED,
                    OAuth2TokenResult.class
            );
            return r.getAccess_token(); //96c4d013-5d7e-45eb-a462-a0698cea5721
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //认证： http://localhost:8081/auth/oauth/authorize?client_id=client1&redirect_uri=http://localhost:8082/ui/login&response_type=token
    //回调： http://localhost:8082/ui/login#access_token=415ac9ea-3b93-41c6-8819-17c583c280d9&token_type=bearer&expires_in=944&scope=all
    public void implicit(){
        //使用implicit方式授权，和authorizationCode基本相同，只是把responseType改为token，则auth-server会直接返回token，而非返回code。通过这种方式
        //便于使用浏览器得js客户端获取token。 比code方式减少一步但是token会暴露，不安全。
    }

    //使用password & authorizationCode模式可以用token获取用户
    public OAuth2User userInfo(String accessToken){

        OAuth2User r = null;
        try {
            return doGet("/auth/user/me?access_token=" + accessToken, null, null, OAuth2User.class);
        } catch (Exception e) {
            e.printStackTrace();
            return r;
        }
    }

    //检查token是否激活
    public OAuth2CheckTokenResult checkToken(String token){
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "basic " + authorization);
        try {
            OAuth2CheckTokenResult r = doGet(authServerUrl + "/auth/oauth/check_token?token="+ token, null, header, OAuth2CheckTokenResult.class);
            return r;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //访问资源，使用tokenType + accessToken访问资源
    public void resourceInvoke(OAuth2TokenResult accessToken){
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", accessToken.getToken_type() + " " + accessToken.getAccess_token());

        try {
            String result = doGet(resourceServerUrl + "/api", null, header, String.class);
            System.out.println(result);//SUCCESS
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //刷新token（只有用户密码方式返回该token，可以刷新，refreshToken与accessToken不同
    public void refresh(String refreshToken){
        try {
            Map<String, String> header = new HashMap<>();
            header.put("Authorization", "basic " + authorization);
            String r = doPost(authServerUrl + "/auth/oauth/token",
                    new RefreshToken("refresh_token", refreshToken, clientId, clientSecurity, "all"), header, ContentType.APPLICATION_FORM_URLENCODED, String.class);
            System.out.println(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Data
    @AllArgsConstructor
    static class RefreshToken{
        String grant_type;
        String refresh_token;
        String client_id;
        String client_secret;
        String scope;

    }

    public static void main(String[] args){
        OauthClient cli = new OauthClient();
        cli.clientSecurity = "client1secret";
//        cli.clientSecurity = "cc16bfb89e7de37e23789e75de78bb46";
        cli.clientId = "client1";
        cli.authServerUrl = "http://127.0.0.1:8081";
        cli.resourceServerUrl = "http://127.0.0.1:8088";
        cli.init();

        /*OAuth2TokenResult tokenRes = new OAuth2TokenResult(){
            {
                setAccess_token("415ac9ea-3b93-41c6-8819-17c583c280d9");
            }
        };*/
        OAuth2TokenResult tokenRes = cli.loginByPwd("ed", "1");
//        cli.implicit();
//        OAuth2TokenResult tokenRes = cli.clientCredentials();

        //访问认证服务登陆：http://localhost:8081/auth/oauth/authorize?client_id=client1&redirect_uri=http://localhost:8082/ui/login&response_type=code
        //回调：http://localhost:8082/ui/login?code=2LsxAa 使用回调的code调用authorizationCode
//        String token = cli.authorizationCode("2LsxAa");
        System.out.println(tokenRes);

        //1556683013
        OAuth2CheckTokenResult r = cli.checkToken(tokenRes.getAccess_token());
        cli.resourceInvoke(tokenRes);
        cli.refresh(tokenRes.getRefresh_token());
//        System.out.println(r);
//        OAuth2User user = cli.userInfo(token);//使用password & authorizationCode模式可以用token获取用户
//        System.out.println(token);
    }

}
