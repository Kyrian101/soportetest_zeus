package soportetest.zeus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soportetest.zeus.data.dao.IDAOJob;
import soportetest.zeus.data.dto.DTOJob;

@Service
public class SVJob implements ISVJob {

    @Autowired
    IDAOJob jobDAO;

    @Override
    public boolean exists(int jobId) {
        return jobDAO.getJob(jobId) == null? false : true;
    }
}
