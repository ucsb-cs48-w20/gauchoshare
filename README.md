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

Dependencies are specified in the build.gradle files and will be installed automatically when building the Android app. Manual installation of each individual dependency is not required.

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
* User can create a listing that includes the name of the item they are selling, the price they are selling it at, an image of the item they are selling, and any other additional description/details
* User can view listings created by other users and directly message them through the app, or contact them through the each other's provided contact information
* User can also mark certain postings as SOLD in order to inform potential buyers that the item has already been bought
* User can view all the listings they have created and edit any information related to it (e.g. title, price, description, category, image)
* User can edit profile information (e.g. name, phone number, Venmo, username, profile picture)
* User can search through all listings by keywords contained in listing titles or description

## Known Problems

* Image upload does not work if user immediately saves their profile/listing information
* Profile/listing image display does not always immediately display due to the retrieval time it takes to get the image from Firebase Storage
* When running on an emulator, the very first authentication action can often take a while, but this is likely due to an inherent issue with emulators


## Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

## License

Check the LICENSE file for details
