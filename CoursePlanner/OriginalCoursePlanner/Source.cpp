
// Developer: Kristie O'Brien
// Code Purpose: ABCU Advising Program to read in a data from a file, parse it, store it in data strucutre, print alphanumerically, and find specific course informaiton
// Data Strucutre: Vectors
// School: SNHU
// Class: CS 300
// Project: Project Two Week Seven



#include <iostream> // This allows for cout to be used
#include <vector> // This allows for the data strucutre vector to be used
#include <sstream> // This allows for sstream to be used
#include <fstream> // This allows for fstream to be used
#include <algorithm> // This allows for the sort algorithm and the transform algorithm to be called


using namespace std; // This calls the namespace standard library

#include "Header.h"



void Course::DisplayMenu() { // This begins the void function DisplayMenu with respect to Course 
	cout << "Welcome to the course planner." << endl; // This prints a message saying "Welcome to the course planner." and a new line at the end 
	cout << "1. Load Data Structure." << endl; // This prints a message saying "1. Load Data Structure." and a new line at the end 
	cout << "2. Print Course List." << endl; // This prints a message saying "2. Print Course List." and a new line at the end 
	cout << "3. Print Course." << endl; // This prints a message saying "3. Print Course." and a new line at the end 
	cout << "9. Exit" << endl; // This prints a message saying "9. Exit" and a new line at the end 
}

void Course::UserInputToUpper() {  // This begins the void function UserInputToUpper with respect to Course 
	cout << "What course would you like to know more about? Please enter its code here-> "; // This prints a message saying "What course would you like to know more about? Please enter its code here-> " 
	cin >> userInput; // This reads in the user input and stores it in userInput

	transform(userInput.begin(), userInput.end(), userInput.begin(), ::toupper); // This takes the user input and transforms it into upper case 
}

void Course::FindCourseInfo() { // This begins the void function FindCourseInfo with respect to Course 
	bool flag = false; // This declares a bool variable called flag and sets it equal to false
	UserInputToUpper(); // This calls the UserInputToUpper funtion

	for (int m = 0; m < courseList.size(); m++) { // This begins a for loop that will keep looping until there are no more elements in courselist 
		if (userInput == courseList.at(m).getNumber()) { // This asks if the userInput is in the course
			cout << endl; // If the above if statement is met, than these following lines are entered, starting with this line which prints a new line 
			cout << "you entered the course : " << userInput << endl; // This prints the message "you entered the course : ", followed by the contents of userInput, and a new line  
			cout << "The information for " << userInput << " is shown as follows: " << endl; // This prints the message "The information for ", followed by the contents of userInput, the message " is shown as follows: ", and a new line 

			if (courseList.at(m).getPrerequisites().empty()) { // This asks if the course that was found has any prerequisites or not 
				cout << courseList.at(m).getNumber() << ", " << courseList.at(m).getName(); // If the above if statement is met, meaning that the course does not have any corresponding prerequisites, then this line of code is reached which prints the requested courseNumber, a comma, and the corresponding requested course name 
				flag = true; // This sets flag equal to true 
				break;// This breaks the loop
			}

			else { // If the above inner if statement is not met, than this else statement is reached. In other words, if the course does have prerequisites, then this line will be reached 
				cout << courseList.at(m).getNumber() << ", " << courseList.at(m).getName() << " " << endl; //This prints out the course number, a comma, the course name, a space, and a new line 
				cout << "Prerequisites: "; // This prints out the message "Prerequisites: "
				for (int p = 0; p < courseList.at(m).getPrerequisites().size(); p++) { // This begins a for loop that will keep looping through the prerequisite list that goes with the corresponding element in courseList

					if (p != courseList.at(m).getPrerequisites().size() - 1) { // This asks if there is more than one prerequisite
						cout << courseList.at(m).getPrerequisites().at(p) << ", "; // If the above if statement is met, then this line of code is reached which prints the prerequisites seperated by a comma all except for the last prerequisite
					}
					else { // If the above if statement is not met, than this line of code is reached, which basically means if the course either does not have multiple prerequisites, or if this is the last prerequisite the course has 
						cout << courseList.at(m).getPrerequisites().at(p); // This prints out the corresponding prerequisite that goes with the course from courseList 

					}

					flag = true; // This sets flag equal to true 
				}
			}
		}
	}
	cout << endl; // This prints a new line 
	cout << endl; // This prints a new line 

	if (flag == false) { // This asks if the variable flag is equal to false 
		cout << "I am sorry, that is not a valid course. Thank you!" << endl; // If the above if statement is met, than this line of code is reached which prints the message "I am sorry, that is not a valid course. Thank you!" and a new line 

	}

}


