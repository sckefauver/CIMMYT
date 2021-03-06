//Macro to batch process apply hue (HSB/HSV) thresholding of vegetation components

var input = "$P{batch_input}";
var saveImages = $P{save_images};
var imagesDir = "$P{save_images_dir}";
var list = getFileList(input);

setBatchMode(true);

run("Clear Results");

for (i = 0; i < list.length; i++) {
	if(isImage(list[i])) {
		action(list[i]);
    }
}

selectWindow("Results");
run("Input/Output...", "jpeg=85 gif=-1 file=.csv use_file copy_column copy_row save_column save_row");
saveAs("Results", "$P{save_results_file}");
run("Close");
call("java.lang.System.gc");

setBatchMode(false);

function action(imageName) {
        run("Set Measurements...", "area_fraction redirect=None decimal=3");
        open(input + imageName);
        originalID = getImageID();
        fileName = File.nameWithoutExtension;

        processSoil(originalID, fileName);
        processGreenVeg(originalID, fileName);
        processYellowVeg(originalID, fileName);
        processBleachedVeg(originalID, fileName);

        selectImage(originalID);
        close();
}

function processSoil(originalID, fileName) {
        selectImage(originalID);
        run("Duplicate...", fileName+"-1");
        soilImageID = getImageID();
        selectImage(soilImageID);
        run("HSB Stack");
        run("Stack to Images");
        selectWindow("Hue");
        close();
        selectWindow("Saturation");
        close();
        selectWindow("Brightness");
        setAutoThreshold("Default");
        run("Convert to Mask");
        run("Invert");
        run("Despeckle");

        if(saveImages) {
                saveAs("Tiff", imagesDir + fileName + "_soil-hsb");
        }

        run("Invert");
        run("Select All");
        run("Measure");
        close();
}

function processGreenVeg(originalID, fileName) {
        selectImage(originalID);
        run("Duplicate...", fileName+"-2");
        greenVegImageID = getImageID();
        selectImage(greenVegImageID);
        run("HSB Stack");
        run("Stack to Images");
        selectWindow("Saturation");
        close();
        selectWindow("Brightness");
        close();
        selectWindow("Hue");
        setAutoThreshold("Default");
        setThreshold(45, 80);
        setOption("BlackBackground", false);
        run("Convert to Mask");
        run("Invert");
        run("Despeckle");

        if(saveImages) {
                saveAs("Tiff", imagesDir + fileName + "_greenveg-hsb");
        }

        run("Invert");
        run("Select All");
        run("Measure");
        close();
}

function processYellowVeg(originalID, fileName) {
        selectImage(originalID);
        run("Duplicate...", fileName+"-3");
        yellowVegImageID = getImageID();
        selectImage(yellowVegImageID);
        run("HSB Stack");
        run("Stack to Images");
        selectWindow("Saturation");
        close();
        selectWindow("Brightness");
        close();
        selectWindow("Hue");
        setAutoThreshold("Default");
        setThreshold(35, 44);
        setOption("BlackBackground", false);
        run("Convert to Mask");
        run("Invert");
        run("Despeckle");

        if(saveImages) {
                saveAs("Tiff", imagesDir + fileName + "_yellowveg-hsb");
        }

        run("Invert");
        run("Select All");
        run("Measure");
        close();
}

function processBleachedVeg(originalID, fileName) {
        selectImage(originalID);
        run("Duplicate...", fileName+"-3");
        bleachedVegImageID = getImageID();
        selectImage(bleachedVegImageID);
        run("HSB Stack");
        run("Stack to Images");
        selectWindow("Hue");
        close();
        selectWindow("Saturation");
        close();
        selectWindow("Brightness");
        setAutoThreshold("Default");
        setThreshold(192, 255);
        setOption("BlackBackground", false);
        run("Convert to Mask");
        run("Invert");
        run("Despeckle");

        if(saveImages) {
                saveAs("Tiff", imagesDir + fileName + "_bleachedveg-hsb");
        }

        run("Invert");
        run("Select All");
        run("Measure");
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