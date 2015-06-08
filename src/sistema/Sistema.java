package sistema;

import dominio.Esquina;
import dominio.Propiedad;
import dominio.PuntoDeInteres;
import dominio.Vendedor;
import estructuras.ArbolB;
import estructuras.GrafoMatriz;
import estructuras.Hash;
import estructuras.HashPropiedad;
import estructuras.Lista;
import estructuras.Queue;
import interfaces.ISistema;
import sistema.Enumerados.Rubro;
import sistema.Enumerados.TipoPropiedad;
import sistema.Retorno.Resultado;

public class Sistema implements ISistema {
	
	//atributos
	public Queue queueDeVendedores;
	public ArbolB arbolDeVendedores;
	public GrafoMatriz matrizMapa;
	public Hash tableHash;
	public HashPropiedad tableHashProp; //xa que necesitamos el hash este aca?

	//PRE: El valor de �cantPuntos� > 0.
	//POST: Quedan todas las estructuras inicializadas para manejar el sistema (Hash, Arboles, etc).
	public Retorno inicializarSistema (int cantPuntos) {
		Retorno ret;
		//Verificar si la cantidad de puntos ingresado es menor o igual a 0
		if(cantPuntos <= 0){
			ret = new Retorno(Resultado.ERROR_1);
			return ret;
		}
		this.queueDeVendedores = new Queue();
		this.arbolDeVendedores = new ArbolB();
		this.matrizMapa = new GrafoMatriz();
		this.matrizMapa.crearGrafo(cantPuntos);
		this.tableHash = new Hash(cantPuntos);
		this.tableHashProp = new HashPropiedad(cantPuntos);
		
		ret = new Retorno(Resultado.OK);
		return ret;
	}
	
	//PRE:  ------
	//POST: Se destruyen todas las estructuras creadas.
	public Retorno destruirSistema() {
		this.queueDeVendedores = null;
		this.arbolDeVendedores = null;
		this.matrizMapa = null;
		this.tableHash = null;
		this.tableHashProp = null;
		
		return new Retorno(Resultado.OK);
	}

	//PRE: Alguna celda del Hash = null || celda del hash = (0.0,0.0).
		//coordX y coordY no existe en el sistema.
	//POST: La esquina queda registrada en el sistema.
	@Override
	public Retorno registrarEsquina(double coordX, double coordY) {
		//Verificar si hay lugar para ingresar la esquina
		if(!matrizMapa.hayLugar()){
			System.out.println("No hay lugar");
			return new Retorno(Resultado.ERROR_1);
		}
		
		//Verificar si la las coordenadas ya existen
		if(tableHash.pertenece(coordX, coordY)){
			System.out.println("hay uno igual");
			return new Retorno(Resultado.ERROR_2);
		}
		
		//Ingresar la esquina en el Hash general y en el grafo
		Esquina e = new Esquina(coordX, coordY);
		int pos = tableHash.insertar(e);
		matrizMapa.agregarVertice(pos);
		
		return new Retorno(Resultado.OK);
	}

	//PRE: El valor de nombre != null || nombre != �� (vacio)
		//Alguna celda del Hash = null || celda del hash = (0.0,0.0).
		//coordX y coordY no existe en el sistema.
	//POST: El punto de interes queda registrado en el sistema.
	@Override
	public Retorno registrarPuntoInteres(double coordX, double coordY, Rubro rubro, String nombre) {
		//Verificar si hay lugar para ingresar la esquina
		if(!matrizMapa.hayLugar()){
			System.out.println("No hay lugar");
			return new Retorno(Resultado.ERROR_1);
		}
		
		//Verificar si el nombre ingresado es nulo o vac�o
		if(nombre == null || nombre == ""){
			return new Retorno(Resultado.ERROR_2);
		}
		
		//Verificar si la las coordenadas ya existen
		if(tableHash.pertenece(coordX, coordY)){
			System.out.println("hay uno igual");
			return new Retorno (Resultado.ERROR_3);
		}
		
		//Ingresar el Punto de Interes en el Hash general y en el grafo
		PuntoDeInteres pi = new PuntoDeInteres(coordX, coordY, rubro, nombre);
		int pos = tableHash.insertar(pi);
		matrizMapa.agregarVertice(pos);
		
		return new Retorno(Resultado.OK);
	}

