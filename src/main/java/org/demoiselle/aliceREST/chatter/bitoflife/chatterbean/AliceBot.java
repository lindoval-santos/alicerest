/*
Copyleft (C) 2004 H�lio Perroni Filho
xperroni@yahoo.com
ICQ: 2490863

This file is part of ChatterBean.

ChatterBean is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

ChatterBean is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with ChatterBean (look at the Documentos/ directory); if not, either write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA, or visit (http://www.gnu.org/licenses/gpl.txt).
*/

package org.demoiselle.aliceREST.chatter.bitoflife.chatterbean;

//import java.util.List;
//import static org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.text.Sentence.ASTERISK;

import java.text.Normalizer;

import org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.aiml.Category;
import org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.text.Request;
import org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.text.Response;
import org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.text.Sentence;
import org.demoiselle.aliceREST.chatter.bitoflife.chatterbean.text.Transformations;

public class AliceBot
{
  /*
  Attribute Section
  */

  /** Context information for this bot current conversation. */
  private Context context;

  /** The Graphmaster maps user requests to AIML categories. */
  private Graphmaster graphmaster;
  
  /*
  Constructor Section
  */
  
  /**
  Default constructor.
  */
  public AliceBot()
  {
  }

  /**
  Creates a new AliceBot from a Graphmaster.
  
  @param graphmaster Graphmaster object.
  */  
  public AliceBot(Graphmaster graphmaster)
  {
    setContext(new Context());
    setGraphmaster(graphmaster);
  }

  /**
  Creates a new AliceBot from a Context and a Graphmaster.
  
  @param context A Context.
  @param graphmaster A Graphmaster.
  */
  public AliceBot(Context context, Graphmaster graphmaster)
  {
    setContext(context);
    setGraphmaster(graphmaster);
  }
  
  /*
  Method Section
  */
  
  private void respond(Sentence sentence, Sentence that, Sentence topic, Response response)
  {
    if (sentence.length() > 0)
    {
      Match match = new Match(this, sentence, that, topic);
      Category category = graphmaster.match(match);
      response.append(category.process(match));
    }
  }

/**
  Responds a request.
  
  @param request A Request.
  
  @return A response to the request.
  **/
  public Response respond(Request request, String _topic, String _that)
  {
    String original = request.getOriginal();
    if (original == null || "".equals(original.trim()))
      return new Response("");
    
    String oldTopic = context.getTopic().getOriginal().trim();
    String newTopic = _topic != null?_topic.trim():"*";
    String newThat = _that != null?_that.trim():"*";
    
    boolean isNewTopic = !newTopic.equals(oldTopic);
    boolean isThat = !newThat.equals("*");
    
    if (isNewTopic)
    {
      Sentence topic = new Sentence(newTopic);
      transformations().normalization(topic);
      context.setTopic(topic);
    }
    
    if (isThat)
    {
      Sentence that = new Sentence(newThat);
      transformations().normalization(that);
      context.setThat(that);
    }
    
    Sentence that = context.getThat();
    Sentence topic = context.getTopic();
    transformations().normalization(request);
    context.appendRequest(request);

    Response response = new Response();
    for(Sentence sentence : request.getSentences())
      respond(sentence, that, topic, response);
    context.appendResponse(response);
    response.setSentences(new Sentence[]{context.getTopic(),context.getThat()});
    return response;
  }

  /**
  Responds a request.
  
  @param input a request string.
  
  @return A response to the input string.
  **/
  public Response respond(String input, String topic, String that)
  {
	String s = Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	String _that = Normalizer.normalize(that, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    Response response = respond(new Request(s), topic, _that);
    return response;
  }
  
  /*
  Accessor Section
  */
  
  public Transformations transformations()
  {
    return context.getTransformations();
  }
  
  /*
  Property Section
  */

  /**
  Returns this AliceBot's Context.
  
  @return The Context associated to this AliceBot.
  */
  public Context getContext()
  {
    return context;
  }
  
  public void setContext(Context context)
  {
    this.context = context;
  }
  
  public Graphmaster getGraphmaster()
  {
    return graphmaster;
  }
  
  public void setGraphmaster(Graphmaster graphmaster)
  {
    this.graphmaster = graphmaster;
  }
}
