#CIMMYT

##Already Have FIJI Installed ?

Already having [FIJI] (http://fiji.sc/) installed is great and will lower the time spent getting started.

Go directly to the __Install CIMMYT Maize Scanner Plugin__ section

##Install Java, FIJI and Plugin

This section is for those users who need to install everything in order to get started.

##Install Java

The very first thing that needs to be done is to make sure that the latest version of Java is installed. Follow the steps below to install the latest version of Java.

* Download & Install Java [Download Here] (https://www.java.com/en/download/)
* Make sure it was installed correctly
  * Visit Oracle's [Verify Java Version Page] (https://www.java.com/en/download/installed.jsp)
  * If your browser blocks the Java web-plugin visit the Oracle's [Manual Java Check Page] (https://java.com/en/download/help/version_manual.xml)

Go to __Install Fiji__ section
  
##Install Fiji

Now that the latest version of Java is installed we can proceed with installing [FIJI] (http://fiji.sc/). There are 2 options available here:

1. Download the latest version of FIJI
  * Make sure to download the "No JRE" version!
    * The regular versions of FIJI come with Java 6 pre-packaged which is not recommended
	* Java 6 has been End of Life by Oracle
	* Java 6 has security holes that leaves your computer vulnrable to attacks
	* Oracle recomends always having the latest version of Java installed
  * Download FIJI [Here] (http://fiji.sc/Downloads)
2. Download our distribution of FIJI that comes with CIMMYT Maize plugin pre-installed
  * What we did was download the latest version of FIJI on Nov-18-2015
  * Installed the CIMMYT Maize Scanner Plugin
  * Installed the auto-run script so that the plugin will start right when you run FIJI
  * Download the FIJI with [CIMMYT Plugin Pre-installed here] (https://github.com/george-haddad/CIMMYT/releases/download/v0.12/fiji-nojre-cimmyt.zip)

###Verify Integrity of fiji-nojre-cimmyt.zip

Provided are 3 hash codes to check the integrity of the file you downloaded

* md5: 7276EC818BD6E7E9BE0A022A77F6F495
* sha1: EBA9C7194EB83886BD83C504DF9FA1D2CD980BE8
* sha256: AE5DEAFEE9C90445A2862E5B5F4ADB0BA6C8E29E0CEB51F694888D076FA02A3D

An online check tool can be used like [Online MD5] (http://onlinemd5.com/)

##Install CIMMYT Maize Scanner Plugin

1. Download the CIMMYT Maize Scanner Jar file [Here] (https://github.com/george-haddad/CIMMYT/releases/download/v0.12/CIMMYT_Maize_Scanner_v0.12.jar)
2. Download the dependency Jar file *tablelayout.jar* [Here] (https://github.com/george-haddad/CIMMYT/releases/download/v0.11/tablelayout.jar)
3. Place the *CIMMYT_Maize_Scanner_v0.12.jar* file in Fiji's plugins folder `Fiji.app\plugins`
4. Place the dependency *tablelayout.jar* jar file in Fiji's plugins folder `Fiji.app\plugins`
5. Start up Fiji

*note* If you are running a version of Fiji that comes bundled with Java6, then please try to download the Java6 backward compatible version of the CIMMYT Maize Scanner plugin *"CIMMYT_Maize_Scanner_v0.12_java6.jar"* [Here] (https://github.com/george-haddad/CIMMYT/releases/download/v0.12/CIMMYT_Maize_Scanner_v0.12_java6.jar)

##Installing Auto-Run Script

The CIMMYT Maize Scanner Plugin can be auto-run when FIJI starts. To do this follow the instructions below to install the auto-run script.

1. Download the auto-run script file [Here] (https://github.com/george-haddad/CIMMYT/releases/download/v0.12/autorun_cimmyt.ijm)
2. Create a folder called AutoRun inside Fiji's "macros" folder `\Fiji.app\macros`
3. Place the downloaded macro __autorun_cimmyt.ijm__ in the AutoRun folder that was created in step 2
4. Start Fiji and the plugin should auto-run

##Screenshots

![scanner_img_proc](https://cloud.githubusercontent.com/assets/3069650/11250951/6512e4e0-8e48-11e5-8e53-d342111f3c4e.png)

Image processing tab shows various processing options to enable on the batch selection of images

![scanner_img_sys](https://cloud.githubusercontent.com/assets/3069650/11250955/6516d974-8e48-11e5-8cb1-2acfa0396d4f.png)

Image analysis tab allows the researcher to define an unlimited number of particle analysis passes as well as save a summary of the results in excel format and option to save the analysis overlays for each image analyzed.

![canopy_macro_default](https://cloud.githubusercontent.com/assets/3069650/11250954/6513dd0a-8e48-11e5-8e28-5e65008f7239.png)

Canopy macros tab runs a default batch macro on a defined set of images

![canopy_macro_vegindex](https://cloud.githubusercontent.com/assets/3069650/11250952/65138166-8e48-11e5-9b4a-1ef78f1824b2.png)

More macros can be added that will allow the user to customize the macro and add their own vegetation index calculator

![canopy_macro_ngrdi](https://cloud.githubusercontent.com/assets/3069650/11250950/650f7ad0-8e48-11e5-80d6-6f6426e21107.png)

NGRDI & TGI macros to run on a defined set of images

![breed_pix](https://cloud.githubusercontent.com/assets/3069650/11250953/6513c5f4-8e48-11e5-9b1f-50ac1bff4182.png)

BreedPix tab that will process batch images through the breedpix algorithm and optionally output GA/GGA imagees


#Authors
- George El-Haddad
-- Software Engineering Consultant
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


[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/george-haddad/cimmyt/trend.png)](https://bitdeli.com/free "Bitdeli Badge")

