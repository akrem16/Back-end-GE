package tn.sbbc.SpringBoot.Gestion_Employes.Services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import tn.sbbc.SpringBoot.Gestion_Employes.Documents.Adresse;
import tn.sbbc.SpringBoot.Gestion_Employes.Documents.Employe;
import tn.sbbc.SpringBoot.Gestion_Employes.Repository.EmployeRep;
import org.bson.Document;
import com.mongodb.client.MapReduceIterable;
@Service
public class EmployeServiceImp implements EmployeService {
    
    @Autowired
    private EmployeRep employeRep;
    
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Employe createEmployee(Employe employee) {
        return employeRep.save(employee);
    }

    @Override
    public List<Employe> getAllEmployees() {
        return employeRep.findAll();  // Correct usage of repository
    }

    @Override
    public Employe updateEmployee(String id, Employe employe) {
        Optional<Employe> existingEmployee = employeRep.findById(id);
        if (existingEmployee.isPresent()) {
            Employe emp = existingEmployee.get();
            
            // Update fields explicitly
            emp.setNom(employe.getNom());
            emp.setPrenom(employe.getPrenom());
            emp.setAnciennete(employe.getAnciennete());
            emp.setTel(employe.getTel());
            emp.setPrime(employe.getPrime());
            
            // Ensure nested objects are properly handled
            if (employe.getAdresse() != null) {
                if (emp.getAdresse() == null) {
                    emp.setAdresse(new Adresse());  // Initialize address if null
                }
                emp.getAdresse().setRue(employe.getAdresse().getRue());
                emp.getAdresse().setCodePostal(employe.getAdresse().getCodePostal());
                emp.getAdresse().setVille(employe.getAdresse().getVille());
            }
            
            // Save the updated employee
            return employeRep.save(emp);
        } else {
            return null; // Return null if employee not found
        }
    }
    @Override
    public String deleteEmployee(String id) {
        Optional<Employe> employee = employeRep.findById(id);
        if (employee.isPresent()) {
            employeRep.deleteById(id);
            return "Employee deleted successfully : ";  // Assurez-vous que cette réponse atteint le frontend
        } else {
            throw new RuntimeException("Employee with ID: " + id + " not found.");  // Cela doit envoyer un code d'erreur approprié (404)
        }
    }

    public Optional<Employe> getEmployeeById(String id) {
        return employeRep.findById(id);  // Récupère l'employé par son ID
    }
    
    //Methode 
    public Long calculateTotalEmployees() {
        String mapFunction = "function() { emit('total', 1); }";
        String reduceFunction = "function(key, values) { return Array.sum(values); }";
        
        // Exécution de l'opération MapReduce
        Document result = mongoTemplate.getDb().getCollection("employes")
                .mapReduce(mapFunction, reduceFunction)
                .first();

        if (result != null) {
            Number value = result.get("value", Number.class);  // Get the value as a Number
            if (value != null) {
                return value.longValue();  // Convert the value to Long
            }
        }
        return 0L;  // Si aucun résultat, retourne 0
    }
    
    
    public Map<String, Long> calculateEmployeesByCity() {
        String mapFunction = "function() { emit(this.adresse.ville, 1); }"; // Emit city as the key and 1 as the value
        String reduceFunction = "function(key, values) { return Array.sum(values); }"; // Sum the values for each city

        // Execute MapReduce
        MapReduceIterable<Document> results = mongoTemplate.getDb().getCollection("employes")
                .mapReduce(mapFunction, reduceFunction);

        Map<String, Long> employeesByCity = new HashMap<>();

        // Iterate through the results and populate the map
        for (Document result : results) {
            String city = result.getString("_id"); // Get the city name
            Number value = result.get("value", Number.class); // Get the count of employees
            if (city != null && value != null) {
                employeesByCity.put(city, value.longValue()); // Convert count to Long and add to the map
            }
        }

        return employeesByCity; // Return the map of employees by city
    }

    
  
}
