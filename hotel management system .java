import java.time.LocalDateTime;
import java.util.*;

enum RoomStatus 
{
    AVAILABLE,
    NOT_AVAILABLE
}

enum RoomType 
{
    SINGLE,
    DOUBLE
}

enum Role 
{
    NORMAL_USER,
    HOTEL_AGENT,
    ADMIN
}

class Room 
{
    int roomId;
    int hotelId;
    RoomType roomType;
    RoomStatus roomStatus;

    Room(int roomId, int hotelId, RoomType roomType, RoomStatus roomStatus) 
    {
        this.roomId = roomId;
        this.hotelId = hotelId;
        this.roomType = roomType;
        this.roomStatus = roomStatus;
    }

    public RoomStatus dispalyRoomStatus()
    {
        return roomStatus;
    }
}

class Hotel 
{
    int hotelId;
    String name;
    Address address;
    List<String> facilities;
    List<Room> rooms;
    float rating;
    boolean safetyMeasuresIncluded;
    boolean hasFiveYearITR;
    boolean policeVerified;

    Hotel(int hotelId, String name, Address address, List<Room> rooms, List<String> facilities,
          boolean safetyMeasuresIncluded, boolean hasFiveYearITR, boolean policeVerified) 
    {
        this.hotelId = hotelId;
        this.name = name;
        this.address = address;
        this.rooms = rooms;
        this.facilities = facilities;
        this.safetyMeasuresIncluded = safetyMeasuresIncluded;
        this.hasFiveYearITR = hasFiveYearITR;
        this.policeVerified = policeVerified;
    }
}

class Address 
{
    String city;
    String pinCode;
    String streetNo;
    String state;

    Address(String city, String pinCode, String streetNo, String state)
    {
        this.city = city;
        this.pinCode = pinCode;
        this.streetNo = streetNo;
        this.state = state;
    }
}

class Booking 
{
    int bookingId;
    int userId;
    int hotelId;
    List<Room> bookedRooms;
    int amount;
    LocalDateTime bookingTime;
    LocalDateTime from;
    LocalDateTime to;

    Booking(int bookingId, int userId, int hotelId, List<Room> bookedRooms, LocalDateTime bookingTime,
            LocalDateTime from, LocalDateTime to) 
    {
        this.bookingId = bookingId;
        this.userId = userId;
        this.hotelId = hotelId;
        this.bookedRooms = bookedRooms;
        this.bookingTime = bookingTime;
        this.from = from;
        this.to = to;
    }
}

class HotelDataBase 
{
    List<Hotel> hotels;

    HotelDataBase(List<Hotel> hotels)
    {
        this.hotels = hotels;
    }

    public void add(Hotel hotel) 
    {
        hotels.add(hotel);
    }

    public void remove(Hotel hotel) 
    {
        hotels.remove(hotel);
    }
}

class UserDataBase 
{
    List<User> users;

    UserDataBase(List<User> users) 
    {
        this.users = users;
    }

    public void register(User user) 
    {
        users.add(user);
    }

    public void remove(User user) 
    {
        users.remove(user);
    }
}

class User 
{
    int userId;
    String name;
    String email;
    String password;
    String mobileNo;
    Role role;

    User(int userId, String name, String email, String password, String mobileNo, Role role) 
    {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobileNo = mobileNo;
        this.role = role;
    }

    public boolean isRegistered(UserDataBase userDatabase) 
    {
        for (User user : userDatabase.users) 
        {
            if (userId == user.userId) 
            {
                return true;
            }
        }
        System.out.println(this.name + " is not registered");
        return false;
    }

    public void register(UserDataBase userDatabase) 
    {
        userDatabase.register(this);
        System.out.println(this.name + " is registered as " + this.role + " now.");
    }
}

class NormalUser extends User 
{
    NormalUser(int userId, String name, String email, String password, String mobileNo) 
    {
        super(userId, name, email, password, mobileNo, Role.NORMAL_USER);
    }

    public void modifySelfDetails(String email, String password, String mobileNo) 
    {
        this.email = email;
        this.password = password;
        this.mobileNo = mobileNo;
    }

    public List<Hotel> searchHotels(HotelDataBase hotelDatabase, List<String> facilities, String location) 
    {
        List<Hotel> listedHotels = new ArrayList<>();
        for (Hotel hotel : hotelDatabase.hotels) 
        {
            if (hotel.facilities.equals(facilities) && hotel.address.city.equals(location)) 
            {
                listedHotels.add(hotel);
            }
        }
        return listedHotels;
    }

