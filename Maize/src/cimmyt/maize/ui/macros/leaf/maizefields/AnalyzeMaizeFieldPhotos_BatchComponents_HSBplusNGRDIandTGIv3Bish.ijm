//Macro to batch process apply hue (hsb/hsv) thresholding of vegetation components 
//Components include soil bed, greenveg, chlorosis, necrosis
//start out with a clean slate
	call("java.lang.System.gc");
	run("Clear Results");
//set up the log file for transposing the results
	print("\\Clear");
	print("Analyze Maize Plot Photos Batch Components HSB plus NGRDI and TGI for MLN Trials");
a = newArray("id", "filename", "soil", "greenveg", "greenerveg", "chlorosis", "necrosis", "Hue", "Saturation", "Brightness", "L*", "a*", "b*", "NGRDI", "TGI", "StepwisePlotMLN", "CARTPlotMLN", "CIPlotMLN", "EnsembleLeafMLN");
Array.print(a);
//batch processing intro setup
//where the rgb images to be processed are stored
	input = "D:\\CIMMYT\\Fiji_Input_Output\\MLNPlotBatchInputs\\";
//outputs base directory
	output = "D:\\CIMMYT\\Fiji_Input_Output\\MLNPlotBatchOutputs2\\";
//start batching	
setBatchMode(true); 
list = getFileList(input);
for (i = 0; i < list.length; i++)
       action(input, output, list[i]);
