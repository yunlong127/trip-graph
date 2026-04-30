import com.travel.entity.Trip;
import com.travel.entity.User;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class TripFactory {
    public static Trip createTrip(String name, String destination, User user, Date startDate, Date endDate, String status, String description, boolean visaRequired, String transportation) {
        Trip trip = new Trip();
        trip.setName(name);
        trip.setDestination(destination);
        trip.setStartDate(startDate);
        trip.setEndDate(endDate);
        trip.setStatus(status);
        trip.setUser(user);
        trip.setDescription(description);
        trip.setVisaRequired(visaRequired);
        trip.setTransportation(transportation);
        return trip;
    }
}
