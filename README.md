#CIMMYT
Maize Research FIJI Plugin v0.11 [Download Jar Here] (https://github.com/george-haddad/CIMMYT/releases/download/v0.11/CIMMYT_Maize_Scanner_v0.11.jar)

##Pre-Installation Requirements

1. Java 8 must be installed [Download Here] (http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html)
2. Latest version of [FIJI] (http://fiji.sc/) must be installed [Download Here] (http://fiji.sc/Downloads)
3. Use Windows 7/8/10 , Mac OS X, Linux, or any Java supporting operating system Installation

##Installation

1. Download the CIMMYT Maize Scanner Jar file [Here] (https://github.com/george-haddad/CIMMYT/releases/download/v0.11/CIMMYT_Maize_Scanner_v0.11.jar)
2. Download the dependency Jar file *tablelayout.jar* [Here] (https://github.com/george-haddad/CIMMYT/releases/download/v0.11/tablelayout.jar)
3. Place the *CIMMYT_Maize_Scanner_v0.11.jar* file in Fiji's plugins folder `Fiji.app\plugins`
4. Place the dependency *tablelayout.jar* jar file in Fiji's plugins folder `Fiji.app\plugins`
5. Start up Fiji

*note* If you are running a version of Fiji that comes bundled with Java6, then please try to download the Java6 backward compatible version of the CIMMYT Maize Scanner plugin *"CIMMYT_Maize_Scanner_v0.11_java6.jar"* [Here] (https://github.com/george-haddad/CIMMYT/releases/download/v0.11/CIMMYT_Maize_Scanner_v0.11_java6.jar)

##Installing Auto-Run Script

1. After completing the installation instructions download the auto-run script file [Here] (https://github.com/george-haddad/CIMMYT/blob/master/Maize/AutoRun/autorun_cimmyt.ijm)
2. Create a folder called AutoRun inside Fiji's "macros" folder `\Fiji.app\macros`
3. Place the downloaded macro autorun_cimmyt.ijm in the AutoRun folder that was created in step 2
4. Start Fiji and the plugin should auto-run

##Screenshots

![Plugin 1](https://github.com/george-haddad/CIMMYT/blob/master/Maize/screenshots/plugin_1.png)

Image processing tab shows various processing options to enable on the batch selection of images

![Plugin 2](https://github.com/george-haddad/CIMMYT/blob/master/Maize/screenshots/plugin_2.png)

Image analysis tab allows the researcher to define an unlimited number of particle analysis passes as well as save a summary of the results in excel format and option to save the analysis overlays for each image analyzed.

![Plugin 3](https://github.com/george-haddad/CIMMYT/blob/master/Maize/screenshots/plugin_3.png)

Canopy macros tab runs a batch macro processing on a user defined batch of images

![Plugin 4](https://github.com/george-haddad/CIMMYT/blob/master/Maize/screenshots/plugin_4.png)

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
