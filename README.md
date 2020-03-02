# GauchoShare

## Project summary

### One-sentence description of the project

GauchoShare is an Android app that is intended to help UCSB students buy/sell resources to each other.

### Additional information about the project

UCSB students are cheap and do not want to have to buy extra, overpriced materials like Scantrons, textbooks, lab manuals, iClickers, etc. There are also those who already have these materials, but wish to sell them to other UCSB students at a lower price in order to get some money back for their purchases. Hence, GauchoShare hopes to connect the group of people who are selling their resources at a lower price with the group of people who are looking to buy these kinds of resources at a lower price point.

## Installation

### Prerequisites

* Android Studio Version 3.5+
* Any Android Device/Emulator
* Gradle Version 6+
* Java Version 8+
* JDK (Java Developer Kit) Version 11+
* git (any version)

### Dependencies

Dependencies are specified in the build.gradle files and will be installed automatically when building the Android app.

### Installation Steps
1. Install gradle CLI
```
brew install gradle
```

2. Clone the repo
```
git clone git@github.com:ucsb-cs48-w20/gauchoshare.git
```
3. Change your working directory into the repository directory and run the following command:
* On Windows:
```
gradlew build
```
* On Mac or Linux:
```
./gradlew build
```
4. Your Android App is now built and ready to be run on any Android Device/Emulator :D


## Functionality

* App allows user to create profile with contact information that might be relevant to the seller/buyer
* User can create a listing that includes the name of the item they are selling, the price they are selling it at, and any other additional description/details
* User can view listings created by other Users and directly message them through the app, or contact them through their social media
* User can also mark certain postings as SOLD in order to inform potential buyers that the item has already been bought

## Known Problems

* Search functionality only works when User searches the EXACT keywords of the item they are looking for
* Profile image upload does not crop to fit the ImageView provided


## Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

## License

Check the LICENSE file for details
