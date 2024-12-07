package tn.sbbc.SpringBoot.Gestion_Employes.Documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "employes")
@TypeAlias("Employe") // Explicitly set the alias if you want to store it

public class Employe {
	
	@Id
    private String id;
    private String nom;
    private String prenom;
    private int anciennete;
    private String tel;
    private double prime;
    private Adresse adresse;


    

}