	//PRE: Alguna celda del Hash = null || celda del hash = (0.0,0.0).
		//El valor de direccion != null || direccion != �� (vacio) y no exista en el sistema
		//Hay al menos un vendedor registrado
		//coordX y coordY no existe en el sistema.
	//POST: La propiedad queda registrada en el sistema.
	@Override
	public Retorno registrarPropiedad(double coordX, double coordY, TipoPropiedad tipoPropiedad, String direccion) {
		//no hay lugar
		if(!matrizMapa.hayLugar()){
			return new Retorno(Resultado.ERROR_1);
		}
		//si la direccion es null o vacia
		if(direccion == null || direccion == ""){
			return new Retorno(Resultado.ERROR_2);
		}
		//si no hay vendedores registrados
		if(this.arbolDeVendedores.esArbolVacio()){
			return new Retorno(Resultado.ERROR_3);
		}
		//si las coordenadas ya est�n siendo usadas
		if(tableHash.pertenece(coordX, coordY)){
			System.out.println("hay uno igual");
			return new Retorno (Resultado.ERROR_4);
		}
		//si la direcci�n ya existe
		if(tableHashProp.pertenece(direccion)){
			System.out.println("Error 5");
			return new Retorno(Resultado.ERROR_5);
		}
		//Se agrega la propiedad al Hash general
		Propiedad p = new Propiedad(coordX, coordY, tipoPropiedad, direccion);
		int pos = tableHash.insertar(p);
		matrizMapa.agregarVertice(pos);
		//se asigna al has de propiedades del sistema
		this.tableHashProp.insertar(p);
		
		//se le asigna al vendedor
		Vendedor vendAsignado = (Vendedor)this.queueDeVendedores.front();
		System.out.println("nombre vendedor asignado " +vendAsignado.getNombre());
		vendAsignado.getHashPropiedades().insertar(p);
		//lo saco del frente de la queue y lo mando al final
		this.queueDeVendedores.dequeue();
		this.queueDeVendedores.enqueue(vendAsignado);
		
		return new Retorno(Resultado.OK);
	}

	//PRE: coordX y coordY existen en el sistema
	//POST: El punto queda eliminado del sistema.
	@Override
	public Retorno eliminarPuntoMapa(double coordX, double coordY) {
		if(tableHash.pertenece(coordX, coordY)){
			//Elimino vertice del Grafo
			int pos = tableHash.devolverPosActual(coordX, coordY);
			matrizMapa.eliminarVertice(pos);
			
			//Elimino objeto del hash
			tableHash.eliminarPunto(pos);
			tableHash.imprimirLista();
		}
		else{
			System.out.println("no hay uno igual");
			return new Retorno(Resultado.ERROR_1);
		}
		
		return new Retorno();
	}

	//PRE: El valor de �peso� > 0
		//coordXi, coordYi, coordXf y coordYf existen en el sistema.
		//No exista un tramo con las mismas coordenadas en el sistema
	//POST:  El tramo queda registrado en el sistema
	@Override
	public Retorno registrarTramo(double coordXi, double coordYi, double coordXf, double coordYf, int peso) {
		//Verificar si el peso es menor o igual a 0
		if(peso <= 0){
			return new Retorno(Resultado.ERROR_1);
		}
		
		//Verificar si las coordenadas del tramo no existen en el sistema
		if(!tableHash.pertenece(coordXi, coordYi) || !tableHash.pertenece(coordXf, coordYf)){
			System.out.println("no existen las coordenadas en el sistema");
			return new Retorno (Resultado.ERROR_2);
		}

        int origen = tableHash.devolverPosActual(coordXi, coordYi);
        int destino = tableHash.devolverPosActual(coordXf, coordYf);
        
        //Verificar si existe el tramo en el grafo
		if(matrizMapa.existeArista(origen, destino, coordXi, coordYi, coordXf, coordYf)){
			System.out.println("ya existe el tramo en el grafo");
			return new Retorno (Resultado.ERROR_3);
		}

		//Agregar arista al grafo, como es no dirigido se ingresa 2 veces cambiando origen y destino
		matrizMapa.agregarArista(origen, destino, peso, coordXi, coordYi, coordXf, coordYf);
		matrizMapa.agregarArista(destino, origen, peso, coordXf, coordYf, coordXi, coordYi);
			
		return new Retorno(Resultado.OK);
	}

	@Override
	public Retorno eliminarTramo(double coordXi, double coordYi, double coordXf, double coordYf) {
		if(!tableHash.pertenece(coordXi, coordYi) || !tableHash.pertenece(coordXf, coordYf)){
			return new Retorno (Resultado.ERROR_1);
		}
		
		int origen = tableHash.devolverPosActual(coordXi, coordYi);
        int destino = tableHash.devolverPosActual(coordXf, coordYf);
        
		//Verificar si no existe el tramo en el grafo
		if(!matrizMapa.existeArista(origen, destino, coordXi, coordYi, coordXf, coordYf)){
			System.out.println("no existe el tramo en el grafo");
			return new Retorno (Resultado.ERROR_2);
		}
				
		//Eliminar arista del grafo
		matrizMapa.eliminarArista(origen, destino);
		matrizMapa.eliminarArista(destino, origen);

		System.out.println("Exito");
		return new Retorno(Resultado.OK);
	}

