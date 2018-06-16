package com.eagro.security.jwt;

import com.eagro.security.jwt.SecurityProperties.Http.Version;

public interface SecurityPropertiesDefaults {

	interface Http {

        Version version = Version.V_1_1;

        interface Cache {

            int timeToLiveInDays = 1461; // 4 years (including leap day)
        }
    }
	
    interface Security {

        interface ClientAuthorization {

            String accessTokenUri = null;
            String tokenServiceId = null;
            String clientId = null;
            String clientSecret = null;
        }

        interface Authentication {

            interface Jwt {

                String secret = null;
                long tokenValidityInSeconds = 1800; // 0.5 hour
                long tokenValidityInSecondsForRememberMe = 2592000; // 30 hours;
            }
        }

        interface RememberMe {

            String key = null;
        }
    }

}
