//Macro to batch process apply hue (hsb/hsv) thresholding of vegetation components 
//Components include scanner bed, greenveg, chlorosis, necrosis
	
run("Input/Output...", "jpeg=85 gif=-1 file=.csv use_file copy_column copy_row save_column save_row");

var input = "$P{batch_input}";
var output = "$P{batch_output}";
var resultsFile = "$P{save_results_file}";
var list = getFileList(input);
var saveScanner = $P{save_scanner};
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
var scannerArr = newArray(fileCount);
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
var stepwiseLeafMlnArr = newArray(fileCount);
var cartLeafMln = newArray(fileCount);
var ciLeafMln = newArray(fileCount);
var ensembleLeafMln = newArray(fileCount);
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
	
	//Create scanner bed area mask from HSB Saturation
	selectWindow("Hue");
	close();
	selectWindow("Brightness");
	close();
	selectWindow("Saturation");
	setThreshold(24,255);
	setOption("BlackBackground", false);
	run("Convert to Mask");
	run("Despeckle");
	run("Remove Outliers...", "radius=10 threshold=50 which=Dark");
	run("Invert");
	rename("scanner");
	selectWindow("scanner");
	run("Set Measurements...", "area_fraction redirect=None decimal=4");
	run("Select All");
	run("Measure");
	scanner = getResult("%Area");
	selectWindow("scanner");
	run("Duplicate...", "title=scanner-save");
	selectWindow("scanner-save");
	
	if(saveScanner) {
		saveAs("Tiff", output + imageName + "_scanner");
	}

	close();

	//Create scanner bed area mask from HSB Saturation
	selectWindow("scanner");
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
	
	//Measure and greenveg GA based on the scanner mask selection of leaf area
	selectWindow("scanner");
	run("Set Measurements...", "area_fraction redirect=greenveg decimal=4");
	run("Measure");
	greenveg = getResult("%Area");
	selectWindow("greenveg");
	run("Convert to Mask");
	run("Invert");
	run("Despeckle");
	imageCalculator("Max create", "greenveg","scanner");
	selectWindow("Result of greenveg");
	rename("greenvegmasked");
	selectWindow("greenveg");
	close();
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
	
	//Measure and greenerveg GGA based on the scanner mask selection of leaf area
	selectWindow("scanner");
	run("Set Measurements...", "area_fraction redirect=greenerveg decimal=4");
	run("Measure");
	greenerveg = getResult("%Area");
	selectWindow("greenerveg");
	run("Convert to Mask");
	run("Invert");
	run("Despeckle");
	imageCalculator("Max create", "greenerveg","scanner");
	selectWindow("Result of greenerveg");
	rename("greenervegmasked");
	selectWindow("greenerveg");
	close();
	selectWindow("greenervegmasked");

	if(saveGreenerVeg) {
		saveAs("Tiff", output + imageName + "_greenerveg-GGA");
	}
	
	close();

	//Create threshold classification of chlorosis from HSB Hue
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
	
	//Measure chlorosis based on the scanner mask selection of leaf area
	selectWindow("scanner");
	run("Set Measurements...", "area_fraction redirect=chlorosis decimal=4");
	run("Measure");	
	chlorosis = getResult("%Area");	
	selectWindow("chlorosis");
	run("Convert to Mask");
	run("Invert");
	run("Despeckle");
	imageCalculator("Max create", "chlorosis","scanner");
	selectWindow("Result of chlorosis");
	rename("chlorosismasked");
	selectWindow("chlorosis");
	close();
	selectWindow("chlorosismasked");

	if(saveChlorosis) {
		saveAs("Tiff", output + imageName + "_chlorosis");
	}
	
	close();

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
	
	//Measure necrosis based on the scanner mask selection of leaf area
	selectWindow("scanner");
	run("Set Measurements...", "area_fraction redirect=necrosis decimal=4");
	run("Measure");
	necrosis = getResult("%Area");
	selectWindow("necrosis");
	run("Convert to Mask");
	run("Invert");
	run("Despeckle");
	imageCalculator("Max create", "necrosis","scanner");
	selectWindow("Result of necrosis");
	rename("necrosismasked");
	selectWindow("necrosis");
	close();
	selectWindow("necrosismasked");

	if(saveNecrosis) {
		saveAs("Tiff", output + imageName + "_necrosis");
	}
	
	close();

	//Collect area fraction measurements of each image into one file

	//Use scanner image to get leaf area only stats from the HSB and CIELab image spaces
	run("Clear Results");
    open(input + filename);
    
	//Create HSB images
	selectWindow(filename);
	run("HSB Stack");
	run("32-bit");
	selectWindow(filename);
	run("Stack to Images");
	
	//Measure and define HSB one image at a time based on the scanner mask selection
	selectWindow("scanner");
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
	
	//Measure and define CIELab one image at a time based on the scanner mask selection
	selectWindow("scanner");
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
	imageCalculator("Add create 32-bit", "IMG_(green)", "IMG_(red)");
	rename("ResultG+R");
	imageCalculator("Subtract create 32-bit", "IMG_(green)", "IMG_(red)");
	rename("ResultG-R");
	imageCalculator("Divide create 32-bit", "ResultG-R", "ResultG+R");
	rename("IMG_NGRDI");
	selectWindow("IMG_NGRDI");
	run("Multiply...", "value=100");

	selectWindow("ResultG-R");
	close();
	selectWindow("ResultG+R");
	close();
	
	//Calculate the mean NGRDI value for the total of the leaf area
	selectWindow("scanner");
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

	selectWindow("ResultR-G");
	close();
	selectWindow("ResultR-B");
	close();
	selectWindow("IMG_(green)");
	close();
	selectWindow("IMG_(blue)");
	close();
	selectWindow("IMG_(red)");
	close();
	
	//Calculate the mean TGI value for the total of the leaf area
	selectWindow("scanner");
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

	//Copy the data over to an organized transposed log of 4 measurements from each image 
	//Calculate the linear model for LeafMLN lm(formula = LeafMLN ~ greenveg + chlorosis +
	// necrosis + Hue + Saturation + Brightness + b. + TGI.100; R-squared:  0.4695

	StepwiseLeafMLN = 12.3755264 + -0.0952020*greenveg + -0.1042935*chlorosis + -0.1327068*necrosis + -0.0702305*H + 0.0394216*S + 0.0701286*B + -0.16094813*b + -0.0004355*TGI; 

	//Calculate the results of the rpart CART regression tree model using nested if
	// then else statements R-squared (using separate testing and training data): 0.5065
	
	if (necrosis < 15.9745) {
		if (TGI < 10380.61) {
	    	if (chlorosis < 17.133) {
	        	CARTLeafMLN = 1.841;
			}
			else {
	        	CARTLeafMLN = 2.338;
			}
		}
		else {
			CARTLeafMLN = 2.827;
		}
	}
	else {
		if (NGRDI < -4.7821) {
	    	CARTLeafMLN = 3.258;
		}
		else {
			CARTLeafMLN = 3.906;
		}
	}

	//Calculate the results of the conditional inference tree "PartyLeafMLN" using nested if
	// then else statements R-squared (using separate testing and training data): 0.4860
	
	if (B <= 106.731) {
		if (b <= 14.055) {
			PartyLeafMLN = 1.924;
		}
		else {
	        PartyLeafMLN = 2.373;
		}
	}
	else {
		if (necrosis <= 15.685) {
			PartyLeafMLN = 2.866;
		}
		else {
			if (necrosis <= 44.947) {
				PartyLeafMLN = 3.299;
			}
			else {
				PartyLeafMLN = 4.056;
			}
		}
	}

	EnsembleLeafMLN = (StepwiseLeafMLN + CARTLeafMLN + PartyLeafMLN)/3;

	idArr[i] = i;
	filenameArr[i] = filename;
	scannerArr[i] = scanner;
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
	stepwiseLeafMlnArr[i] = StepwiseLeafMLN;
	cartLeafMln[i] = CARTLeafMLN;
	ciLeafMln[i] = PartyLeafMLN;
	ensembleLeafMln[i] = EnsembleLeafMLN;
	n++;
}

function printResults() {
    run("Clear Results");
    for (i = 0; i < fileCount; i++) {
        setResult("id", i, idArr[i]);
        setResult("filename", i, filenameArr[i]);
        setResult("scanner", i, scannerArr[i]);
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
        setResult("StepwiseLeafMLN", i, stepwiseLeafMlnArr[i]);
        setResult("CARTLeafMLN", i, cartLeafMln[i]);
        setResult("CILeafMLN", i, ciLeafMln[i]);
        setResult("EnsembleLeafMLN", i, ensembleLeafMln[i]);
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
};