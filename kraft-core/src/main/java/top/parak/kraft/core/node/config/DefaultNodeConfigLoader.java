package top.parak.kraft.core.node.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.parak.kraft.core.log.Log;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Default node config loader.
 *
 * @author KHighness
 * @since 2022-06-05
 * @email parakovo@gmail.com
 */
public class DefaultNodeConfigLoader implements NodeConfigLoader {

    private static final Logger logger = LoggerFactory.getLogger(DefaultNodeConfigLoader.class);

    /**
     * Property nme prefix.
     */
    private final String propertyNamePrefix;

    /**
     * Create DefaultNodeConfigLoader.
     * <p>Used empty string as default property name prefix.</p>
     */
    public DefaultNodeConfigLoader() {
        this("");
    }

    /**
     * Create DefaultNodeConfigLoader.
     *
     * @param propertyNamePrefix property name prefix
     */
    public DefaultNodeConfigLoader(String propertyNamePrefix) {
        this.propertyNamePrefix = propertyNamePrefix;
    }

    @Nonnull
    @Override
    public NodeConfig load(@Nonnull InputStream input) throws IOException {
        Properties p = new Properties();
        p.load(input);

        NodeConfig config = new NodeConfig();
        config.setMinElectionTimeout(getIntProperty(p, "election.timeout.min", 3000));
        config.setMaxElectionTimeout(getIntProperty(p, "election.timeout.max", 4000));
        config.setLogReplicationDelay(getIntProperty(p, "replication.delay", 0));
        config.setLogReplicationInterval(getIntProperty(p, "replication.interval", 1000));
        config.setLogReplicationReadTimeout(getIntProperty(p, "replication.timeout.read", 900));
        config.setMaxReplicationEntries(getIntProperty(p, "replication.entries.max", Log.ALL_ENTRIES));
        config.setSnapshotDataLength(getIntProperty(p, "snapshot.data.length", 1024));
        config.setMaxReplicationEntriesForNewNode(getIntProperty(p, "new-node.replication.entries.max", Log.ALL_ENTRIES));
        config.setNewNodeMaxRound(getIntProperty(p, "new-node.round.max", 10));
        config.setNewNodeReadTimeout(getIntProperty(p, "new-node.timeout.read", 3000));
        config.setNewNodeAdvanceTimeout(getIntProperty(p, "new-node.timeout.advance", 3000));
        config.setPreviousGroupConfigChangeTimeout(getIntProperty(p, "group.config.change.timeout", 0));
        config.setNioWorkerThreads(getIntProperty(p, "connector.workers", 0));
        return config;
    }

    private int getIntProperty(Properties properties, String name, int defaultValue) {
        String value = properties.getProperty(propertyNamePrefix + name);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                logger.warn("illegal value [" + value + "] for property " + name +
                        ", fallback to default value " + defaultValue);
            }
        }
        return defaultValue;
    }

}
