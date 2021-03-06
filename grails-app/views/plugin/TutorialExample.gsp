%{-- Include js lib for tutorialExample dynamically.                        --}%
%{-- Note: the functions of tutorialExampleView are defined in this module. --}%
%{--     For example, see the clear button of each input field.             --}%

%{-- This GSP layout is part of a tutorial example. See comment block at the end of this code file--}%

<r:require modules="tutorial_example"/>
<r:layoutResources disposition="defer"/>

<div id="introductionDiv">
    %{-- ************************************************************************************************* --}%
    %{-- introduction -- more details are availabe in the comments of the controller located at:           --}%
    %{--         grails-app/controllers/com/recomdata/transmart/data/association/TutorialExampleController --}%
    %{-- ************************************************************************************************* --}%
    <h2>Tutorial Example Pllugin - Introduction</h2>
    <p>
    This Advanced Analysis plugin is part of a Tutorial on creating a such plugins. See the controler file
    at com/recomdata/transmart/data/association/TutorialExampleController for more details.</p>
    <p>
    This Example Advanced Analysis, lets you select any clinical-date leaf-node concepts and will generate a histogram of the
    counts of the concepts, and an output table of the values. The output table will be in the Download raw R data
    zip file and will be labeled outputfile.txt. Note: as a short-cut for including all the leaf-nodes of a folder
    that contains only leaf-nodes, you can drag the folder into the selection box.</p>
</div>

<div id="analysisWidget">

    %{--help and title--}%
    <h2>
        Variable Selection
        <a href='JavaScript:D2H_ShowHelp(1291,helpURL,"wndExternal",CTXT_DISPLAY_FULLHELP )'>
            <img src="${resource(dir: 'images', file: 'help/helpicon_white.jpg')}" alt="Help"/>
        </a>
    </h2>

    <form id="analysisForm">
        <div class="container">

            %{-- *************************************************************************************************  --}%
            %{-- Left input feild only, in this concept; see LineGraph.gsp for an example using both left and right --}%
            %{-- *************************************************************************************************  --}%
            <div class="left">
                <fieldset class="inputFields">

                    %{--Time/Measurement Concepts--}%
                    <div class="highDimContainer">
                        <h3>Time/Measurement Concepts</h3>
                        <span class="hd-notes">

                          Drag one or more multiple numerical or high dimensional nodes from the tree into box below.

                        </span>
                        <div id='divDependentVariable' class="queryGroupIncludeSmall highDimBox"></div>
                        <div class="highDimBtns">
                            <button type="button" onclick="highDimensionalData.gather_high_dimensional_data('divDependentVariable', true)">High Dimensional Data</button>
                            <button type="button" onclick="tutorialExampleView.clear_high_dimensional_input('divDependentVariable')">Clear</button>
                        </div>
                        <input type="hidden" id="dependentVarDataType">
                        <input type="hidden" id="dependentPathway">
                    </div>

                    %{--Display dependent variable--}%
                    <div id="displaydivDependentVariable" class="dependentVars"></div>

                </fieldset>
            </div>

        %{-- ************************************************************************************************* --}%
        %{-- Tool Bar --}%
        %{-- ************************************************************************************************* --}%

        <fieldset class="toolFields">
            <input type="button" value="Run" onClick="tutorialExampleView.submit_job(this.form);" class="runAnalysisBtn">
        </fieldset>
    </form>
</div>


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