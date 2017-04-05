//Macro to batch process calculate new vegetation indexes

setBatchMode(true);

run("Clear Results");

var input = "$P{batch_input}";
var saveImages = $P{save_images};
var imagesDir = "$P{save_images_dir}";
var list = getFileList(input);

for (i = 0; i < list.length; i++) {
	if(isImage(list[i]) {
		action(list[i]);
    }
}

selectWindow("Results")
run("Input/Output...", "jpeg=100 gif=-1 file=.csv use_file copy_column copy_row save_column save_row");
saveAs("Results","$P{save_results_file}");
run("Close");
call("java.lang.System.gc");

setBatchMode(false);

function action(imageName) {
    run("Set Measurements...", "mean standard redirect=None decimal=3");
    open(input + imageName);
    originalID = getImageID;
    title = getTitle;
    fileName = File.nameWithoutExtension;

    selectImage(originalID);
    rename("IMG_Index1");
	
	/*
	 * Add your own vegetation index calculator here
	 */
	
    selectWindow("IMG_Index1");
    run("Select All");
    run("Measure");

    if(saveImages) {
        saveAs("Tiff", imagesDir + fileName + "_index1");
    }

    close();
    close("IMG_(*");
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