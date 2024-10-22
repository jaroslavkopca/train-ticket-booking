package cvut.fel.sit.nss.vlak.service;

import cvut.fel.sit.nss.vlak.components.Pipes.TrainPipe;
import cvut.fel.sit.nss.vlak.dao.TrainDao;
import cvut.fel.sit.nss.vlak.model.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainService {
    private final TrainDao trainDao;
    private final TrainPipe trainPipe;
    @Autowired
    public TrainService(TrainDao trainDao, TrainPipe trainPipe) {
        this.trainDao = trainDao;
        this.trainPipe = trainPipe;
    }

    public Train findTrain(Integer trainId){
        return trainDao.find(trainId);
    }

}
