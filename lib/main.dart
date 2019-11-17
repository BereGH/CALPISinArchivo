import 'package:flutter/material.dart';
import 'dart:async';
import 'package:flutter/services.dart';
import 'package:permission_handler/permission_handler.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  //Caracterisitcas de la aplicacion
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Calculadora',
      home: PaginaPrincipal(title: 'CALPI'),
      theme: ThemeData(
        primarySwatch: Colors.cyan,
      ),
    );
  }
}

// Define las caracteristicas del widget que se pondrá sobre la aplicación
class PaginaPrincipal extends StatefulWidget {
  PaginaPrincipal({Key key, this.title}) : super(key: key);
  final String title;
  @override
  _PaginaPrincipalState createState() => _PaginaPrincipalState();
}

// Definición de la clase State
class _PaginaPrincipalState extends State<PaginaPrincipal> {

  //Comunicacion con el codigo de java
  static const platform = const MethodChannel('flutter.native/helper');
  String _respuestaCodigoNativo = '0';

  void llamadaPila() {
    respuestaCodigoNativo("pila", myController.text);
  }

  void llamadaCola() {
    respuestaCodigoNativo("cola", myController.text);
  }

  Future<String> respuestaCodigoNativo(String tipo_operacion, String operacion) async {
    String response = "";
    Map <PermissionGroup,PermissionStatus> permissions = await PermissionHandler().requestPermissions([PermissionGroup.storage]);
    try {
      final String result = await platform.invokeMethod('respuestaCodigoNativo',{tipo_operacion : operacion});
      response = result;
    } on PlatformException catch (e) {
      response = "Falla en invocacion: '${e.message}'.";
    }
    setState(() {
      _respuestaCodigoNativo = response;
    });
  }

  // Controller que regresará el texto encontrado
  final myController = TextEditingController();

  // Método para usar dispose
  @override
  void dispose() {
    // Clean up the controller when the widget is disposed.
    myController.dispose();
    super.dispose();
  }

  // Esto es lo que se va a mostrar
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      // Aqui es donde se declaran los objetos para desplegar
      body: new Container(
          padding: const EdgeInsets.all(40.0),
          child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Spacer(flex:1),
                TextField(
                  decoration: InputDecoration(hintText: 'Aqui inserte su operacion'),
                  controller: myController,
                ),
                Spacer(flex:1),
                Row (
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                      Spacer(),
                      Container(
                        height: 100.0,
                        width: 100.0,
                        child: FittedBox(
                          child: FloatingActionButton(
                            onPressed: llamadaPila,
                            tooltip: 'Mostrar el valor',
                            child: Text('Pila'),
                          ),
                        ),
                      ),
                      Spacer(),
                      Container(
                        height: 100.0,
                        width: 100.0,
                        child: FittedBox(
                          child: FloatingActionButton(
                            onPressed: llamadaCola,
                            tooltip: 'Mostrar el valor',
                            child: Text('Cola'),
                          ),
                        ),
                      ),
                      Spacer(),
                    ]
                ),
                Spacer(flex:1),
                Text(
                    _respuestaCodigoNativo,
                    style: TextStyle(fontSize: 54)
                ),
              ]
          )
      ),
    );
  }
}