    public Hotel selectHotel(List<Hotel> listedHotels) 
    {
        return listedHotels.get(0);
    }

    public Booking bookRooms(Hotel selectedHotel, RoomType roomType, int noOfRooms, int bookingId,
                             LocalDateTime from, LocalDateTime to) 
    {
        List<Room> bookedRooms = new ArrayList<>();
        int roomCount = 0;
        for (Room room : selectedHotel.rooms)
        {
            if (room.roomType == roomType && room.roomStatus == RoomStatus.AVAILABLE && roomCount < noOfRooms) 
            {
                bookedRooms.add(room);
                room.roomStatus = RoomStatus.NOT_AVAILABLE;
                roomCount++;
            }
        }
        if (bookedRooms.size() == noOfRooms) 
        {
            Booking booking = new Booking(bookingId, userId, selectedHotel.hotelId, bookedRooms,
                    LocalDateTime.now(), from, to);
            return booking;
        } 
        else 
        {
            System.out.println("Cannot book rooms");
            return null;
        }
    }
}

class HotelAgent extends User 
{
    int hotelId;
    HotelAgent(int userId, String name, String email, String password, String mobileNo, int hotelId) 
    {
        super(userId, name, email, password, mobileNo, Role.HOTEL_AGENT);
        this.hotelId = hotelId;
    }

    public void addFacility(Hotel hotel, String facility) 
    {
        hotel.facilities.add(facility);
        System.out.println("Facility added");
    }

    public void removeFacility(Hotel hotel, String facility) 
    {
        hotel.facilities.remove(facility);
    }

    public void updateRoomStatus(Hotel hotel, int roomId, RoomStatus roomStatus) 
    {
        for (Room room : hotel.rooms) 
        {
            if (room.roomId == roomId) 
            {
                room.roomStatus = roomStatus;
                System.out.println("Room Status of room " + roomId + " of " + hotel.name  + " is updated to " + roomStatus + " by " + this.name);
                return;
            }
        }
        System.out.println("Room with ID " + roomId + " not found in the hotel");
    }

    public void updateRoomType(Hotel hotel, int roomId, RoomType roomType) 
    {
        for (Room room : hotel.rooms) 
        {
            if (room.roomId == roomId) 
            {
                room.roomType = roomType;
                System.out.println("Room type of room " + roomId + " of " + hotel.name + " is updated to " + roomType + " by " + this.name);
                return;
            }
        }
        System.out.println("Room with ID " + roomId + " not found in the hotel");
    }
}

class Admin extends User 
{
    Admin(int userId, String name, String email, String password, String mobileNo) 
    {
        super(userId, name, email, password, mobileNo, Role.ADMIN);
    }

    public boolean approveHotel(Hotel hotel) {
        if (hotel.safetyMeasuresIncluded && hotel.hasFiveYearITR && hotel.policeVerified) 
        {
            return true;
        }
        return false;
    }

    public void deleteHotel(HotelDataBase hotelDatabase, Hotel hotel) 
    {
        hotelDatabase.remove(hotel);
        System.out.println(hotel.name + " is removed by admin from Hotel DB");

    }

    public void deleteUser(UserDataBase userDatabase, User user) 
    {
        userDatabase.remove(user);
        System.out.println(user.name + " is removed by admin from Users DB");
    }
}

