//
// Created by alik on 12/8/17.
//

#include "Student.h"
#include <iostream>

Student::Student(const std::string &name,
                 const std::string &lastName,
                 const StudentMark &mark) :
        name_(name),
        lastName_(lastName),
        mark_(mark) {
	//std::cout << "Constructing student!" << std::endl;
}

Student::~Student() {
	//std::cout << "Destructing student!" << std::endl;
}

const std::string &Student::getName() const {
    return name_;
}

void Student::setName(const std::string &name) {
    name_ = name;
}

const std::string &Student::getLastName() const {
    return lastName_;
}

void Student::setLastName(const std::string &lastName) {
    lastName_ = lastName;
}

const StudentMark &Student::getMark() const {
    return mark_;
}

void Student::setMark(const StudentMark &mark) {
    mark_ = mark;
}

bool Student::operator==(const Student &other) const {
	return std::make_tuple(name_, lastName_, mark_) == std::make_tuple(other.name_, other.lastName_, other.mark_);
}