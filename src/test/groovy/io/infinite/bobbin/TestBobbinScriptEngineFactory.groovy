package io.infinite.bobbin

import groovy.text.SimpleTemplateEngine
import groovy.text.Template
import groovy.transform.Memoized
import io.infinite.bobbin.config.BobbinConfig
import io.infinite.bobbin.config.DestinationConfig

class TestBobbinScriptEngineFactory {

    TestBobbinScriptEngineFactory(BobbinConfig bobbinConfig) {
        this.bobbinConfig = bobbinConfig
    }
    SimpleTemplateEngine simpleTemplateEngine = new SimpleTemplateEngine()


    String combinedTemplateFileString = new BobbinScriptEngineFactory().getTemplateText()


    Template combinedTemplate = simpleTemplateEngine.createTemplate(combinedTemplateFileString)


    GroovyClassLoader groovyClassLoader = new GroovyClassLoader(getClass().getClassLoader())

    BobbinConfig bobbinConfig


    String bobbinScriptEngineImplCode = combinedTemplate.make([
            "levelScript"          : bobbinConfig.levels,
            "classesScript"        : bobbinConfig.classes,
            "formatScript"         : bobbinConfig.format,
            "argFormatScript"      : bobbinConfig.formatArg,
            "argsFormatScript"     : bobbinConfig.formatArgs,
            "arg1arg2FormatScript" : bobbinConfig.formatArg1Arg2,
            "throwableFormatScript": bobbinConfig.formatThrowable,
            "dateFormat"           : bobbinConfig.dateFormat,
            "dateTimeFormat"       : bobbinConfig.dateTimeFormat,
            "fileNameScript"       : "\"\""
    ])

    Class bobbinScriptEngineImplClass = groovyClassLoader.parseClass(bobbinScriptEngineImplCode)

    BobbinScriptEngine bobbinScriptEngine = bobbinScriptEngineImplClass.newInstance(bobbinScriptEngineImplCode) as BobbinScriptEngine

    @Memoized
    BobbinScriptEngine getDestinationBobbinScriptEngine(DestinationConfig destinationConfig) {
        return groovyClassLoader.parseClass(combinedTemplate.make([
                "levelScript"          : destinationConfig.levels ?: bobbinConfig.levels,
                "classesScript"        : destinationConfig.classes ?: bobbinConfig.classes,
                "formatScript"         : destinationConfig.format ?: bobbinConfig.format,
                "argFormatScript"      : destinationConfig.formatArg ?: bobbinConfig.formatArg,
                "argsFormatScript"     : destinationConfig.formatArgs ?: bobbinConfig.formatArgs,
                "arg1arg2FormatScript" : destinationConfig.formatArg1Arg2 ?: bobbinConfig.formatArg1Arg2,
                "throwableFormatScript": destinationConfig.formatThrowable ?: bobbinConfig.formatThrowable,
                "dateFormat"           : destinationConfig.dateFormat ?: bobbinConfig.dateFormat,
                "dateTimeFormat"       : destinationConfig.dateTimeFormat ?: bobbinConfig.dateTimeFormat,
                "fileNameScript"       : destinationConfig.properties.get("fileName")

        ]) as String).newInstance(bobbinScriptEngineImplCode) as BobbinScriptEngine
    }

}
