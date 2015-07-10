
# This R script is part of a tutorial example. See comment block at the end of this code file

TutorialGraph.loader <- function(input.filename)
{

	line.data<-read.delim(input.filename, header=T, stringsAsFactors = FALSE)
}

TutorialGraph.plotter <- function(data.to.plot)
{
print('---------------------------------------------------------------------------------------')
print('in Tutorial Gragh Loader - TutorialExamplePlotter - just before plot')
print('---------------------------------------------------------------------------------------')

	# ggplot2 examples
    library(ggplot2)

    # create factors with value labels
    mtcars$gear <- factor(mtcars$gear,levels=c(3,4,5),labels=c("3gears","4gears","5gears"))
    mtcars$am <- factor(mtcars$am,levels=c(0,1),labels=c("Automatic","Manual"))
    mtcars$cyl <- factor(mtcars$cyl,levels=c(4,6,8),labels=c("4cyl","6cyl","8cyl"))

    # Kernel density plots for mpg
    # grouped by number of gears (indicated by color)
  p <- qplot(mpg, data=mtcars, geom="density", fill=gear, alpha=I(.5),main="Distribution of Gas Milage", xlab="Miles Per Gallon",ylab="Density")
  p
}


Plot.error.message <- function(errorMessage) {
    # TODO: This error handling hack is a temporary permissible quick fix:
    # It deals with getting error messages through an already used medium (the plot image) to the end-user in certain relevant cases.
    # It should be replaced by a system wide re-design of consistent error handling that is currently not in place. See ticket HYVE-12.
    print(paste("Error encountered. Caught by Plot.error.message(). Details:", errorMessage))
    tmp <- frame()
    tmp2 <- mtext(errorMessage,cex=2)
    print(tmp)
    print(tmp2)
    dev.off()
}

####
 # Tutorial Example Pllugin
 #   The is part of a tutorial exmaple plugin that is used to show how to create Advance Workflow Plugins that use R code
 #   to compute and display resutls from clinical data. It's parts are distributed in the core code for Rmodules in the following
 #   locations:
 #     grails-app/controllers/com/recomdata/transmart/data/association/TutorialExampleController.groovy - starts here
 #     src/groovy/jobs/TutorialExample.groovy - the top level job controller
 #     web-app/Rscripts/TutorialExample/BuildTutorialData.R - used as part of job
 #     web-app/Rscripts/TutorialExample/TutorialGraphLoader.R - used as part of job
 #     grails-app/views/plugin/TutorialExample.gsp - the view holder
 #     grails-app/views/plugin/_tutorialExample_out.gsp - the data output template
 #     web-app/js/plugin/TutorialExample.js - defines loaders and other functional support for view
####