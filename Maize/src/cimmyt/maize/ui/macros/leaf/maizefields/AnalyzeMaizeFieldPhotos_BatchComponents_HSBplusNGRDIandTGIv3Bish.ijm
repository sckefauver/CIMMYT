//Macro to batch process apply hue (hsb/hsv) thresholding of vegetation components 
//Components include soil bed, greenveg, chlorosis, necrosis

run("Input/Output...", "jpeg=85 gif=-1 file=.csv use_file copy_column copy_row save_column save_row");

var input = "$P{batch_input}";
var output = "$P{batch_output}";
var resultsFile = "$P{save_results_file}";
var list = getFileList(input);
var saveSoil = $P{save_soil};
var saveGreenVeg = $P{save_green_veg};
var saveGreenerVeg = $P{save_greener_veg};
var saveChlorosis = $P{save_chlorosis};
var saveNecrosis = $P{save_necrosis};
var saveNgrdi = $P{save_ngrdi};
var saveTgi = $P{save_tgi};
var fileCount = getImageFileCount(list);

setBatchMode(true);
run("Clear Results");

//Initialize data arrays
var idArr = newArray(fileCount);
var filenameArr = newArray(fileCount);
var soilArr = newArray(fileCount);
var greenvegArr = newArray(fileCount);
var greenervegArr = newArray(fileCount);
var chlorosisArr = newArray(fileCount);
var necrosisArr = newArray(fileCount);
var hueArr = newArray(fileCount);
var saturationArr = newArray(fileCount);
var brightnessArr = newArray(fileCount);
var lArr = newArray(fileCount);
var aArr = newArray(fileCount);
var bArr = newArray(fileCount);
var ngrdiArr = newArray(fileCount);
var tgiArr = newArray(fileCount);
var stepwisePlotMlnArr = newArray(fileCount);
var cartPlotMlnArr = newArray(fileCount);
var ciPlotMlnArr = newArray(fileCount);
var ensembleLeafMlnArr = newArray(fileCount);
var n = 0;

for (i = 0; i < list.length; i++) {
	if(isImage(list[i])) {
       action(input, output, list[i]);
	}
}

printResults();

setBatchMode(false);
call("java.lang.System.gc");

