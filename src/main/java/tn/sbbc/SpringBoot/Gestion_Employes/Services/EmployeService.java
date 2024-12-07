package tn.sbbc.SpringBoot.Gestion_Employes.Services;

import java.util.List;

import tn.sbbc.SpringBoot.Gestion_Employes.Documents.Employe;

public interface EmployeService {
    Employe createEmployee(Employe employee);
    List<Employe> getAllEmployees();
    Employe updateEmployee(String id, Employe employe);
    String deleteEmployee(String id);




}
