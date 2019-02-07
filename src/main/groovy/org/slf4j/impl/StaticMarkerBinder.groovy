package org.slf4j.impl

import groovy.transform.CompileStatic
import org.slf4j.IMarkerFactory
import org.slf4j.helpers.BasicMarkerFactory
import org.slf4j.spi.MarkerFactoryBinder

@CompileStatic
public class StaticMarkerBinder implements MarkerFactoryBinder {


    public static final StaticMarkerBinder SINGLETON = new StaticMarkerBinder()

    final IMarkerFactory markerFactory = new BasicMarkerFactory()

    private StaticMarkerBinder() {
    }

    public IMarkerFactory getMarkerFactory() {
        return markerFactory
    }

    public String getMarkerFactoryClassStr() {
        return BasicMarkerFactory.class.getName()
    }

}
