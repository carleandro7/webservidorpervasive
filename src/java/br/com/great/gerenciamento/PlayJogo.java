/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.great.gerenciamento;

import java.util.ArrayList;

/**
 *
 * @author carleandro
 */
public class PlayJogo {
  
  private static ArrayList<EstadoJogador> listJogadores;
  private static ArrayList<EstadoJogo> listJogos;

    public static ArrayList<EstadoJogador> getListJogadores() {
       if(listJogadores == null)
           listJogadores = new ArrayList<EstadoJogador>();
        return listJogadores;
    }



    public static ArrayList<EstadoJogo> getListJogos() {
        if(listJogos == null)
           listJogos = new ArrayList<EstadoJogo>();
        return listJogos;
    }

    public static void setListJogadores(ArrayList<EstadoJogador> listJogadores) {
        PlayJogo.listJogadores = listJogadores;
    }

    public static void setListJogos(ArrayList<EstadoJogo> listJogos) {
        PlayJogo.listJogos = listJogos;
    }
  
  
}
