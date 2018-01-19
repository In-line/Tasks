//
// Created by alik on 12/8/17.
//

#ifndef TASK001_STUDENT_H
#define TASK001_STUDENT_H

#include <string>
#include <tuple>
#include "Mark.h"

class Student {
public:
    Student(const std::string &name, const std::string &lastName, const StudentMark &mark);
	~Student();
private:
    std::string name_;
    std::string lastName_;
    StudentMark mark_;
public:
    const std::string &getName() const;

    void setName(const std::string &name);

    const std::string &getLastName() const;

    void setLastName(const std::string &lastName);

    const StudentMark &getMark() const;

    void setMark(const StudentMark &mark);

	bool operator==(const Student &other) const;
};


#endif //TASK001_STUDENT_H
