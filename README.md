# CIMMYT MaizeScanner

## Already Have FIJI Installed ?

Already having [FIJI](http://fiji.sc/) installed is great and will lower the time spent getting started.

Go directly to the __Install CIMMYT Maize Scanner Plugin__ section

## Install Java, FIJI and Plugin

This section is for those users who need to install everything in order to get started.

## Install Java

The very first step that needs to be done is to make sure that the latest version of Java is installed. Follow the steps below to install the latest version of Java.

* Download & Install Java [Download Here](https://www.java.com/en/download/)
* Make sure it was installed correctly
  * Visit Oracle's [Verify Java Version Page](https://www.java.com/en/download/installed.jsp)
  * If your browser blocks the Java web-plugin visit the Oracle's [Manual Java Check Page](https://java.com/en/download/help/version_manual.xml)

Go to __Install Fiji__ section
  
## Install Fiji

Now that the latest version of Java is installed we can proceed with installing [FIJI](http://fiji.sc/).

1. Download the latest version of FIJI [Here](http://fiji.sc/#download)
   * Make sure to download the "No JRE" version

## Install CIMMYT Maize Scanner Plugin

1. Download the CIMMYT Maize Scanner Jar file [Here](https://github.com/sckefauver/CIMMYT/releases/download/v1.16/CIMMYT_Maize_Scanner.jar)
2. Download the dependency Jar file *tablelayout.jar* [Here](https://github.com/sckefauver/CIMMYT/releases/download/v1.16/tablelayout.jar)
3. Place the *CIMMYT_Maize_Scanner.jar* file in Fiji's plugins folder `Fiji.app\plugins`
4. Overwrite any existing version of the jar when or if prompted to
5. Place the dependency *tablelayout.jar* jar file in Fiji's plugins folder `Fiji.app\plugins`
6. Start up Fiji

### Verify Integrity of the downloaded files (optional)

Provided are 3 hash codes to check the integrity of the file *CIMMYT_Maize_Scanner.jar*

* md5: 0DABCA3BF75730DE41D0AD753C1BDD77
* sha1: E03AC17B5316567125130958FBD8AE7EF49C36C4
* sha256: FF4A82E64F1E699CC0E9BCD6AC63A3E0809BD14ED479FEC40D6C5F86BE635E1D

An online check tool can be used like [Online MD5](http://onlinemd5.com/)

## Installing Auto-Run Script

The CIMMYT Maize Scanner Plugin can be auto-run when FIJI starts. To do this follow the instructions below to install the auto-run script.

1. Download the auto-run script file [Here](https://github.com/sckefauver/CIMMYT/blob/master/autorun_cimmyt.ijm)
2. Create a folder called AutoRun inside Fiji's "macros" folder `\Fiji.app\macros`
3. Place the downloaded macro __autorun_cimmyt.ijm__ in the AutoRun folder that was created in step 2
4. Start Fiji and the plugin should auto-run

## Screenshots

![scanner_01](https://cloud.githubusercontent.com/assets/3069650/25216300/9e96ceac-25aa-11e7-994e-e96892250e98.png)

Image processing tab shows various processing options to enable on the batch selection of images

![scanner_02](https://cloud.githubusercontent.com/assets/3069650/25216297/9e95ebc2-25aa-11e7-8fde-70afb34b2cbf.png)

Image analysis tab allows the researcher to define an unlimited number of particle analysis passes as well as save a summary of the results in excel format and option to save the analysis overlays for each image analyzed.

![scanner_03](https://cloud.githubusercontent.com/assets/3069650/25216301/9e9988ea-25aa-11e7-8fc5-7b7f56aa334c.png)

Canopy macros tab runs a default batch macro on a defined set of images.
More macros can be added that will allow the user to customize the macro and add their own vegetation index calculator

![scanner_04](https://cloud.githubusercontent.com/assets/3069650/25216299/9e9684c4-25aa-11e7-9ef3-c662b0f07838.png)

NGRDI & TGI macros to run on a defined set of images

![scanner_05](https://cloud.githubusercontent.com/assets/3069650/25216302/9e9b957c-25aa-11e7-96c3-c7c2b3e6c5f5.png)

Maize MLN scans macro

![scanner_06](https://cloud.githubusercontent.com/assets/3069650/25216298/9e96209c-25aa-11e7-9f2e-14e8e70a47af.png)

Maize MLN fields macro

![scanner_07](https://cloud.githubusercontent.com/assets/3069650/25216304/9ec3a1ca-25aa-11e7-902f-5d9dea01073f.png)

BreedPix tab that will process batch images through the breedpix algorithm and optionally output GA/GGA imagees

![scanner_08](https://cloud.githubusercontent.com/assets/3069650/25216303/9ec310de-25aa-11e7-90a6-10b3845aa510.png)

Customize your CSV output with delimiter of your choice

# Authors

- George El-Haddad
   - Software Engineer
- Dr. Shawn Kefauver
   - Project Principal Investigator

# Organizations

- Funded by [CIMMYT](http://www.cimmyt.org/)
- Administered by [University of Barcelona](http://www.ub.edu/) / [Department of Plant Biology](http://www.ub.edu/bioveg/index.htm)

# License

Copyright 2015 Shawn Carlisle Kefauver

Licensed under the General Public License version 3.0

- [http://www.gnu.org/licenses/gpl-3.0.en.html](http://www.gnu.org/licenses/gpl-3.0.en.html)
- [https://tldrlegal.com/license/gnu-general-public-license-v3-(gpl-3)](https://tldrlegal.com/license/gnu-general-public-license-v3-(gpl-3))
