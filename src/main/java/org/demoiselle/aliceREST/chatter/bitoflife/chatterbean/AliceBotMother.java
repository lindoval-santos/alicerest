/*
Copyleft (C) 2005 H�lio Perroni Filho
xperroni@yahoo.com
ICQ: 2490863

This file is part of ChatterBean.

ChatterBean is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

ChatterBean is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with ChatterBean (look at the Documents/ directory); if not, either write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA, or visit (http://www.gnu.org/licenses/gpl.txt).
*/

package org.demoiselle.aliceREST.chatter.bitoflife.chatterbean;

import org.demoiselle.aliceREST.business.AliceBotBC;
import org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.parser.AliceBotParser;
import org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.util.Searcher;

public class AliceBotMother
{
  /*
  Attribute Section
  */
  
  public static String PATH_CONTEXT = "";
  public static String PATH_SPLITTERS = "";
  public static String PATH_SUBSTITUTIONS = "";
  public static String PATH_BRAINBASE = "";
  	
  //private ByteArrayOutputStream gossip;
  
  //@Inject
 //private ResourceBundle b;
  
  //private String recurso;
  
  /*
  Event Section
  */

  public static boolean configIsLoaded(){
	  return (!"".equals(PATH_CONTEXT));
  }
  
  public void setUp(String tipoExecucao)
  {
    //gossip = new ByteArrayOutputStream();
//    if ("T".equals(tipoExecucao.toUpperCase()))
//    	recurso = "test";
//    else
//    	recurso = "messages";
  }
  
  /*
  Method Section
  */
  
  //pra atender os testes unitários
  //public ResourceBundle getBundle(){
//	  if(b == null){
//		  b = new ResourceBundle(recurso, Locale.getDefault());
//	  }
//	  return b;
//  }
  
/*  public String gossip()
  {
    return gossip.toString();
  }*/
  
  public void loadConfig(){
	  AliceBotBC bc = new AliceBotBC();
	  PATH_CONTEXT = bc.getConfigByNome("load.context").getValor();
	  PATH_SPLITTERS = bc.getConfigByNome("load.splitters").getValor();
	  PATH_SUBSTITUTIONS = bc.getConfigByNome("load.substitutions").getValor();
	  PATH_BRAINBASE = bc.getConfigByNome("load.cerebrobase").getValor();
	  bc = null;
  }

  public AliceBot newInstance() throws Exception
  {
	Searcher searcher = new Searcher();
    AliceBotParser parser = new AliceBotParser();

    //carrega os paths do banco
    if(!configIsLoaded())
	 loadConfig();

    System.out.println("Consultando...");
    AliceBot bot = parser.parse(getClass().getResourceAsStream(PATH_CONTEXT),
            getClass().getResourceAsStream(PATH_SPLITTERS),
            getClass().getResourceAsStream(PATH_SUBSTITUTIONS),
            searcher.search(PATH_BRAINBASE, ".*\\.aiml"));
    
    //Context context = bot.getContext(); 
    //context.outputStream(gossip);
    return bot;
  }
}