function action(input, output, filename) {
    open(input + filename);
    original = getImageID;
    title = getTitle; 
    imageName = File.nameWithoutExtension;
    
	//Convert to HSB image space and split channels for processing
	selectWindow(filename);
	run("HSB Stack");
	run("32-bit");
	selectWindow(filename);
	run("Stack to Images");
	
	//Create soil bed area mask from HSB Saturation
	selectWindow("Hue");
	close();
	selectWindow("Saturation");
	close();
	selectWindow("Brightness");
	setThreshold(0,128);
	setOption("BlackBackground", false);
	run("Convert to Mask");
	run("Despeckle");
	run("Invert");
	rename("soil");
	selectWindow("soil");
	run("Set Measurements...", "area_fraction redirect=None decimal=4");
	run("Select All");
	run("Measure");
	soil = getResult("%Area");
	selectWindow("soil");
	run("Duplicate...", "title=soil-save");
	selectWindow("soil-save");

	if(saveSoil) {
		saveAs("Tiff", output + imageName + "_soil");
	}
	close();

	//Create soil bed area mask from HSB Saturation
	selectWindow("soil");
	run("Make Binary");
	run("Fill Holes");
	run("Convert to Mask");
	run("Create Selection");
	run("Make Inverse");
	
	//Create threshold classification of green vegetation (GA or greenveg)
	//Convert to HSB image space and split channels for processing
    open(input + filename);
	selectWindow(filename);
	run("HSB Stack");
	run("32-bit");
	selectWindow(filename);
	run("Stack to Images");
	
	//Create greenveg mask from HSB Hue
	selectWindow("Saturation");
	close();
	selectWindow("Brightness");
	close();
	selectWindow("Hue");
	setThreshold(42.5, 127.5);
	setOption("BlackBackground", false);
	rename("greenveg");
	
	//Measure and greenveg GA based on the soil mask selection of leaf area
	selectWindow("soil");
	run("Set Measurements...", "area_fraction redirect=greenveg decimal=4");
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
	
	if(saveGreenVeg) {
		saveAs("Tiff", output + imageName + "_greenveg-GA");
	}
	close();

	//Create threshold classification of greener green vegetation (GGA or greenerveg)
	//Convert to HSB image space and split channels for processing
    open(input + filename);
	selectWindow(filename);
	run("HSB Stack");
	run("32-bit");
	selectWindow(filename);
	run("Stack to Images");
	
	//Create greenerveg mask from HSB Hue
	selectWindow("Saturation");
	close();
	selectWindow("Brightness");
	close();
	selectWindow("Hue");
	setThreshold(56.67, 127.5);
	setOption("BlackBackground", false);
	rename("greenerveg");
	
	//Measure and greenerveg GGA based on the soil mask selection of leaf area
	selectWindow("soil");
	run("Set Measurements...", "area_fraction redirect=greenerveg decimal=4");
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

	if(saveGreenerVeg) {
		saveAs("Tiff", output + imageName + "_greenerveg-GGA");
	}
	close();

	//Ccreate threshold classification of chlorosis from HSB Hue
	//Convert to HSB image space and split channels for processing
    open(input + filename);
	selectWindow(filename);
	run("HSB Stack");
	run("32-bit");
	selectWindow(filename);
	run("Stack to Images");
	
	//Create chlorosis mask from HSB Hue thresholding
	selectWindow("Saturation");
	close();
	selectWindow("Brightness");
	close();
	selectWindow("Hue");
	setThreshold(28.34, 42.5);
	setOption("BlackBackground", false);
	rename("chlorosis");
	
	//Measure chlorosis based on the soil mask selection of leaf area
	selectWindow("soil");
	run("Set Measurements...", "area_fraction redirect=chlorosis decimal=4");
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

	if(saveChlorosis) {
		saveAs("Tiff", output + imageName + "_chlorosis");
	}
	close();
	close("chlorosis");

	//Create threshold classification of necrosis from HSB
	//Convert to HSB image space and split channels for processing
    open(input + filename);
	selectWindow(filename);
	run("HSB Stack");
	run("32-bit");
	selectWindow(filename);
	run("Stack to Images");
	selectWindow("Brightness");
	close();
	selectWindow("Saturation");
	close();
	
	//Threshold necrosis using HSB Hue	
	selectWindow("Hue");
	setAutoThreshold("Default");
	setThreshold(0, 28.33);
	setOption("BlackBackground", false);
	rename("necrosis");
	
	//Measure necrosis based on the soil mask selection of leaf area	
	selectWindow("soil");
	run("Set Measurements...", "area_fraction redirect=necrosis decimal=4");
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
	if(saveNecrosis) {
		saveAs("Tiff", output + imageName + "_necrosis");
	}
	close();

	//Collect area fraction measurements of each image into one file
	
	//Use soil image to get leaf area only stats from the HSB and CIELab image spaces
	run("Clear Results");
    open(input + filename);
    
	//Create HSB images
	selectWindow(filename);
	run("HSB Stack");
	run("32-bit");
	selectWindow(filename);
	run("Stack to Images");
	
	//Measure and define HSB one image at a time based on the soil mask selection
	selectWindow("soil");
	run("Set Measurements...", "mean redirect=Hue decimal=4");
	run("Measure");
	H = getResult("Mean");
	run("Set Measurements...", "mean redirect=Saturation decimal=4");
	run("Measure");
	S = getResult("Mean");
	run("Set Measurements...", "mean redirect=Brightness decimal=4");
	run("Measure");
	B = getResult("Mean");
	
	selectWindow("Hue");
	close();
	selectWindow("Saturation");	
	close();
	selectWindow("Brightness");
	close();

	//Create Lab images
    open(input + filename);
	selectWindow(filename);
	run("Lab Stack");
	selectWindow(filename);
	run("Stack to Images");
	
	//Measure and define CIELab one image at a time based on the soil mask selection
	selectWindow("soil");
	run("Set Measurements...", "mean redirect=L* decimal=4");
	run("Measure");
	L = getResult("Mean");
	run("Set Measurements...", "mean redirect=a* decimal=4");
	run("Measure");
	a = getResult("Mean");
	run("Set Measurements...", "mean redirect=b* decimal=4");
	run("Measure");
	b = getResult("Mean");
	
	selectWindow("L*");
	close();
	selectWindow("a*");	
	close();
	selectWindow("b*");
	close();
	
	//Calculate the Normalized Green Red Difference Index (NGRDI) and Triangular Greeness (TGI) for the leaf area
	//These are vegetation indexes from RGB components based on Hunt et al. 2014. 
	
	run("Clear Results");

	//Perform rgb threshold classification and make masks
	//Convert to separate red, green and blue images for processing
    open(input + filename);
	selectWindow(filename);
	run("Split Channels");
	selectWindow(filename + " (red)");
	rename("IMG_(red)");
	selectWindow(filename + " (green)");
	rename("IMG_(green)");
	selectWindow(filename + " (blue)");
	rename("IMG_(blue)");
	
	//Use image math calculator plus functions to calculate the NGRDI
	imageCalculator("Add create 32-bit", "IMG_(green)","IMG_(red)");
	rename("ResultG+R");
	imageCalculator("Subtract create 32-bit", "IMG_(green)","IMG_(red)");
	rename("ResultG-R");
	imageCalculator("Divide create 32-bit", "ResultG-R","ResultG+R");
	rename("IMG_NGRDI");
	selectWindow("IMG_NGRDI");
	run("Multiply...", "value=100");
	close("ResultG+R");
	close("ResultG-R");
	
	//Calculate the mean NGRDI value for the total of the leaf area
	selectWindow("soil");
	run("Set Measurements...", "mean redirect=IMG_NGRDI decimal=4");
	run("Measure");
	NGRDI = getResult("Mean");
	
	//Use image math calculator plus functions to calculate the TGI
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
	close("ResultR-B");
	close("ResultR-G");
	close("IMG_(blue)");
	close("IMG_(red)");
	close("IMG_(green)");
	
	//Calculate the mean TGI value for the total of the leaf area
	selectWindow("soil");
	run("Set Measurements...", "mean redirect=IMG_TGI decimal=4");
	run("Measure");
	TGI = getResult("Mean");
	
	selectWindow("IMG_NGRDI");
	if(saveNgrdi) {
		saveAs("Tiff", output + imageName + "_NGRDI");
	}
	close();
	
	selectWindow("IMG_TGI");
	if(saveTgi) {
		saveAs("Tiff", output + imageName + "_TGI");
	}
	close();
	
	run("Clear Results");
	run("Close All");
	
	//Calculate the linear model for PlotMLN = lm(formula = PlotMLN ~ greenveg + greenerveg + chlorosis + necrosis + Hue + Saturation + Brightness + L. + a. + NGRDI; R-squared:  0.6462
	StepwisePlotMLN = 24.433074 + -0.186087 * greenveg + 0.029204 * greenerveg + -0.197714 * chlorosis + -0.214809 * necrosis + -0.098555 * H + 0.011697 * S + -0.098701 * B + 0.281423 * L + 0.042320 * a + -0.144420 * NGRDI;
	
	//Calculate the results of the rpart CART regression tree model using
	//nested if then else statements R-squared (using separate testing and training data): 0.6695
	
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

	//Apply Conditional Inference Tree model using terminal mode means
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
	
	//Calculate the Ensemble average of the three different PlotMLN predictive models
	EnsemblePlotMLN = (StepwisePlotMLN + CARTPlotMLN + PartyPlotMLN) / 3;

	idArr[i] = i;
	filenameArr[i] = filename;
	soilArr[i] = soil;
	greenvegArr[i] = greenveg;
	greenervegArr[i] = greenerveg;
	chlorosisArr[i] = chlorosis;
	necrosisArr[i] = necrosis;
	hueArr[i] = H;
	saturationArr[i] = S;
	brightnessArr[i] = B;
	lArr[i] = L;
	aArr[i] = a;
	bArr[i] = b;
	ngrdiArr[i] = NGRDI;
	tgiArr[i] = TGI;
	stepwisePlotMlnArr[i] = StepwisePlotMLN;
	cartPlotMlnArr[i] = CARTPlotMLN;
	ciPlotMlnArr[i] = PartyPlotMLN;
	ensembleLeafMlnArr[i] = EnsemblePlotMLN;
	n++;
}

