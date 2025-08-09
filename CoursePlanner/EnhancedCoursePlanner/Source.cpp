
// Developer: Kristie O'Brien
/////////////////////////////////////////////////////////////////////////////////////////////
// Code Original Purpose: 
// ABCU Advising Program to read in a data from a file, parse it, store it in data strucutre, 
// print alphanumerically, and find specific course informaiton
/////////////////////////////////////////////////////////////////////////////////////////////
// /////////////////////////////////////////////////////////////////////////////////////////
// Code Updated Purpose:
// The code originally had a time complexity for course look ups of O(n).
// In the updated version of this code, time complexity is now O(1).
// This is accomplished with the help of unordered maps rather than complex 
// for loops and vextor struxtures.
///////////////////////////////////////////////////////////////////////////////////////////
// Data Strucutre: Vectors and unordered map
// School: SNHU
// Class: CS 499
// Project: Artifact Two for ePortfolio



// The following are the include statements that include the nexessary tools for working with this code.
// Note that this list of include statements now includes unordered_map so that this code can use unordered maps.
#include <iostream>
#include <vector> 
#include <sstream> 
#include <fstream>
#include <algorithm>
#include <unordered_map>
#include "Header.h"

// This calls the namespace standard library
using namespace std;

// This creates the unordered_map data strucutre that will be used in this code and calls it courseMap.
unordered_map<string, Course> courseMap;


// The following code is a function which displays a welcome message and the menu options to the user.
// This tells them what commands to enter in order to perform certain actions.
void Course::DisplayMenu() { // This begins the void function DisplayMenu with respect to Course 
	cout << "Welcome to the course planner." << endl;
	cout << "1. Load Data Structure." << endl;
	cout << "2. Print Course List." << endl;
	cout << "3. Print Course." << endl;
	cout << "9. Exit" << endl;
}


// The following code is a function that takes in user input and transforms it to upper case.
// This makes for more user friendly user input interactions with the code.
void Course::UserInputToUpper() {
	cout << "What course would you like to know more about? Please enter its code here-> ";
	cin >> userInput;
	transform(userInput.begin(), userInput.end(), userInput.begin(), ::toupper);
}

// The following code is a function that allows the user to try and find a course.
// So the user will input the name and number of the course they are trying to find and this code will search for it.
// Note that this code uses an unordered map to keep the time complexity down to O(1) for course look up.
// However, the prereq section still uses a more complex, close to O(n) functionality as this allows for error checking
// in case a course does not have a prereq while still maintaining efficiency. 
// It also handles the situation should a user enter invalid inputs.
void Course::FindCourseInfo() {
	bool flag = false; 
	UserInputToUpper();
	auto it = courseMap.find(userInput);

	if (it != courseMap.end()) {
		const Course& foundCourse = it->second;
		cout << endl;
		cout << "you entered the course : " << userInput << endl; 
		cout << "The information for " << userInput << " is shown as follows: " << endl;

		if (foundCourse.getPrerequisites().empty()) {
			cout << foundCourse.getNumber() << ", " << foundCourse.getName() << " " << endl;
		}

		else {
			cout << foundCourse.getNumber() << ", " << foundCourse.getName() << " " << endl;
			cout << "Prerequisites: ";
			
			for (size_t p = 0; p < foundCourse.getPrerequisites().size(); ++p) {
				cout << foundCourse.getPrerequisites().at(p);
				
				if (p != foundCourse.getPrerequisites().size() - 1) {
					cout << ", ";
				}
			}
		}
		flag = true;
	}

	cout << endl;
	cout << endl;

	if (flag == false) {
		cout << "I am sorry, that is not a valid course. Thank you!" << endl; 
	}
}

// The following code is a function that prints the list of courses.
// Note that this now uses an unordered map to change the complexity from O(n) to O(1).
void Course::PrintList() {
	cout << endl; 
	vector<string> sortedKeys;

	for (const auto& pair : courseMap) {
		sortedKeys.push_back(pair.first);
	}

	sort(sortedKeys.begin(), sortedKeys.end());

	for (const string& key : sortedKeys) {
		const Course& course = courseMap[key];
		cout << course.getNumber() << ", " << course.getName();
		const vector<string>& prereqs = course.getPrerequisites();

		if (!prereqs.empty()) {
			cout << ", ";

			for (size_t i = 0; i < prereqs.size(); ++i) {

				if (courseMap.find(prereqs[i]) != courseMap.end()) {
					cout << prereqs[i];
				}

				else {
					cout << "invalid";
				}

				if (i != prereqs.size() - 1) cout << ", ";

			}
		}

		cout << endl;
	}

	cout << endl;
}

// The following code is a function to open the file to load in the data.
// Note that this now uses an unordered map to change the complexity from O(n) to O(1).
void Course::OpenFile() {
	string fileName;
	string s;
	ifstream myFile;

	cout << "Please enter a file name here. Remember to put what type of file it is, such as .txt -> ";
	cin >> fileName;
	myFile.open(fileName); 

	if (!myFile) {
		cout << "Could not open the file, sorry." << endl;
		return;
	}

	cout << "Reading data from the file " << endl;

	while (getline(myFile, s)) {
		preRef = " ";
		stringstream sts(s);
		string numberRef;
		string nameRef;
		string prereqItem;
		vector<string> prereq;

		// The following two lines reads in information with stringstream and stores that information
		// in the appropriate data type until a comma is reached
		getline(sts, numberRef, ',');
		getline(sts, nameRef, ',');

		while (getline(sts, prereqItem, ',')) {
			prereq.push_back(prereqItem);
		}

		if (!numberRef.empty() && !nameRef.empty()) {
			Course course(numberRef, nameRef, prereq);
			courseMap[numberRef] = course;
			continue;
		}
	}

	myFile.close();
}

// The following code is a function for the start up information of the user run loop.
// In otherwords, this prints information for the user and takes in their input at the start of a new iteration.
void Course::StartUpInfo() {
	DisplayMenu();
	cout << "What would you like to do?" << endl; 
	cin >> menuChoice;
}

// The following code is a function that handles the user run, which basically means it handles
// what happens when a user selects a certain menu choice.
void Course::UserRun() {
	while (menuChoice != "9") {
		StartUpInfo();

		if (find_if(menuChoice.begin(), menuChoice.end(), isalpha) != menuChoice.end()) {
			cout << "you entered: " << menuChoice << endl; 
			cout << menuChoice << " Is not valid as no letters are allowed. Please enter a number from the menu instead." << endl;
			continue;
		}

		if (menuChoice == "1") {
			OpenFile();
		}

		else if (menuChoice == "2") {
			PrintList(); 
		}

		else if (menuChoice == "3") {
			FindCourseInfo();
		}

		else if (menuChoice == "9") {
			cout << "Thank you for using the Course Planner! Goodbye!" << endl;
			break; 
		}

		else {
			ElseInvalid(); 
			continue;
		}
	}	
}

// The following code is a function that handles invalid input should the user enter invalid input in the user run.
void Course::ElseInvalid() {
	cout << "You entered: " << menuChoice << endl;
	cout << menuChoice << " Is not a valid option." << endl;
	cout << "Please enter an option from the menu." << endl;
}

// The following code is the main code for the function.
// It calls the functions necessary for the code to function as expected.
int main() {
	Course programRun;
	programRun.UserRun();
	return 0;
}


