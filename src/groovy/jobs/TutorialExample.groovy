package jobs

import com.google.common.base.Function
import com.google.common.collect.Maps
import jobs.steps.*
import jobs.steps.helpers.CategoricalColumnConfigurator
import jobs.steps.helpers.SimpleAddColumnConfigurator
import jobs.steps.helpers.OptionalColumnConfiguratorDecorator
import jobs.table.ConceptTimeValuesTable
import jobs.table.Table
import jobs.table.columns.PrimaryKeyColumn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.transmartproject.core.dataquery.highdim.projections.Projection

import javax.annotation.PostConstruct

import static jobs.steps.AbstractDumpStep.DEFAULT_OUTPUT_FILE_NAME

@Component
@Scope('job')
class TutorialExample extends AbstractAnalysisJob {

    // This Analysis Job description is part of a tutorial example. See comment block at the end of this code file

    private static final String SCALING_VALUES_FILENAME = 'conceptScaleValues'

    @Autowired
    SimpleAddColumnConfigurator primaryKeyColumnConfigurator

    @Autowired
    CategoricalColumnConfigurator conceptConfigurator

    @Autowired
    Table table

    @Override
    void init() {
        primaryKeyColumnConfigurator.column = new PrimaryKeyColumn(header: 'PATIENT_NUM')

        print("conceptConfigurator = " + conceptConfigurator)
        conceptConfigurator.header                      = 'VALUE'

        conceptConfigurator.multiRow              = true

        conceptConfigurator.multiConcepts         = true

        // we do not want group name pruning

        conceptConfigurator.keyForConceptPath     = 'dependentVariable'
        conceptConfigurator.keyForDataType        = 'divDependentVariableType'
        conceptConfigurator.keyForSearchKeywordId = 'divDependentVariablePathway'
    }

    @Override
    protected List<Step> prepareSteps() {
        List<Step> steps = []

        steps << new BuildTableResultStep(
                table:         table,
                configurators: [primaryKeyColumnConfigurator,
                                conceptConfigurator])

        steps << new LineGraphDumpTableResultsStep(
                table:              table,
                temporaryDirectory: temporaryDirectory,
                outputFileName: DEFAULT_OUTPUT_FILE_NAME)

        Map<String, Closure<String>> extraParams = [:]
        extraParams['scalingFilename'] = { getScalingFilename() }
        extraParams['inputFileName'] = { DEFAULT_OUTPUT_FILE_NAME }

        steps << new RCommandsStep(
                temporaryDirectory: temporaryDirectory,
                scriptsDirectory:   scriptsDirectory,
                rStatements:        RStatements,
                studyName:          studyName,
                params:             params,
                extraParams:        Maps.transformValues(extraParams, { it() } as Function))

        steps
    }

    @Override
    protected List<String> getRStatements() {
        [ '''source('$pluginDirectory/TutorialExample/TutorialGraphLoader.R')''',
                '''TutorialGraph.loader(
                    input.filename           = '$inputFileName',
        )''' ]
    }

    @Override
    protected getForwardPath() {
        "/TutorialExample/TutorialExampleOutput?jobName=$name"
    }
}

/**
 * Tutorial Example Pllugin
 *   The is part of a tutorial exmaple plugin that is used to show how to create Advance Workflow Plugins that use R code
 *   to compute and display resutls from clinical data. It's parts are distributed in the core code for Rmodules in the following
 *   locations:
 *     grails-app/controllers/com/recomdata/transmart/data/association/TutorialExampleController.groovy - starts here
 *     src/groovy/jobs/TutorialExample.groovy - the top level job controller
 *     web-app/Rscripts/TutorialExample/BuildTutorialData.R - used as part of job
 *     web-app/Rscripts/TutorialExample/TutorialGraphLoader.R - used as part of job
 *     grails-app/views/plugin/TutorialExample.gsp - the view holder
 *     grails-app/views/plugin/_tutorialExample_out.gsp - the data output template
 *     web-app/js/plugin/TutorialExample.js - defines loaders and other functional support for view
 */
