package cvut.fel.sit.nss.vlak.components.Filters;

import cvut.fel.sit.nss.vlak.components.Abstract.SimpleFilter;
import cvut.fel.sit.nss.vlak.components.Pipes.ConnectionPipe;
import cvut.fel.sit.nss.vlak.components.Pipes.TrainPipe;
import cvut.fel.sit.nss.vlak.dao.TrainDao;
import cvut.fel.sit.nss.vlak.model.Connection;
import cvut.fel.sit.nss.vlak.model.Train;
import cvut.fel.sit.nss.vlak.util.Pair;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class TrainFilter extends SimpleFilter<Pair<HttpSession, List<Connection>>, Pair<HttpSession,List<Train>>> {
    private final TrainDao trainDao;
    @Autowired
    public TrainFilter(ConnectionPipe input, TrainPipe output, TrainDao trainDao) {
        super(input, output);
        this.trainDao = trainDao;
    }

    @Override
    protected Pair<HttpSession,List<Train>> transformOne(Pair<HttpSession, List<Connection>> connectionsPair) {
        List<Train> trains = new ArrayList<>();
        List<Connection> connections = connectionsPair.getRight();

        connections.forEach(connection -> {
            Train train = trainDao.findByConnection(connection);
            if (train != null) {
                trains.add(train);
            }
        });

        return new Pair<>(connectionsPair.getLeft(),trains);
    }
}