void Course::PrintList() {// This begins the void function PrintList with respect to Course 
	cout << endl; // This prints a new line 
	cout << "Here is a sample schedule: " << endl; // This prints the message "Here is a sample schedule: " followed by a new line 

	sort(courseList.begin(), courseList.end()); // This calls a pre built sort function that will sort the courseList from beginning to end in alhphanumeric order going from smallest to largest 

	for (int i = 0; i < courseList.size(); i++) {// This begins a for loop that will keep looping until the end of courseList is reached 
		if (courseList.at(i).getPrerequisites().empty()) { // This asks if, at a specific element in courseList, there are no prerequisites 
			cout << courseList.at(i).getNumber() << ", " << courseList.at(i).getName(); // This prints the courseNumber at the specific index, a comma, and the courseName at the specific index 
		}
		else { // If the above if statement is not met, than this else statement is reached. This basically means, if the course does have prerequisites 
			cout << courseList.at(i).getNumber() << ", " << courseList.at(i).getName() << ", ";  // This prints the courseNumber at the specific index, a comma, the courseName at the specific index, and a comma with a space 
		}
		for (int j = 0; j < courseList.at(i).getPrerequisites().size(); j++) { // This begins a for loop that will loop through all the prerequisites that correspond to the element at the specific indext in courseList 
			if (checkCourseNumber(courseList.at(i).getPrerequisites().at(j), courseList) == true) { // This calls the checkCourseNumber function and runs it with respect to the element in courseList at the specific index along with its corresponding prerequisite
				if (j != courseList.at(i).getPrerequisites().size() - 1) { // This asks if the prerequisite at the indext is not the last
					cout << courseList.at(i).getPrerequisites().at(j) << ", ";// If the above if statement is met, than this line of code is reached which prints out the prerequisites that go with the specific course from the courseList index, and it prints a comma too 
				}
				else { // If the above if statement is not met, meaning that the prerequisite is the last in the list, than this else statement is reached 
					cout << courseList.at(i).getPrerequisites().at(j); // This prints the prerequisite at the index that goes with the course at the index

				}
			}
			else { // If the above if statement is not met, than this line of code is reached
				if (j != courseList.at(i).getPrerequisites().size() - 1) { // This asks if the prerequisite is not the last prerequisite
					cout << "invalid, "; // If the above if statement is met, than this line of code is reached which prints the message "invalid, "
					
				}
				else { // If the above if statement is not met, than this line of code is reached 
					cout << "invalid" << endl; // This prints the message "invalid"

				}

			}
		}

		cout << endl; // This prints a new line


	}

	cout << endl; // This prints a new line
}

void Course::OpenFile() { // This begins the void function OpenFile with respect to Course 
	string fileName; // This declares the string variable fileName
	string s; // This declares the string variable s
	ifstream myFile; // This declares the ifstream variable myFile

	
	cout << "Please enter a file name here. Remember to put what type of file it is, such as .txt -> "; // This prints the message "Please enter a file name here. Remember to put what type of file it is, such as .txt -> "
	cin >> fileName; // This reads in the user input and stores it in fileName

	myFile.open(fileName); // This opens the file entered by the user and stored in fileName and opens it with the ifstream myFile

	if (!myFile) { // This asks if the file would not open
		cout << "Could not open the file, sorry." << endl; // This prints out the message "Could not open the file, sorry." and a new line
		UserRunTwo(); // This calls the function UserRunTwo
		return; // This calls a return as the file would not open
	}

	cout << "Reading data from the file " << endl; // This prints the message "Reading data from the file " and a new line


	while (getline(myFile, s)) { // This begins a while loop that will keep looping while there are lines in the file to be read

		preRef = " "; // This sets pre to an empty value
		stringstream sts(s); // This declares a string stream variable sts with respect to s

		getline(sts, numberRef, ','); // This reads in information with stringstream and stores it in number until a comma is reached 
		getline(sts, nameRef, ',');  // This reads in information with stringstream and stores it in name until a comma is reached 


		string prereq; // This declares a string variable called prereq
		while (getline(sts, prereq, ',')) { // This begins a while loop that will loop through the the file, reading lines until a comma is reached and storing these elements in prereq

			prereqList.push_back(prereq); // This pushes back the elements of prereq into the vector prereqList

		}

		if (numberRef.empty() || nameRef.empty()) { // This asks if numberRef is empty or if nameRef is empty
			continue; // If the above if statement is met, than this line of code is reached which continues the loop
		}

		else { // If the above if statement is not met, than this line of code is reached 
			Course newCourse = Course(numberRef, nameRef, prereqList); // This creates a new Course object, containing numberRef, nameRef, and prereqList contents

			courseList.push_back(newCourse); // This pushes back that new Course object into the vector courseList

			prereqList.clear(); // This clears the prereqList vector

		}

	}

	myFile.close(); // This closes the file

}