function printResults() {
    run("Clear Results");
    for (i = 0; i < fileCount; i++) {
        setResult("id", i, idArr[i]);
        setResult("filename", i, filenameArr[i]);
        setResult("soil", i, soilArr[i]);
        setResult("greenveg", i, greenvegArr[i]);
        setResult("greenerveg", i, greenervegArr[i]);
        setResult("chlorosis", i, chlorosisArr[i]);
        setResult("necrosis", i, necrosisArr[i]);
        setResult("Hue", i, hueArr[i]);
        setResult("Saturation", i, saturationArr[i]);
        setResult("Brightness", i, brightnessArr[i]);
        setResult("L*", i, lArr[i]);
        setResult("a*", i, aArr[i]);
        setResult("b*", i, bArr[i]);
        setResult("NGRDI", i, ngrdiArr[i]);
        setResult("TGI", i, tgiArr[i]);
        setResult("StepwisePlotMLN", i, stepwisePlotMlnArr[i]);
        setResult("CARTPlotMLN", i, cartPlotMlnArr[i]);
        setResult("CIPlotMLN", i, ciPlotMlnArr[i]);
        setResult("EnsembleLeafMLN", i, ensembleLeafMlnArr[i]);
    }

    setOption("ShowRowNumbers", false);
    updateResults();
    selectWindow("Results");
    saveAs("Results", resultsFile);
}

function getImageFileCount(list) {
    count = 0;
    for (i = 0; i < list.length; i++) {
        if(isImage(list[i])) {
            count++;
        }
    }
    return count;
}

function isImage(filename) {
    if(endsWith(toLowerCase(filename), ".tif")||
       endsWith(toLowerCase(filename), ".tiff") ||
       endsWith(toLowerCase(filename), ".jpg") ||
       endsWith(toLowerCase(filename), ".jpeg")) {
       return true
    }
    else {
        return false
    }
}