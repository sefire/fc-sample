package bel.tnp.fb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

/**
 * Created by Sergey Glotov on 02.03.2017.
 */

@Configuration
public class SocialConfig {

    static final String clientId = "1189110511209604";
    static final String clientSecret = "5d3bef32631e0d5c926f46d209cad34b";


    @Bean
    public ConnectionFactoryLocator connectionFactoryLocator() {
        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
        registry.addConnectionFactory(new FacebookConnectionFactory(
                                      clientId,
                                      clientSecret));
        return registry;
    }
}