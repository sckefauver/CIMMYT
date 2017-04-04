// Macro to batch process and calculate the Normalized Green Red Difference Index (NGRDI)
// and Triangular Greeness (TGI) vegetation indexes from RGB components based on Hunt et al. 2014. 

input = "$P{batch_input}";
ngdriDir = "$P{save_ngrdi_dir}";
trgiDir = "$P{save_tgi_dir}";
saveNgrdi = $P{save_ngrdi_images};
saveTgi = $P{save_tgi_images};
list = getFileList(input);

setBatchMode(true);
run("Clear Results");

for (i = 0; i < list.length; i++) {
	if(isImage(list[i]) {
		action(list[i]);
    }
}

selectWindow("Results")
saveAs("Results", "$P{save_results_file}");
run("Close");

call("java.lang.System.gc");

setBatchMode(false);

function action(filename) {
    open(input + filename);
    original = getImageID;
    title = getTitle; 
    imageName = File.nameWithoutExtension;

    selectImage(original);
    run("Split Channels");
    selectWindow(filename + " (red)");
    rename("IMG_(red)");
    selectWindow(filename + " (green)");
    rename("IMG_(green)");
    selectWindow(filename + " (blue)");
    rename("IMG_(blue)");

    /*
        Hunt, E. Raymond, et al. "A visible band index for remote sensing
        leaf chlorophyll content at the canopy scale." International Journal
        of Applied Earth Observation and Geoinformation 21 (2013): 103-112.
    */
    imageCalculator("Add create 32-bit", "IMG_(green)","IMG_(red)");
    rename("ResultG+R");
    imageCalculator("Subtract create 32-bit", "IMG_(green)","IMG_(red)");
    rename("ResultG-R");
    imageCalculator("Divide create 32-bit", "ResultG-R","ResultG+R");
    rename("IMG_NGRDI");
    close("Result*");

    /*
        Hunt Jr, E. Raymond, et al. "Evaluation of digital photography from
        model aircraft for remote sensing of crop biomass and nitrogen status."
        Precision Agriculture 6.4 (2005): 359-378.
    */
    imageCalculator("Subtract create 32-bit", "IMG_(red)","IMG_(blue)");
    rename("ResultR-B");
    imageCalculator("Subtract create 32-bit", "IMG_(red)","IMG_(green)");
    rename("ResultR-G");
    selectWindow("ResultR-G");
    run("Multiply...", "value=190");
    selectWindow("ResultR-B");
    run("Multiply...", "value=120");
    run("Calculator Plus", "i1=ResultR-G i2=ResultR-B operation=[Subtract: i2 = (i1-i2) x k1 + k2] k1=-0.5 k2=0 create");
    rename("IMG_TGI");
    close("Result*");
    close("IMG_(*");

    run("Set Measurements...", "mean standard redirect=None decimal=3");
    run("Input/Output...", "jpeg=85 gif=-1 file=.xls use_file copy_column copy_row save_column save_row");
    selectWindow("IMG_NGRDI");
    run("Select All");
    run("Measure");

    if(saveNgrdi) {
        saveAs("Tiff", ngdriDir + imageName + "_NGRDI");
    }

    close();
    
    selectWindow("IMG_TGI");
    run("Select All");
    run("Measure");
    
    if(saveTgi) {
        saveAs("Tiff", trgiDir + imageName + "_TGI");
    }
	
	setResult("Image", i+i, imageName);
	setResult("Image", i+(i+1), imageName);
	updateResults();
	
    close();
}

function isImage(filename) {
    if(endsWith(toLowerCase(filename), ".tif")||
       endsWith(toLowerCase(filename), ".tiff") ||
       endsWith(toLowerCase(filename), ".jpg") ||
       endsWith(toLowerCase(filename), ".jpeg")) {
       return true;
    }
    else {
        return false;
    }
}