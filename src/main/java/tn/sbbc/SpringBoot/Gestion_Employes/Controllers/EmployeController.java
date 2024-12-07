package tn.sbbc.SpringBoot.Gestion_Employes.Controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.sbbc.SpringBoot.Gestion_Employes.Documents.Employe;
import tn.sbbc.SpringBoot.Gestion_Employes.Documents.ErrorResponse;
import tn.sbbc.SpringBoot.Gestion_Employes.Documents.SuccessResponse;
import tn.sbbc.SpringBoot.Gestion_Employes.Services.EmployeServiceImp;

@RestController
@CrossOrigin(origins = "http://localhost:4200")  // CORS enabled for Angular
@RequestMapping("/api/employees")
public class EmployeController {
    
	
    @Autowired
    private EmployeServiceImp employeServiceImp;
    
    @GetMapping("/getAll")
    public List<Employe> getAllEmployees() {
        return employeServiceImp.getAllEmployees();
    }
    
    @PostMapping("/addEmploye")
    public ResponseEntity<Employe> createEmployee(@RequestBody Employe employe) {
        Employe savedEmployee = employeServiceImp.createEmployee(employe);
        return ResponseEntity.ok(savedEmployee); // Retourner l'objet avec l'ID généré
    }


    
  
    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable String id) {
        try {
            String result = employeServiceImp.deleteEmployee(id);
            // Réponse avec message JSON en cas de succès
            return ResponseEntity.ok(new SuccessResponse(result));
        } catch (RuntimeException e) {
            // En cas d'erreur, renvoyer une erreur au frontend
            return ResponseEntity.status(404).body(new ErrorResponse(e.getMessage()));
        }
    }

   
    

    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<Employe> getEmployeeById(@PathVariable String id) {
        Optional<Employe> employee = employeServiceImp.getEmployeeById(id);
        return employee.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.status(404).build());  // Retourne 404 si pas trouvé
    }
    
	@PutMapping(value = "/updateEmployee/{id}")
	public Employe updateEmploye(@RequestBody Employe  employe ,@PathVariable String id) {
		return employeServiceImp.updateEmployee(id, employe);
	}
	
	
	@GetMapping("/total")
    public ResponseEntity<Long> getTotalEmployees() {
        Long totalEmployees = employeServiceImp.calculateTotalEmployees();
        return ResponseEntity.ok(totalEmployees);  // Retourner le nombre total d'employés
    }
	
	
	@GetMapping("/countByCity")
    public ResponseEntity<Map<String, Long>> getEmployeeCountByCity() {
        Map<String, Long> result = employeServiceImp.calculateEmployeesByCity();
        return ResponseEntity.ok(result);
    }
	
	


}