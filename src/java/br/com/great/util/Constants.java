/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.great.util;

/**
 * Constants used on GCM service communication.
 */
public final class Constants {

   /**
   * Chave da API do Google GCM
   */
   public static final String API_KEY = "AIzaSyCmu6aVxeFh6ZQ_u1J7DXg_fndmeXGP83g";
   
   public static final int JOGO_NEWJOGO = 1;
   public static final int JOGO_LISTAJGO = 2;
   public static final int JOGO_CADASTRAR = 3;
   public static final int JOGADOR_CADASTRAR = 4;
   public static final int JOGADOR_LOGIN = 5;
   
  private Constants() {
    throw new UnsupportedOperationException();
  }

}
