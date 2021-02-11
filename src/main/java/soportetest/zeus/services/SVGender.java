package soportetest.zeus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soportetest.zeus.data.dao.IDAOGender;

@Service
public class SVGender implements ISVGender {

    @Autowired
    IDAOGender genderDAO;

    @Override
    public boolean exists(int genderId) {
        return (genderDAO.getGender(genderId) == null? false : true);
    }
}
