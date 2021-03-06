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

    // Valores da temperatura e volume
    private static double volume;
    private static double temperatura;
    private static double pressao;
    //Valores de pertinencia de cada conjunto temperatura
    private static double tempBaixa;
    private static double tempMedia;
    private static double tempAlta;
    //Valores de pertinencia de cada conjunto volume
    private static double volPequeno;
    private static double volMedio;
    private static double volGrande;
    //Valores para a definição da pressão no eixo X
    private static final int presBaixaInf = 0;
    private static final int presBaixaSup = 8;
    private static final int presMediaaInf = 6;
    private static final int presMediaaSup = 10;
    private static final int presAltaInf = 8;
    private static final int presAltaSup = 12;
    
    //QUANTIDADE de intervalo a ser dividido o eixoX e o eixoY
    private static final int intervalo = 500;
    

    /*Regras de Inferência (9 regras)
	 * {temperatura, volume, pressao}
	 * 0 - baixo | 1 - medio | 2 - alta
    */
    private static int[][] rulesInferencia = new int[][]{
    	
    	    {0,0,0}, //Se (Temperatura é Baixa) e (Volume é Pequeno) Então (Pressão é Baixa)
            {1,0,0}, //Se (Temperatura é Média) e (Volume é Pequeno) Então (Pressão é Baixa)
            {2,0,1}, //Se (Temperatura é Alta) e (Volume é Pequeno) Então (Pressão é Média)
            {0,1,0}, //Se (Temperatura é Baixa) e (Volume é Médio) Então (Pressão é Baixa)
            {1,1,1}, //Se (Temperatura é Média) e (Volume é Médio) Então (Pressão é Média)
            {2,1,2}, //Se (Temperatura é Alta) e (Volume é Médio) Então (Pressão é Alta)
            {0,2,1}, //Se (Temperatura é Baixa) e (Volume é Grande) Então (Pressão é Média)
            {1,2,2}, //Se (Temperatura é Média) e (Volume é Grande) Então (Pressão é Alta)
            {2,2,2}, //Se (Temperatura é Alta) e (Volume é Grande) Então (Pressão é Alta)

    };
    
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
        
        /*Valor significa o valor de X, min e max os limites, m e n os modais*/
        
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
    private static double[][] selecaoRegras(){
		
        double regrasAtivadas[][];
        // As pertinencias ao longo do eixo Y
        double tempPertinencia = 0;
        double volPertinencia = 0;
        double presPertinencia = 0;
        //os limites superior e inferior para os valores do x, ou seja, os valores dos limites
        int pressaoMin = 0;
        int pressaoMax = 0;
        
        int temp, vol, pressao;
        int i;
        
        
        //9 regras, cada coluna armazena: [0] - pertinencia, [1] - limite inferior, [2] - limite superior
        regrasAtivadas = new double[9][3];
	
        for(i = 0; i < rulesInferencia.length; i++){
            
            System.out.println("\n=> Regra " + i);
            
            temp = rulesInferencia[i][0];
            vol = rulesInferencia[i][1];
            pressao = rulesInferencia[i][2];
        
		/*System.out.println("Temp: " + temp);	
                System.out.println("Vol: " + vol);	
                System.out.println("pressao: " + pressao);*/
                
            switch(temp){
                
                case 0:
                    tempPertinencia = tempBaixa;
                    System.out.println("Temperatura Baixa: " + tempPertinencia);
                    break;
		case 1:
                    tempPertinencia = tempMedia;
                    System.out.println("Temperatura Média: " + tempPertinencia);
                    break;
		case 2:
                    tempPertinencia = tempAlta;
                    System.out.println("Temperatura Alta: " + tempPertinencia);
                    break;
		}
			
            switch(vol){
                    
                case 0:
                    volPertinencia = volPequeno;
                    System.out.println("Volume Pequeno: " + volPertinencia);
                    break;
		case 1:
                    volPertinencia = volMedio;
                    System.out.println("Volume Médio: " + volPertinencia);
                    break;
		case 2:
                    volPertinencia = volGrande;
                    System.out.println("Volume Grande: " + volPertinencia);
                    break;
            }
            
            /*Só ativa a regra quando as condições da temperatura e volume são satisfeitos
            Neste caso pega o menor valor de pertinencia.*/	
            //Como os antecedentes de todas as regras são compostos pelo operador E, escolhe-se a menor pertinência
            if(tempPertinencia < volPertinencia){
                presPertinencia = tempPertinencia;
            }
            else{
		presPertinencia = volPertinencia;
            }
			
            switch(pressao){
                
                case 0: 
                    pressaoMin = presBaixaInf;
                    pressaoMax = presBaixaSup;
                    System.out.println("Pressão Baixa: " + presPertinencia);
                    break;
		case 1:
                    pressaoMin = presMediaaInf;
                    pressaoMax = presMediaaSup;
                    System.out.println("Pressão Média: " + presPertinencia);
                    break;
		case 2:
                    pressaoMin = presAltaInf;
                    pressaoMax = presAltaSup;
                    System.out.println("Pressão Alta: " + presPertinencia );
                    break;
		}
			
            regrasAtivadas[i][0] = presPertinencia;//pega o valor da pressão
            regrasAtivadas[i][1] = pressaoMin;//pega o limite da pressão o inferior
            regrasAtivadas[i][2] = pressaoMax;//pega o limite da pressão superior
            System.out.println("Regras["+i+"][0]=>:" + regrasAtivadas[i][0]);
            System.out.println("Regras["+i+"][1]=>lim inferior:" + regrasAtivadas[i][1]);
            System.out.println("Regras["+i+"][2]=> lim superior:" + regrasAtivadas[i][2]);
                        		
	}
		
	return regrasAtivadas;
		
    }
    
    private static double[] agregacaoRegras(double regrasAtivadas[][]){
        
        double eixoX[] = new double [intervalo];//armazena o valor do eixoX, que é a pressão
        double eixoY[] = new double [intervalo];//Vai armazenar os valores das pertinencias para cada valor do eixoX
        double inferior, superior, sum = 0;//variaveis do limite superior e inferior da pressao e a soma
        double aux, aux1, pertPressao, incremento;
        int i,j;
        
        //System.out.println("presAltaSup: " + presAltaSup);
        //System.out.println("presBaixaInf: " + presBaixaInf);
        
        incremento = (double) (presAltaSup - presBaixaInf)/intervalo;
        //System.out.println("Incremento: " + incremento);
                
        for(i = 0; i < intervalo; i++){
            
            eixoX[i] = sum;
            
            for(j = 0; j < regrasAtivadas.length; j++){
                
                inferior = regrasAtivadas[j][1];
                superior = regrasAtivadas[j][2];
                
                if(sum >= inferior && sum <= superior){
                    
                    aux = regrasAtivadas[j][0];//a variavel auxiliar recebe o valor da pressão se atender as codições
                    aux1 = aux;
                    
                    if(sum >= 4 && sum <= 8){//se a soma entra na validação do primeiro intervalo
                        //calculo a pertinencia da pressão
                        pertPressao = pertinenciaTrapezoidal(sum, inferior, superior, 4, 5);
                        
                        if(pertPressao < aux){
                            
                            aux1 = pertPressao;
                        }
                        
                    }
                    if(sum >= 6 && sum <= 10){
                    
                        pertPressao = pertinenciaTriangular(sum, inferior, superior, 8);
                        
                        if(pertPressao < aux){
                            
                            aux1 = pertPressao;
                        }
                    }
                    if(sum >= 8 && sum <= 12){//se a soma entra na validação do primeiro intervalo
                        //calculo a pertinencia da pressão
                        pertPressao = pertinenciaTrapezoidal(sum, inferior, superior, 11, 12);
                        
                        if(pertPressao < aux){
                            
                            aux1 = pertPressao;
                        }
                        
                    }
                    
                    if(aux1 > eixoY[i]){
			
                        eixoY[i] = aux1;
                    }
                }
            }
  
            sum += incremento; 
            //System.out.println("Sum: " + sum);
        }
        
        System.out.println("Eixo X" + " => " + "Eixo Y");
        for(i = 0; i < intervalo; i++){
		
            System.out.println(eixoX[i] + " => " + eixoY[i]);
        }
     
        return eixoY;
   
    }
    
    private static double desfuzzificacao(double[] eixoY){
        
        double[] eixoX = new double[intervalo];
        double incremento, sum = 0, auxN = 0, auxD = 0, resultado = 0;
        int i;
        
        incremento = (double) (presAltaSup - presBaixaInf)/intervalo;
        
        //Faz a desfuzzificacao utilizando o centroide
        //Soma todo valor de x
        for(i = 0; i < intervalo; i++){
            
            eixoX[i] = sum;
            sum += incremento;
            //System.out.println("SUM1: " + sum);
            //System.out.println("EIXOX[i]1: " + eixoX[i]);
            //System.out.println("INCREMENTO1: " + incremento);
        }
        //Depois multiplica o valor do eixoX pelo valor de pertinencia no caso eixoY.
        for(i = 0; i < intervalo; i++){
            
            auxN += eixoX[i] * eixoY[i];
            auxD += eixoY[i];
            //System.out.println("Numerador: " + auxN);
            //System.out.println("Denominador: " + auxD);        
        }
        
        resultado = auxN/auxD;
        //System.out.println("Resultado: " + resultado);
        return resultado;
    
    }
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in); 
        //System.out.print("Insira a temperatura: ");
        //temperatura = sc.nextDouble();
        System.out.println("Temperatura: " + temperatura);
        temperatura = 965;
        //System.out.print("Insira o volume: ");
        //volume = sc.nextDouble();
        System.out.println("Volume: " + volume);
        volume = 11;
        
        //Inicio
        System.out.println("\nFuzificação");
        fuzzificacao();
        
        System.out.println("\nSeleção das Regras");
        double regrasAvaliadas[][] = selecaoRegras();
        
        System.out.println("\nAgregacao das Regras");
        double[] pertinenciasAgregadas = agregacaoRegras(regrasAvaliadas);
        
        System.out.println("\nDesfuzificacao");
        pressao = desfuzzificacao(pertinenciasAgregadas);
        System.out.println("Pressão = " + pressao);
        
        

    }
}
