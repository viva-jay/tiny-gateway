package v2.pool;

import java.util.concurrent.atomic.AtomicInteger;

public class HostNameLoadBalancer {
    private AtomicInteger index = new AtomicInteger();
    private String[] serverNames;

    public HostNameLoadBalancer(String[] serverNames) {
        this.serverNames = serverNames;
    }
    public String getNext() {
        return serverNames[index.getAndIncrement() & serverNames.length - 1];
    }
}
