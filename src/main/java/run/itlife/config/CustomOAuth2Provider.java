package run.itlife.config;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

public enum CustomOAuth2Provider {
    VK {
        public ClientRegistration.Builder getBuilder(String registrationId) {

            //ClientRegistration.Builder builder = this.getBuilder("vk", ClientAuthenticationMethod.CLIENT_SECRET_POST, "{baseUrl}/{action}/oauth2/code/{registrationId}");
            ClientRegistration.Builder builder = this.getBuilder("vk", ClientAuthenticationMethod.POST, "{baseUrl}/{action}/oauth2/code/{registrationId}");
            builder.scope(new String[]{"photo_id"});
            //builder.scope("photo_id,verified,sex,bdate,city,country,photo_max,home_town,has_photo");
            builder.authorizationUri("https://id.vk.com/authorize");
            builder.tokenUri("https://id.vk.com/oauth2/auth");
            builder.userInfoUri("https://id.vk.com/method/users.get?{sub}&v=5.95&fields=photo_id,verified,sex,bdate,city,country,photo_max,home_town,has_photo&display=popup&lang=ru&access_token=1111111111111111111");
            builder.clientName("vk");
            builder.redirectUriTemplate("{baseUrl}/oauth2/callback/{registrationId}");
            builder.clientId("11111111111");
            builder.clientSecret("111111111111111");
            builder.userNameAttributeName("sub");
            builder.registrationId("vk");
            return builder;
        }
    };

    private static final String DEFAULT_REDIRECT_URL = "{baseUrl}/{action}/oauth2/code/{registrationId}";

    CustomOAuth2Provider() {
    }

    protected final ClientRegistration.Builder getBuilder(String registrationId, ClientAuthenticationMethod method, String redirectUri) {
        ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
        builder.clientAuthenticationMethod(method);
        builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
        builder.redirectUri(redirectUri);
        return builder;
    }

    public abstract ClientRegistration.Builder getBuilder(String registrationId);
}