	@Override
	public Retorno registrarVendedor(String cedula, String nombre, String email, String celular) {
		if(!Utils.verificarCedula(cedula)){
			Retorno ret = new Retorno(Resultado.ERROR_1);
			return ret;
		}else if(!Utils.verificarCelular(celular)){
			Retorno ret = new Retorno(Resultado.ERROR_2);
			return ret;
		}else if(!Utils.verificarMail(email)){
			Retorno ret = new Retorno(Resultado.ERROR_3);
			return ret;
		}else if(this.arbolDeVendedores.existeElemento(new Vendedor(cedula))){
			Retorno ret = new Retorno(Resultado.ERROR_4);
			return ret;
		}else{
			//insertar vendedor en el arbol y la queue
			this.arbolDeVendedores.insertarElemento(new Vendedor(cedula, nombre, email, celular));
			this.queueDeVendedores.enqueue(new Vendedor(cedula, nombre, email, celular));
			Retorno ret = new Retorno(Resultado.OK);
			return ret;
		}
	}

	@Override
	public Retorno listadoVendedores() {
		String resultadoString = this.arbolDeVendedores.mostrarInOrder();
		return new Retorno(Resultado.OK, resultadoString);
	}

	@Override
	public Retorno eliminarVendedor(String cedula) {
		//el vendedor no est� ingresado
		if(!this.arbolDeVendedores.existeElemento(new Vendedor(cedula))){
			return new Retorno(Resultado.ERROR_1);
		}else{
			//se pudo eliminar exitosamente
			this.arbolDeVendedores.eliminar(new Vendedor(cedula));
			this.queueDeVendedores.borrarElemento(new Vendedor(cedula));
			return new Retorno(Resultado.OK);
		}
	}

	@Override
	public Retorno verMapa() {
		Utils.crearMapa(this.tableHash);
		return new Retorno(Resultado.OK);
	}

	@Override
	public Retorno puntoInteresMasCercano(String direccionPropiedad, Rubro rubroPuntoInteres) {
		//propiedad de esa direccion no existe 
		if(this.tableHashProp.pertenece(direccionPropiedad)){
			return new Retorno(Resultado.ERROR_1);
		}
		//si no hay ningun punto de interes de ese rubro
		if(!this.tableHash.existePuntoDeEseRubro(rubroPuntoInteres)){
			return new Retorno(Resultado.ERROR_2);
		}
		//si hay alg�n punto pero no es alcanzable
		Propiedad p = this.tableHashProp.devolverPropiedad(direccionPropiedad);
		int keyATableHash = 0;
		if(p != null){
			keyATableHash = this.tableHash.h(p.getCoordX(), p.getCoordY());
		}
		Lista verticesAdyascentes = this.matrizMapa.obtenerVerticesAdyacentes(keyATableHash);
		if(!verticesAdyascentes.chequearSiAlMenosUnoDeRubro(rubroPuntoInteres)){
			return new Retorno(Resultado.ERROR_3);
		}
		//TODO mostrar el pto de interes m�s cercano
		return new Retorno();
	}

	@Override
	public Retorno caminoMinimo(String direccionPropiedad, Double coordX, Double coordY) {
		//esa direccion no esta registrada en el sistema
		if(!this.tableHashProp.pertenece(direccionPropiedad)){
			return new Retorno(Resultado.ERROR_1);
		}
		//ambas coordenadas no estan registradas
		if(!this.tableHash.pertenece(coordX, coordY)){
			return new Retorno(Resultado.ERROR_2);
		}
		//no hay camino posible entre prop y pto de interes
		Propiedad p = this.tableHashProp.devolverPropiedad(direccionPropiedad);
		int keyDePropEnTableHash = 0;
		if(p != null){
			keyDePropEnTableHash = this.tableHash.h(p.getCoordX(), p.getCoordY());
		}
		int keyDePtoInteresEnTableHash = this.tableHash.h(coordX, coordY);
		if(this.matrizMapa.sonAdyacentes(keyDePropEnTableHash, keyDePtoInteresEnTableHash)){
			return new Retorno(Resultado.ERROR_3);
		}
		//TODO mostrar el camino m�nimo a pto de interes
		BreadthFirstSearch bfs = new BreadthFirstSearch(this.matrizMapa, keyDePropEnTableHash,this.tableHash);
		return new Retorno();
	}

	@Override
	public Retorno listadoPropiedades(String cedulaVendedor) {
		//si el vendedor no existe
		if(!this.arbolDeVendedores.existeElemento(new Vendedor(cedulaVendedor))){
			return new Retorno(Resultado.ERROR_1);
		}
		//si no tiene propiedades
		Vendedor v = this.arbolDeVendedores.Buscar(new Vendedor(cedulaVendedor)).getDato();
		if(v.getHashPropiedades().esVacio()){
			return new Retorno(Resultado.ERROR_2);
		}
		//si no listar propiedades
		System.out.println("es vacio " + v.getCedula() + v.getHashPropiedades().esVacio());
		String retorno = "";
		retorno = v.listarPropiedadesDelVendedor();
		System.out.println();
		return new Retorno(Resultado.OK, retorno);
	}

	

	
}
