#include <iostream>
#include "ClassRoom.h"

int main() {
    ClassRoom room;

    room.put(Student("Alik", "Aslanyan", StudentMark(10)));
    room.put(Student("Petros", "Davtyan", StudentMark(90)));
    room.put(Student("Sergey", "Saroyan", StudentMark(70)));
    room.put(Student("Test", "Testyan", StudentMark(10)));

    room.print();

	room.del(Student("Alik", "Aslanyan", StudentMark(10))); // room.print();
	room.del(Student("Petros", "Davtyan", StudentMark(90)));  room.print();
	room.del(Student("Sergey", "Saroyan", StudentMark(70))); //room.print();
	room.del(Student("Test", "Testyan", StudentMark(10))); room.print();

    return 0;
}