/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gohllibreriapgadmin;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author AluCiclesGS1
 */
public class GOHllibreriapgadmin {

    public static void main(String[] args) throws SQLException, IOException {
        int opcio = 0;
        menu();
        
//Loop del menu
        while (opcio != 9) {

            Scanner teclado = new Scanner(System.in); //Creem objecte de escáner o lectura
            System.out.println("  --Escull opció: ");
            opcio = teclado.nextInt(); //cridem metode nextInt per llegar el numero i guardar a opcio.
            System.out.println("");
            seleccio(opcio); //Executem exercici escollit.
        }

    }
// El menu en si

    private static void menu() {
        System.out.println("******MENU********"); // Aqui tenim les opcións del menu
        System.out.println("Opcio 1: Llistar els jugador i les seves partides"); //Opcio 1, registrar Usuari amb la seva contrassenya
        System.out.println("Opcio 2: Afegir un jugador"); //Opcio 2, fer login amb L'Usuari
        System.out.println("Opcio 3: Modificar un jugador");
        System.out.println("Opcio 4: Afegir una nova partida al jugadors");
        System.out.println("Opcio 5: Esborrar jugadors");
        System.out.println("9 - SORTIR");
    }
// Aqui hi ha la seleccio del menu

    private static void seleccio(int opcio) throws IOException, SQLException { // Amb un switch podem triar cada exercici
        switch (opcio) { //Aqui posem el switch amb les opcions

            case 1:
                exercici1();
                break;
            case 2:
                exercici2();
                break;
            case 3:
                exercici3();
                break;
            case 4:
                exercici4();
                break;
            case 5:
                exercici5();
                break;
            case 6:
                //  exercici6();
                break;
            case 7:
                //exercici7();
                break;
            default:
                break;

        }
    }
    // We register the PostgreSQL driver
    // Registramos el driver de PostgresSQL

    private static void exercici1() throws SQLException { // Opcio 1, la qual mostra tots els susuaris i les seves partides.
        try {
            Class.forName("org.postgresql.Driver");// Aqui declaro que utilitzarem el driver de postgres.
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al registrar el driver de PostgreSQL: " + ex);// I aqui que indiqui si ha fallat a carregar el driver.
        }

        Connection connection = null;
        // Database connect
        // Conectamos con la base de datos
        connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/clash", "postgres", "Lsg-1234");// Aqui s'estableix com s'ha de conectar a la BDD de postgres.

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM jugadors");// Aqui indico la taula de la qual ha d'extreure les dades.

        while (rs.next()) {// Aqui el codi ha de llegir i anar mostrant per pantalla els jugadors.

            System.out.println("\n");
            System.out.println("Jugador Nom: " + rs.getString("Nom") + " nivell:" + rs.getInt("nivell") + " gemes:" + rs.getInt("gemes") + " oro:" + rs.getInt("oro") + " copes:" + rs.getInt("copes"));

            Statement stmt2 = connection.createStatement();// Aqui indico la taula de la qual ha d'extreure les dades.
            ResultSet rs2 = stmt2.executeQuery("SELECT * FROM partides where idjug1=" + rs.getInt("id") + "OR idjug2=" + rs.getInt("id"));

            while (rs2.next()) {// Aqui el codi ha de llegir i anar mostrant per pantalla les partides.
                System.out.println("Resultat: " + rs2.getString("Resultat"));
                System.out.println("Jugador 1:" + mostraNomJugador(rs2.getInt("idjug1")) + " Jugador 2: " + mostraNomJugador(rs2.getInt("idjug2")));

            }
        }

    }    // We register the PostgreSQL driver
    // Registramos el driver de PostgresSQL

    private static void exercici2() throws SQLException {//Aqui el codi ha d'afegir un usuari nou amb tota la seva info a la taula d'usuaris.

        try {
            Class.forName("org.postgresql.Driver");// Aqui declaro que utilitzarem el driver de postgres.
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al registrar el driver de PostgreSQL: " + ex);// I aqui que indiqui si ha fallat a carregar el driver.
        }

        Connection connection = null;
        // Database connect
        // Conectamos con la base de datos
        connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/clash", "postgres", "Lsg-1234");// Aqui s'estableix com s'ha de conectar a la BDD de postgres.

        Scanner entrada = new Scanner(System.in);// Ara en aquesta part li demano les dades del nou jugador a l'user.

        System.out.println("entra la ID d'usuari: ");
        int id = entrada.nextInt();
        entrada.nextLine();
        System.out.println("entra el Nom d'usuari: ");
        String nom = entrada.nextLine();
        System.out.println("entra les Gemes d'usuari: ");
        int gemes = entrada.nextInt();
        entrada.nextLine();
        System.out.println("entra l' Oro d'usuari: ");
        int oro = entrada.nextInt();
        System.out.println("entra el Nivell d'usuari: ");
        int nivell = entrada.nextInt();
        System.out.println("entra les Copes d'usuari: ");
        int copes = entrada.nextInt();

        PreparedStatement updateAlum = connection.prepareStatement("INSERT INTO jugadors (id,nom,gemes,oro,nivell,copes) VALUES (?,?,?,?,?,?) ");

        updateAlum.setInt(1, id);
        updateAlum.setString(2, nom);
        updateAlum.setInt(3, gemes);
        updateAlum.setInt(4, oro);
        updateAlum.setInt(5, nivell);
        updateAlum.setInt(6, copes);
        updateAlum.executeUpdate();

    }

