package redis.session;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * 利用redis，实现集群中的进程同步
 */
public class ClusterTest
{
    public static void main(String[] args) throws Exception
    {
        Config config = new Config();
        config.useSingleServer()
                .setConnectionMinimumIdleSize(3)
                .setConnectionPoolSize(3)
                .setAddress("redis://127.0.0.1:6379");

        RedissonClient localRedisson = Redisson.create(config);

        RLock lock = localRedisson.getLock("locker");
        String value = "2";
        System.out.println("***************************" + value + "***************************");
        if (lock.isLocked())
        {
            System.out.println("***************************locked***************************");
            localRedisson.shutdown();
            return;
        }
        lock.lock();

        RMap<String, String> map = localRedisson.getMap("mapper");
        map.put("a", value);
        Thread.sleep(1000 * 30);

        lock.unlock();
        localRedisson.shutdown();
    }
}
