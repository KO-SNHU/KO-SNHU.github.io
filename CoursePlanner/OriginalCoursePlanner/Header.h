// Developer: Kristie O'Brien
// Code Purpose: ABCU Advising Program to read in a data from a file, parse it, store it in data strucutre, print alphanumerically, and find specific course informaiton
// Data Strucutre: Vectors
// School: SNHU
// Class: CS 300
// Project: Project Two Week Seven


#pragma once

#ifndef COURSES_H // This begins the header file
#define COURSES_H // This puts a define guard on the header file
#include <string> // This allows strings to be used
#include <vector> // This allows for the data strucutre vector to be used

using namespace std; // This calls the namespace standard library

class Course { // This begins a class called Course
private: // This begins the section where the private terms will be declared
	string numberRef; // This declares a string variable called numberRef
	string nameRef; // This declares a string variable called nameRef
	string preRef; // This declares a string variable called preRef
	string courseNumber; // This declares a string variable called courseNumber
	string courseName; // This declares a string variable called courseName

	string userInput; // This declares a string variable called userInput
	//vector<string> prereqrequisites; 
	vector<Course> courseList; // This declares a vector of strings called courseList
	vector<string> prereqList; // This declares a vector of strings called prereqList
	string menuChoice = " "; // This declares a string variable called menuChoice

public: // This begins the section where the public terms will be declared
	Course(); // This declares a constructor called Course
	void DisplayMenu(); // This declares a function called DisplayMenu
	void OpenFile(); // This declares a function called OpenFile
	void PrintList(); // This declares a function called PrintList
	void FindCourseInfo(); // This declares a function called FindCourseInfo
	void UserRun(); // This declares a function called UserRun
	void ElseInvalid(); // This declares a function called ElseInvalid
	void UserInputToUpper(); // This declares a function called UserInputToUpper
	void UserRunTwo(); // This declares a function called UserRunTwo
	void StartUpInfo(); // This declares a function called StartUpInfo
	bool operator<(Course& c) { // This declares a bool operator which will overload the operator for comparison 
		return courseNumber < c.courseNumber; // This returns courseNumber less than courseNumber with regards to c 
	}

	bool checkCourseNumber(string number, vector<Course> courseList) { // This begins the bool function checkCourseNuber with reference to string number and the vector of objects from Course called courseList
		for (int h = 0; h < courseList.size(); h++) { // This begins a for loop that will keep looping through all the elements of courseList
			if (number == courseList.at(h).getNumber()) { // This asks if the variable number is in the courseList anywhere 
				return true; // If the above is found to be correct, then the return value is true 
			}
		}


		return false; // If the above if statement does not work to return true, then this returns false 

	}
	Course(string number, string name, vector<string> prereq) { // This declares an object called Course containing the string number, the string name, and the vector of strings prereq
		courseNumber = number; // This sets courseNumber equal to number 
		courseName = name; // This sets courseName equal to name
		prereqList = prereq; // This sets prereqList equal to prereq

	}

	string getNumber() { // This declares the string getter function getNumber
		return courseNumber; // This returns courseNumber
	}

	string getName() { // This declares the string getter function getName
		return courseName; // This returns courseName
	}

	vector<string> getPrerequisites() { // This declares the vector of strings getter function getPrerequisites
		return prereqList; // This returns PrereqList
	}



};

Course::Course() { // This declares a default constructor that will set initial values to default values for all the other defined fields 

}

#endif // This ends the header file