class HelloWorld 
{
    public static void main(String[] args) 
    {
        System.out.println("Hello, World!");

        NormalUser u1 = new NormalUser(1, "Riya", "riya123@gmail.com", "riya@123", "9845678301");
        NormalUser u2 = new NormalUser(2, "Simsim", "simsim123@gmail.com", "simsim@123", "7895674893");
        Admin admin = new Admin(100, "Isha", "isha123@gmail.com", "isha@123", "9368298626");
        List<User> registeredUsers = new ArrayList<>();
        registeredUsers.add(u1);
        registeredUsers.add(u2);
        registeredUsers.add(admin);

        UserDataBase udb = new UserDataBase(registeredUsers);

        NormalUser u3 = new NormalUser(4, "Neha", "neha123@gmail.com", "simsim@123", "8956789093");

        if (!u3.isRegistered(udb)) 
        {
            u3.register(udb);
        }
        else
        {
            System.out.println(u3.name + " is already registered");
        }

        //Hotel 1 details
        Room r1 = new Room(101, 201, RoomType.DOUBLE, RoomStatus.AVAILABLE);
        Room r2 = new Room(102, 201, RoomType.SINGLE, RoomStatus.AVAILABLE);
        Room r3 = new Room(103, 201, RoomType.DOUBLE, RoomStatus.AVAILABLE);

        List<Room> roomsH1 = new ArrayList<>();
        roomsH1.add(r1);
        roomsH1.add(r2);
        roomsH1.add(r3);

        Address ah1 = new Address("Noida", "230098", "A-68", "UP");

        List<String> facH1 = new ArrayList<>();
        facH1.add("Full time Electricity");
        facH1.add("Breakfast free");
        facH1.add("AC rooms");

        Hotel h1 = new Hotel(201, "Raddison", ah1, roomsH1, facH1, true, true, true);

        //Hotel 2 details
        Room r21 = new Room(301, 202, RoomType.DOUBLE, RoomStatus.AVAILABLE);
        Room r22 = new Room(302, 202, RoomType.SINGLE, RoomStatus.AVAILABLE);
        Room r23 = new Room(303, 202, RoomType.DOUBLE, RoomStatus.AVAILABLE);

        List<Room> roomsH2 = new ArrayList<>();
        roomsH2.add(r21);
        roomsH2.add(r22);
        roomsH2.add(r23);

        Address ah2 = new Address("Mumbai", "100923", "A-909", "Maharashtra");

        List<String> facH2 = new ArrayList<>();
        facH2.add("Full time Electricity");
        facH2.add("Breakfast free");
        facH2.add("AC rooms");

        Hotel h2 = new Hotel(202, "Taj", ah2, roomsH2, facH2, true, true, true);

        List<Hotel> hotels = new ArrayList<>();
        hotels.add(h1);
        hotels.add(h2);

        HotelDataBase hdb = new HotelDataBase(hotels);

        List<Hotel> listedHotels;

        List<String> neededFacilities = new ArrayList<>();
        neededFacilities.add("Full time Electricity");
        neededFacilities.add("Breakfast free");
        neededFacilities.add("AC rooms");

        listedHotels = u1.searchHotels(hdb, neededFacilities, "Noida");
        if (!listedHotels.isEmpty()) 
        {
            Hotel selectedHotel = u1.selectHotel(listedHotels);
            LocalDateTime from = LocalDateTime.of(2023, 7, 20, 10, 30);
            LocalDateTime to = from.plusDays(2);
            Booking booking1 = u1.bookRooms(selectedHotel, RoomType.DOUBLE, 1, 1001, from, to);
            if (booking1 != null) 
            {
                System.out.println("Booking successful for " + u1.name + " ! Booking ID: " + booking1.bookingId);
            } 
            else 
            {
                System.out.println("Booking failed!");
            }
        } 
        else 
        {
            System.out.println("No rooms found");
        }

        HotelAgent ha1 = new HotelAgent(10, "Tom", "tom123@gmail.com", "tom@123", "9567009841", 201);
        if (!ha1.isRegistered(udb)) 
        {
            ha1.register(udb);
            ha1.updateRoomStatus(h1, 102, RoomStatus.NOT_AVAILABLE);
        }
        else
        {
            System.out.println(ha1.name + " is already registered");
        }

        //Displaying room status of room3 of hotel 1
        System.out.println("Room status of " + r3.roomId + " of hotel having hotelId " + r3.hotelId + " is " + r3.dispalyRoomStatus());
        
        //hotel 3 details
        Room r31 = new Room(401, 203, RoomType.DOUBLE, RoomStatus.AVAILABLE);
        Room r32 = new Room(402, 203, RoomType.SINGLE, RoomStatus.AVAILABLE);
        Room r33 = new Room(403, 203, RoomType.DOUBLE, RoomStatus.AVAILABLE);

        List<Room> roomsH3 = new ArrayList<>();
        roomsH3.add(r31);
        roomsH3.add(r32);
        roomsH3.add(r33);

        Address ah3 = new Address("Kolkata", "148001", "A-10", "WestBengal");

        List<String> facH3 = new ArrayList<>();
        facH3.add("Full time Electricity");
        facH3.add("Breakfast free");
        facH3.add("Non-AC rooms");

        Hotel h3 = new Hotel(203, "Bengal Houses", ah3, roomsH3, facH3, true, false, true);
        //h3 doesnt has previous five year ITR so should not be approved by admin
        if(!admin.approveHotel(h3))
        {
            System.out.println(h3.name + " is not approved by admin " + admin.name + " and cannot added to Hotel DB");
        }
        else
        {
            System.out.println(h3.name + " is approved by admin " + admin.name + " and added to Hotel DB");
            hdb.hotels.add(h3);
        }

        admin.deleteHotel(hdb,h2);

        admin.deleteUser(udb,u2);
        

    }
}
