# SETI Targets

SetiTargets is a Java program that finds celestial points of interest in view of a given radio (or optical) telescope and supplies relevant pointing data for a said telescope.
Although it is called SetiTargets, this program can also aid any amateur astronomer (not just the ones looking for E.T.) All you need to do is supplement targets.txt with your own data.

## Configure

### Step 1
Once you have cloned or unzipped the code, the first thing you need to do is gather the data for your dish. You will need to know:

* The dish's latitude 
* The dish's longitude 
* The dish's maximum Azimuth 
* The dish's minimum Azimuth
* The dish's maximum Elevation
* The dish's minimum Elevation
* How many hours you want to observe (sampled once a minute)

### Step 2
Next we want to add these values to our config.txt file. Be careful to maintain the order and format of the config file. The default is configured to the ATA (Allen Telescope Array). In config.txt you will also specify how many targets you want to calculate and over what period of time.
If SetiTargets throws a 'IOExeption Mismatch' error double check your config file.

## Running the calculations
### Step 4
The final step is running the program. In the main directory you'll find a script called findTargets that will... find targets. 
To print the output to the console, simply run,
```
./findTargets 
```
Optionally, you can provide findTargets with an output file, this will write to the output file as well as printing the results to the console.
```
./findTargets output.txt
```
When this script is run, it will run the SetiTargets.jar executable. If the jar file has not been made or is not there, it will `make`  again.
If, in the future, you make any alterations to the code, this command will recompile and reassemble the program if it sees changes in the code.

## Authors

* [**Daniel Richards**](https://danieldrichards.github.io/)

### Acknowledgements

* [**Jon Richards**](https://twitter.com/jrseti) - supplied unit conversion code

#### NOTE
>If you notice minute discrepancies in your results, this is because the calculations do not take your elevation (the one above see level) in to account, and the figures may be off by a very small amount. This program is meant to find things for you to look at, not provide pinpoint tracking data for your telescope.

## License

This project is unlicensed and in the [Public Domain](https://wiki.creativecommons.org/wiki/Public_domain) with the sole provision that if you make anything cool with my code, I want to know about it!

