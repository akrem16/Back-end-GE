package tn.sbbc.SpringBoot.Gestion_Employes.Documents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Adresse {
	
    private String rue;
    private String codePostal;
    private String ville;

}
