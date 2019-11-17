package com.example.calculator;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;
public class MainActivity extends FlutterActivity {
    //Platform channel
    private static final String CHANNEL = "flutter.native/helper";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GeneratedPluginRegistrant.registerWith(this);
        new MethodChannel(getFlutterView(), CHANNEL).setMethodCallHandler(
                new MethodChannel.MethodCallHandler() {
                    @Override
                    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
                        String tipo_operacion = "";
                        String greetings = "";
                        if (call.method.equals("respuestaCodigoNativo")) {
                            HashMap<String, String> respuesta = new HashMap<String,String>();
                            respuesta = call.arguments();
                            for (String llave: respuesta.keySet()) {
                                 tipo_operacion = llave;
                            }
                            if (tipo_operacion.equals("pila")) {
                                greetings = respuestaCodigoNativo(respuesta.get("pila"),1);
                            }
                            else if (tipo_operacion.equals("cola")) {
                                greetings = respuestaCodigoNativo(respuesta.get("cola"),2);
                            }
                            result.success(greetings);
                        }
                    }});
    }

    private String respuestaCodigoNativo(String respuesta, int tipo_operacion) {
        //return "Esta es la operacion " + respuesta;

        //System.out.println(respuesta.length());

        // Lo de BB
        String cadena = respuesta;
        System.out.println(cadena.length());
        ArrayList<String> digitos = new ArrayList<String>();
        ArrayList<String> operadores = new ArrayList<String>();
        ArrayList<String> resultado;
        DigitosOperadores digitosOperadores = new DigitosOperadores(digitos,operadores,null);
        boolean errores = digitosOperadores.sintaxis(cadena);
        if(errores){
            //Eleccion de tipo de operacion
            int opcion = tipo_operacion;
            System.out.println();
            if(opcion == 1 || opcion == 2){
                if (opcion == 2)
                    digitosOperadores.ordenarCola();
                digitosOperadores.ordenarArrays();
                digitosOperadores.invertirArrayResultado();
                resultado = digitosOperadores.getResultado();
                Operaciones operaciones = new Operaciones(resultado,digitosOperadores,null, this);
                try {
                    String fileName;
                    if(opcion == 1){
                        fileName = "ProcedimeientosPila.txt";
                    }else{
                        fileName = "ProcedimeientosCola.txt";
                    }
                    File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),fileName);
                    FileWriter writer = new FileWriter(root);
                    operaciones.hacerOperacion(writer);
                    writer.flush();
                    writer.close();
                    Toast.makeText(this, "El procedimiento fue guardado en: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return digitosOperadores.imprimirResultados();
            }else{
                return "No existe la opcion";
            }
        }
        else {
            Toast.makeText(this,digitosOperadores.error, Toast.LENGTH_SHORT).show();
        }
        return "0";
    }
}