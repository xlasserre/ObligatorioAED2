package tests;

import static org.junit.Assert.*;
import interfaces.ISistema;

import org.junit.Test;

import dominio.Propiedad;
import dominio.PuntoDeInteres;
import dominio.Vendedor;
import sistema.Enumerados.Rubro;
import sistema.Enumerados.TipoPropiedad;
import sistema.Retorno;
import sistema.Sistema;
import sistema.Retorno.Resultado;

public class TestsSistema {
	
	
	/********************INICIALIZAR SISTEMA**************************/
	
	@Test
	public void testInicializarSistemaNumIncorrecto(){
		ISistema sistema = new Sistema();
		Retorno ret = sistema.inicializarSistema(-10);
		assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);
		
		sistema.destruirSistema();
		ret = sistema.inicializarSistema(0);
		assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);
	}
	
	/********************REGISTRO VENDEDOR **************************/
	
	@Test
	public void testRegistroVendedor() {
		ISistema sistema = new Sistema();
		sistema.inicializarSistema(10);
		//Datos de la prueba
		String cedula = "3.702.156-9";
		String nombre = "Omar";
		String email = "omar@gmail.com";
		String celular = "098123456";
		//Estimulo
		Retorno ret = sistema.registrarVendedor(cedula, nombre, email, celular);
		assertEquals(Retorno.Resultado.OK, ret.resultado);	//Deberia retornar OK
		
	}

	/********************LISTADO VENDEDOR**************************/
	
	@Test
	public void testListadoVendedor() {
		ISistema sistema = new Sistema();
		sistema.inicializarSistema(10);
		//Datos de la prueba
		String cedula = "3.702.156-9";
		String nombre = "Omar";
		String email = "omar@gmail.com";
		String celular = "098123456";
		//Estimulo
		Retorno ret = sistema.registrarVendedor(cedula, nombre, email, celular);
		assertEquals(Retorno.Resultado.OK, ret.resultado);	//Deberia retornar OK
		ret = sistema.listadoVendedores();
		assertEquals(Retorno.Resultado.OK, ret.resultado);	//Deberia retornar OK
		// El valor string deberia contener los datos del vendedor ingresado
		assertTrue(ret.valorString.contains(cedula) && ret.valorString.contains(nombre) && ret.valorString.contains(celular));
	}

	
	
	@Test
	public void testListadoVendedorConVarios(){
		Sistema sistema = new Sistema();
		sistema.inicializarSistema(10);
		//Datos de la prueba
		//V1
		String cedula = "3.702.156-9";
		String nombre = "Omar";
		String email = "omar@gmail.com";
		String celular = "098123456";
		//V2
		String cedula1 = "4.702.156-9";
		String nombre1 = "Luis";
		String email1 = "luis@gmail.com";
		String celular1 = "099548554";
		//V3
		String cedula2 = "3.602.156-9";
		String nombre2 = "Ana";
		String email2 = "ana@gmail.com";
		String celular2 = "091455874";
		
		//Estimulo
		Retorno ret = sistema.registrarVendedor(cedula, nombre, email, celular);
		assertEquals(Retorno.Resultado.OK, ret.resultado);	//Deberia retornar OK
		Retorno ret1 = sistema.registrarVendedor(cedula1, nombre1, email1, celular1);
		assertEquals(Retorno.Resultado.OK, ret1.resultado);	//Deberia retornar OK
		Retorno ret2 = sistema.registrarVendedor(cedula2, nombre2, email2, celular2);
		assertEquals(Retorno.Resultado.OK, ret2.resultado);	//Deberia retornar OK
		
		Retorno retListado = sistema.listadoVendedores();
		assertEquals(Retorno.Resultado.OK, retListado.resultado);	//Deberia retornar OK
		// El valor string deberia contener los datos del vendedor ingresado
		assertTrue(retListado.valorString.contains(cedula) && retListado.valorString.contains(nombre) && retListado.valorString.contains(celular) &&
				retListado.valorString.contains(cedula1) && retListado.valorString.contains(nombre1) && retListado.valorString.contains(celular1) &&
				retListado.valorString.contains(cedula2) && retListado.valorString.contains(nombre2) && retListado.valorString.contains(celular2));
		
	}
	
	/********************ELIMINACION VENDEDOR**************************/
	
	@Test
	public void testEliminarVendedor(){
		Sistema sis = new Sistema();
		sis.inicializarSistema(10);
		//Datos de la prueba
		//V1
		String cedula = "3.702.156-9";
		String nombre = "Omar";
		String email = "omar@gmail.com";
		String celular = "098123456";
		//V2
		String cedula1 = "4.702.156-9";
		String nombre1 = "Luis";
		String email1 = "luis@gmail.com";
		String celular1 = "099548554";
		//V3
		String cedula2 = "3.602.156-9";
		String nombre2 = "Ana";
		String email2 = "ana@gmail.com";
		String celular2 = "091455874";
		
		//Estimulo
		Retorno ret = sis.registrarVendedor(cedula, nombre, email, celular);
		assertEquals(Retorno.Resultado.OK, ret.resultado);	//Deberia retornar OK
		Retorno ret1 = sis.registrarVendedor(cedula1, nombre1, email1, celular1);
		assertEquals(Retorno.Resultado.OK, ret1.resultado);	//Deberia retornar OK
		Retorno ret2 = sis.registrarVendedor(cedula2, nombre2, email2, celular2);
		assertEquals(Retorno.Resultado.OK, ret2.resultado);	//Deberia retornar OK
		
		assertEquals(sis.arbolDeVendedores.cantNodos(), 3);
		assertEquals(sis.queueDeVendedores.cantNodos(), 3);
		
		Retorno retEliminar = sis.eliminarVendedor("3.602.156-9");
		assertEquals(Retorno.Resultado.OK, retEliminar.resultado);
		retEliminar = sis.eliminarVendedor("5.484.245-2");
		assertEquals(Retorno.Resultado.ERROR_1, retEliminar.resultado);	
		
		assertEquals(sis.arbolDeVendedores.cantNodos(), 2);
		assertEquals(sis.queueDeVendedores.cantNodos(), 2);
		
	}

	/********************REGISTRO ESQUINA**************************/
	
	@Test
	public void testRegistrarEsquinaCorrectamente(){
		ISistema s = new Sistema();
		s.inicializarSistema(10);
		

		Retorno ret = s.registrarEsquina(34.764167,56.213889);
		assertEquals(Retorno.Resultado.OK, ret.resultado);	//Deberia retornar OK
		ret = s.registrarEsquina(-34.905, -56.190);
		assertEquals(Retorno.Resultado.OK, ret.resultado);	//Deberia retornar OK
		ret = s.registrarEsquina(34.851944,56.188333);
		assertEquals(Retorno.Resultado.OK, ret.resultado);	//Deberia retornar OK
		ret = s.registrarEsquina(-39.905, -56.190);
		assertEquals(Retorno.Resultado.OK, ret.resultado);	//Deberia retornar OK
		ret = s.registrarEsquina(32.516667, 54.533333);
		assertEquals(Retorno.Resultado.OK, ret.resultado);	//Deberia retornar OK
		
	}
	
	@Test
	public void testRegistrarEsquinaExcederMasUno(){
		ISistema s = new Sistema();
		s.inicializarSistema(3);
		
		//Crear datos
		Retorno ret = s.registrarEsquina(34.764167,56.213889);
		assertEquals(Retorno.Resultado.OK, ret.resultado);	//Deberia retornar OK
		ret = s.registrarEsquina(-34.905, -56.190);
		assertEquals(Retorno.Resultado.OK, ret.resultado);	//Deberia retornar OK
		ret = s.registrarEsquina(34.851944,56.188333);
		assertEquals(Retorno.Resultado.OK, ret.resultado);	//Deberia retornar OK
		ret = s.registrarEsquina(-39.905, -56.190);
		assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);	//Deberia retornar OK
		ret = s.registrarEsquina(32.516667, 54.533333);
		assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);	//Deberia retornar OK
	}

	@Test
	public void testRegistroEsquinaExistente(){
		ISistema s = new Sistema();
		s.inicializarSistema(4);
		
		//Crear datos
		Retorno ret = s.registrarEsquina(-33.905,-56.188);
		assertEquals(Retorno.Resultado.OK, ret.resultado);
		ret = s.registrarEsquina(-34.905, -56.190);
		assertEquals(Retorno.Resultado.OK, ret.resultado);
		ret = s.registrarEsquina(-39.905,-56.188);
		assertEquals(Retorno.Resultado.OK, ret.resultado);
		ret = s.registrarEsquina(-39.905,-56.188);
		assertEquals(Retorno.Resultado.ERROR_2, ret.resultado);	
	}
	
	/********************REGISTRO PUNTO DE INTERES**************************/
	
	@Test
	public void testRegistroPuntoDeInteresCorrectamente(){
		Sistema s = new Sistema();
		s.inicializarSistema(3);
		
		//Crear datos
		Retorno ret = s.registrarPuntoInteres(34.764167,56.213889, Rubro.CAJERO, "Abitab");
		assertEquals(Retorno.Resultado.OK, ret.resultado);
		ret = s.registrarPuntoInteres(-34.905, -56.190, Rubro.CENTRO_COMERCIAL, "Nuevo Centro");
		assertEquals(Retorno.Resultado.OK, ret.resultado);
		ret = s.registrarPuntoInteres(34.851944,34.851944, Rubro.FARMACIA, "Cruz Roja");
		assertEquals(Retorno.Resultado.OK, ret.resultado);
		
		assertEquals(true, s.tableHash.pertenece(34.764167, 56.213889));
		assertEquals(true, s.tableHash.pertenece(-34.905, -56.190));
		assertEquals(true, s.tableHash.pertenece(34.851944, 34.851944));
	}
	
	@Test
	public void testRegistrarPuntoDeInteresExcederMasUno(){
		ISistema s = new Sistema();
		s.inicializarSistema(3);
		
		//Crear datos
		Retorno ret = s.registrarPuntoInteres(34.764167,56.213889, Rubro.CAJERO, "Abitab");
		assertEquals(Retorno.Resultado.OK, ret.resultado);
		ret = s.registrarPuntoInteres(-34.905, -56.190, Rubro.CENTRO_COMERCIAL, "Nuevo Centro");
		assertEquals(Retorno.Resultado.OK, ret.resultado);
		ret = s.registrarPuntoInteres(34.851944,56.188333, Rubro.FARMACIA, "Cruz Roja");
		assertEquals(Retorno.Resultado.OK, ret.resultado);
		ret = s.registrarPuntoInteres(-39.905, -56.190, Rubro.SUPERMERCADO, "Devoto");
		assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);
	}
	
	@Test
	public void testRegistrarPuntoDeInteresErrorNombre(){
		ISistema s = new Sistema();
		s.inicializarSistema(4);
		
		//Crear datos
		Retorno ret = s.registrarPuntoInteres(34.764167,56.213889, Rubro.CAJERO, "Abitab");
		assertEquals(Retorno.Resultado.OK, ret.resultado);
		ret = s.registrarPuntoInteres(-34.905, -56.190, Rubro.CENTRO_COMERCIAL, "Nuevo Centro");
		assertEquals(Retorno.Resultado.OK, ret.resultado);
		ret = s.registrarPuntoInteres(34.851944,56.188333, Rubro.FARMACIA, "Cruz Roja");
		assertEquals(Retorno.Resultado.OK, ret.resultado);
		ret = s.registrarPuntoInteres(-39.905, -56.190, Rubro.SUPERMERCADO, "");
		assertEquals(Retorno.Resultado.ERROR_2, ret.resultado);
		
		s.destruirSistema();
		s.inicializarSistema(4);
		
		//Crear datos
		ret = s.registrarPuntoInteres(34.764167,56.213889, Rubro.CAJERO, "Abitab");
		assertEquals(Retorno.Resultado.OK, ret.resultado);
		ret = s.registrarPuntoInteres(-34.905, -56.190, Rubro.CENTRO_COMERCIAL, "Nuevo Centro");
		assertEquals(Retorno.Resultado.OK, ret.resultado);
		ret = s.registrarPuntoInteres(34.851944,56.188333, Rubro.FARMACIA, "Cruz Roja");
		assertEquals(Retorno.Resultado.OK, ret.resultado);
		ret = s.registrarPuntoInteres(-39.905, -56.190, Rubro.SUPERMERCADO, null);
		assertEquals(Retorno.Resultado.ERROR_2, ret.resultado);
	}
	
	@Test
	public void testRegistroPuntoInteresExistente(){
		ISistema s = new Sistema();
		s.inicializarSistema(3);
		
		//Crear datos
		Retorno ret = s.registrarPuntoInteres(34.764167,56.213889, Rubro.CAJERO, "Abitab");
		assertEquals(Retorno.Resultado.OK, ret.resultado);
		ret = s.registrarPuntoInteres(34.851944,56.188333, Rubro.CENTRO_COMERCIAL, "Nuevo Centro");
		assertEquals(Retorno.Resultado.OK, ret.resultado);
		ret = s.registrarPuntoInteres(34.851944,56.188333, Rubro.FARMACIA, "Cruz Roja");
		assertEquals(Retorno.Resultado.ERROR_3, ret.resultado);
	}
	
		
	@Test
	public void testRegistroPuntosTipoVariadoCorrectamente(){
		ISistema s = new Sistema();
		s.inicializarSistema(5);
		
		//Crear datos
		Retorno ret = s.registrarPuntoInteres(34.764167,56.213889, Rubro.CAJERO, "Abitab");
		assertEquals(Retorno.Resultado.OK, ret.resultado);
		ret = s.registrarEsquina(-34.905, -56.190);
		assertEquals(Retorno.Resultado.OK, ret.resultado);	
		ret = s.registrarPuntoInteres(34.851944,56.188333, Rubro.CENTRO_COMERCIAL, "Nuevo Centro");
		assertEquals(Retorno.Resultado.OK, ret.resultado);
		ret = s.registrarEsquina(-39.905,-56.188);
		assertEquals(Retorno.Resultado.OK, ret.resultado);
	}
	
	@Test
	public void testRegistroPuntoTipoVariadoExcederMasUno(){
		ISistema s = new Sistema();
		s.inicializarSistema(3);
		
		//Crear datos
		Retorno ret = s.registrarPuntoInteres(34.764167,56.213889, Rubro.CAJERO, "Abitab");
		assertEquals(Retorno.Resultado.OK, ret.resultado);
		ret = s.registrarEsquina(-34.905, -56.190);
		assertEquals(Retorno.Resultado.OK, ret.resultado);	
		ret = s.registrarPuntoInteres(34.851944,56.188333, Rubro.CENTRO_COMERCIAL, "Nuevo Centro");
		assertEquals(Retorno.Resultado.OK, ret.resultado);
		ret = s.registrarEsquina(-39.905,-56.188);
		assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);
	}
	
	@Test
	public void testRegistroPuntoTipoVariadoExistente(){
		ISistema s = new Sistema();
		s.inicializarSistema(5);
		
		//Crear datos
		Retorno ret = s.registrarPuntoInteres(34.764167,56.213889, Rubro.CAJERO, "Abitab");
		assertEquals(Retorno.Resultado.OK, ret.resultado);
		ret = s.registrarEsquina(-34.905, -56.190);
		assertEquals(Retorno.Resultado.OK, ret.resultado);	
		ret = s.registrarPuntoInteres(34.851944,56.188333, Rubro.CENTRO_COMERCIAL, "Nuevo Centro");
		assertEquals(Retorno.Resultado.OK, ret.resultado);
		ret = s.registrarEsquina(34.851944,56.188333);
		assertEquals(Retorno.Resultado.ERROR_2, ret.resultado);
	}
	
	/********************REGISTRO DE PROPIEDAD**************************/
	
	@Test
	public void testRegistroPropiedadCorrectamente(){
		Sistema sis = new Sistema();
		sis.inicializarSistema(5);
		
		//V1
		String cedula = "3.702.156-9";
		String nombre = "Omar";
		String email = "omar@gmail.com";
		String celular = "098123456";
		Vendedor v = new Vendedor(cedula, nombre, email, celular);
		//V2
		String cedula1 = "4.702.156-9";
		String nombre1 = "Luis";
		String email1 = "luis@gmail.com";
		String celular1 = "099548554";
		Vendedor v1 = new Vendedor(cedula1, nombre1, email1, celular1);
		
		Retorno ret = sis.registrarVendedor(cedula, nombre, email, celular);
		assertEquals(Retorno.Resultado.OK, ret.resultado);	//Deberia retornar OK
		Retorno ret1 = sis.registrarVendedor(cedula1, nombre1, email1, celular1);
		assertEquals(Retorno.Resultado.OK, ret1.resultado);	//Deberia retornar OK
		
		//verificamos que v1 esta al frent de la queue
		assertEquals(sis.queueDeVendedores.front(), new Vendedor("3.702.156-9"));
		
		Propiedad p = new Propiedad(34.764167, 56.213889, TipoPropiedad.APARTAMENTO, "18 de Julio 1234");
		Retorno retRegistroProp = sis.registrarPropiedad(34.764167, 56.213889, TipoPropiedad.APARTAMENTO, "18 de Julio 1234");
		assertEquals(Retorno.Resultado.OK, retRegistroProp.resultado);
		
		//verificamos que el oto vendedor est� al frente de la queue ahora
		assertEquals(sis.queueDeVendedores.front(), new Vendedor("4.702.156-9"));
		
		Propiedad p1 = new Propiedad(12.444167, 43.243289, TipoPropiedad.CASA, "Avda Italia 1234");
		Retorno retRegistroProp1 = sis.registrarPropiedad(12.444167, 43.243289, TipoPropiedad.CASA, "Avda Italia 1234");
		assertEquals(Retorno.Resultado.OK, retRegistroProp1.resultado);
		
		//verificamos que cada vendedor tenga una
		assertEquals(false, sis.arbolDeVendedores.Buscar(v).getDato().getHashPropiedades().pertenece("18 de Julio 1234"));
		assertEquals(false, sis.arbolDeVendedores.Buscar(v1).getDato().getHashPropiedades().pertenece("Avda Italia 1234"));
	}
	
	@Test
	public void testRegistroPropiedadCoordenadasExistentes(){
		Sistema sis = new Sistema();
		sis.inicializarSistema(5);
		
		//V1
		String cedula = "3.702.156-9";
		String nombre = "Omar";
		String email = "omar@gmail.com";
		String celular = "098123456";
		Vendedor v = new Vendedor(cedula, nombre, email, celular);
		//V2
		String cedula1 = "4.702.156-9";
		String nombre1 = "Luis";
		String email1 = "luis@gmail.com";
		String celular1 = "099548554";
		Vendedor v1 = new Vendedor(cedula1, nombre1, email1, celular1);
		
		Retorno ret = sis.registrarVendedor(cedula, nombre, email, celular);
		assertEquals(Retorno.Resultado.OK, ret.resultado);	//Deberia retornar OK
		Retorno ret1 = sis.registrarVendedor(cedula1, nombre1, email1, celular1);
		assertEquals(Retorno.Resultado.OK, ret1.resultado);	//Deberia retornar OK
		
		//verificamos que v1 esta al frent de la queue
		assertEquals(sis.queueDeVendedores.front(), new Vendedor("3.702.156-9"));
		
		Retorno retRegistroProp = sis.registrarPropiedad(34.764167, 56.213889, TipoPropiedad.APARTAMENTO, "18 de Julio 1234");
		assertEquals(Retorno.Resultado.OK, retRegistroProp.resultado);
		
		Retorno retRegistroProp1 = sis.registrarPropiedad(34.764167, 56.213889, TipoPropiedad.APARTAMENTO, "Avda Italia 1234");
		assertEquals(Retorno.Resultado.ERROR_4, retRegistroProp1.resultado);
	}
	
	@Test
	public void testRegistroPropiedadYaNoHayLugar(){
		Sistema sis = new Sistema();
		sis.inicializarSistema(1);
		
		//V1
		String cedula = "3.702.156-9";
		String nombre = "Omar";
		String email = "omar@gmail.com";
		String celular = "098123456";
		Vendedor v = new Vendedor(cedula, nombre, email, celular);
		//V2
		String cedula1 = "4.702.156-9";
		String nombre1 = "Luis";
		String email1 = "luis@gmail.com";
		String celular1 = "099548554";
		Vendedor v1 = new Vendedor(cedula1, nombre1, email1, celular1);
		
		Retorno ret = sis.registrarVendedor(cedula, nombre, email, celular);
		assertEquals(Retorno.Resultado.OK, ret.resultado);	//Deberia retornar OK
		Retorno ret1 = sis.registrarVendedor(cedula1, nombre1, email1, celular1);
		assertEquals(Retorno.Resultado.OK, ret1.resultado);	//Deberia retornar OK
		
		//verificamos que v1 esta al frent de la queue
		assertEquals(sis.queueDeVendedores.front(), new Vendedor("3.702.156-9"));
		
		Retorno retRegistroProp = sis.registrarPropiedad(34.764167, 56.213889, TipoPropiedad.APARTAMENTO, "18 de Julio 1234");
		assertEquals(Retorno.Resultado.OK, retRegistroProp.resultado);
		
		Retorno retRegistroProp1 = sis.registrarPropiedad(14.764167, 16.213889, TipoPropiedad.APARTAMENTO, "Avda Italia 1234");
		assertEquals(Retorno.Resultado.ERROR_1, retRegistroProp1.resultado);
	}
	
	@Test
	public void testRegistroPropiedadNoHayVendedor(){
		Sistema sis = new Sistema();
		sis.inicializarSistema(5);
		
		Retorno retRegistroProp = sis.registrarPropiedad(34.764167, 56.213889, TipoPropiedad.APARTAMENTO, "18 de Julio 1234");
		assertEquals(Retorno.Resultado.ERROR_3, retRegistroProp.resultado);
	}
	
	@Test
	public void testRegistroPropiedadDireccionExistente(){
		Sistema sis = new Sistema();
		sis.inicializarSistema(5);
		
		//V1
		String cedula = "3.702.156-9";
		String nombre = "Omar";
		String email = "omar@gmail.com";
		String celular = "098123456";
		Vendedor v = new Vendedor(cedula, nombre, email, celular);
		//V2
		String cedula1 = "4.702.156-9";
		String nombre1 = "Luis";
		String email1 = "luis@gmail.com";
		String celular1 = "099548554";
		Vendedor v1 = new Vendedor(cedula1, nombre1, email1, celular1);
		
		Retorno ret = sis.registrarVendedor(cedula, nombre, email, celular);
		assertEquals(Retorno.Resultado.OK, ret.resultado);	//Deberia retornar OK
		Retorno ret1 = sis.registrarVendedor(cedula1, nombre1, email1, celular1);
		assertEquals(Retorno.Resultado.OK, ret1.resultado);	//Deberia retornar OK
		
		//verificamos que v1 esta al frent de la queue
		assertEquals(sis.queueDeVendedores.front(), new Vendedor("3.702.156-9"));
		
		Retorno retRegistroProp = sis.registrarPropiedad(34.764167, 56.213889, TipoPropiedad.APARTAMENTO, "18 de Julio 1234");
		assertEquals(Retorno.Resultado.OK, retRegistroProp.resultado);
		
		Retorno retRegistroProp1 = sis.registrarPropiedad(32.764167, 12.213889, TipoPropiedad.APARTAMENTO, "18 de Julio 1234");
		assertEquals(Retorno.Resultado.ERROR_5, retRegistroProp1.resultado);
	}
	
	@Test
	public void testRegistroPropiedadDireccionVaciaONula(){
		Sistema sis = new Sistema();
		sis.inicializarSistema(5);
		
		//V1
		String cedula = "3.702.156-9";
		String nombre = "Omar";
		String email = "omar@gmail.com";
		String celular = "098123456";
				
		Retorno ret = sis.registrarVendedor(cedula, nombre, email, celular);
		assertEquals(Retorno.Resultado.OK, ret.resultado);	//Deberia retornar OK
				
		//verificamos que v1 esta al frent de la queue
		assertEquals(sis.queueDeVendedores.front(), new Vendedor("3.702.156-9"));
		
		Retorno retRegistroProp = sis.registrarPropiedad(34.764167, 56.213889, TipoPropiedad.APARTAMENTO, null);
		assertEquals(Retorno.Resultado.ERROR_2, retRegistroProp.resultado);
		
		Retorno retRegistroProp2 = sis.registrarPropiedad(34.764167, 56.213889, TipoPropiedad.APARTAMENTO, "");
		assertEquals(Retorno.Resultado.ERROR_2, retRegistroProp2.resultado);
		
	}
	
	/********************LISTADO DE PROPIEDAD**************************/
	
	@Test
	public void testListadoDePropiedadesOK(){
		ISistema s = new Sistema();
		s.inicializarSistema(5);
		
		//Datos de la prueba
		//vendedor
		String cedula = "3.702.156-9";
		String nombre = "Omar";
		String email = "omar@gmail.com";
		String celular = "098123456";
		
		//propiedad1
		Double coordX1 = 34.764167;
		Double coordY1 = 56.213889;
		TipoPropiedad tipo1 = TipoPropiedad.APARTAMENTO;
		String dir1 = "18 de Julio 1234";
		
		//propiedad2
		Double coordX2 = 12.444167;
		Double coordY2 = 43.243289;
		TipoPropiedad tipo2 = TipoPropiedad.CASA;
		String dir2 = "Avda Italia 1234";
				
		//Estimulo
		Retorno ret = s.registrarVendedor(cedula, nombre, email, celular);
		assertEquals(Retorno.Resultado.OK, ret.resultado);	//Deberia retornar OK
		
		Retorno retRegistroProp = s.registrarPropiedad(coordX1, coordY1, tipo1, dir1);
		assertEquals(Retorno.Resultado.OK, retRegistroProp.resultado); //Deberia retornar OK
		
		Retorno retRegistroProp1 = s.registrarPropiedad(coordX2, coordY2, tipo2, dir2);
		assertEquals(Retorno.Resultado.OK, retRegistroProp1.resultado); //Deberia retornar OK
		
		Retorno retListado = s.listadoPropiedades(cedula);
		System.out.println(retListado.valorString);
		
		assertEquals(Retorno.Resultado.OK, retListado.resultado.OK);
		assertEquals(true, retListado.valorString.contains(coordX1.toString()) && retListado.valorString.contains(coordX2.toString()) &&
			retListado.valorString.contains(coordY1.toString()) && retListado.valorString.contains(coordY2.toString()));
		
		
	}
	
	@Test
	public void testListadoDePropiedadesError1VendedorNoExiste(){
		ISistema s = new Sistema();
		s.inicializarSistema(5);
		
		//Estimulo
		Retorno ret = s.listadoPropiedades("3.354.988-2");
		assertEquals(Retorno.Resultado.ERROR_1, ret.resultado);
	}
	
	@Test
	public void testListadoDePropiedadesError2NoTienePropiedades(){
		ISistema s = new Sistema();
		s.inicializarSistema(5);
		
		//Datos de la prueba
		String cedula = "3.702.156-9";
		String nombre = "Omar";
		String email = "omar@gmail.com";
		String celular = "098123456";
		//Estimulo
		Retorno ret = s.registrarVendedor(cedula, nombre, email, celular);
		assertEquals(Retorno.Resultado.OK, ret.resultado);	//Deberia retornar OK
		
		//Estimulo
		Retorno retList = s.listadoPropiedades("3.702.156-9");
		assertEquals(Retorno.Resultado.ERROR_2, retList.resultado);
	}
}
