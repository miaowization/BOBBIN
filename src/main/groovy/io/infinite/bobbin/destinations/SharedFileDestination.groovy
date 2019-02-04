package io.infinite.bobbin.destinations


import io.infinite.bobbin.config.BobbinConfig
import io.infinite.bobbin.config.DestinationConfig
import io.infinite.supplies.ast.cache.Cache

class SharedFileDestination extends SharedDestination {

    @Cache
    Map<String, EventQueueRunnable> eventQueueRunnableMap = [:]

    SharedFileDestination(DestinationConfig destinationConfig, BobbinConfig parentBobbinConfig) {
        super(destinationConfig, parentBobbinConfig)
    }

    @Override
    Destination getActualDestination() {
        return new FileDestination(destinationConfig, parentBobbinConfig)
    }

    @Override
    String getSharedDestinationName() {
        return "Bobbin Async File Logger"
    }
}
