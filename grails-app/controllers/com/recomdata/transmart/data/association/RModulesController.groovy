/*************************************************************************   
 * Copyright 2008-2012 Janssen Research & Development, LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************/

package com.recomdata.transmart.data.association

import grails.util.Holders
import jobs.Heatmap
import jobs.KMeansClustering
import jobs.HierarchicalClustering
import jobs.MarkerSelection
import org.quartz.JobDataMap
import org.quartz.JobDetail
import org.quartz.SimpleTrigger
import grails.converters.JSON
import org.transmartproject.core.dataquery.highdim.HighDimensionResource

class RModulesController {

    def springSecurityService
    def asyncJobService
    def RModulesService
    def grailsApplication
    def jobResultsService

    /**
     * Method called that will cancel a running job
     */
    def canceljob = {
        def jobName = request.getParameter("jobName")
        def jsonResult = asyncJobService.canceljob(jobName)

        response.setContentType("text/json")
        response.outputStream << jsonResult.toString()
    }

    /**
     * Method that will create the new asynchronous job name
     * Current methodology is username-jobtype-ID from sequence generator
     */
    def createnewjob = {
        def result = asyncJobService.createnewjob(params.jobName, params.jobType)

        response.setContentType("text/json")
        response.outputStream << result.toString()
    }

    /**
     * Method that will schedule a Job
     */
    def scheduleJob = {
        def jsonResult
        if (jobResultsService[params.jobName] == null) {
            throw new IllegalStateException('Cannot schedule job; it has not been created')
        }

        if (params['analysis'] == "heatmap") {
            jsonResult = scheduleJob(params, Heatmap.class)
        } else if (params['analysis'] == "kclust") {
            jsonResult = scheduleJob(params, KMeansClustering.class)
        } else if (params['analysis'] == "hclust") {
            jsonResult = scheduleJob(params, HierarchicalClustering.class)
        } else if (params['analysis'] == "markerSelection") {
            jsonResult = scheduleJob(params, MarkerSelection.class)
        } else {
            jsonResult = RModulesService.scheduleJob(springSecurityService.getPrincipal().username, params)
        }

        response.setContentType("text/json")
        response.outputStream << jsonResult.toString()
    }

    void scheduleJob(Map params, def classFile) {
        params.grailsApplication = grailsApplication
        params.analysisConstraints = JSON.parse(params.analysisConstraints)

        JobDetail jobDetail   = new JobDetail(params.jobName, params.jobType, classFile)
        jobDetail.jobDataMap  = new JobDataMap(params)
        SimpleTrigger trigger = new SimpleTrigger("triggerNow ${Calendar.instance.time.time}", 'RModules')
        quartzScheduler.scheduleJob(jobDetail, trigger)
    }

    def knownDataTypes() {
        def resource = Holders.grailsApplication.mainContext.getBean HighDimensionResource
        Map output = [:]
        resource.knownTypes.each {
            def subResource = resource.getSubResourceForType(it)
            output[it] = ['assayConstraints':subResource.supportedAssayConstraints, 'dataConstraints':subResource.supportedDataConstraints,'projections':subResource.supportedProjections]
        }
        render output as JSON
    }

    private static def getQuartzScheduler() {
        Holders.grailsApplication.mainContext.quartzScheduler
    }
}
