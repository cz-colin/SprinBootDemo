package com.cloudzon.config;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

@ConfigurationProperties(prefix = "ExStuff", ignoreUnknownFields = false)
@Configuration
public class AppProperties {

	private final Http http = new Http();

	private final Security security = new Security();

	private final Swagger swagger = new Swagger();

	private final CorsConfiguration cors = new CorsConfiguration();

	private final Logging logging = new Logging();

	public Http getHttp() {
		return http;
	}

	public Security getSecurity() {
		return security;
	}

	public Swagger getSwagger() {
		return swagger;
	}

	public CorsConfiguration getCors() {
		return cors;
	}

	public Logging getLogging() {
		return logging;
	}

	public static class Http {

		public enum Version {
			V_1_1, V_2_0
		}

		private final Cache cache = new Cache();

		/**
		 * HTTP version, must be "V_1_1" (for HTTP/1.1) or V_2_0 (for (HTTP/2)
		 */
		public Version version = Version.V_1_1;

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

			private int timeToLiveInDays = 1461;

			public int getTimeToLiveInDays() {
				return timeToLiveInDays;
			}

			public void setTimeToLiveInDays(int timeToLiveInDays) {
				this.timeToLiveInDays = timeToLiveInDays;
			}
		}
	}

	public static class Security {

		private final RememberMe rememberMe = new RememberMe();

		private final ClientAuthorization clientAuthorization = new ClientAuthorization();

		private final Authentication authentication = new Authentication();

		public RememberMe getRememberMe() {
			return rememberMe;
		}

		public ClientAuthorization getClientAuthorization() {
			return clientAuthorization;
		}

		public Authentication getAuthentication() {
			return authentication;
		}

		public static class ClientAuthorization {

			private String accessTokenUri;

			private String tokenServiceId;

			private String clientId;

			private String clientSecret;

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

			private final Oauth oauth = new Oauth();

			private final Jwt jwt = new Jwt();

			public Oauth getOauth() {
				return oauth;
			}

			public Jwt getJwt() {
				return jwt;
			}

			public static class Oauth {

				private String clientId;

				private String clientSecret;

				private int tokenValidityInSeconds = 1800;

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

				public int getTokenValidityInSeconds() {
					return tokenValidityInSeconds;
				}

				public void setTokenValidityInSeconds(int tokenValidityInSeconds) {
					this.tokenValidityInSeconds = tokenValidityInSeconds;
				}
			}

			public static class Jwt {

				private String secret;

				private long tokenValidityInSeconds = 1800;

				private long tokenValidityInSecondsForRememberMe = 2592000;

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
			private String key;

			public String getKey() {
				return key;
			}

			public void setKey(String key) {
				this.key = key;
			}
		}
	}

	public static class Swagger {

		private String title = "Application API";

		private String description = "API documentation";

		private String version = "0.0.1";

		private String termsOfServiceUrl;

		private String contactName;

		private String contactUrl;

		private String contactEmail;

		private String license;

		private String licenseUrl;

		private String defaultIncludePattern = "/api/.*";

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getTermsOfServiceUrl() {
			return termsOfServiceUrl;
		}

		public void setTermsOfServiceUrl(String termsOfServiceUrl) {
			this.termsOfServiceUrl = termsOfServiceUrl;
		}

		public String getContactName() {
			return contactName;
		}

		public void setContactName(String contactName) {
			this.contactName = contactName;
		}

		public String getContactUrl() {
			return contactUrl;
		}

		public void setContactUrl(String contactUrl) {
			this.contactUrl = contactUrl;
		}

		public String getContactEmail() {
			return contactEmail;
		}

		public void setContactEmail(String contactEmail) {
			this.contactEmail = contactEmail;
		}

		public String getLicense() {
			return license;
		}

		public void setLicense(String license) {
			this.license = license;
		}

		public String getLicenseUrl() {
			return licenseUrl;
		}

		public void setLicenseUrl(String licenseUrl) {
			this.licenseUrl = licenseUrl;
		}

		public String getDefaultIncludePattern() {
			return defaultIncludePattern;
		}

		public void setDefaultIncludePattern(String defaultIncludePattern) {
			this.defaultIncludePattern = defaultIncludePattern;
		}
	}

	public static class Logging {

		private final Logstash logstash = new Logstash();

		public Logstash getLogstash() {
			return logstash;
		}

		public static class Logstash {

			private boolean enabled = false;

			private String host = "localhost";

			private int port = 5000;

			private int queueSize = 512;

			public boolean isEnabled() {
				return enabled;
			}

			public void setEnabled(boolean enabled) {
				this.enabled = enabled;
			}

			public String getHost() {
				return host;
			}

			public void setHost(String host) {
				this.host = host;
			}

			public int getPort() {
				return port;
			}

			public void setPort(int port) {
				this.port = port;
			}

			public int getQueueSize() {
				return queueSize;
			}

			public void setQueueSize(int queueSize) {
				this.queueSize = queueSize;
			}
		}
	}

}