package tn.sbbc.SpringBoot.Gestion_Employes.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import tn.sbbc.SpringBoot.Gestion_Employes.Documents.Employe;

@Repository
public interface EmployeRep extends MongoRepository<Employe, String>{

}
