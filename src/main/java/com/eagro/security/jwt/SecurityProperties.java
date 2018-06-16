package com.eagro.security.jwt;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 *
 * <p> Properties are configured in the application.yml file. </p>
 */
@ConfigurationProperties(prefix = "eagro", ignoreUnknownFields = false)
public class SecurityProperties {


	private final Security security = new Security();
	
	private final Http http = new Http();

    public Security getSecurity() {
        return security;
    }

    public Http getHttp() {
        return http;
    }
    
    public static class Http {

        public enum Version {V_1_1, V_2_0}

        private final Cache cache = new Cache();

        /**
         * HTTP version, must be "V_1_1" (for HTTP/1.1) or V_2_0 (for (HTTP/2)
         */
        public Version version = SecurityPropertiesDefaults.Http.version;

        public Cache getCache() {
            return cache;
        }

        public Version getVersion() {
            return version;
        }

        public void setVersion(Version version) {
            this.version = version;
        }

        public static class Cache {

            private int timeToLiveInDays = SecurityPropertiesDefaults.Http.Cache.timeToLiveInDays;

            public int getTimeToLiveInDays() {
                return timeToLiveInDays;
            }

            public void setTimeToLiveInDays(int timeToLiveInDays) {
                this.timeToLiveInDays = timeToLiveInDays;
            }
        }
    }
    public static class Security {

        private final ClientAuthorization clientAuthorization = new ClientAuthorization();

        private final Authentication authentication = new Authentication();

        private final RememberMe rememberMe = new RememberMe();

        public ClientAuthorization getClientAuthorization() {
            return clientAuthorization;
        }

        public Authentication getAuthentication() {
            return authentication;
        }

        public RememberMe getRememberMe() {
            return rememberMe;
        }

        public static class ClientAuthorization {

            private String accessTokenUri = SecurityPropertiesDefaults.Security.ClientAuthorization.accessTokenUri;

            private String tokenServiceId = SecurityPropertiesDefaults.Security.ClientAuthorization.tokenServiceId;

            private String clientId = SecurityPropertiesDefaults.Security.ClientAuthorization.clientId;

            private String clientSecret = SecurityPropertiesDefaults.Security.ClientAuthorization.clientSecret;

            public String getAccessTokenUri() {
                return accessTokenUri;
            }

            public void setAccessTokenUri(String accessTokenUri) {
                this.accessTokenUri = accessTokenUri;
            }

            public String getTokenServiceId() {
                return tokenServiceId;
            }

            public void setTokenServiceId(String tokenServiceId) {
                this.tokenServiceId = tokenServiceId;
            }

            public String getClientId() {
                return clientId;
            }

            public void setClientId(String clientId) {
                this.clientId = clientId;
            }

            public String getClientSecret() {
                return clientSecret;
            }

            public void setClientSecret(String clientSecret) {
                this.clientSecret = clientSecret;
            }
        }

        public static class Authentication {

            private final Jwt jwt = new Jwt();

            public Jwt getJwt() {
            	System.out.println("Inside Jwt");
                return jwt;
            }

            public static class Jwt {
                private String secret = "93d41101287a0e18a5273f1880640f212e472d10";
                		//SecurityPropertiesDefaults.Security.Authentication.Jwt.secret;

                private long tokenValidityInSeconds = 1800;
                		//SecurityPropertiesDefaults.Security.Authentication.Jwt           .tokenValidityInSeconds;

                private long tokenValidityInSecondsForRememberMe = 2592000;
                		//SecurityPropertiesDefaults.Security.Authentication.Jwt                   .tokenValidityInSecondsForRememberMe;

                public String getSecret() {
                    return secret;
                }

                public void setSecret(String secret) {
                    this.secret = secret;
                }

                public long getTokenValidityInSeconds() {
                    return tokenValidityInSeconds;
                }

                public void setTokenValidityInSeconds(long tokenValidityInSeconds) {
                    this.tokenValidityInSeconds = tokenValidityInSeconds;
                }

                public long getTokenValidityInSecondsForRememberMe() {
                    return tokenValidityInSecondsForRememberMe;
                }

                public void setTokenValidityInSecondsForRememberMe(long tokenValidityInSecondsForRememberMe) {
                    this.tokenValidityInSecondsForRememberMe = tokenValidityInSecondsForRememberMe;
                }
            }
        }

        public static class RememberMe {

            @NotNull
            private String key = SecurityPropertiesDefaults.Security.RememberMe.key;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }
        }
    }

    }