void Course::UserRunTwo() {  // This begins the void function UserRunTwo with respect to Course 
	while (menuChoice != "9") { // This begins a while loop that will keep looping so long as menuChoice is not equal to 9
		
		StartUpInfo(); // This calls the function StartUpInfo

		if (find_if(menuChoice.begin(), menuChoice.end(), isalpha) != menuChoice.end()) { // This asks if the menuChoice is an alphabetical value or alphabetical value with characters 
			cout << "you entered: " << menuChoice << endl; // If the above if statement is met, than this line of code is reached which prints the message "you entered: ", the contents of menuChoice, and a new line 
			cout << menuChoice << " Is not valid as no letters are allowed. Please enter a number from the menu instead." << endl; // This prints the contents of menuChoice, the message " Is not valid as no letters are allowed. Please enter a number from the menu instead.", and a new line 
			continue; // This continues the loop
		}


		if (menuChoice != "1") { // This asks if the menuChoice is not equal to 1
			if (menuChoice == "2") { // If the above if statement is met, than this line of code is reached which asks if the menuChoice is equal to 2
				cout << "Please make sure the file is open first before you print a sample scheudle! " << endl; // If the above if statement is met, than this line of code is reached which prints the message  "Please make sure the file is open first before you print a sample scheudle! " and a new line 

			}

			else if (menuChoice == "3") { // If the above if statement is not met, than this line of code is reached which asks if the menuChoice is equal to 3 
				cout << "Please make sure the file is open first before you print a course info! " << endl; // This line of code is reached which prints the message  "Please make sure the file is open first before you print a sample scheudle! " and a new line 

			}

			else if (menuChoice == "9") { // If the above else if statement is not met, than this line of code is reached which asks if the menuChoice is equal to 9
				cout << "Thank you for using the Course Planner! Goodbye!" << endl; // This line prints the message "Thank you for using the Course Planner! Goodbye!" and a new line
				break; // This breaks the loop
			}

			else { // If none of the above if or else if statements are met, than this line of code is reached.
				ElseInvalid(); // This calls the ElseInvalid functio
				continue; // This continues the loop

			}
		}

		else { // If the above if statement is not met, than this line of code is reached 
			OpenFile(); // This calls the OpenFile function to open the file 
			break; // This breaks the loop
		}



	}
}

void Course::StartUpInfo() { // This begins the void function StartUpInfo with respect to Course 
	DisplayMenu(); // This calls the DisplayMenu funtion 
	cout << "What would you like to do?" << endl; // This prints the message "What would you like to do?" and a new line 
	cin >> menuChoice; // This reads in the user input and stores it in menuChoice
}

void Course::UserRun() { // This begins the void function UserRun with respect to Course
	UserRunTwo(); // This calls the function UserRunTwo

	while (menuChoice != "9") { // This begins a while loop that will keep looping as long as menuChoice does not equal 9
		StartUpInfo(); // This calls the function StartUpInfo

		if (find_if(menuChoice.begin(), menuChoice.end(), isalpha) != menuChoice.end()) { // This asks if the menuChoice is an alphabetical value or alphabetical value with characters 
			cout << "you entered: " << menuChoice << endl; // If the above if statement is met, than this line of code is reached which prints the message "you entered: ", the contents of menuChoice, and a new line 
			cout << menuChoice << " Is not valid as no letters are allowed. Please enter a number from the menu instead." << endl; // This prints the contents of menuChoice, the message " Is not valid as no letters are allowed. Please enter a number from the menu instead.", and a new line 
			continue; // This continues the loop
		}


		if (menuChoice == "1") { // This asks if the menuChoice is equal to 1
			cout << "The file is already open!" << endl; // If the above if statement is met, than this line of code is reached which prints the message "The file is already open!" and a new line

		}


		else if (menuChoice == "2") { // If the above if statement is not met, than this line of code is reached which as if the menuChoice is equal to 2
			PrintList(); // This calls the PrintList function 

		}

		else if (menuChoice == "3") { // If the above else if statement is not met, than this line of code is reached which as if the menuChoice is equal to 3
			FindCourseInfo(); // This calls the FindCourseInfo function

		}

		else if (menuChoice == "9") { // If the above else if statement is not met, than this line of code is reached which as if the menuChoice is equal to 9
			cout << "Thank you for using the Course Planner! Goodbye!" << endl; // This line prints the message "Thank you for using the Course Planner! Goodbye!" and a new line
			break;  // This breaks the loop

		}

		else { // If none of the above if or else if statements are met, than this line of code is reached.
			ElseInvalid(); // This calls the ElseInvalid functio
			continue; // This continues the loop

		}

	}	

}

void Course::ElseInvalid() { // This begins the void function ElseInvalid with respect to Course 
	cout << "You entered: " << menuChoice << endl; // This prints the message "You entered: ", the contents of menuChoice, and a new line 
	cout << menuChoice << " Is not a valid option." << endl; // This the contents of menuChoice, the message " Is not a valid option.", and a new line 
	cout << "Please enter an option from the menu." << endl; // This prints the message  "Please enter an option from the menu." and a new line

}

int main() { // This begins the main function where the program will be run 
	Course programRun; // This declares an instance reference of Course called programRun



	programRun.UserRun(); // This calls the function UserRun with respect to programRun

	

	return 0; // This returns 0
}


