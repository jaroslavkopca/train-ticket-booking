package cvut.fel.sit.nss.vlak.service;

import cvut.fel.sit.nss.vlak.dao.ConnectionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;

@Service
public class DataSetup {
    private final ConnectionDao connectionDao;

    @Autowired
    public DataSetup(ConnectionDao connectionDao){
        this.connectionDao = connectionDao;
    };

    @Transactional
    public void dataSetup(){
        connectionDao.dataSetup();
    }
}
