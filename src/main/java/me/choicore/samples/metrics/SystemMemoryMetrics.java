package me.choicore.samples.metrics;

import com.sun.management.OperatingSystemMXBean;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;

@Component
public class SystemMemoryMetrics implements MeterBinder {
    @Override
    public void bindTo(@Nonnull MeterRegistry meterRegistry) {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        Gauge.builder("system.memory.total", osBean, operatingSystemMXBean1 -> operatingSystemMXBean1.getTotalMemorySize() / 1024.0)
                .description("The total system memory in KB")
                .baseUnit("kilobytes")
                .strongReference(true)
                .register(meterRegistry);

        Gauge.builder("system.memory.free", osBean, operatingSystemMXBean -> operatingSystemMXBean.getFreeMemorySize() / 1024.0)
                .description("The amount of free memory in KB")
                .baseUnit("kilobytes")
                .strongReference(true)
                .register(meterRegistry);
    }
}
