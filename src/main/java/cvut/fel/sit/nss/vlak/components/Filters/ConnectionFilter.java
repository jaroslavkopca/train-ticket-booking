package cvut.fel.sit.nss.vlak.components.Filters;

import cvut.fel.sit.nss.vlak.components.Abstract.Pipe;
import cvut.fel.sit.nss.vlak.components.Abstract.SimpleFilter;
import cvut.fel.sit.nss.vlak.components.Pipes.ConnectionPipe;
import cvut.fel.sit.nss.vlak.components.Pipes.FormPipe;
import cvut.fel.sit.nss.vlak.dao.ConnectionDao;
import cvut.fel.sit.nss.vlak.model.Connection;
import cvut.fel.sit.nss.vlak.model.Form;
import cvut.fel.sit.nss.vlak.model.Station;
import cvut.fel.sit.nss.vlak.util.Pair;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component
public class ConnectionFilter extends SimpleFilter<Pair<HttpSession,Form>, Pair<HttpSession,List<Connection>>> {
    private ConnectionDao connectionDao;
    @Autowired
    public ConnectionFilter(FormPipe input, ConnectionPipe output, ConnectionDao connectionDao) {
        super(input, output);
        this.connectionDao = connectionDao;
    }


    @Override
    protected Pair<HttpSession, List<Connection>> transformOne(Pair<HttpSession, Form> formPairData) {
        // Extract data from formData
        HttpSession session = formPairData.getLeft();
        Form formData = formPairData.getRight();

        Station originStation = formData.getOriginStation();
        Station destinationStation = formData.getDestinationStation();
        Date departureTime = formData.getDepartureTime();
        LocalDate dayOfDeparture = formData.getDayOfDeparture();

        // Define a time window for searching connections (e.g., 1 hour window)
        Date departureTimeEnd = new Date(departureTime.getTime() + 3600 * 1000); // 1 hour window

        // Use connectionDao to find connections
        List<Connection> connections = connectionDao.findByStationAndTime(originStation, destinationStation, departureTime, departureTimeEnd);

        // Iterate through connections to find arrival and departure times
        for (Connection connection : connections) {
            // Assuming you have methods in your connectionDao or service to fetch arrival and departure times
            Date arrivalTime = connectionDao.findArrivalTime(connection.getId(), destinationStation.getId(), dayOfDeparture);
            Date departureTimeFromOrigin = connectionDao.findDepartureTime(connection.getId(), originStation.getId(), dayOfDeparture);

            // Set the found times back to the connection object
            session.setAttribute("arrivalTime",arrivalTime);
            session.setAttribute("departureTime", departureTimeFromOrigin);
        }

        // Return Pair of session and connections
        return new Pair<>(session, connections);
    }


    @Override
    protected void transformBetween(Pipe<Pair<HttpSession,Form>> input, Pipe<Pair<HttpSession,List<Connection>>> output) {
        super.transformBetween(input, output);
        System.out.println("FILTERED");
    }
}
