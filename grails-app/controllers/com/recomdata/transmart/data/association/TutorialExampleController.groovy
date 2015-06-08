package com.recomdata.transmart.data.association

class TutorialExampleController {

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
 * Extend comment field
 */
