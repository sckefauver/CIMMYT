#CIMMYT

##Already Have FIJI Installed ?

Already having [FIJI] (http://fiji.sc/) installed is great and will lower the time spent getting started.

Go directly to the __Install CIMMYT Maize Scanner Plugin__ section

##Install Java, FIJI and Plugin

This section is for those users who need to install everything in order to get started.

##Install Java

The very first step that needs to be done is to make sure that the latest version of Java is installed. Follow the steps below to install the latest version of Java.

* Download & Install Java [Download Here] (https://www.java.com/en/download/)
* Make sure it was installed correctly
  * Visit Oracle's [Verify Java Version Page] (https://www.java.com/en/download/installed.jsp)
  * If your browser blocks the Java web-plugin visit the Oracle's [Manual Java Check Page] (https://java.com/en/download/help/version_manual.xml)

Go to __Install Fiji__ section
  
##Install Fiji

Now that the latest version of Java is installed we can proceed with installing [FIJI] (http://fiji.sc/).

1. Download the latest version of FIJI [Here] (http://fiji.sc/#download)
  * Make sure to download the "No JRE" version

##Install CIMMYT Maize Scanner Plugin

1. Download the CIMMYT Maize Scanner Jar file [Here] (https://github.com/george-haddad/CIMMYT/releases/download/v1.14/CIMMYT_Maize_Scanner_v1.14.jar)
2. Download the dependency Jar file *tablelayout.jar* [Here] (https://github.com/george-haddad/CIMMYT/releases/download/v1.14/tablelayout.jar)
3. Remove any old versions of the CIMMYT Maize Scanner jar file
4. Place the *CIMMYT_Maize_Scanner_v1.14.jar* file in Fiji's plugins folder `Fiji.app\plugins`
5. Place the dependency *tablelayout.jar* jar file in Fiji's plugins folder `Fiji.app\plugins`
6. Start up Fiji

###Verify Integrity of the downloaded files (optional)

Provided are 3 hash codes to check the integrity of the file *CIMMYT_Maize_Scanner_v1.14.jar*

* md5: E7980E99EDDD1778ECEF442E378520BC
* sha1: 97AF0D01926E07173683577CA039C48F78F67843
* sha256: DC08BDE9E293CEA511DDD4CC13B40F134960346F0891E41C556FED6157CC5FA1

An online check tool can be used like [Online MD5] (http://onlinemd5.com/)

##Installing Auto-Run Script

The CIMMYT Maize Scanner Plugin can be auto-run when FIJI starts. To do this follow the instructions below to install the auto-run script.

1. Download the auto-run script file [Here] (https://github.com/george-haddad/CIMMYT/releases/download/v1.14/autorun_cimmyt.ijm)
2. Create a folder called AutoRun inside Fiji's "macros" folder `\Fiji.app\macros`
3. Place the downloaded macro __autorun_cimmyt.ijm__ in the AutoRun folder that was created in step 2
4. Start Fiji and the plugin should auto-run

##Screenshots

![scanner-image-processing](https://cloud.githubusercontent.com/assets/3069650/23836520/69e63972-0782-11e7-8514-6a9f114484e3.png)

Image processing tab shows various processing options to enable on the batch selection of images

![scanner-image-analysis](https://cloud.githubusercontent.com/assets/3069650/23836518/69cecac6-0782-11e7-9526-1127303f3ec8.png)

Image analysis tab allows the researcher to define an unlimited number of particle analysis passes as well as save a summary of the results in excel format and option to save the analysis overlays for each image analyzed.

![canopy-macros-maize](https://cloud.githubusercontent.com/assets/3069650/23836514/69bf622a-0782-11e7-8d43-2434f1ce6a58.png)

Canopy macros tab runs a default batch macro on a defined set of images

![canopy-macros-maize-veg-index](https://cloud.githubusercontent.com/assets/3069650/23836515/69cc5cc8-0782-11e7-86e2-f371b0d7bda9.png)

More macros can be added that will allow the user to customize the macro and add their own vegetation index calculator

![canopy-macros-ngrdi](https://cloud.githubusercontent.com/assets/3069650/23836519/69cecdc8-0782-11e7-8cc8-75469bc84fbf.png)

NGRDI & TGI macros to run on a defined set of images

![canopy-macros-mln-fields](https://cloud.githubusercontent.com/assets/3069650/23836517/69ce7ac6-0782-11e7-8eb2-3b8d261b39e8.png)

Maize MLN fields macro

![canopy-macros-mln-scans](https://cloud.githubusercontent.com/assets/3069650/23836516/69ce6b08-0782-11e7-8764-28907dea2c84.png)

Maize MLN scans macro

![breedpix](https://cloud.githubusercontent.com/assets/3069650/23836513/699e66ec-0782-11e7-9017-0f8c2250fc84.png)

BreedPix tab that will process batch images through the breedpix algorithm and optionally output GA/GGA imagees


#Authors
- George El-Haddad
-- Software Engineer
- Dr. Shawn Kefauver
-- Project Principal Investigator

#Organizations
- Funded by [CIMMYT] (http://www.cimmyt.org/)
- Administered by [University of Barcelona] (http://www.ub.edu/) / [Department of Plant Biology] (http://www.ub.edu/bioveg/index.htm)

#License
Copyright 2015 Shawn Carlisle Kefauver

Licensed under the General Public License version 3.0

- [http://www.gnu.org/licenses/gpl-3.0.en.html] (http://www.gnu.org/licenses/gpl-3.0.en.html)
- [https://tldrlegal.com/license/gnu-general-public-license-v3-(gpl-3)] (https://tldrlegal.com/license/gnu-general-public-license-v3-(gpl-3))
