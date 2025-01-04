package app.cta4j.provider;

import com.google.inject.Provider;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.UnifiedJedis;

public final class JedisClientProvider implements Provider<UnifiedJedis> {
    @Override
    public UnifiedJedis get() {
        String user = System.getenv("REDIS_USER");

        if (user == null) {
            throw new RuntimeException("REDIS_USER is not set");
        }

        String password = System.getenv("REDIS_PASSWORD");

        if (password == null) {
            throw new RuntimeException("REDIS_PASSWORD is not set");
        }

        String host = System.getenv("REDIS_HOST");

        if (host == null) {
            throw new RuntimeException("REDIS_HOST is not set");
        }

        String portString = System.getenv("REDIS_PORT");

        if (portString == null) {
            throw new RuntimeException("REDIS_PORT is not set");
        }

        int port = Integer.parseInt(portString);

        JedisClientConfig config = DefaultJedisClientConfig.builder()
                                                           .user(user)
                                                           .password(password)
                                                           .build();

        HostAndPort hostAndPort = new HostAndPort(host, port);

        return new UnifiedJedis(hostAndPort, config);
    }
}
