package cvut.fel.sit.nss.vlak.dao;

import cvut.fel.sit.nss.vlak.model.Connection;
import cvut.fel.sit.nss.vlak.model.Train;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class TrainDao extends BaseDao<Train> {
    public TrainDao() {
        super(Train.class);
    }

    @Transactional
    public Train findByConnection(Connection connection) {
        String jpql = "SELECT t FROM Train t WHERE t.connection = :connection";
        TypedQuery<Train> query = em.createQuery(jpql, Train.class);
        query.setParameter("connection", connection);
        return query.getSingleResult();
    }

    // Add specific methods for Train if needed
}
