//Macro to batch process apply hue (hsb/hsv) thresholding of vegetation components
//start out with a clean slate

//TODO we can run this command from within the plugin
//call("java.lang.System.gc");
run("Clear Results");

//batch processing intro setup
//where the rgb images to be processed are stored
input = "$P{batch_input}";
output = "$P{batch_output}";
saveHsb = $P{save_hsb_images};
hsbDir = "$P{save_hsb_dir}";
list = getFileList(input);

for (i = 0; i < list.length; i++) {
        action(input, output, list[i]);
}

selectWindow("Results")
run("Input/Output...", "jpeg=85 gif=-1 file=.xls use_file copy_column copy_row save_column save_row");
saveAs("Results", "$P{save_results_file}");

function action(input, output, filename) {
        //open and get id and base name
        open(input + filename);
        //get info from original image
        original = getImageID;
        title = getTitle; 
        base = File.nameWithoutExtension;
        //perform rgb threshold classification and make masks
        selectImage(original);
        //convert to HSB images for processing
        run("HSB Stack");
        run("Stack to Images");
        //create and save soil mask from rgb brightness
        selectWindow("Hue");
        close();
        selectWindow("Saturation");
        close();
        selectWindow("Brightness");
        setAutoThreshold("Default");
        run("Convert to Mask");
        run("Invert");
        run("Despeckle");
        rename("soil");

        //create threshold classification of green veg
        //open and get id and base name
        open(input + filename);
        //get info from original image
        original = getImageID;
        title = getTitle; 
        base = File.nameWithoutExtension;
        //split channels        
        selectImage(original);
        //split channels
        run("HSB Stack");
        run("Stack to Images");
        //create and save greenveg mask from hsb
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
        rename("greenveg");
        //create threshold classification of yellowveg from hsb
        //open and get id and base name
        open(input + filename);
        //get info from original image
        original = getImageID;
        title = getTitle; 
        base = File.nameWithoutExtension;
        //split channels        
        selectImage(original);
        //split the channels	
        run("HSB Stack");
        run("Stack to Images");
        //create and save yellowveg mask from hsb
        selectWindow("Saturation");
        close();
        selectWindow("Brightness");
        close();
        //Threshold images	
        selectWindow("Hue");
        setAutoThreshold("Default");
        setThreshold(35, 44);
        setOption("BlackBackground", false);
        run("Convert to Mask");
        run("Invert");
        run("Despeckle");
        rename("yellowveg");
        //create threshold classification of bleachedveg from hsb
        //open and get id and base name
        open(input + filename);
        //get info from original image
        original = getImageID;
        title = getTitle; 
        base = File.nameWithoutExtension;
        //split channels        
        selectImage(original);
        //split the images	
        run("HSB Stack");
        run("Stack to Images");
        //create and save bleachedveg mask from hsb
        selectWindow("Hue");
        close();
        selectWindow("Saturation");
        close();
        //Threshold bleached veg using brightness	
        selectWindow("Brightness");
        setAutoThreshold("Default");
        setThreshold(192, 255);
        setOption("BlackBackground", false);
        run("Convert to Mask");
        run("Invert");
        run("Despeckle");
        rename("bleachedveg");
        //collect area fraction measurements of each image into one file
        //...and optionally save and close all images before next in the batch
        run("Set Measurements...", "area_fraction redirect=None decimal=3");
        selectWindow("soil");
        
        if(saveHsb) {
                saveAs("Tiff", hsbDir + base + "_soil-hsb");
        }
        
        run("Invert");
        run("Select All");
        run("Measure");
        close();
        selectWindow("greenveg");
        
        if(saveHsb) {
                saveAs("Tiff", hsbDir + base + "_greenveg-hsb");
        }
        
        run("Invert");
        run("Select All");
        run("Measure");
        close();
        selectWindow("yellowveg");
        
        if(saveHsb) {
                saveAs("Tiff", hsbDir + base + "_yellowveg-hsb");
        }
        
        run("Invert");
        run("Select All");
        run("Measure");
        close();
        selectWindow("bleachedveg");
        
        if(saveHsb) {
                saveAs("Tiff", hsbDir + base + "_bleachedveg-hsb");
        }
        
        run("Invert");
        run("Select All");
        run("Measure");
        close();
        
        //TODO might not need to do this.
        //clean up memory after each file
        //call("java.lang.System.gc");
        //memory should be clear and all windows closed
        //end of process move on to next in batch
}

