package com.akerke.salonservice.generator;

import com.akerke.salonservice.domain.entity.Address;
import com.akerke.salonservice.infrastructure.elastic.SalonWrapper;

import java.util.ArrayList;
import java.util.List;

public class SalonGenerator {
    public static List<SalonWrapper> salonWrappers() {
        List<SalonWrapper> salonList = new ArrayList<>();
        salonList.add(createSalon(1, "Salon A", "123-456-7890", "salonA@example.com", "123 Main St, City A, State X", "High-end salon offering a variety of services", "Hair Styling, Manicure, Pedicure"));
        salonList.add(createSalon(2, "Salon B", "987-654-3210", "salonB@example.com", "456 Elm St, City B, State Y", "Elegant salon specializing in hair and makeup", "Hair Coloring, Facials, Waxing"));
        salonList.add(createSalon(3, "Salon C", "555-123-4567", "salonC@example.com", "789 Oak St, City C, State Z", "Relaxing spa and salon for all your beauty needs", "Massage Therapy, Nails, Skin Care"));
        salonList.add(createSalon(4, "Salon D", "777-888-9999", "salonD@example.com", "101 Pine St, City D, State W", "Modern salon with a focus on customer satisfaction", "Haircuts, Tanning, Body Treatments"));
        salonList.add(createSalon(5, "Salon E", "888-555-1234", "salonE@example.com", "234 Cedar St, City E, State V", "Luxurious spa offering top-notch beauty services", "Hairstyling, Makeup, Manicure, Pedicure"));
        salonList.add(createSalon(6, "Salon F", "555-987-6543", "salonF@example.com", "321 Birch St, City F, State U", "Chic salon for all your beauty needs", "Hair Styling, Facials, Waxing"));
        salonList.add(createSalon(7, "Salon G", "111-222-3333", "salonG@example.com", "654 Maple St, City G, State T", "Urban spa with a focus on relaxation", "Massage, Nails, Skin Care"));
        salonList.add(createSalon(8, "Salon H", "999-777-5555", "salonH@example.com", "789 Pine St, City H, State S", "Trendy salon for the modern individual", "Hair Coloring, Tanning, Body Treatments"));
        salonList.add(createSalon(9, "Salon I", "444-555-6666", "salonI@example.com", "987 Cedar St, City I, State R", "Spa and wellness center for all ages", "Hairstyling, Makeup, Manicure"));
        salonList.add(createSalon(10, "Salon J", "777-555-2222", "salonJ@example.com", "432 Oak St, City J, State Q", "Luxury salon and spa", "Facials, Waxing, Massage"));
        salonList.add(createSalon(11, "Salon K", "123-456-7890", "salonK@example.com", "555 Birch St, City K, State P", "Hair salon with a unique touch", "Hair Styling, Nails, Skin Care"));
        salonList.add(createSalon(12, "Salon L", "888-999-2222", "salonL@example.com", "123 Elm St, City L, State O", "Salon for beauty and relaxation", "Makeup, Manicure, Pedicure"));
        salonList.add(createSalon(13, "Salon M", "555-555-5555", "salonM@example.com", "987 Oak St, City M, State N", "Your destination for beauty", "Hair Coloring, Facials, Waxing"));
        salonList.add(createSalon(14, "Salon N", "777-777-7777", "salonN@example.com", "654 Cedar St, City N, State M", "Eco-friendly salon and spa", "Massage, Tanning, Haircuts"));
        salonList.add(createSalon(15, "Salon O", "333-444-5555", "salonO@example.com", "234 Pine St, City O, State L", "Modern beauty center", "Nails, Skin Care, Hairstyling"));
        salonList.add(createSalon(16, "Salon P", "111-111-1111", "salonP@example.com", "432 Main St, City P, State K", "Your one-stop beauty solution", "Makeup, Manicure, Pedicure"));
        salonList.add(createSalon(17, "Salon Q", "666-777-8888", "salonQ@example.com", "123 Birch St, City Q, State J", "Chic and trendy salon", "Facials, Waxing, Hair Styling"));
        salonList.add(createSalon(18, "Salon R", "555-555-5555", "salonR@example.com", "321 Elm St, City R, State I", "Beauty and wellness at its best", "Tanning, Body Treatments, Massage"));
        salonList.add(createSalon(19, "Salon S", "999-999-9999", "salonS@example.com", "789 Oak St, City S, State H", "Experience the difference in beauty", "Nails, Skin Care, Hair Coloring"));
        salonList.add(createSalon(20, "Salon T", "888-888-8888", "salonT@example.com", "555 Pine St, City T, State G", "Your source for beauty inspiration", "Manicure, Pedicure, Makeup"));
        salonList.add(createSalon(21, "Salon U", "444-444-4444", "salonU@example.com", "456 Main St, City U, State F", "Your journey to beauty and relaxation", "Facials, Waxing, Hairstyling"));
        salonList.add(createSalon(22, "Salon V", "777-777-7777", "salonV@example.com", "654 Cedar St, City V, State E", "The ultimate spa experience", "Tanning, Body Treatments, Nails"));
        salonList.add(createSalon(23, "Salon W", "111-111-1111", "salonW@example.com", "123 Birch St, City W, State D", "Elevate your beauty game", "Skin Care, Hair Coloring, Massage"));
        salonList.add(createSalon(24, "Salon X", "555-555-5555", "salonX@example.com", "789 Elm St, City X, State C", "Your beauty oasis", "Hair Styling, Makeup, Manicure"));
        salonList.add(createSalon(25, "Salon Y", "888-888-8888", "salonY@example.com", "234 Oak St, City Y, State B", "Where beauty meets perfection", "Pedicure, Haircuts, Facials"));

        return salonList;
    }

    private static Long ADDRESS_CURRENT_ID = 1L;

    private static SalonWrapper createSalon(long id, String name, String phone, String email, String address, String description, String treatments) {
        SalonWrapper salon = new SalonWrapper();
        salon.setId(id);
        salon.setName(name);
        salon.setPhone(phone);
        salon.setEmail(email);
        salon.setAddress(convertAddressStringToEntity(address));
        salon.setDescription(description);

        String[] treatmentArray = treatments.split(", ");
        salon.setTreatments(List.of(treatmentArray));

        return salon;
    }

    public static Address convertAddressStringToEntity(String addressString) {
        String[] addressParts = addressString.split(", ");

        String street = addressParts[0];
        String city = addressParts[1];
        String state = addressParts[2];

        Address address = new Address();
        address.setStreet(street);
        address.setCity(city);
        address.setId(ADDRESS_CURRENT_ID++);
        address.setState(state);

        return address;
    }

    public static void main(String[] args) {
        salonWrappers().forEach(System.out::println);
    }
}