function action(input, output, filename) {
//Start batch functions
//open and get id and base name
    open(input + filename);
//get info from original image
    original = getImageID;
    title = getTitle; 
    base = File.nameWithoutExtension;
//convert to HSB image space and split channels for processing
selectWindow(filename);
	run("HSB Stack");
	run("32-bit");
selectWindow(filename);
	run("Stack to Images");
//create soil bed area mask from HSB Saturation
selectWindow("Hue");
	close("Hue");
selectWindow("Saturation");
	close("Saturation");
selectWindow("Brightness");
	setThreshold(0,128);
	setOption("BlackBackground", false);
	run("Convert to Mask");
	run("Despeckle");
	run("Invert");
	rename("soil");
selectWindow("soil");
run("Set Measurements...", "area_fraction redirect=None decimal=2");
	run("Select All");
	run("Measure");
soil = getResult("%Area");
selectWindow("soil");
	run("Duplicate...", "title=soil-save");
selectWindow("soil-save");
	saveAs("Tiff", output + base + "_soil");
//
//create soil bed area mask from HSB Saturation
selectWindow("soil");
	run("Make Binary");
	run("Fill Holes");
	run("Convert to Mask");
	run("Create Selection");
	run("Make Inverse");
//create threshold classification of green vegetation (GA or greenveg)
//open and get id and base name
    open(input + filename);
//convert to HSB image space and split channels for processing
selectWindow(filename);
	run("HSB Stack");
	run("32-bit");
selectWindow(filename);
	run("Stack to Images");
//create greenveg mask from HSB Hue
selectWindow("Saturation");
	close("Saturation");
selectWindow("Brightness");
	close("Brightness");
selectWindow("Hue");
	setThreshold(42.5, 127.5);
	setOption("BlackBackground", false);
	rename("greenveg");
// Measure and greenveg GA based on the soil mask selection of leaf area
selectWindow("soil");
	run("Set Measurements...", "area_fraction redirect=greenveg decimal=2");
	run("Measure");
greenveg = getResult("%Area");
selectWindow("greenveg");	
	run("Convert to Mask");
	run("Invert");
	run("Despeckle");
	imageCalculator("Max create", "greenveg","soil");
selectWindow("Result of greenveg");
	rename("greenvegmasked");
	close("greenveg");
selectWindow("greenvegmasked");
	saveAs("Tiff", output + base + "_greenveg-GA");	
//
//create threshold classification of greener green vegetation (GGA or greenerveg)
//open and get id and base name
    open(input + filename);
//convert to HSB image space and split channels for processing
selectWindow(filename);
	run("HSB Stack");
	run("32-bit");
selectWindow(filename);
	run("Stack to Images");
//create greenerveg mask from HSB Hue
selectWindow("Saturation");
	 close("Saturation");
selectWindow("Brightness");
	 close("Brightness");
selectWindow("Hue");
	setThreshold(56.67, 127.5);
	setOption("BlackBackground", false);
	rename("greenerveg");
// Measure and greenerveg GGA based on the soil mask selection of leaf area
selectWindow("soil");
	run("Set Measurements...", "area_fraction redirect=greenerveg decimal=2");
	run("Measure");
greenerveg = getResult("%Area");
selectWindow("greenerveg");	
	run("Convert to Mask");
	run("Invert");
	run("Despeckle");
	imageCalculator("Max create", "greenerveg","soil");
selectWindow("Result of greenerveg");
	rename("greenervegmasked");
	close("greenerveg");
selectWindow("greenervegmasked");
	saveAs("Tiff", output + base + "_greenerveg-GGA");
//		
//create threshold classification of chlorosis from HSB Hue
//open and get id and base name
    open(input + filename);
//convert to HSB image space and split channels for processing	
selectWindow(filename);
	run("HSB Stack");
	run("32-bit");
selectWindow(filename);
	run("Stack to Images");
//create chlorosis mask from HSB Hue thresholding
selectWindow("Saturation");
	close("Saturation");
selectWindow("Brightness");
	close("Brightness");
selectWindow("Hue");
	setThreshold(28.34, 42.5);
	setOption("BlackBackground", false);
	rename("chlorosis");
// Measure chlorosis based on the soil mask selection of leaf area
selectWindow("soil");
	run("Set Measurements...", "area_fraction redirect=chlorosis decimal=2");
	run("Measure");	
chlorosis = getResult("%Area");	
selectWindow("chlorosis");
	run("Convert to Mask");
	run("Invert");
	run("Despeckle");
	imageCalculator("Max create", "chlorosis","soil");
selectWindow("Result of chlorosis");
	rename("chlorosismasked");
selectWindow("chlorosismasked");
	saveAs("Tiff", output + base + "_chlorosis");
// 	
//create threshold classification of necrosis from HSB
//open and get id and base name
    open(input + filename);
//convert to HSB image space and split channels for processing
selectWindow(filename);
	run("HSB Stack");
	run("32-bit");
selectWindow(filename);
	run("Stack to Images");
selectWindow("Brightness");
	close("Brightness");
selectWindow("Saturation");
	close("Saturation");
//Threshold necrosis using HSB Hue	
selectWindow("Hue");
	setAutoThreshold("Default");
	setThreshold(0, 28.33);
	setOption("BlackBackground", false);
	rename("necrosis");
// Measure necrosis based on the soil mask selection of leaf area	
selectWindow("soil");
	run("Set Measurements...", "area_fraction redirect=necrosis decimal=2");
	run("Measure");
necrosis = getResult("%Area");	
selectWindow("necrosis");	
	run("Convert to Mask");
	run("Invert");
	run("Despeckle");
	imageCalculator("Max create", "necrosis","soil");
selectWindow("Result of necrosis");
	rename("necrosismasked");
	close("necrosis");
selectWindow("necrosismasked");
	saveAs("Tiff", output + base + "_necrosis");
//
//collect area fraction measurements of each image into one file
//...and optionally save and close all images before next in the batch
//
//Use soil image to get leaf area only stats from the HSB and CIELab image spaces
	call("java.lang.System.gc");
	run("Clear Results");
//open and get id and base name
    open(input + filename);
//Create HSB images
selectWindow(filename);
	run("HSB Stack");
	run("32-bit");
selectWindow(filename);
	run("Stack to Images");
// Measure and define HSB one image at a time based on the soil mask selection
selectWindow("soil");
	run("Set Measurements...", "mean redirect=Hue decimal=2");
	run("Measure");
H = getResult("Mean");
	run("Set Measurements...", "mean redirect=Saturation decimal=2");
	run("Measure");
S = getResult("Mean");
	run("Set Measurements...", "mean redirect=Brightness decimal=2");
	run("Measure");
B = getResult("Mean");
//close HSB
selectWindow("Hue");
	close("Hue");
selectWindow("Saturation");	
	close("Saturation");
selectWindow("Brightness");
	close("Brightness");
//open and get id and base name
    open(input + filename);
//Create Lab images
selectWindow(filename);
	run("Lab Stack");
selectWindow(filename);
	run("Stack to Images");
// Measure and define CIELab one image at a time based on the soil mask selection
selectWindow("soil");
	run("Set Measurements...", "mean redirect=L* decimal=2");
	run("Measure");
L = getResult("Mean");
	run("Set Measurements...", "mean redirect=a* decimal=2");
	run("Measure");
a = getResult("Mean");
	run("Set Measurements...", "mean redirect=b* decimal=2");
	run("Measure");
b = getResult("Mean");
//close CIELab
selectWindow("L*");
	close("L*");
selectWindow("a*");	
	close("a*");
selectWindow("b*");
	close("b*");
//clean up memory after each file
	call("java.lang.System.gc");
//Calculate the Normalized Green Red Difference Index (NGRDI) and Triangular Greeness (TGI) for the leaf area
//These are vegetation indexes from RGB components based on Hunt et al. 2014. 
//start out with a clean slate
	call("java.lang.System.gc");
	run("Clear Results");
//open and get id and base name
    open(input + filename);
//perform rgb threshold classification and make masks
selectWindow(filename);
//convert to separate red, green and blue images for processing
	run("Split Channels");
selectWindow(filename + " (red)");
	rename("IMG_(red)");
selectWindow(filename + " (green)");
	rename("IMG_(green)");
selectWindow(filename + " (blue)");
	rename("IMG_(blue)");
//use image math calculator plus functions to calculate the NGRDI
	imageCalculator("Add create 32-bit", "IMG_(green)","IMG_(red)");
	rename("ResultG+R");
	imageCalculator("Subtract create 32-bit", "IMG_(green)","IMG_(red)");
	rename("ResultG-R");
	imageCalculator("Divide create 32-bit", "ResultG-R","ResultG+R");
	rename("IMG_NGRDI");
selectWindow("IMG_NGRDI");
	run("Multiply...", "value=100");
//Calculate the mean NGRDI value for the total of the leaf area
selectWindow("soil");
	run("Set Measurements...", "mean redirect=IMG_NGRDI decimal=2");
	run("Measure");
NGRDI = getResult("Mean");	
//use image math calculator plus functions to calculate the TGI
	imageCalculator("Add create 32-bit", "IMG_(red)","IMG_(blue)");
	rename("ResultR-B");
	imageCalculator("Subtract create 32-bit", "IMG_(red)","IMG_(green)");
	rename("ResultR-G");
selectWindow("ResultR-G");
	run("Multiply...", "value=190");
selectWindow("ResultR-B");
	run("Multiply...", "value=120");
	run("Calculator Plus", "i1=ResultR-G i2=ResultR-B operation=[Subtract: i2 = (i1-i2) x k1 + k2] k1=-0.5 k2=0 create");
	rename("IMG_TGI");
//Calculate the mean TGI value for the total of the leaf area
selectWindow("soil");
	run("Set Measurements...", "mean redirect=IMG_TGI decimal=2");
	run("Measure");
TGI = getResult("Mean");
//Optionally save and close all images before next in the batch
selectWindow("IMG_NGRDI");
saveAs("Tiff", output+base+"_NGRDI");
selectWindow("IMG_TGI");
saveAs("Tiff", output+base+"_TGI");
//clean up memory after each file
	run("Clear Results");
	run("Close All");
	call("java.lang.System.gc");	
//memory should be clear and all windows closed except for Results and Log files
//copy the data over to an organized transposed log of 4 measurements from each image 
//Calculate the linear model for PlotMLN = lm(formula = PlotMLN ~ greenveg + greenerveg + chlorosis + necrosis + Hue + Saturation + Brightness + L. + a. + NGRDI; R-squared:  0.6462
StepwisePlotMLN = 24.433074 + -0.186087*greenveg + 0.029204*greenerveg + -0.197714*chlorosis + -0.214809*necrosis + -0.098555*H + 0.011697*S + -0.098701*B + 0.281423*L + 0.042320*a + -0.144420*NGRDI;
//Calculate the results of the rpart CART regression tree model using nested if then else statements R-squared (using separate testing and training data): 0.6695
if (NGRDI < -2.56345) {
        if (chlorosis < 22.79 ) {
        if (NGRDI >= 22.79) {
        CARTPlotMLN = 2.672;
}
else {
        CARTPlotMLN = 2.957;
}
}
else {
        if (TGI < 12555.37 ) {
        CARTPlotMLN = 3.03;
}
else {
        if (NGRDI >= 0.7582 ) {
        CARTPlotMLN = 3.228;
}
else {
        CARTPlotMLN = 3.513;
}
}
}
}
else {
        if (chlorosis >= 10.79) {
        if (NGRDI >= 6.238) {
        if (TGI < 9014) {
        CARTPlotMLN = 3.359;
}
else {
        CARTPlotMLN = 3.859;
}
}
else {
        CARTPlotMLN = 3.888;
}
}
else {
        if (necrosis < 88.95) {
        CARTPlotMLN = 3.941;
}
else {
        CARTPlotMLN = 4.444;
}
}
}
//
////Calculate the results of the conditional inference tree "PartyPlotMLN" using nested if then else statements R-squared (using separate testing and training data): 
/*
//avoid values excluded by model with dummy average value
if (soil > 0) {
	PartyPlotMLN = (StepwisePlotMLN+CARTPlotMLN)/2;
}
else {
        PartyPlotMLN = (StepwisePlotMLN+CARTPlotMLN)/2;
}
*/
//apply Conditional Inference Tree model using terminal mode means
if (NGRDI <= -2.582) {
        if (greenerveg <= 4.607) {
        if (soil <= 30.957) {
        PartyPlotMLN = 4.047;
}        
else {
        if (TGI <= 8922.66) {
        if (NGRDI <= -6.254) {
        PartyPlotMLN = 3.778;
}
else {
        PartyPlotMLN = 3.435;
}
        }
else {
        PartyPlotMLN = 3.957;
}
}
}
else {
        if (B <= 91.591) {
        PartyPlotMLN = 3.296;
}
else {
        PartyPlotMLN = 3.75;
}
}
}
else {
        if (greenerveg <= 15.634) {
        if (chlorosis <= 26.747) {
        PartyPlotMLN = 3.217;
}
else {
        if (TGI <= 8163.437) {
        PartyPlotMLN = 3.071;
}
else {
        if (NGRDI <= -0.186) {
        PartyPlotMLN = 3.616;
}
else {
        PartyPlotMLN = 3.367;
}
}
}
}
else {
        if (greenerveg <= 42.425) {
        if (chlorosis <= 14.055) {
        if (chlorosis <= 15.127) {
        PartyPlotMLN = 2.77;
}
else {
        PartyPlotMLN = 2.936;
}
}
else {
        if (B <= 150.539) {
        PartyPlotMLN = 3;
}
else {
        PartyPlotMLN = 3.327;
}
}
}
else {
        PartyPlotMLN = 2.554;
}
}
}
//
//Calculate the Ensemble average of the three different PlotMLN predictive models
EnsemblePlotMLN = (StepwisePlotMLN + CARTPlotMLN + PartyPlotMLN)/3;
r = newArray(i, filename, soil, greenveg, greenerveg, chlorosis, necrosis, H, S, B, L, a, b, NGRDI, TGI, StepwisePlotMLN, CARTPlotMLN, PartyPlotMLN, EnsemblePlotMLN);
Array.print(r);
//
//end of process move on to next in batch
}
setBatchMode(false);
//save the result from the log window printouts
run("Input/Output...", "jpeg=85 gif=-1 file=.csv use_file copy_column copy_row save_column save_row");
//Log with ordered Results 
selectWindow("Log");  
saveAs("Text", output + "MaizePlotPhotos-Soil-GA-GGA-Chlorosis-Necrosis-HSB-CIELAB-NGRDI-TGI-PlotMLN-ResultsV3.csv"); 
