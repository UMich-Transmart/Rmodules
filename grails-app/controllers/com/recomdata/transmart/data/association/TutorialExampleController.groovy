package com.recomdata.transmart.data.association

class TutorialExampleController {

    // This Controller is part of a tutorial example. See comment block at the end of this code file

	def RModulesOutputRenderService
	
	def tutorialExampleOutput =
	{
		//This will be the array of image links.
		def ArrayList<String> imageLinks = new ArrayList<String>()
		
		//This will be the array of text file locations.
		def ArrayList<String> txtFiles = new ArrayList<String>()
		
		//Grab the job ID from the query string.
		String jobName = params.jobName

		//Gather the image links.
		RModulesOutputRenderService.initializeAttributes(jobName,"TutorialExample",imageLinks)

		String tempDirectory = RModulesOutputRenderService.tempDirectory
		
		//Create a directory object so we can pass it to be traversed.
		def tempDirectoryFile = new File(tempDirectory)

		render(template: "/plugin/TutorialExample_out", model:[imageLocations:imageLinks,zipLink:RModulesOutputRenderService.zipLink], contextPath:pluginContextPath)
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
