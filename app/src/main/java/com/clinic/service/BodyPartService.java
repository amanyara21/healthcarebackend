package com.clinic.service;

import com.clinic.model.BodyPart;
import com.clinic.repository.BodyPartRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BodyPartService {
    private final BodyPartRepository bodyPartRepository;

    public BodyPartService(BodyPartRepository bodyPartRepository) {
        this.bodyPartRepository = bodyPartRepository;
    }

    public void saveBodyPart(BodyPart bodyPart) {
        bodyPartRepository.save(bodyPart);
    }
    public void saveAllBodyPart(List<BodyPart> bodyParts) {
        for (BodyPart part : bodyParts) {
            try{
                    bodyPartRepository.save(part);
            }catch(Exception e){
                System.out.println(part);
            }
        }
    }


    public List<BodyPart> getAllParts() {
        return bodyPartRepository.findAll();
    }
}
