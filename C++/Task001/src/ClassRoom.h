//
// Created by alik on 12/8/17.
//

#ifndef TASK001_CLASSROOM_H
#define TASK001_CLASSROOM_H


#include <iostream>
#include "Student.h"
#include "Containers/SimpleList.h"

class ClassRoom {
private:
    Containers::SimpleList<Student> studentList;
public:
    void put(const Student &newStudent) {
        studentList.push_back(newStudent);
    }

	void del(const Student &toRemove) {
		studentList.remove(toRemove);
	}

    // Aka type
    void print() {
		if (studentList.size() == 0) {
			std::cout << "No students in the list" << std::endl;
			return;
		} else {
			std::cout << "Showing " << studentList.size() << " students" << std::endl;
		}

#if 1
		//std::cout << std::endl << "List of best students: " << std::endl;
        for (auto &student : studentList) {
            if (student.getMark().toNum() > 80) {
                std::cout << "Best student: " + student.getName() + " " + student.getLastName()
                          << " got score " + std::to_string(student.getMark().toNum()) << std::endl;
            }
        }

        //std::cout << std::endl << "List of worst students: " << std::endl;

        for (auto &student : studentList) {
            if (student.getMark().toNum() < 40) {
                std::cout << "Worst student: " + student.getName() + " " + student.getLastName()
                          << " got score " + std::to_string(student.getMark().toNum()) << std::endl;
            }
        }

		std::cout << std::endl;
#else
		std::cout << "||||||||||||||\n List of best students: " << std::endl;

		for (auto &student : studentList) {
			if (true) {
				std::cout << "Student: " + student.getName() + " " + student.getLastName() << std::endl;
			}
		}
#endif

    }
};


#endif //TASK001_CLASSROOM_H
