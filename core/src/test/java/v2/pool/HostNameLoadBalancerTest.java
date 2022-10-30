package v2.pool;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HostNameLoadBalancerTest {
    @Test
    void roundRobbinLoadbalancer() {
        HostNameLoadBalancer loadBalancer = new HostNameLoadBalancer(new String[]{"a.host", "b.host"});

        loadBalancer.getNext();
        Assertions.assertEquals(loadBalancer.getNext(), "b.host");
        Assertions.assertEquals(loadBalancer.getNext(), "a.host");
    }
}