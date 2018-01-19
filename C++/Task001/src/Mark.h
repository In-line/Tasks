//
// Created by alik on 12/8/17.
//

#ifndef TASK001_MARK_H
#define TASK001_MARK_H

#include <tuple>
#include <stdexcept>
#include <string>

class InvalidMarkForRange : public std::runtime_error {
    std::string getString(int mark, const std::pair<int, int> &range) {
        return (std::string) "Invalid mark for current range { " +
               std::to_string(mark) +
               ", [" +
               std::to_string(range.first) +
               " , " +
               std::to_string(range.second) +
               "] }";
    }

public:
    InvalidMarkForRange(int mark, const std::pair<int, int> &range) :
            std::runtime_error(getString(mark, range)) {}
};

class Mark {
private:
    int mark_;
    std::pair<int, int> range_;
public:
    Mark(int mark, const std::pair<int, int> &range);

    int toNum() const;

    std::pair<int, int> getRange() const;
};

struct StudentMark : public Mark {
    explicit StudentMark(unsigned short mark) : Mark(mark, {0, 100}) {}
	bool operator==(const StudentMark &other) const {
		return std::make_tuple(toNum(), getRange()) == std::make_tuple(other.toNum(), other.getRange());
	}
};



#endif //TASK001_MARK_H
