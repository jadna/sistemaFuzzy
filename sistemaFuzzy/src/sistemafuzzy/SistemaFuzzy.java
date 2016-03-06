/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemafuzzy;

import java.util.Scanner;

/**
 *
 * @author Jadna
 */
public class SistemaFuzzy {

    private static double volume;
    private static double temperatura;
    private static double tempBaixa;
    private static double tempMedia;
    private static double tempAlta;
    private static double volPequeno;
    private static double volMedio;
    private static double volGrande;
    
    private static double pertinenciaTriangular(double valor, double min, double max, double m){
    
        double pertTriangular = 0;
        
        
        if(valor <= min){
            
            pertTriangular = 0; 
        }
        else if(valor > min && valor < m){
            
            pertTriangular = (valor-min)/(m-min);
             
        }
        else if(valor > m && valor < max){
        
            pertTriangular = (max-valor)/(max-m);
        }
        else if(valor > max){
        
            pertTriangular = 0; 
        }
        
        return pertTriangular; 
       
    
    }
    
    private static double pertinenciaTrapezoidal(double valor, double min, double max, double m, double n){
        
        double pertTrapezoidal = 0;
        
        if(valor < min){
            
            pertTrapezoidal = 0;
	}
	else if(valor >= min && valor <= m){
            
            pertTrapezoidal = (valor - min)/(m - min);
	}
	else if(valor >= m && valor <= n){
            
            pertTrapezoidal = 1.0;
	}
	else if(valor >= n && valor <= max){
			 
            pertTrapezoidal = (max - valor)/(max - n);
	}
	else if(valor > max){
			 
            pertTrapezoidal = 0;
	}
		 
        return pertTrapezoidal;
    }
    
    private static void fuzzificacao(){
		
	//Fuzzificação da temperatura
        // as entradas estão de acordo com os parametros da função e a segunda opção no trapezoidal é quando estão em 1
	tempBaixa = pertinenciaTrapezoidal(temperatura, 800, 1000, 800, 900);
	System.out.println("Grau de pertinencia da temperatura ao conjunto 'temperaturaBaixa': " + tempBaixa);
	tempMedia = pertinenciaTriangular(temperatura, 900, 1100, 1000);
	System.out.println("Grau de pertinencia da temperatura ao conjunto 'temperaturaMedia': " + tempMedia);
	tempAlta = pertinenciaTrapezoidal(temperatura, 1000, 1200, 1100, 1200);
	System.out.println("Grau de pertinencia da temperatura ao conjunto 'temperaturaAlta': " + tempAlta);
		
	//Fuzzificação do volume
	volPequeno = pertinenciaTrapezoidal(volume, 2.0, 7.0, 2.0, 4.5);
	System.out.println("Grau de pertinencia do volume ao conjunto 'volumePequeno': " + volPequeno);
	volMedio = pertinenciaTriangular(volume, 4.5, 9.5, 7.0);
	System.out.println("Grau de pertinencia do volume ao conjunto 'volumeMedio': " + volMedio);
	volGrande = pertinenciaTrapezoidal(volume, 7.0, 12.0, 9.5, 12.0);
	System.out.println("Grau de pertinencia do volume ao conjunto 'volumeGrande': " + volGrande);
		
    }
    
    /*Regras de Inferência (9 regras)
	 * {temperatura, volume, pressao}
	 * 0 - baixo/pequeno | 1 - medio | 2 - alta/grande
	 */
   /* private static int[][] regrasInferencia = new int[][]{
    	
    	    {0,0,0}, //Se (Temperatura é Baixa) e (Volume é Pequeno) Então (Pressão é Baixa)
            {1,0,0}, //Se (Temperatura é Média) e (Volume é Pequeno) Então (Pressão é Baixa)
            {2,0,1}, //Se (Temperatura é Alta) e (Volume é Pequeno) Então (Pressão é Média)
            {0,1,0}, //Se (Temperatura é Baixa) e (Volume é Médio) Então (Pressão é Baixa)
            {1,1,1}, //Se (Temperatura é Média) e (Volume é Médio) Então (Pressão é Média)
            {2,1,2}, //Se (Temperatura é Alta) e (Volume é Médio) Então (Pressão é Alta)
            {0,2,1}, //Se (Temperatura é Baixa) e (Volume é Grande) Então (Pressão é Média)
            {1,2,2}, //Se (Temperatura é Média) e (Volume é Grande) Então (Pressão é Alta)
            {2,2,2}, //Se (Temperatura é Alta) e (Volume é Grande) Então (Pressão é Alta)

          };*/

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in); 
        System.out.print("Insira a temperatura:");
        temperatura = sc.nextDouble();
        System.out.print("Insira o volume:");
        volume = sc.nextDouble();
        fuzzificacao();
        

    }
}
    

