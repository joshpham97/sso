package App;

import com.github.scribejava.core.oauth.OAuth20Service;

public class Context {
    private SSOManagerFactory ssoManager;
    private OAuth20Service oAuthService;

    public Context(){}

    public void setSsoManager(SSOType ssoType){
        ssoManager = SSOManagerFactory.getSSOManager(ssoType);
    }

    public SSOManagerFactory getSsoManager(){
        return ssoManager;
    }

    public void setOAuthService(OAuth20Service service){
        oAuthService = service;
    }

    public OAuth20Service getOAuthService(){
        return oAuthService;
    }
}
