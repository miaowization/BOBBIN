package io.infinite.bobbin

import com.fasterxml.jackson.databind.ObjectMapper
import io.infinite.bobbin.config.BobbinConfig
import io.infinite.supplies.ast.cache.Cache
import io.infinite.supplies.conf.ResourceLookupThread
import org.slf4j.ILoggerFactory
import org.slf4j.Logger
import org.slf4j.helpers.Util

class BobbinFactory implements ILoggerFactory {

    String confName = "Bobbin.json"

    @Cache
    BobbinConfig bobbinConfig = initBobbinConfig()

    BobbinConfig initBobbinConfig() {
        BobbinConfig bobbinConfig
        String configResourceString = new ResourceLookupThread("Bobbin", getConfName(), true).getResourceAsString()
        if (configResourceString != null) {
            bobbinConfig = new ObjectMapper().readValue(
                    configResourceString
                    , BobbinConfig.class
            )
        } else {
            bobbinConfig = zeroConf()
        }
        return bobbinConfig
    }

    BobbinConfig zeroConf() {
        Util.report("Bobbin: using zero configuration")
        return new BobbinConfig()
    }

    @Override
    Logger getLogger(String name) {
        BobbinThreadLocal.getBobbin()
        return new BobbinAdapter(name)
    }

}
