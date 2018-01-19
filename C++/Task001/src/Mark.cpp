//
// Created by alik on 12/8/17.
//

#include "Mark.h"

Mark::Mark(int mark, const std::pair<int, int> &range) :
        mark_(mark),
        range_(range) {
    if (mark_ < range.first || mark_ > range.second) {
        throw InvalidMarkForRange(mark, range);
    }
}

int Mark::toNum() const {
    return mark_;
}

std::pair<int, int> Mark::getRange() const {
    return range_;
}
