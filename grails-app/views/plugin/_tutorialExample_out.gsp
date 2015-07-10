<h2>Tutorial Example Graph</h2>

%{-- This GSP layout is part of a tutorial example. See comment block at the end of this code file --}%

<p>
    <g:each var="location" in="${imageLocations}">
        <g:img file="${location}" class="img-result-size"></g:img> <br/>
    </g:each>

    <g:if test="${zipLink}">
        <a class='AnalysisLink' class='downloadLink' href="${resource(file: zipLink)}">Download raw R data</a>
    </g:if>
</p>

%{-- Tutorial Example Pllugin                                                                                       --}%
%{-- The is part of a tutorial exmaple plugin that is used to show how to create Advance Workflow Plugins           --}%
%{-- that use R code                                                                                                --}%
%{-- to compute and display resutls from clinical data. It's parts are distributed in the core code for             --}%
%{-- Rmodules in the following locations:                                                                           --}%
%{--   grails-app/controllers/com/recomdata/transmart/data/association/TutorialExampleController.groovy-starts here --}%
%{--   src/groovy/jobs/TutorialExample.groovy - the top level job controller                                        --}%
%{--   web-app/Rscripts/TutorialExample/BuildTutorialData.R - used as part of job                                   --}%
%{--   web-app/Rscripts/TutorialExample/TutorialGraphLoader.R - used as part of job                                 --}%
%{--   grails-app/views/plugin/TutorialExample.gsp - the view holder                                                --}%
%{--   grails-app/views/plugin/_tutorialExample_out.gsp - the data output template                                  --}%
%{--   web-app/js/plugin/TutorialExample.js - defines loaders and other functional support for view                 --}%