package com.example.calculator;


import android.content.Context;
import android.widget.Toast;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Operaciones{

	ArrayList<String> resultado;
	DigitosOperadores digitosOperadores;
	String error;
	Context contexto;

	public Operaciones(ArrayList<String> resultado, DigitosOperadores digitosOperadores, String error, Context contexto){
		this.resultado = resultado;
		this.digitosOperadores = digitosOperadores;
		this.error = error;
		this.contexto = contexto;
	}

	public void hacerOperacion(FileWriter archivo) throws IOException, Exception{
		int numero1, numero2, resultadoNumero;
		char operador;
		while(resultado.size()>2){
			archivo.append(digitosOperadores.imprimirResultados()+"\n");
			numero1 = obtenerNumeros();
			numero2 = obtenerNumeros();
			operador = obtenerOperador();
			resultadoNumero = operar(numero1,numero2,operador);
			resultado.add(Integer.toString(resultadoNumero));

		}
		archivo.append(digitosOperadores.imprimirResultados());

	}

	public int obtenerNumeros(){
		int numero;
		numero = Integer.parseInt(resultado.get(resultado.size()-1));
		resultado.remove(resultado.size()-1);
		return numero;
	}

	public char obtenerOperador(){
		char operador = resultado.get(resultado.size()-1).charAt(0);
		resultado.remove(resultado.size()-1);
		return operador;
	}

	public int operar(int digito1, int digito2, char operador) throws Exception {
		switch(operador){
			case '+':
			return digito1 + digito2;
			case '-':
			return digito1 - digito2;
			case '*':
			return digito1 * digito2;
			case '/':
				if(digito2 == 0){
					error = "Operacion invalida: Division entre 0";
					throw new Exception (error);

				}else{
					return digito1 / digito2;
				}
			case '%':
				if(digito2 == 0){
					error="Operacion invalida: Modulo entre 0";
					throw new Exception(error);
				}else{
					return digito1 % digito2;
				}
			default:
				return 0;
		}
	}
	
}