    private static void exercici3() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
        }

        Connection connection = null;
        // Database connect
        // Conectamos con la base de datos
        connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/clash", "postgres", "Lsg-1234");
        Scanner entrada = new Scanner(System.in);
        System.out.println("Introdueix la id del jugador que vols modificar: ");
        int idMod = entrada.nextInt();
        entrada.nextLine();
        System.out.println("entra el Nom d'usuari: ");
        String nomMod = entrada.nextLine();
        System.out.println("entra les Gemes d'usuari: ");
        int gemesMod = entrada.nextInt();
        System.out.println("entra l' Oro d'usuari: ");
        int oroMod = entrada.nextInt();
        System.out.println("entra el Nivell d'usuari: ");
        int nivellMod = entrada.nextInt();
        System.out.println("entra les Copes d'usuari: ");
        int copesMod = entrada.nextInt();

        PreparedStatement updateJugad = connection.prepareStatement("UPDATE jugadors SET Nom = ?, gemes=?, oro=?, nivell=?, copes=? WHERE id = ? ");

        updateJugad.setInt(6, idMod);
        updateJugad.setString(1, nomMod);
        updateJugad.setInt(2, gemesMod);
        updateJugad.setInt(3, oroMod);
        updateJugad.setInt(4, nivellMod);
        updateJugad.setInt(5, copesMod);
        updateJugad.executeUpdate();

        connection.close();

    }

    private static String mostraNomJugador(int id) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
        }
        Connection connection = null;
        // Database connect
        // Conectamos con la base de datos
        connection = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/clash",
                "postgres", "Lsg-1234");

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT Nom FROM jugadors where id=" + id);

        while (rs.next()) {
            return rs.getString("Nom");
        }
        return "";
    }

    private static int idNouJugador() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
        }

        Connection connection = null;
        connection = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/clash",
                "postgres", "Lsg-1234");

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT ID FROM jugadors Order by id DESC");

        while (rs.next()) {
            return rs.getInt("ID") + 1;
        }
        return 0;
    }

    private static void exercici4() throws SQLException {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
        }

        Connection connection = null;
        // Database connect
        // Conectamos con la base de datos
        connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/clash", "postgres", "Lsg-1234");

        Scanner entrada = new Scanner(System.in);

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM jugadors");

        System.out.println("Introdueix la ID de partida:");
        int id = entrada.nextInt();
        entrada.nextLine();

        System.out.println("Introdueix el primer jugador:");
        int idjug1 = entrada.nextInt();
        entrada.nextLine();

        System.out.println("Introdueix el segon jugador:");
        int idjug2 = entrada.nextInt();
        entrada.nextLine();

        System.out.println("Introdueix els resultats:");
        String resultat = entrada.next();
        entrada.nextLine();

        System.out.println("Introdueix el tipus de partida:");
        String tipus = entrada.next();
        entrada.nextLine();

        System.out.println("Introdueix el temps de la partida :");
        String temps = entrada.next();
        entrada.nextLine();


        
   PreparedStatement updateAlum = connection.prepareStatement("INSERT INTO partides (id, idjug1, idjug2, resultat, tipus, temps) VALUES (?, ?, ?, ?, ?, ?)");
    updateAlum.setInt(1, id);
    updateAlum.setInt(2, idjug1);
    updateAlum.setInt(3, idjug2);
    updateAlum.setString(4, resultat);
    updateAlum.setString(5, tipus);
    updateAlum.setString(6, temps);
    updateAlum.executeUpdate();
    }

    private static void exercici5() throws SQLException {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
        }

        Connection connection = null;
        // Database connect
        // Conectamos con la base de datos
        connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/clash", "postgres", "Lsg-1234");

        Scanner entrada = new Scanner(System.in);

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM jugadors");
  
        System.out.println("Entra la ID d'usuari que vols eliminar: ");
        int id = entrada.nextInt();
        
        PreparedStatement updateJugad = connection.prepareStatement("Delete FROM jugadors WHERE id = ? ");
        updateJugad.setInt(1, id);
        updateJugad.executeUpdate();
        connection.close();
    }
}
