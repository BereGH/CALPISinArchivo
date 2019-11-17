package com.example.calculator;

import java.util.ArrayList;
import java.util.Collections;

public class DigitosOperadores{

	ArrayList<String> digitos;
	ArrayList<String> operadores;
	ArrayList<String> resultado;
	String error;
	int n;

	public DigitosOperadores(ArrayList<String> digitos, ArrayList<String> operadores, String error){
		this.digitos = digitos;
		this.operadores = operadores;
		this.error = error;
	}

	public boolean sintaxis(String entrada){
		//Validar que el primer caracter sea un número
		if(validarNumero(entrada.charAt(0))){
			separarOperadoresDigitos(entrada);
			//Validar que haya un operador menos que el número de dígitos
			if(!validarTamanioOperadoresDigitos()){
				error = "Estructura incorrecta: Hay más operadores que números.";
				return false;
			}
		} else{
			error = "Sintaxis incorrecta: No puede haber un operador al principio.";
			return false;
		}
		return true;
	}

	public void separarOperadoresDigitos(String entrada){
		//Bandera que cuando encuentra un número después de un caracter cambia a false
		boolean banderaOperadores = true;
		for (int i = 0; i< entrada.length(); i++) {
			//Si es un número y no está después de operadores => agrega el número al array de números
			if(validarNumero(entrada.charAt(i)) && banderaOperadores){
				digitos.add(Character.toString(entrada.charAt(i)));
			//si es un número y está después de los operadores => Manda error
			}else if(validarNumero(entrada.charAt(i)) && !banderaOperadores){
				error="Estructura incorrecta: No se permiten números después de operadores.";
			}
			//Cuando es un operador => Lo agrega el array de operadores y pone la bandera en false 
			else if(validarOperadores(entrada.charAt(i))){
				operadores.add(Character.toString(entrada.charAt(i)));
				banderaOperadores = false;
			}else{
				error="Error de sintaxis: no existe el caracter " + entrada.charAt(i);
				System.exit(0);
			}
		}
	}

	public boolean validarOperadores(char posibleOperador){
		switch(posibleOperador){
			case '+':
				return true;
			case '-':
				return true;
			case '/':
				return true;
			case '%':
				return true;
			case '*':
				return true;
			default:
				return false;
		}
	}

	//Imprime el  arreglo de números
	public void imprimirDigitos(){
		System.out.println("Estos son números");
		for (int i = 0;i<digitos.size();i++) {
			System.out.println(digitos.get(i));
		}
	}

	//Imprime el arreglo de operadores
	public void imprimirOperadores(){
		System.out.println("Estos son operadores");
		for (int i = 0;i<operadores.size();i++) {
			System.out.println(operadores.get(i));
		}
	}

	public void invertirArrayResultado(){
		Collections.reverse(resultado);
	}

	public String imprimirResultados(){
		String resultadoOperacion = "";
		for (int i = resultado.size()-1;i>=0;i--) {
			resultadoOperacion += resultado.get(i)+ " ";
		}
		return resultadoOperacion;
	}

	public void ordenarArrays(){
		resultado = new ArrayList<String>();
		resultado.add(digitos.get(0));
		for (int i = 1;i<digitos.size(); i++) {
			resultado.add(digitos.get(i));
			resultado.add(operadores.get(i-1));
		}
	}

	public void ordenarCola(){
		Collections.reverse(digitos);	
	}

	public void tamanioArrayResultado(){
		n = digitos.size() + operadores.size();
	}

	public ArrayList<String> getResultado(){
		return resultado;
	}

	//Valida que el caracter sea un número
	public boolean validarNumero(char caracter){
		return Character.isDigit(caracter);
	}

	//Valida que el tamaño se array de operadores sea menor en 1 que el tamaño del array de digitos
	public boolean validarTamanioOperadoresDigitos(){
		//System.out.println(operadores.size());
		//System.out.println(digitos.size());
		return digitos.size()-1 == operadores.size() ? true : false;
	}
}