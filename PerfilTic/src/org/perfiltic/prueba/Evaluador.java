package org.perfiltic.prueba;
	
	import java.util.Stack;
	
	public class Evaluador {
		
		public static void main(String[] args) {

			String[] a= {"100 * ( 2 + 12 )","+","100 * ( 2 + 12 ) / 14" };
			
		    System.out.println(Evaluador.
		               evaluar(a));

		}
		
	    public static int evaluar(String[] arreglo) {
	    	
	    	//tomamos la primera expresion del arreglo 
	    	String expresion=arreglo[0];
	    	
	    	//convertimos la expresion en arreglo de char
	    	char[] valores = expresion.toCharArray();
	    	
	    	//pila para los numeros y sus respctivas operaciones
	       Stack<Integer> numeros = new Stack<Integer>();
	
	       //pila para los simbolos
	       Stack<Character> simbolos = new Stack<Character>();
	                             
	
	       for (int i = 0; i < valores.length; i++)
	       {
	    	   char a= valores[i];
	    	   
	    	   //si la posicion actual es un espacio en blanco , omitir
	           if (valores[i] == ' ')
	               continue;
	
	           //si la posicion es un numero entre 0 y 9 se almacena en sbuf
	           if (valores[i] >= '0' && valores[i] <= '9') {
	                
	               StringBuffer sbuf = new StringBuffer();                         
	                
	               //validar que el numero tenga mas digitos
	               while (i < valores.length &&
	            		   valores[i] >= '0' &&
	            				   valores[i] <= '9')
	                   sbuf.append(valores[i++]);
	               numeros.push(Integer.parseInt(sbuf.toString()));
	                 
	               	//restablecemos el valor de i	
	                 i--;
	           }
	
	           //si la posicion actual es una apertura 
	           //guardarla en el stack de simbolos
	           else if (valores[i] == '(')
	        	   simbolos.push(valores[i]);
	
	           //si la posicion es un cierre
	           //ejecute las operaciones aritmeticas
	           else if (valores[i] == ')')
	           {
	               while (simbolos.peek() != '(')
	            	   numeros.push(aplicarOperacion(simbolos.pop(),
	            			   numeros.pop(),
	            			   numeros.pop()));
	               simbolos.pop();
	           }
	
	           //si es un simbolo matematico
	           else if (valores[i] == '+' ||
	        		   valores[i] == '-' ||
	        	       valores[i] == '*' ||
	        	       valores[i] == '/')
	           {
	        	   
	        	   //si el ultimo elemento de la pila simbolos es un operador
	        	   //ejecute las operaciones con los ultimos valores de
	        	   //la pila que almacena los numeros;
	               while (!simbolos.empty() &&
	            		   tienePrecedencia(valores[i],
	                    		  simbolos.peek()))
	            	   numeros.push(aplicarOperacion(simbolos.pop(),
	            			   numeros.pop(),
	            			   numeros.pop()));
	
	               simbolos.push(valores[i]);
	           }
	       }
	
	       int a=0;

	       while (!simbolos.empty())
	    	   numeros.push(aplicarOperacion(simbolos.pop(),
	    			   numeros.pop(),
	    			   numeros.pop()));
	
	       a=numeros.pop();
	       return a;
	   }
	
	    //metodo que valida precedencia
	   public static boolean tienePrecedencia(
	                          char op1, char op2)
	   {
	       if (op2 == '(' || op2 == ')')
	           return false;
	       if ((op1 == '*' || op1 == '/') &&
	           (op2 == '+' || op2 == '-'))
	           return false;
	       else
	           return true;
	   }
	
	   //metodo que aplica operaciones aritmeticas
	   public static int aplicarOperacion(char op,
	                          int b, int a)
	   {
	       switch (op)
	       {
	       case '+':
	           return a + b;
	       case '-':
	           return a - b;
	       case '*':
	           return a * b;
	       case '/':
	           if (b == 0)
	               throw new
	               UnsupportedOperationException(
	                     "no se puede dividir entre 0");
	           return a / b;
	       }
	       return 0;
	    }
	
	
}
