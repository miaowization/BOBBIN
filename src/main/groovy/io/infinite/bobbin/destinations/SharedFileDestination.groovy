package io.infinite.bobbin.destinations

import groovy.transform.CompileStatic
import io.infinite.bobbin.Event
import io.infinite.bobbin.config.BobbinConfig
import io.infinite.bobbin.config.DestinationConfig

@CompileStatic
class SharedFileDestination extends FileDestination {

    SharedFileDestination(DestinationConfig destinationConfig, BobbinConfig parentBobbinConfig) {
        super(destinationConfig, parentBobbinConfig)
    }

    @Override
    synchronized protected void store(Event event) {
        super.store(event)
    }
}
